package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    private WebDriver driver;

    private By cartIcon = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addProductToCart(String productName) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='inventory_item']//button", productName);
        driver.findElement(By.xpath(xpath)).click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }
}