package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.*;
import testcases.TestBase;

import java.util.Objects;

public class PurchaseAPet extends TestBase
{
    static HomePage homePage;
    static SignInPage signInPage;
    static RegistrationPage registrationPage;
    static AllPetsPage allPetsPage;

    static PetPage petPage;
    static PetItemsPage petItemsPage;
    static ItemDetailsPage itemDetailsPage;
    static ShoppingCartPage shoppingCartPage;


    static String userID = "", password = "", firstName = "";

    @Given("^user opens the home page using \"([^\"]*)\"$")
    public void user_opens_the_home_page_using(String url)
    {
        // Home Page
        homePage = new HomePage();
        homePage.goToSignInPage(url, driver, actions, wait);
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("signonForm"));
    }

    @When("^user go to sign in page$")
    public void User_go_to_sign_in_page()
    {
        // Sign In Page
        signInPage = new SignInPage();
        signInPage.goToRegistrationPage(wait, actions, driver);
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("newAccountForm"));
    }

    @When("^make registration using \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void make_registration_using(String countryCode, String firstNumber, String secondNumber, String secondNumberLength, String numberLength, String url)
    {
        //Register a new user
        registrationPage = new RegistrationPage();
        String[] loginData = registrationPage.registerNewUser(wait, driver, actions, countryCode, firstNumber, secondNumber, Integer.parseInt(secondNumberLength),
                Integer.parseInt(numberLength));

        // If current URL = Expected URL (Registration done successfully), then pass registered data to log in with, otherwise, pass default data

        //System.out.println("Outside if condition");
        if(Objects.equals(driver.getCurrentUrl(), url))
        {
            //System.out.println("inside if condition");
            userID = loginData[0];
            password = loginData[1];
            firstName = loginData[2];
        }

        //System.out.println("User ID: "+userID+"\nPassword: "+password+"\nFirst Name: "+firstName);
    }

    @Then("^home page should be displaying expected \"([^\"]*)\"$")
    public void Home_page_should_be_displaying_expected(String url)
    {
        // Check that home page is displaying
        Assert.assertEquals(driver.getCurrentUrl(), url);
    }

    /* ==================================================================
    Login Scenario */

    @Given("^user go to sign in page and may use \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void user_go_to_sign_in_page_and_may_use(String url, String alternativeUserID, String alternativeUserPassword, String alternativeFirstName)
    {
        homePage = new HomePage();
        homePage.goToSignInPage(url, driver, actions, wait);
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("signonForm"));

        if(Objects.equals(userID, "") || Objects.equals(password, "") || Objects.equals(firstName, ""))
        {
            // Alternative User ID and password will be used in case of failed registration, this account is registered before
            userID = alternativeUserID;
            password = alternativeUserPassword;
            firstName = alternativeFirstName;
        }
        //System.out.println("User ID: "+userID+"\nPassword: "+password+"\nFirst Name: "+firstName);
    }

    @When("^login with valid data$")
    public void login_with_valid_data() throws InterruptedException {
        // Sign in either using registered data or using alternative data
        Thread.sleep(Long.parseLong("3000"));

        signInPage = new SignInPage();
        signInPage.signIn(userID, password, wait, actions, driver);
    }

    @When("^all pets page is opened$")
    public void all_pets_page_is_opened()
    {
        // Ensure that user has logged in successfully, and if login is successful, then go to "Cats" page
        allPetsPage = new AllPetsPage();
        Assert.assertTrue(allPetsPage.checkLoginName(wait, driver).contains(firstName));
    }

    @Then("^go to any pet page containing \"([^\"]*)\"$")
    public void go_to_any_pet_page_containing(String petName)
    {
        allPetsPage.goToPetPage(driver, actions, petName);
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains(petName.toUpperCase()));
    }

    /* ==================================================================
    Add to cart Scenario */

    @Given("^target pet page is displaying \"([^\"]*)\"$")
    public void target_pet_page_is_displaying(String petName)
    {
        // Check that open page is the required pet page
        petPage = new PetPage();
        Assert.assertEquals(petPage.checkPetName(wait, driver).toLowerCase(), petName.toLowerCase());
    }

    @When("^add pet to the cart with \"([^\"]*)\", \"([^\"]*)\"$")
    public void add_pet_to_the_cart_with(String petType, String itemID)
    {
        // Add pet type to the cart
        petPage.goToProductItemsPage(petType, driver, actions);
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains(petType));

        // Choose Item ID
        petItemsPage = new PetItemsPage();
        petItemsPage.goToItemDetailsPage(itemID, driver, actions);
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains(itemID));

        // Add Item to cart
        itemDetailsPage = new ItemDetailsPage();
        itemDetailsPage.addItemToCart(itemID, wait, driver, actions);
    }

    @Then("^pet should be added to the cart with \"([^\"]*)\", \"([^\"]*)\"$")
    public void pet_should_be_added_to_the_cart_with(String itemID, String petType)
    {
        // Check if the added product is the same required product
        shoppingCartPage = new ShoppingCartPage();
        String[] shoppingCartItems = shoppingCartPage.checkShoppingCartItems(itemID, petType, driver, wait);

        Assert.assertEquals(shoppingCartItems[0].toLowerCase(), itemID.toLowerCase());
        Assert.assertEquals(shoppingCartItems[1].toLowerCase(), petType.toLowerCase());
        Assert.assertEquals(shoppingCartItems[2],"Proceed to Checkout");
    }
}
