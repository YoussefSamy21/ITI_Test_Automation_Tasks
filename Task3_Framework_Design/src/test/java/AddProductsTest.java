import Actions.SeleniumBrowserActions;
import POM.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddProductsTest
{
    @BeforeMethod
    void setup()
    {

        // Using HashMap:
        //String threadID = String.valueOf(Thread.currentThread().threadId());
        //SeleniumBrowserActions.createWebDriver(SeleniumBrowserActions.BrowserName.CHROME , threadID);
        //SeleniumBrowserActions.maximizeWindow(threadID);
        //SeleniumBrowserActions.deleteCookies(threadID);

        //Using ThreadLocal:
        SeleniumBrowserActions.createWebDriver(SeleniumBrowserActions.BrowserName.CHROME);
        SeleniumBrowserActions.maximizeWindow();
        SeleniumBrowserActions.deleteCookies();
    }
    @AfterMethod
    void tearDown()
    {
        // Using HashMap:
        // String threadID = String.valueOf(Thread.currentThread().threadId());
        // SeleniumBrowserActions.closeWebDriver(threadID);

        // Using ThreadLocal
        SeleniumBrowserActions.closeWebDriver();
    }

    @Test(dataProvider = "Products Names")
    public void addOrRemoveProductToCarts_StandardUser(String productName)
    {
        // SoftAssert softassert = new SoftAssert();
        String expectedButtonText_addProduct = "REMOVE";
        String actualButtonText_addProduct;
        String expectedButtonText_removeProduct = "ADD TO CART";
        String actualButtonText_removeProduct;
        Login login = new Login();
        login.navigateToLoginPage();
        Products products = login.login_threadLocal("standard_user" , "secret_sauce");

        // Validate Add Product Feature
        products.addProductToCart(productName);
        actualButtonText_addProduct = products.getButtonText(productName);
        Assert.assertEquals(actualButtonText_addProduct, expectedButtonText_addProduct, "Failed to add: " + productName);

        // Validate Remove Product Feature
        products.removeProduct(productName);
        actualButtonText_removeProduct = products.getButtonText(productName);
        Assert.assertEquals(actualButtonText_removeProduct , expectedButtonText_removeProduct , "Failed to remove: " + productName);

        // softassert.assertAll();
    }

    @Test(dataProvider = "Products Names")
    public void addOrRemoveProductToCarts_ProblemUser(String productName)
    {
        String expectedButtonText_addProduct = "REMOVE";
        String actualButtonText_addProduct;
        String expectedButtonText_removeProduct = "ADD TO CART";
        String actualButtonText_removeProduct;
        Login login = new Login();
        login.navigateToLoginPage();
        Products products = login.login_threadLocal("problem_user" , "secret_sauce");

        // Validate Add Product Feature
        products.addProductToCart(productName);
        actualButtonText_addProduct = products.getButtonText(productName);
        Assert.assertEquals(actualButtonText_addProduct, expectedButtonText_addProduct, "Failed to add: " + productName);

        // Validate Remove Product Feature
        products.removeProduct(productName);
        actualButtonText_removeProduct = products.getButtonText(productName);
        Assert.assertEquals(actualButtonText_removeProduct , expectedButtonText_removeProduct , "Failed to remove: " + productName);
    }

    @DataProvider(name = "Products Names") //parallel = true)
    public String[] getProductsNames()
    {
        return new String[]{"Backpack" , "Bike" , "Bolt T-Shirt" , "Jacket" , "Onesie" , "(Red)"};
    }

}
