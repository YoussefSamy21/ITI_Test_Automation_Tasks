package POM;

import Actions.SeleniumWebUIActions;

public class CheckoutComplete
{
    SeleniumWebUIActions uiActions;
    String checkOutCompleteURL = "https://www.saucedemo.com/v1/checkout-complete.html";
    String confirmationMessage = "h2.complete-header";

    public CheckoutComplete(String key)
    {
        uiActions = new SeleniumWebUIActions(key);
    }

    public CheckoutComplete()
    {
        uiActions = new SeleniumWebUIActions();
    }

    public void navigateToCheckOutCompletePage()
    {
        uiActions.navigateToPage(checkOutCompleteURL , confirmationMessage , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_PRESENCE);
    }

    public String getPageURL()
    {
        return uiActions.getPageURL();
    }

    public String getConfirmationMessage()
    {
        return uiActions.getText(confirmationMessage , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
    }


}
