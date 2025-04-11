package POM;

import Actions.SeleniumWebUIActions;

public class Login
{
    SeleniumWebUIActions uiActions;
    String key;
    String loginURL = "https://www.saucedemo.com/v1/";
    // Selectors
    String userNameFieldID = "user-name";
    String passwordFieldID = "password";
    String loginButtonID = "login-button";
    String errorMessageField = "//h3[contains(text(),'sadface')]";

    public Login(String key)
    {
        this.key = key; // for HashMap drivers, it's used in Methods returning new object from another Page (as clickOnLoginButton())
        uiActions = new SeleniumWebUIActions(key);
    }
    public Login()
    {
        uiActions = new SeleniumWebUIActions();
    }

    public void navigateToLoginPage()
    {
        uiActions.navigateToPage(loginURL ,userNameFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_PRESENCE);
    }

    public void setUserName(String username)
    {
        uiActions.sendText(username , userNameFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public void setPassword(String password)
    {
        uiActions.sendText(password , passwordFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public String getUserName()
    {
        return uiActions.getText(userNameFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public String getPassword()
    {
        return uiActions.getText(passwordFieldID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public String getErrorMessage()
    {
        return uiActions.getText(errorMessageField , SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }

    public String getPageURL()
    {
        return uiActions.getPageURL();
    }

    //=========================================================================================
    /*
     *  Since these Methods considered as a transition to another page (Products Page)
     *  so they are implemented using the 2 ways of initializing the driver in the BrowserActions Class
     *  1- HashMap drivers   2- ThreadLocal drivers
     */

    // using HashMap Drivers
    public Products clickOnLoginButton_hashMap()
    {
        uiActions.clickOn(loginButtonID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Products(key);
    }

    public Products login_hashMap(String username , String password)
    {
        setUserName(username);
        setPassword(password);
        return clickOnLoginButton_hashMap();
    }

    // using ThreadLocal Drivers
    public Products clickOnLoginButton_threadLocal()
    {
        uiActions.clickOn(loginButtonID , SeleniumWebUIActions.LocatorTypes.ID , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Products();
    }

    public Products login_threadLocal(String username , String password)
    {
        setUserName(username);
        setPassword(password);
        return clickOnLoginButton_threadLocal();
    }
}
