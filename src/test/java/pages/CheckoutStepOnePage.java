package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutStepOnePage extends BasePage {
    private By firstNameInput = By.cssSelector("#first-name");
    private By lastNameInput = By.cssSelector("#last-name");
    private By zipInput = By.cssSelector("#postal-code");
    private By continueBtn = By.cssSelector("#continue");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    public void fillFormAndContinue(String firstName, String lastName, String zip) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(zipInput).sendKeys(zip);
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(continueBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }
}
