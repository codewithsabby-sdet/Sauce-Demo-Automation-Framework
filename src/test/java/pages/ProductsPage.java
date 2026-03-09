package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsPage {

    private  WebDriver driver;
    private  final Logger logger = LogManager.getLogger(ProductsPage.class);

    // Locators
    private By productList = By.className("inventory_item");
    private By productName = By.className("inventory_item_name");
    private By productPrice = By.className("inventory_item_price");
    private By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private By cartBadge = By.className("shopping_cart_badge");
    private By sortDropdown = By.className("product_sort_container");
    private By firstProduct = By.xpath("(//button[contains(text(),'Add to cart')])[1]");
    private By productDetailTitle = By.xpath("(//div[@class='inventory_item_name '])[1]");
    private By cartIcon = By.className("shopping_cart_link");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Get total products count
    public int getProductCount() {
        return driver.findElements(productList).size();
    }

    // Get first product name
    public String getFirstProductName() {
        List<WebElement> products = driver.findElements(productName);
        if (!products.isEmpty()) {
            logger.info("First product name retrieved");
            return products.get(0).getText();
        } else {
            logger.error("Could not find the first product name");
            return null;
        }
    }

    // Click on first product
    public void clickFirstProduct() {
        List<WebElement> products = driver.findElements(productList);
        if (!products.isEmpty()) {
            products.get(0).click();
            logger.info("Clicked the first product");
        } else {
            logger.error("No products found to click");
        }
    }

    // Get product detail page title
    public String getProductDetailTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String title = wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailTitle)).getText();
        logger.info("Product detail title: {}", title);
        return title;
    }

    // Sort products by option
    public void selectSortOption(String optionText) {
        driver.findElement(sortDropdown).click();
        driver.findElement(By.xpath("//option[text()='" + optionText + "']")).click();
        logger.info("Selected sort option: {}", optionText);
    }

    // Get all product prices
    public List<Double> getAllProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrice);
        List<Double> prices = new ArrayList<>();
        for (WebElement price : priceElements) {
            prices.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        logger.info("Collected all product prices: {}", prices);
        return prices;
    }

    // Add first N products to cart
    public void addFirstNProductsToCart(int n) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (int i = 0; i < n && i < buttons.size(); i++) {
            buttons.get(i).click();

            // Wait for cart count to update
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.textToBePresentInElementLocated(cartBadge, String.valueOf(i + 1)));
        }
        logger.info("{} products added to cart", n);
    }

    // Get current cart count
    public int getCartCount() {
        try {
            return Integer.parseInt(driver.findElement(cartBadge).getText());
        } catch (Exception e) {
            return 0;
        }
    }

    // Open cart page
    public void openCart() {
        driver.findElement(cartIcon).click();
        logger.info("Navigated to cart page");
    }


    // Logout from product page
    public void logout() {
        driver.findElement(menuButton).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        logger.info("User logged out successfully");
    }

    // Check if prices are sorted ascending
    public boolean isSortedAscending(List<Double> prices) {
        List<Double> sortedList = new ArrayList<>(prices);
        Collections.sort(sortedList);
        return prices.equals(sortedList);
    }

}
