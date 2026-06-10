package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTests extends BaseTest {

    @Test(description = "UC-1: Checkout Flow (one item)")
    public void singleItemCheckoutTest() {
        String product = "Sauce Labs Backpack";

        new LoginPage(getDriver()).login(TestData.USERNAME, TestData.PASSWORD);

        InventoryPage inventory = new InventoryPage(getDriver());
        inventory.addProductToCart(product);
        inventory.goToCart();

        CartPage cart = new CartPage(getDriver());
        Assert.assertTrue(cart.isProductPresent(product), "Product is not in the cart!");
        cart.proceedToCheckout();

        new CheckoutStepOnePage(getDriver()).fillFormAndContinue(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);

        new CheckoutStepTwoPage(getDriver()).finishCheckout();

        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertEquals(completePage.getCompleteMessage(), "Thank you for your order!");
    }

    @Test(description = "UC-2: Checkout Flow (several items)")
    public void multipleItemsCheckoutTest() {
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";

        new LoginPage(getDriver()).login(TestData.USERNAME, TestData.PASSWORD);

        InventoryPage inventory = new InventoryPage(getDriver());
        inventory.addProductToCart(product1);
        inventory.addProductToCart(product2);
        inventory.goToCart();

        CartPage cart = new CartPage(getDriver());
        Assert.assertTrue(cart.isProductPresent(product1), "Product 1 is missing!");
        Assert.assertTrue(cart.isProductPresent(product2), "Product 2 is missing!");
        cart.proceedToCheckout();

        new CheckoutStepOnePage(getDriver()).fillFormAndContinue(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(getDriver());
        double calculatedSum = stepTwo.getCalculatedItemsSubtotal();
        double displayedSum = stepTwo.getDisplayedSubtotal();
        Assert.assertEquals(calculatedSum, displayedSum, "Calculated sum does not match the displayed subtotal!");

        stepTwo.finishCheckout();

        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertEquals(completePage.getCompleteMessage(), "Thank you for your order!");
    }
}
