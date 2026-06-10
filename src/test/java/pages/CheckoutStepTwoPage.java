package pages;

import io.qameta.allure.Step;
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

    @Step("Calculate items subtotal from displayed prices")
    public double getCalculatedItemsSubtotal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemPrices));
        List<WebElement> pricesElements = driver.findElements(itemPrices);
        double totalSum = 0.0;
        for (WebElement element : pricesElements) {
            totalSum += Double.parseDouble(element.getText().replace("$", ""));
        }
        return totalSum;
    }

    @Step("Read displayed subtotal label")
    public double getDisplayedSubtotal() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(subtotalLabel)).getText();
        return Double.parseDouble(text.replace("Item total: $", ""));
    }

    @Step("Finish checkout")
    public void finishCheckout() {
        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(finishBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", finishButton);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }
}
