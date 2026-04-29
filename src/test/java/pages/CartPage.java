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

public class CartPage {

    private WebDriver driver;
    private final Logger logger = LogManager.getLogger(CartPage.class);

    // Locators
    private By cartItems = By.className("cart_item");
    private By itemName = By.className("inventory_item_name");
    private By itemPrice = By.className("inventory_item_price");
    private By removeButtons = By.xpath("//button[text()='Remove']");
    private By continueShoppingBtn = By.id("continue-shopping");
    private By checkoutBtn = By.xpath("//button[@id='checkout']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Get total items displayed in cart
    public int getCartItemsCount() {
        return driver.findElements(cartItems).size();
    }

    // Get all item names in cart
    public List<WebElement> getCartItemNames() {
        return driver.findElements(itemName);
    }

    // Get all item prices in cart
    public List<WebElement> getCartItemPrices() {
        return driver.findElements(itemPrice);
    }

    // Remove item by index
    public void removeItem(int index) {
        List<WebElement> removeBtns = driver.findElements(removeButtons);
        if (index < removeBtns.size()) {
            removeBtns.get(index).click();
            logger.info("Removed item at index {}", index);
        } else {
            logger.error("Invalid index for remove: {}", index);
        }
    }

    // Click Continue Shopping → Products Page
    public void clickContinueShopping() {
        driver.findElement(continueShoppingBtn).click();
        logger.info("Clicked Continue Shopping");
    }

    // Click Checkout → Checkout Step One Page
    public void clickCheckout() {
        driver.findElement(checkoutBtn).click();
        logger.info("Clicked Checkout button");
    }
}
