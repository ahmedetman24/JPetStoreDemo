package testcases;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.PageBase;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestBase extends AbstractTestNGCucumberTests
{
    public static WebDriver driver;
    public static Actions actions;
    public static WebDriverWait wait;

    //@Parameters({"browser"})
    @BeforeClass
    public static void openBrowser(@Optional("headless") String browser) {

        switch (browser.toLowerCase()){
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "headless":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterMethod
    public void takeScreenshotInCaseOfFailure(ITestResult result) throws IOException {
        // Take Screenshot in case of any test case failed
        if(result.getStatus()== ITestResult.FAILURE)
        {
            // Take Screenshot and save it at Screenshots folder
            String errorDateTime = PageBase.getTodayDateTime();
            errorDateTime = errorDateTime.replaceAll(":", "_");

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(System.getProperty("user.dir")+"/Screenshots/"+result.getName()+"_"+errorDateTime+".png");

            // If Screenshots folder is not existing, system will create it
            destination.getParentFile().mkdirs();

            FileUtils.copyFile(source, destination);
        }

    }

    @AfterTest
    public void closeBrowser() throws InterruptedException {

        Thread.sleep(3000);
        driver.quit();
    }

}
