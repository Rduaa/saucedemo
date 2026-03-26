package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private WebDriver driver;

    private By checkoutBtn = By.cssSelector("#checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductPresent(String productName) {
        String xpath = String.format("//div[@class='inventory_item_name' and text()='%s']", productName);
        return !driver.findElements(By.xpath(xpath)).isEmpty();
    }

    public void proceedToCheckout() {
        driver.findElement(checkoutBtn).click();
    }
}
