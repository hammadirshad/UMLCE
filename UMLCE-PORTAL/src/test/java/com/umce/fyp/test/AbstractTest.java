package com.umce.fyp.test;

import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public abstract class AbstractTest {

	@Autowired
	private URI baseURL;

	@Autowired
	private WebDriver driver;

	public URI getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(URI baseURL) {
		this.baseURL = baseURL;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	@Before
	public void tearUp() {
		System.out.println(baseURL);
		getDriver().manage().deleteAllCookies();
		getDriver().get(baseURL.toString());
	}

	@Test
	public void test() {
		System.out.println(baseURL);
	}

	@After
	public void tearDown() {
		driver.close();
	}

}