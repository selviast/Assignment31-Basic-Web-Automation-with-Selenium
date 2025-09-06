package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class BankingProject {

    WebDriver driver;
    WebDriverWait driverWait;

    @BeforeClass
    public void setUp() {
        String driverPath = "/src/test/java/drivers/chromedriver";
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + driverPath);

        ChromeOptions options = new ChromeOptions();
        options.setImplicitWaitTimeout(Duration.ofSeconds(5));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/customer");
        System.out.println("Browser dibuka dan halaman Customer Login ditampilkan.");
    }

    @Test
    public void testDepositFlow() {
        // Verify title
        String getTextTitle = driver.getTitle();
        System.out.println("Title: " + getTextTitle);
        Assert.assertEquals(getTextTitle, "XYZ Bank");

        // Pilih dropdown Your Name
        WebElement dropdown = driver.findElement(By.cssSelector("[name=\"userSelect\"]"));
        Select idDrop = new Select(dropdown);
        idDrop.selectByValue("2"); // contoh pilih value 2
        String selectedOptionText = idDrop.getFirstSelectedOption().getText();
        System.out.println("Selected option text: " + selectedOptionText);

        // Klik Login
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"submit\"]")));
        WebElement buttonLogin = driver.findElement(By.cssSelector("[type=\"submit\"]"));
        Assert.assertTrue(buttonLogin.isEnabled());
        buttonLogin.click();
        System.out.println("Berhasil login sebagai: " + selectedOptionText);

        // Klik Deposit
        WebElement buttonDeposit = driver.findElement(By.cssSelector("button[ng-click='deposit()']"));
        buttonDeposit.click();
        System.out.println("Menu Deposit dibuka.");

        // Input jumlah deposit
        String depositAmount = "500";
        WebElement inputDeposit = driver.findElement(By.cssSelector("input[ng-model='amount']"));
        inputDeposit.sendKeys(depositAmount);
        System.out.println("Input deposit amount: " + depositAmount);

        // Klik tombol Submit Deposit
        WebElement submitDeposit = driver.findElement(By.cssSelector("button[type='submit']"));
        submitDeposit.click();
        System.out.println("Klik tombol Deposit.");

        // Assert pesan sukses
        WebElement successMsg = driver.findElement(By.cssSelector(".error[ng-show='message']"));
        String successText = successMsg.getText();
        System.out.println("Pesan sukses: " + successText);
        Assert.assertEquals(successText, "Deposit Successful");

        // Assert balance setelah deposit
        WebElement balanceElement = driver.findElement(By.xpath("//strong[2]"));
        String balanceText = balanceElement.getText();
        System.out.println("Balance setelah deposit: " + balanceText);
        Assert.assertEquals(balanceText, depositAmount, "Balance tidak sesuai dengan deposit amount");

        System.out.println("Test case berhasil: Deposit sukses dan balance sesuai.");
    }

    @AfterClass
    public void quit() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser ditutup.");
        }
    }
}

