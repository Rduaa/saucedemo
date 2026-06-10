package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage extends BasePage {
    private By completeHeader = By.cssSelector(".complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get order completion message")
    public String getCompleteMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
        return driver.findElement(completeHeader).getText();
    }
}
