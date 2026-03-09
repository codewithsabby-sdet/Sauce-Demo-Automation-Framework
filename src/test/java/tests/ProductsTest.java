package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.time.Duration;
import java.util.List;

public class ProductsTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private static final Logger logger = LogManager.getLogger(ProductsTest.class);

    @BeforeMethod
    public void loginProductPage() {
        loginPage = new LoginPage(driver);
        loginPage.loginAsValidUser();
        logger.info("Logged in successfully");

        productsPage = new ProductsPage(driver); // <- Fix NullPointerException
        logger.info("ProductsPage initialized");
    }

    @Test
    public void verifyProductListDisplayed() {
        int count = productsPage.getProductCount();
        logger.info("Number of products displayed: {}", count);
        Assert.assertTrue(count > 0, "No products displayed on the products page");
    }

    @Test
    public void verifySortLowToHigh() {
        productsPage.selectSortOption("Price (low to high)");
        List<Double> prices = productsPage.getAllProductPrices();
        Assert.assertTrue(productsPage.isSortedAscending(prices), "Products not sorted correctly by price low to high");
        logger.info("Verified product sorting (low to high) successfully");
    }

    @Test
    public void verifyProductDetailsNavigation() {
        String productName = productsPage.getFirstProductName();
        productsPage.clickFirstProduct();
        String detailName = productsPage.getProductDetailTitle();
        Assert.assertEquals(detailName, productName, "Product name mismatch on details page");
        logger.info("Product detail navigation verified for product: {}", productName);
    }


    @Test
    public void verifyAddMultipleProductsToCart() {
        int productsToAdd = 2;

        // Add first N products to the cart
        productsPage.addFirstNProductsToCart(productsToAdd);
        logger.info("{} products added to cart", productsToAdd);
        // Get cart count
        int cartCount = productsPage.getCartCount();
        logger.info("Cart count after adding products: {}", cartCount);
        Assert.assertEquals(cartCount, productsToAdd, "Cart count mismatch after adding products");
    }


}


