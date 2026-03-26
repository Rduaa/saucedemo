package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTests extends BaseTest {

    @Test(description = "UC-1: Checkout Flow (one item)")
    public void singleItemCheckoutTest() {
        String product = "Sauce Labs Backpack";

        // 1. Perform login
        new LoginPage(getDriver()).login("standard_user", "secret_sauce");

        // 2. Add 1 item to the cart and navigate to it
        InventoryPage inventory = new InventoryPage(getDriver());
        inventory.addProductToCart(product);
        inventory.goToCart();

        // 3. Validate the cart and proceed to checkout
        CartPage cart = new CartPage(getDriver());
        Assert.assertTrue(cart.isProductPresent(product), "Product is not in the cart!");
        cart.proceedToCheckout();

        // 4. Fill in the form
        new CheckoutStepOnePage(getDriver()).fillFormAndContinue("Ivan", "Ivanov", "12345");

        // 5. Complete the purchase
        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(getDriver());
        stepTwo.finishCheckout();

        // 6. Check the final success message
        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertEquals(completePage.getCompleteMessage(), "Thank you for your order!");
    }

    @Test(description = "UC-2: Checkout Flow (several items)")
    public void multipleItemsCheckoutTest() {
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";

        new LoginPage(getDriver()).login("standard_user", "secret_sauce");

        InventoryPage inventory = new InventoryPage(getDriver());
        inventory.addProductToCart(product1);
        inventory.addProductToCart(product2);
        inventory.goToCart();

        CartPage cart = new CartPage(getDriver());
        Assert.assertTrue(cart.isProductPresent(product1), "Product 1 is missing!");
        Assert.assertTrue(cart.isProductPresent(product2), "Product 2 is missing!");
        cart.proceedToCheckout();

        new CheckoutStepOnePage(getDriver()).fillFormAndContinue("Jane", "Doe", "54321");

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(getDriver());

        // Main feature of UC-2: Price validation (sum of items == total sum on screen)
        double calculatedSum = stepTwo.getCalculatedItemsSubtotal();
        double displayedSum = stepTwo.getDisplayedSubtotal();
        Assert.assertEquals(calculatedSum, displayedSum, "Calculated sum does not match the displayed subtotal!");

        stepTwo.finishCheckout();

        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertEquals(completePage.getCompleteMessage(), "Thank you for your order!");
    }
}
