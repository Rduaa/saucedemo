package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutStepOnePage {
    private WebDriver driver;

    private By firstNameInput = By.cssSelector("#first-name");
    private By lastNameInput = By.cssSelector("#last-name");
    private By zipInput = By.cssSelector("#postal-code");
    private By continueBtn = By.cssSelector("#continue");

    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFormAndContinue(String firstName, String lastName, String zip) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(zipInput).sendKeys(zip);
        driver.findElement(continueBtn).click();

        // Wait for the URL to change to the second step
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("checkout-step-two"));
    }
}