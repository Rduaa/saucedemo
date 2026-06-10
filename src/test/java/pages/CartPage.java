package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {
    private By checkoutBtn = By.cssSelector("#checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify product '{0}' is present in the cart")
    public boolean isProductPresent(String productName) {
        String xpath = String.format("//div[@class='inventory_item_name' and text()='%s']", productName);
        return !driver.findElements(By.xpath(xpath)).isEmpty();
    }

    @Step("Proceed to checkout")
    public void proceedToCheckout() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }
}
