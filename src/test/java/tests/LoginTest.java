package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;


public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

//    @BeforeMethod
//    public void setUpPage() {
//        loginPage = new LoginPage(driver);
//        validLoginTest();
//        logger.info("Login successful");
//    }

    @Test(priority = 1)
    public void validLoginTest() {
        logger.info("Starting valid login test");
        loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.getProperty("username"));
        loginPage.enterPassword(ConfigReader.getProperty("password"));
        loginPage.clickLoginButton();
        logger.info("Clicked on Login button");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                "Login failed: User not redirected to inventory page.");
        logger.info("Valid login successful - redirected to inventory page");
    }

    @Test(priority = 2)
    public void invalidLoginTest() {
        logger.info("Starting invalid login test");
        loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.getProperty("invalid-username"));
        loginPage.enterPassword(ConfigReader.getProperty("invalid-password"));
        loginPage.clickLoginButton();

        String errorMsg = loginPage.getErrorMessage();
        Assert.assertEquals(errorMsg,
                "Epic sadface: Username and password do not match any user in this service",
                "Error message mismatch for invalid credentials.");
        logger.warn("Invalid login attempt - Error message displayed: {}", errorMsg);
    }

    @Test(priority = 4)
    public void emptyCredentialsTest() {
        logger.info("Starting empty credentials test");
        loginPage = new LoginPage(driver);
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");
        logger.warn("Empty credentials - Error message displayed correctly");
    }

    @Test(priority = 3)
    public void lockedOutUserTest() {
        logger.info("Starting locked out user test");
        loginPage = new LoginPage(driver);
        loginPage.enterUsername (ConfigReader.getProperty("locked-out-Username"));
        loginPage.enterPassword(ConfigReader.getProperty("password"));
        loginPage.clickLoginButton();

        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out.");
        logger.warn("Locked-out user test - Error message verified successfully");
    }
}
