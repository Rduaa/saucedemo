package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CheckoutStepTwoPage extends BasePage {
    private By itemPrices = By.cssSelector(".inventory_item_price");
    private By subtotalLabel = By.cssSelector(".summary_subtotal_label");
    private By finishBtn = By.cssSelector("#finish");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public double getCalculatedItemsSubtotal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemPrices));
        List<WebElement> pricesElements = driver.findElements(itemPrices);
        double totalSum = 0.0;
        for (WebElement element : pricesElements) {
            totalSum += Double.parseDouble(element.getText().replace("$", ""));
        }
        return totalSum;
    }

    public double getDisplayedSubtotal() {
        String text = driver.findElement(subtotalLabel).getText();
        return Double.parseDouble(text.replace("Item total: $", ""));
    }

    public void finishCheckout() {
        WebElement finishButton = wait.until(ExpectedConditions.presenceOfElementLocated(finishBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", finishButton);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }
}
