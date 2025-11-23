package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ItemDetailsPage extends PageBase
{
    By itemNumber;
    By addToCartBtn = By.linkText("Add to Cart");

    public void addItemToCart(String itemID, WebDriverWait wait, WebDriver driver, Actions actions)
    {
        itemNumber = By.xpath("//b[normalize-space()='"+itemID+"']");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(itemNumber)));

        // Add item to the cart
        moveAndClick(driver.findElement(addToCartBtn), actions);
    }
}
