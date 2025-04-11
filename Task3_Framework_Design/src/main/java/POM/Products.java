package POM;

import Actions.SeleniumWebUIActions;

public class Products
{
    SeleniumWebUIActions uiActions;
    String key;

    String productsURL = "https://www.saucedemo.com/v1/inventory.html";
    String productSortButton = ".product_sort_container";
    String cartIconButton = "//a[contains(@class,'shopping_cart_link')]";
    // Dynamic Locator: Input --> Product Name , Output --> AddtoCart/REMOVE Button Selector
    String customAddToCartOrRemoveButton = "//div[contains(text(),'%s')]/ancestor::div[@class='inventory_item']//button";


    public Products(String key)
    {
        this.key = key; // for HashMap drivers, it's used in Methods returning new object from another Page (as clickOnCartIcon())
        uiActions = new SeleniumWebUIActions(key);
    }
    public Products()
    {
        uiActions = new SeleniumWebUIActions();
    }

    public void navigateToProductsPage()
    {
        uiActions.navigateToPage(productsURL , productSortButton , SeleniumWebUIActions.LocatorTypes.CSS , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_PRESENCE);
    }

    public String getPageURL()
    {
        return uiActions.getPageURL();
    }

    public void addProductToCart(String productName)
    {
        String addToCartSelector = String.format(customAddToCartOrRemoveButton, productName);
        uiActions.clickOn(addToCartSelector, SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
    }

    public String getButtonText(String productName)
    {
        String buttonSelector = String.format(customAddToCartOrRemoveButton , productName);
        return uiActions.getText(buttonSelector , SeleniumWebUIActions.LocatorTypes.XPATH);
    }

    public void removeProduct(String productName)
    {
        String removeFromCartSelector = String.format(customAddToCartOrRemoveButton, productName);
        uiActions.clickOn(removeFromCartSelector, SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
    }

    /*
     *  Since this Method considered as a transition to another page (Cart Page)
     *  so it is implemented using the 2 ways of initializing the driver in the BrowserActions Class
     *  1- HashMap drivers   2- ThreadLocal drivers
     */
    public Cart clickOnCartIcon_hashMap()
    {
        uiActions.clickOn(cartIconButton , SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Cart(key);
    }
    public Cart clickOnCartIcon_threadLocal()
    {
        uiActions.clickOn(cartIconButton , SeleniumWebUIActions.LocatorTypes.XPATH , 10 , SeleniumWebUIActions.ExpectedConditionsEnum.ELEMENT_CLICKABLE);
        return new Cart();
    }

    // ============ to-do later ============
    public void sortProducts(SortProductsEnum sortType)
    {

    }
    public enum SortProductsEnum
    {
        A_TO_Z,
        Z_TO_A,
        HIGH_TO_LOW,
        LOW_TO_HIGH
    }

}
