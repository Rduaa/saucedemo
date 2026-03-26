package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By usernameInput = By.cssSelector("#user-name");
    private By passwordInput = By.cssSelector("#password");
    private By loginBtn = By.cssSelector("#login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        driver.findElement(usernameInput).sendKeys(user);
        driver.findElement(passwordInput).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }
}
