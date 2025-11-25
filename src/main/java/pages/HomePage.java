package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends PageBase {

    By signInBtn = By.linkText("Sign In");

    public void  goToSignInPage(String url, WebDriver driver, Actions actions, WebDriverWait wait)
    {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(signInBtn)));
        moveAndClick(driver.findElement(signInBtn), actions);
    }
}
