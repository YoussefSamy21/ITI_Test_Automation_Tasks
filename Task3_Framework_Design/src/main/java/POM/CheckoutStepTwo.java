package POM;

import Actions.SeleniumWebUIActions;

public class CheckoutStepTwo
{
    SeleniumWebUIActions uiActions;
    String key;

    String checkOutStepTwoURL = "https://www.saucedemo.com/v1/checkout-step-two.html";

    // Dynamic Locator: Input->Product Name , Output-> price
    String productPrice = "//div[contains(text(),'%s')]//ancestor::div[@class='cart_item']//div[contains(@class,'price')]";

    String finishButton = "a.cart_button";
    String cancelButton = "a.cart_cancel_link";
    String totalItemsPrice = "div.summary_subtotal_label";
    String taxPrice = "div.summary_tax_label";
    String totalPrice = "div.summary_total_label";



    public CheckoutStepTwo(String key)
    {
        this.key = key; // for HashMap drivers, it's used in Methods returning new object from another Page
        uiActions = new SeleniumWebUIActions(key);
    }

    public CheckoutStepTwo()
    {
        uiActions = new SeleniumWebUIActions();
    }

    public void navigateToCheckOutStepTwoPage()
    {
        uiActions.navigateToPage(checkOutStepTwoURL , finishButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_PRESENCE);
    }

    public String getPageURL()
    {
        return uiActions.getPageURL();
    }

    public double getItemsPrice()
    {
        String str = uiActions.getText(totalItemsPrice, SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
        String price = str.split("\\$")[1].trim();
        return Double.parseDouble(price);
    }

    public double getTaxPrice()
    {
        String str = uiActions.getText(taxPrice , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
        String price = str.split("\\$")[1].trim();
        return Double.parseDouble(price);
    }

    public double getTotalPrice()
    {
        String str = uiActions.getText(totalPrice , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE);
        String price = str.split("\\$")[1].trim();
        return Double.parseDouble(price);
    }

    public double getProductPrice(String productName)
    {
        String productPriceSelector = String.format(productPrice , productName);
        String str = uiActions.getText(productPriceSelector , SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_VISIBLE).trim();
        String price = str.split("\\$")[1].trim();
        return Double.parseDouble(price);
    }


    /*
     *  Since these Methods considered as a transition to another page (Checkout-Complete & Product Pages)
     *  so they are implemented using the 2 ways of initializing the driver in the BrowserActions Class
     *  1- HashMap drivers   2- ThreadLocal drivers
     */
    public CheckoutComplete clickOnFinish_hashMap()
    {
        uiActions.clickOn(finishButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new CheckoutComplete(key);
    }

    public CheckoutComplete clickOnFinish_thread_Local()
    {
        uiActions.clickOn(finishButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new CheckoutComplete();
    }

    public Products clickOnCancelOrder_hashMap()
    {
        uiActions.clickOn(cancelButton ,  SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Products(key);
    }

    public Products clickOnCancelOrder_threadLocal()
    {
        uiActions.clickOn(cancelButton ,  SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Products();
    }
}
