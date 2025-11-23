package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllPetsPage extends PageBase
{
    By signOutBtn = By.linkText("Sign Out");
    By welcomeLabel = By.id("WelcomeContent");
    By petLink;// = By.xpath("//img[@src='../images/cats_icon.gif']");

    public String checkLoginName(WebDriverWait wait, WebDriver driver)
    {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(signOutBtn)));
        return driver.findElement(welcomeLabel).getText();
    }

    public void goToPetPage(WebDriver driver, Actions actions, String petName)
    {
        petLink = By.xpath("//img[@src='../images/"+petName.toLowerCase()+"_icon.gif']");
        moveAndClick(driver.findElement(petLink), actions);
    }
}
