package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CheckoutStepTwoPage {
    private WebDriver driver;

    private By itemPrices = By.cssSelector(".inventory_item_price");
    private By subtotalLabel = By.cssSelector(".summary_subtotal_label");
    private By finishBtn = By.cssSelector("#finish");

    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
    }

    // Collect prices of all items in the cart and calculate the total sum
    public double getCalculatedItemsSubtotal() {
        List<WebElement> pricesElements = driver.findElements(itemPrices);
        double totalSum = 0.0;
        for (WebElement element : pricesElements) {
            String priceText = element.getText().replace("$", "");
            totalSum += Double.parseDouble(priceText);
        }
        return totalSum;
    }

    // Retrieve the total subtotal price displayed on the UI
    public double getDisplayedSubtotal() {
        String text = driver.findElement(subtotalLabel).getText();
        String priceText = text.replace("Item total: $", "");
        return Double.parseDouble(priceText);
    }

    public void finishCheckout() {
        // Explicit wait to handle fast browser transitions
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishBtn)).click();
    }
}