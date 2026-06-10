package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {
    private By cartIcon = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Add product '{0}' to the cart")
    public void addProductToCart(String productName) {
        By addBtn = By.xpath(String.format(
                "//div[text()='%s']/ancestor::div[@class='inventory_item']//button", productName));
        jsClick(addBtn);
    }

    @Step("Open the shopping cart")
    public void goToCart() {
        jsClick(cartIcon);
        wait.until(ExpectedConditions.urlContains("cart"));
    }
}
