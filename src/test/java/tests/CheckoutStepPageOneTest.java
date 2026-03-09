package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutStepPageOneTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutStepPageOne checkoutStepOnePage;

    private static final Logger logger = LogManager.getLogger(CheckoutStepPageOneTest.class);

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepPageOne(driver);

        // Login
        loginPage.loginAsValidUser();
        logger.info("User logged in successfully");

        // Add 1 product
        productsPage.addFirstNProductsToCart(1);
        productsPage.openCart();

        // Navigate to checkout
        cartPage.clickCheckout();
        logger.info("Navigated to Checkout Step One page");
    }

    @Test
    public void verifySuccessfulCheckoutStepOneNavigation() {
        checkoutStepOnePage.fillUserDetails("John", "Doe", "560001");
        checkoutStepOnePage.clickContinue();

        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("checkout-step-two"),
                "Did not navigate to Checkout Step Two page!");

        logger.info("Successfully moved to Checkout Step Two page");
    }

    @Test
    public void verifyErrorDisplayedForEmptyFields() {
        checkoutStepOnePage.clickContinue(); // No data entered

        String error = checkoutStepOnePage.getErrorMessage();

        Assert.assertNotNull(error, "Error message not displayed!");
        Assert.assertTrue(error.contains("Error"), "Unexpected error message");

        logger.info("Verified error message for empty required fields: {}", error);
    }

}
