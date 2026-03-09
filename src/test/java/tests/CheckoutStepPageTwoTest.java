package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutStepPageTwoTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(CheckoutStepPageTwoTest.class);

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutStepPageOne checkoutStepPageOne;
    private CheckoutStepPageTwo checkoutStepPageTwo;

    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(driver);
        loginPage.loginAsValidUser();
        logger.info("User logged in successfully");

        productsPage = new ProductsPage(driver);
        productsPage.addFirstNProductsToCart(2);
        logger.info("Added 2 products to cart");

        productsPage.openCart();

        cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        checkoutStepPageOne = new CheckoutStepPageOne(driver);

        checkoutStepPageOne.fillUserDetails("John", "Doe", "560001");
        checkoutStepPageOne.clickContinue();

        checkoutStepPageTwo = new CheckoutStepPageTwo(driver);
        logger.info("Navigated to Checkout Step Two page");
    }

    @Test
    public void verifyItemsDisplayedCorrectly() {
        int itemCount = checkoutStepPageTwo.getItemCount();
        logger.info("Number of items found: {}", itemCount);

        Assert.assertEquals(itemCount, 2, "Incorrect number of items displayed in checkout overview");
    }

    @Test
    public void verifySubtotalCalculation() {
        double expectedSubtotal = checkoutStepPageTwo.calculateExpectedSubtotal();
        double displayedSubtotal = checkoutStepPageTwo.getDisplayedSubtotalAmount();

        logger.info("Expected subtotal: {}", expectedSubtotal);
        logger.info("Displayed subtotal: {}", displayedSubtotal);

        Assert.assertEquals(displayedSubtotal, expectedSubtotal,
                "Displayed subtotal does not match calculated subtotal");
    }

    @Test
    public void verifyFinishCheckout() {

        checkoutStepPageTwo.clickFinish();
        logger.info("Clicked Finish");

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"),
                "User was not redirected to checkout complete page");
    }
    @Test
    public void verifyCancelCheckout() {
        checkoutStepPageTwo.clickCancel();
        logger.info("Clicked Cancel");
        System.out.println(driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Cancel button did not redirect back to cart page");
    }

}
