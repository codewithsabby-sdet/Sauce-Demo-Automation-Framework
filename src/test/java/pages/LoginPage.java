package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class LoginPage {
    private static WebDriver driver;

    private  By username = By.xpath("//input[@id='user-name']");
    private  By password = By.xpath("//input[@id='password']");
    private  By loginBtn = By.xpath("//input[@id='login-button']");
    private  By errorMsg = By.xpath("//h3[@data-test = 'error']");

    //Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String usernameText) {
        driver.findElement(username).sendKeys(usernameText);
    }

    public void enterPassword(String passwordText) {
        driver.findElement(password).sendKeys(passwordText);
    }

    public void clickLoginButton()  {
        driver.findElement(loginBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }

    public void loginAsValidUser() {
        enterUsername(ConfigReader.getProperty("username"));
        enterPassword(ConfigReader.getProperty("password"));
        clickLoginButton();
    }
}