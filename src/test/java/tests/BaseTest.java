package tests;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver.set(new FirefoxDriver());
        }

        getDriver().manage().window().maximize();
        getDriver().get(TestData.BASE_URL);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] captureScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (getDriver() != null) {
            if (!result.isSuccess()) {
                captureScreenshot();
            }
            getDriver().quit();
            driver.remove();
        }
    }
}
