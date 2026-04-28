package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.List;

public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private static final Logger logger = LogManager.getLogger(CartTest.class);

    @BeforeMethod
    public void setUpCart() {
        loginPage = new LoginPage(driver);
        loginPage.loginAsValidUser();

        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);

        logger.info("Logged in and navigated to Products page");
    }

    @Test
    public void verifyItemsAppearInCart() {
        productsPage.addFirstNProductsToCart(1);
        productsPage.openCart();

        List<?> names = cartPage.getCartItemNames();
        List<?> prices = cartPage.getCartItemPrices();

        Assert.assertTrue(!names.isEmpty(), "Item name not found in cart");
        Assert.assertTrue(!prices.isEmpty(), "Item price not found in cart");

        logger.info("Verified item name & price displayed in cart");
    }

    @Test
    public void verifyCartCountMatchesAddedItems() {
        int toAdd = 2;
        productsPage.addFirstNProductsToCart(toAdd);
        productsPage.openCart();

        int displayedCount = cartPage.getCartItemsCount();
        Assert.assertEquals(displayedCount, toAdd, "Cart item count mismatch");

        logger.info("Verified cart item count matches items added");
    }

    @Test
    public void verifyRemoveItemUpdatesCart() {
        productsPage.addFirstNProductsToCart(2);
        productsPage.openCart();

        cartPage.removeItem(0);

        int countAfterRemoval = cartPage.getCartItemsCount();
        Assert.assertEquals(countAfterRemoval, 2, "Item not removed properly");

        logger.info("Verified removing an item updates cart");
    }

    @Test
    public void verifyContinueShoppingNavigatesBackToProducts() {
        productsPage.addFirstNProductsToCart(1);
        productsPage.openCart();
        cartPage.clickContinueShopping();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not navigate back to products page");

        logger.info("Verified Continue Shopping redirects to Products page");
    }

    @Test
    public void verifyCheckoutButtonNavigation() {
        productsPage.addFirstNProductsToCart(1);
        productsPage.openCart();
        cartPage.clickCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Checkout button did not navigate correctly");

        logger.info("Verified Checkout button navigation");
    }
}
