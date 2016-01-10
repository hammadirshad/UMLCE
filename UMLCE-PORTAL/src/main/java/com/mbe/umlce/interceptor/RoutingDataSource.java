package com.mbe.umlce.interceptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {
	private static final Logger logger = Logger
			.getLogger(RoutingDataSource.class);

	@Override
	protected Object determineCurrentLookupKey() {
		setProperties("hsql");
		return "hsql";
	}

	public void setProperties(String db) {

		URL url = getClass().getClassLoader().getResource("META-INF/dataSource.properties");
		
		String propFileName = url.getPath();
		logger.info(propFileName);
		Properties prop = new Properties();
		InputStream input;
		OutputStream output = null;
		try {
			output = new FileOutputStream(propFileName);
			input = new FileInputStream(propFileName);
			prop.load(input);
			try {
				input.close();
				if (db.equals("hsql")) {
					prop.setProperty("dialect",
							"org.hibernate.dialect.HSQLDialect");
				} else if (db.equals("oracle")) {
					prop.setProperty("dialect",
							"org.hibernate.dialect.Oracle10gDialect");
				}
				prop.store(output, null);
				logger.info("Properties Updated");

			} catch (IOException e) {
				logger.error("File Not close", e);
			}
		} catch (FileNotFoundException e) {
			logger.error("File Not Found", e);
		} catch (IOException e) {
			logger.error("File Not Found", e);
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				logger.error("File Not close", e);
			}
		}

	}

}
