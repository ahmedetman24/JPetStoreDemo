package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PetPage extends PageBase
{
    By petNameLabel = By.tagName("h2");
    By petTypeLink;


    public String checkPetName(WebDriverWait wait, WebDriver driver)
    {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(petNameLabel)));
        return driver.findElement(petNameLabel).getText();
    }

    public void goToProductItemsPage(String petType, WebDriver driver, Actions actions)
    {
        // Choose Product ID
        petTypeLink = By.linkText(petType);
        moveAndClick(driver.findElement(petTypeLink), actions);
    }
}
