package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage extends PageBase
{
    By registerNowLink = By.linkText("Register Now!");
    By userNameTxtBox = By.name("username");
    By passwordTxtBox = By.name("password");
    By loginBtn = By.name("signon");

    public void goToRegistrationPage(WebDriverWait wait, Actions actions, WebDriver driver)
    {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(registerNowLink)));
        moveAndClick(driver.findElement(registerNowLink), actions);
    }

    public void signIn(String userID, String password, WebDriverWait wait, Actions actions, WebDriver driver)
    {
        writeText(driver.findElement(userNameTxtBox), wait, userID);
        //System.out.println("User Name Text Box displaying?"+driver.findElement(userNameTxtBox).isDisplayed());
        writeText(driver.findElement(passwordTxtBox), wait, password);
        moveAndClick(driver.findElement(loginBtn), actions);
    }
}
