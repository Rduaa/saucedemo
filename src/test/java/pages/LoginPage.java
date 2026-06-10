package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private By usernameInput = By.cssSelector("#user-name");
    private By passwordInput = By.cssSelector("#password");
    private By loginBtn = By.cssSelector("#login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Log in as '{0}'")
    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(pass);
        jsClick(loginBtn);
        wait.until(ExpectedConditions.urlContains("inventory"));
    }
}
