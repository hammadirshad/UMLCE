package com.umce.fyp.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;

public class LoginTest extends AbstractTest {

    @Test
    public void startTest() {
        getDriver().findElement(By.id("login")).click();
        getDriver().findElement(By.id("username")).sendKeys("jd");
        getDriver().findElement(By.id("password")).sendKeys("secret");
        getDriver().findElement(By.id("loginButton")).click();
        getDriver().findElement(By.id("account")).click();
        assertEquals("John", getDriver().findElement(By.id("firstName")).getAttribute("value"));
    }

}
