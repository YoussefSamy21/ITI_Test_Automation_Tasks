package POM;

import Actions.SeleniumWebUIActions;

public class CheckoutStepOne
{
    SeleniumWebUIActions uiActions;
    String key;

    String checkOutStepOneURL = "https://www.saucedemo.com/v1/checkout-step-one.html";
    String firstNameFieldID = "first-name";
    String lastNameFieldID = "last-name";
    String postalCodeFieldID = "postal-code";
    String errorMessageField = "//h3[@data-test='error']";
    String continueButton = "input.cart_button";
    String cancelButton = "a.cart_cancel_link";

    public CheckoutStepOne(String key)
    {
        this.key = key; // for HashMap drivers, it's used in Methods returning new object from another Page (as clickOnCartIcon())
        uiActions = new SeleniumWebUIActions(key);
    }

    public CheckoutStepOne()
    {
        uiActions = new SeleniumWebUIActions();
    }

    public void navigateToCheckOutStepOnePage()
    {
        uiActions.navigateToPage(checkOutStepOneURL , firstNameFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_PRESENCE);
    }

    public String getPageURL()
    {
        return uiActions.getPageURL();
    }

    public void setFirstName(String firstname)
    {
        uiActions.sendText(firstname , firstNameFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public void setLastName(String lastname)
    {
        uiActions.sendText(lastname , lastNameFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public void setPostalCode(String postalCode)
    {
        uiActions.sendText(postalCode , postalCodeFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public void checkoutInformation(String firstname , String lastname , String postalcode)
    {
        setFirstName(firstname);
        setLastName(lastname);
        setPostalCode(postalcode);
    }

    public String getErrorMessage()
    {
        return uiActions.getText(errorMessageField , SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    /*
     *  Since these Methods considered as a transition to another page (Checkout & Cart Pages)
     *  so they are implemented using the 2 ways of initializing the driver in the BrowserActions Class
     *  1- HashMap drivers   2- ThreadLocal drivers
     */

    public CheckoutStepTwo continueCheckOut_hashMap()
    {
        uiActions.clickOn(continueButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new CheckoutStepTwo(key);
    }

    public CheckoutStepTwo continueCheckOut_threadLocal()
    {
        uiActions.clickOn(continueButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new CheckoutStepTwo();
    }

    public Cart cancelCheckOut_hashMap()
    {
        uiActions.clickOn(cancelButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Cart(key);
    }

    public Cart cancelCheckOut_threadLocal()
    {
        uiActions.clickOn(cancelButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Cart();
    }


}