package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        String driverPath = "/src/test/java/drivers/chromedriver";
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + driverPath);

        ChromeOptions options = new ChromeOptions();
        options.setImplicitWaitTimeout(Duration.ofSeconds(1));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.tokopedia.com/login");
    }


    @Test
    public void testTokopedia() {

        WebElement titleLogin = driver.findElement(By.className("css-1dmmzzh"));
        WebElement inputUsername = driver.findElement(By.cssSelector("input[name='login']"));
        WebElement buttonLogin = driver.findElement(By.cssSelector("[data-testid=\"button-submit\"]"));
        WebElement textErrorLogin = driver.findElement(By.cssSelector("#input > div.css-y0w6gg"));
        inputUsername.sendKeys("selvia");
        buttonLogin.click();

        System.out.println(titleLogin.getText());
        System.out.println(textErrorLogin.getText());
    }

    @AfterClass
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}