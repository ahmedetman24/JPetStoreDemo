package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends PageBase
{
    // User Information elements
    By userIDTxtBox = By.name("username");
    By newPasswordTxtBox = By.name("password");
    By repeatPasswordTxtBox = By.name("repeatedPassword");

    //Account Information elements
    By firstNameTxtBox = By.name("account.firstName");
    By lastNameTxtBox = By.name("account.lastName");
    By emailTxtBox = By.name("account.email");
    By phoneTxtBox = By.name("account.phone");
    By address1TxtBox = By.name("account.address1");
    By address2TxtBox = By.name("account.address2");
    By cityTxtBox = By.name("account.city");
    By stateTxtBox = By.name("account.state");
    By zipTxtBox = By.name("account.zip");
    By countryTxtBox = By.name("account.country");

    // Profile Information elements
    By enableMyListCheckBox = By.name("account.listOption");
    By enableMyBannerCheckBox = By.name("account.bannerOption");
    By saveAccountInformationBtn = By.name("newAccount");

    public String[] registerNewUser(WebDriverWait wait, WebDriver driver, Actions actions, String countryCode, String firstNumber,String secondNumber,
                                    int secondNumberLength, int numberLength)
    {
        // Generate a fake user ID and a fake password
        String userID = generateFakeData("user").toLowerCase();
        String password = generateFakeData("P@ssword");
        String firstName = generateFakeString();

        String phoneNumber = generateFakePhoneNumber(countryCode, firstNumber, secondNumber, secondNumberLength, numberLength);
        //System.out.println("Generated Phone Number: "+phoneNumber);

        String email = generateFakeData(userID)+"@"+generateFakeString().toLowerCase()+".com";
        //System.out.println("Generated email: "+ email);

        // Enter registration data
        writeText(driver.findElement(userIDTxtBox), wait, userID);
        writeTextWithoutWait(driver.findElement(newPasswordTxtBox), wait, password);
        writeTextWithoutWait(driver.findElement(repeatPasswordTxtBox), wait, password);

        writeTextWithoutWait(driver.findElement(firstNameTxtBox), wait, firstName);
        writeTextWithoutWait(driver.findElement(lastNameTxtBox), wait, generateFakeString());
        writeTextWithoutWait(driver.findElement(emailTxtBox), wait, email);
        writeTextWithoutWait(driver.findElement(phoneTxtBox), wait, phoneNumber);
        writeTextWithoutWait(driver.findElement(address1TxtBox), wait, generateFakeString());
        writeTextWithoutWait(driver.findElement(address2TxtBox), wait, generateFakeString());
        writeTextWithoutWait(driver.findElement(cityTxtBox), wait, generateFakeString());
        writeTextWithoutWait(driver.findElement(stateTxtBox), wait, generateFakeString());
        writeTextWithoutWait(driver.findElement(zipTxtBox), wait, generateFakeData(""));
        writeTextWithoutWait(driver.findElement(countryTxtBox), wait, generateFakeString());

        moveAndClick(driver.findElement(enableMyListCheckBox), actions);
        moveAndClick(driver.findElement(enableMyBannerCheckBox), actions);
        moveAndClick(driver.findElement(saveAccountInformationBtn), actions);

        return new String[]{userID, password, firstName};
    }
}
