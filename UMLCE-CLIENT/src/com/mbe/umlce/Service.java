package com.mbe.umlce;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Service {
	private static String URL = null;
	private static Service service = null;

	public static void setURL(String url) {
		if (service == null) {
			service = new Service();
		}
		service.setUrl(url);
	}

	private void setUrl(String url) {
		Properties prop = new Properties();
		String classPath = getClass().getClassLoader().getResource("").getPath();
		String propFileName = "connection.properties";
		String dirPath = classPath + "/META-INF";
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filePath = dir + "/" + propFileName;
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		if (!file.exists()) {
			prop.setProperty("URL", url);
			OutputStream output = null;
			try {
				output = new FileOutputStream(filePath);
				prop.store(output, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (file.exists()) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(filePath);
				prop.load(inputStream);
				URL = prop.getProperty("URL").toString();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Service() {
		Properties prop = new Properties();
		String classPath = getClass().getClassLoader().getResource("").getPath();
		String propFileName = "connection.properties";
		String dirPath = classPath + "/META-INF";
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filePath = dir + "/" + propFileName;
		File file = new File(filePath);
		if (!file.exists()) {
			prop.setProperty("URL", "http://localhost:8008/UMLCE");
			OutputStream output = null;
			try {
				output = new FileOutputStream(filePath);
				prop.store(output, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (file.exists()) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(filePath);
				prop.load(inputStream);
				URL = prop.getProperty("URL").toString();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getURL() {
		if (URL == null) {
			service = new Service();
		}
		return URL;
	}

}
