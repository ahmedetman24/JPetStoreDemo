package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCartPage extends PageBase
{
    By shoppingCartLabel = By.xpath("//h2[normalize-space()='Shopping Cart']");
    By itemNumber, productNumber;
    By proceedToCheckoutBtn = By.xpath("//a[normalize-space()='Proceed to Checkout']");

    public String[] checkShoppingCartItems(String itemID, String productID, WebDriver driver, WebDriverWait wait)
    {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(shoppingCartLabel)));
        itemNumber = By.linkText(itemID);
        productNumber = By.xpath("//td[normalize-space()='"+productID+"']");

        return new String[]{driver.findElement(itemNumber).getText(), driver.findElement(productNumber).getText(), driver.findElement(proceedToCheckoutBtn).getText()};
    }
}
