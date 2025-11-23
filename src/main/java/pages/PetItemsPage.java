package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class PetItemsPage extends PageBase
{
    By itemNumber;

    public void goToItemDetailsPage(String itemID, WebDriver driver, Actions actions)
    {
        // View Item Details
        itemNumber = By.linkText(itemID);
        moveAndClick(driver.findElement(itemNumber), actions);
    }
}
