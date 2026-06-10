package pages;

import io.qameta.allure.Step;
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

    @Step("Fill shipping form ({0} {1}, zip: {2}) and continue")
    public void fillFormAndContinue(String firstName, String lastName, String zip) {
        setReactInput(firstNameInput, firstName);
        setReactInput(lastNameInput, lastName);
        setReactInput(zipInput, zip);
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    // React controlled inputs require native value setter + bubbling input event
    // to update component state; sendKeys alone is unreliable under parallel load
    private void setReactInput(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript(
            "var s=Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
            "s.call(arguments[0],arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));",
            el, value);
    }
}
