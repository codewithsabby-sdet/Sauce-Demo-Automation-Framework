package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutStepPageTwo {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(CheckoutStepPageTwo.class);

    // Locators
    private By itemNames = By.className("inventory_item_name");
    private By itemPrices = By.className("inventory_item_price");
    private By summarySubtotal = By.className("summary_subtotal_label");
    private By finishButton = By.xpath("//button[@name='finish']");
    private By cancelButton = By.id("cancel");

    public CheckoutStepPageTwo(WebDriver driver) {
        this.driver = driver;
    }

    // Get item names
    public List<WebElement> getItemNames() {
        logger.info("Fetching all item names in checkout overview");
        return driver.findElements(itemNames);
    }

    // Get item prices
    public List<WebElement> getItemPrices() {
        logger.info("Fetching all item prices in checkout overview");
        return driver.findElements(itemPrices);
    }

    // Get displayed subtotal label text: "Item total: $XX.XXX"
    public String getDisplayedSubtotalText() {
        return driver.findElement(summarySubtotal).getText();
    }

    // Extract numeric subtotal value
    public double getDisplayedSubtotalAmount() {
        String text = getDisplayedSubtotalText().replace("Item total: $", "");
        return Double.parseDouble(text);
    }

    // Click Finish button
    public void clickFinish() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for the Finish button to be clickable, then click
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();

        logger.info("Clicked Finish button successfully");
    }


    // Click Cancel button
    public void clickCancel() {
        driver.findElement(cancelButton).click();
        logger.info("Clicked on Cancel button");
    }

    // Calculate subtotal from item prices
    public double calculateExpectedSubtotal() {
        List<WebElement> priceElements = getItemPrices();
        double sum = 0.0;

        for (WebElement price : priceElements) {
            sum += Double.parseDouble(price.getText().replace("$", ""));
        }

        logger.info("Calculated expected subtotal: {}", sum);
        return sum;
    }

    // Verify page contains expected number of items
    public int getItemCount() {
        return getItemNames().size();
    }
}
