package POM;

import Actions.SeleniumWebUIActions;

public class Cart
{
    SeleniumWebUIActions uiActions;
    String key;

    String cartURL = "https://www.saucedemo.com/v1/cart.html";
    String checkoutButton = "a.checkout_button";
    String continueShoppingButton = "a.btn_secondary";

    // Dynamic Locator: Input --> ProductName , Output --> Remove Button Selector
    String customRemoveButton = "//div[@class='inventory_item_name'][contains(text(),'%s')]/ancestor::div[@class='cart_item']//button[contains(@class,'cart_button')]";

    public Cart(String key)
    {
        this.key = key; // for HashMap drivers, it's used in Methods returning new object from another Page (as proceedToCheckOut())
        uiActions = new SeleniumWebUIActions(key);
    }
    public Cart()
    {
        uiActions = new SeleniumWebUIActions();
    }


    public void navigateToCartPage()
    {
        uiActions.navigateToPage(cartURL, checkoutButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_PRESENCE);
    }

    public String getPageURL()
    {
        return uiActions.getPageURL();
    }

    public void removeProduct(String productName)
    {
        String removeProductSelector = String.format(customRemoveButton,productName);
        uiActions.clickOn(removeProductSelector , SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
    }

    /*
     *  Since these Methods considered as a transition to another page (Checkout & Product Pages)
     *  so they are implemented using the 2 ways of initializing the driver in the BrowserActions Class
     *  1- HashMap drivers   2- ThreadLocal drivers
     */

    public CheckoutStepOne proceedToCheckOut_hashMap()
    {
        uiActions.clickOn(checkoutButton, SeleniumWebUIActions.LocatorTypes.CSS,10, SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new CheckoutStepOne(key);
    }
    public CheckoutStepOne proceedToCheckOut_threadLocal()
    {
        uiActions.clickOn(checkoutButton, SeleniumWebUIActions.LocatorTypes.CSS,10, SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new CheckoutStepOne();
    }

    public Products continueShopping_hashMap()
    {
        uiActions.clickOn(continueShoppingButton, SeleniumWebUIActions.LocatorTypes.CSS,10, SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Products(key);
    }
    public Products continueShopping_threadLocal()
    {
        uiActions.clickOn(continueShoppingButton, SeleniumWebUIActions.LocatorTypes.CSS,10, SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Products();
    }

}
