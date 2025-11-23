package pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PageBase
{
    protected static void clickOnButton(WebElement button, WebDriverWait wait, Actions actions)
    {
        wait.until(ExpectedConditions.elementToBeClickable(button));
        actions.moveToElement(button).click();
        //actions.moveToElement(button).click().build().perform();
    }

    protected static void clickInsidePopupButton(WebElement popup, WebElement button, WebDriverWait wait, Actions actions)
    {
        actions.moveToElement(popup);
        actions.moveToElement(button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    protected static void moveAndClick(WebElement button, Actions actions)
    {
        actions.moveToElement(button);
        button.click();
    }

    protected static void goToTab(WebDriver driver, int tabIndex, String partOfTitle) throws InterruptedException {
        Thread.sleep(3000);
        ArrayList<String> activeTabs = new ArrayList<String>(driver.getWindowHandles());
        //System.out.println(activeTabs.size());
        while (true)
        {
            Boolean tabIsDisplaying = (driver.switchTo().window(activeTabs.get(tabIndex)).getTitle().contains(partOfTitle));
            if (activeTabs.size()>=tabIndex && tabIsDisplaying == true)
                break;

        }
        //Assert.assertTrue(driver.switchTo().window(activeTabs.get(tabIndex)).getTitle().contains(partOfTitle));
        driver.switchTo().window(activeTabs.get(tabIndex));
    }

    protected static void writeText(WebElement textBox, WebDriverWait wait, String text)
    {
        wait.until(ExpectedConditions.visibilityOf(textBox));
        textBox.clear();
        textBox.sendKeys(text);
    }
    protected static void writeTextAndEnter(WebElement textBox, WebDriverWait wait, String text)
    {
        wait.until(ExpectedConditions.visibilityOf(textBox));
        textBox.clear();
        textBox.sendKeys(text);
        textBox.sendKeys(Keys.ENTER);
    }
    protected static void writeTextWithoutWait(WebElement textBox, WebDriverWait wait, String text)
    {
        //wait.until(ExpectedConditions.visibilityOf(textBox));
        textBox.clear();
        textBox.sendKeys(text);
    }

    protected static WebElement detectLink(List<WebElement> elementsList, WebElement targetElement, String elementName)
    {
        System.out.println("Detect Link started");
        for(int i = 0; i<elementsList.size();i++)
        {
            System.out.println("Target Element is: "+elementsList.get(i).getText());
            System.out.println("Target Element class: "+elementsList.get(i).getTagName());
            if(elementsList.get(i).getText().contains(elementName))
            {
                targetElement = elementsList.get(i+1);
                System.out.println("Target Element is: "+targetElement.getText());
                break;
            }
        }

        return targetElement;
    }

    protected static WebElement detectLinkByIndex(List<WebElement> elementsList, WebElement targetElement, int index)
    {
        System.out.println("Detect Link started");
//        for(int i = 0; i<elementsList.size();i++)
//        {
//            System.out.println("Target Element is: "+elementsList.get(i).getText());
//            if(elementsList.get(i).getText().contains(elementName))
//            {
//                targetElement = elementsList.get(i+1);
//                System.out.println("Target Element is: "+targetElement.getText());
//                break;
//            }
//        }
        targetElement = elementsList.get(index);

        return targetElement;
    }

    protected static WebElement showShadowRootElement(WebDriver driver, String jsonPath)
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement element = (WebElement) jse.executeScript("return " + jsonPath);
        //System.out.println("Found element tag: "+ element.getTagName());

        return element;
    }

    protected static void scrollToElement(WebElement element, WebDriver driver)
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", element);
    }
    public void scrollToElementAndClick(WebElement element, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }
    protected static void scrollToBottom(WebDriver driver)
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        // Scroll Down to the bottom of the page
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    protected static void scrollToTop(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    protected static String goToParentWindow(WebDriver driver)
    {
        String parentWindow = driver.getWindowHandle();
        return parentWindow;
    }
    protected static Boolean searchInsideTable(WebElement targetTable, String targetCellText)
    {
        List<WebElement> allRows = targetTable.findElements(By.tagName("tr"));
        Boolean cellisExisted = false;
        for (WebElement row:allRows)
        {
            List<WebElement> column = row.findElements(By.tagName("td"));
            for(WebElement cell:column)
            {
                if(cell.getText().contains(targetCellText))
                {
                    cellisExisted = true;
                    break;
                }
            }
            if(cellisExisted == true)
            {
                break;
            }
        }
        return cellisExisted;
    }

    public static void loginWithHtaccessCredentails(WebDriver driver, String username, String password, int index) throws InterruptedException {
        Thread.sleep(5000);
        ArrayList<String> activeTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(activeTabs.get(index));
        String url = driver.getCurrentUrl();
        url = url.replaceAll("https://", "https://" + username + ":" + password + "@");
        driver.navigate().to(url);
    }
    public static void openNewTabAndLogin(WebDriver driver, String userName, String password, String url, String domain, String pageTitle)
    {
        driver.switchTo().newWindow(WindowType.TAB);
        url+=domain;
        url = url.replaceAll("https://", "https://" + userName + ":" + password + "@");
        driver.navigate().to(url);
        Assert.assertTrue(driver.getTitle().contains(pageTitle));
    }
    public static Boolean isHTAccessNeeded(WebDriver driver, String targetUrl) throws InterruptedException {
        Thread.sleep(5000);
        Boolean urlIsExisted = false;
        if(driver.getCurrentUrl().contains(targetUrl))
            urlIsExisted = true;
        return urlIsExisted;
    }
    protected static String generateFakeData(String startText)
    {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(1000);
        return startText +(String.valueOf(randomNumber));
    }
    protected static String generateFakeString()
    {

        return RandomStringUtils.randomAlphabetic(5);
    }

    protected static String generateFakePhoneNumber(String countryCode, String firstNumber,String secondNumber, int secondNumberLength, int numberLength)
    {
        /* random string = [countryCode][firstNumber][secondNumber (with detected length)][random number]
        ex: consider that: countryCode = +20, firstNumber = 1, secondNumber = "0125", secondNumberLength = 1 (choose 1 number randomly from secondNumber)
        and numberLength = 8
        Then Number should be like this: +2010xxxxxxxx, +2011xxxxxxxx, +2012xxxxxxxx or +2015xxxxxxxx
         */
        return countryCode+ firstNumber+ RandomStringUtils.random(secondNumberLength, secondNumber)+ RandomStringUtils.randomNumeric(numberLength);
    }

    protected static void selectFromDropDownList(WebElement dropDownList, String listItem)
    {
        Select selectList = new Select(dropDownList);
        selectList.selectByVisibleText(listItem);
    }

    protected static String getTodayDate()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    public static String getTodayDateTime()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String formattedDate = sdf.format(date);
        System.out.println("Current Date Time: "+formattedDate);
        return formattedDate;
    }
    protected static String getCurrentTime()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String formattedTime = sdf.format(date);
        return formattedTime;
    }
    protected static String getFutureTime(int futureHours)
    {
        LocalTime futureTime = LocalTime.now().plusHours(futureHours);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String endTime = futureTime.format(formatter);
        return endTime;
    }

    protected static void repeatKeyboardKeysAction(Keys keyAction, int numberOfRepetition, By elementLocator, WebDriver driver)
    {
        for(int i = 0; i<numberOfRepetition; i++)
            driver.findElement(elementLocator).sendKeys(keyAction);
    }

}
