import Actions.SeleniumBrowserActions;
import POM.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CompletePurchaseTest
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
    public void SingleProductCheckoutTest_StandardUser(String productName)
    {
        SoftAssert softassert = new SoftAssert();
        double actualProductPrice ,actualItemsPrice , actualTaxPrice , actualTotalPrice;
        double expectedTotalPrice;
        Login login = new Login();
        login.navigateToLoginPage();
        Products products = login.login_threadLocal("standard_user" , "secret_sauce");
        products.addProductToCart(productName);
        Cart cart = products.clickOnCartIcon_threadLocal();
        CheckoutStepOne checkoutstep1 = cart.proceedToCheckOut_threadLocal();
        checkoutstep1.checkoutInformation("yousfff" , "dsaaemyyy" , "2180");
        CheckoutStepTwo checkoutstep2 = checkoutstep1.continueCheckOut_threadLocal();

        actualProductPrice = checkoutstep2.getProductPrice(productName);
        actualItemsPrice = checkoutstep2.getItemsPrice();
        actualTaxPrice = checkoutstep2.getTaxPrice();
        actualTotalPrice = checkoutstep2.getTotalPrice();

        // to get 2 digits only after the decimal point:
        expectedTotalPrice = Math.round((actualTaxPrice + actualItemsPrice) * 100.0) / 100.0;
        softassert.assertEquals(actualProductPrice , actualItemsPrice , "For ProductName: "+ productName + ", Sum of the Products Prices in the Cart does not Match the total Item price");
        softassert.assertEquals(actualTotalPrice , expectedTotalPrice , "For ProductName: "+ productName + ", Total Price does not Match Items + Tax");
        softassert.assertAll();
    }

    @Test(dataProvider = "Products Names")
    public void SingleProductCheckoutTest_ProblemUser(String productName)
    {
        SoftAssert softassert = new SoftAssert();
        double actualProductPrice ,actualItemsPrice , actualTaxPrice , actualTotalPrice;
        double expectedTotalPrice;
        Login login = new Login();
        login.navigateToLoginPage();
        Products products = login.login_threadLocal("problem_user" , "secret_sauce");
        products.addProductToCart(productName);
        Cart cart = products.clickOnCartIcon_threadLocal();
        CheckoutStepOne checkoutstep1 = cart.proceedToCheckOut_threadLocal();
        checkoutstep1.checkoutInformation("yousfff" , "dsaaemyyy" , "2180");
        CheckoutStepTwo checkoutstep2 = checkoutstep1.continueCheckOut_threadLocal();

        actualProductPrice = checkoutstep2.getProductPrice(productName);
        actualItemsPrice = checkoutstep2.getItemsPrice();
        actualTaxPrice = checkoutstep2.getTaxPrice();
        actualTotalPrice = checkoutstep2.getTotalPrice();

        // to get 2 digits only after the decimal point:
        expectedTotalPrice = Math.round((actualTaxPrice + actualItemsPrice) * 100.0) / 100.0;
        softassert.assertEquals(actualProductPrice , actualItemsPrice , "For ProductName: "+ productName + ", Sum of the Products Prices in the Cart does not Match the total Item price");
        softassert.assertEquals(actualTotalPrice , expectedTotalPrice , "For ProductName: "+ productName + ", Total Price does not Match Items + Tax");
        softassert.assertAll();
    }

    //===================== Adding ALL Products CheckOut with different Users =====================

    @Test(dataProvider = "UserNames")
    public void allProductsCheckoutTest(String username)
    {
        String[] productNames = {"Backpack" , "Bike" , "Bolt T-Shirt" , "Jacket" , "Onesie" , "(Red)"};
        SoftAssert softassert = new SoftAssert();
        double actualProductsPriceSum = 0.0 , totalItemsPrice, TaxPrice, TotalPrice;
        double expectedTotalPrice;

        Login login = new Login();
        login.navigateToLoginPage();
        Products products = login.login_threadLocal(username , "secret_sauce");
        // Add all Products to Cart
        for(String productName : productNames)
        {
            products.addProductToCart(productName);
        }
        Cart cart = products.clickOnCartIcon_threadLocal();
        CheckoutStepOne checkoutstep1 = cart.proceedToCheckOut_threadLocal();
        checkoutstep1.checkoutInformation("yousfff" , "dsaaemyyy" , "2180");
        CheckoutStepTwo checkoutstep2 = checkoutstep1.continueCheckOut_threadLocal();

        // Add actual Products Price
        for(String productName : productNames)
        {
            actualProductsPriceSum += checkoutstep2.getProductPrice(productName);
        }
        totalItemsPrice = checkoutstep2.getItemsPrice();
        // Assert that the Sum of Products Price = Item Total
        softassert.assertEquals(actualProductsPriceSum , totalItemsPrice , "Sum of Products Price in the Cart does NOT Match the Total Items Price");

        TaxPrice = checkoutstep2.getTaxPrice();
        TotalPrice = checkoutstep2.getTotalPrice();

        // to get 2 digits only after the decimal point:
        expectedTotalPrice = Math.round((TaxPrice + totalItemsPrice) * 100.0) / 100.0;

        // Assert that Total = Item_total + Tax
        softassert.assertEquals(TotalPrice, expectedTotalPrice , "Total Price does NOT Match Items + Tax");
        softassert.assertAll();
    }


    @DataProvider(name = "Products Names") //parallel = true)
    public String[] getProductsNames()
    {
        return new String[]{"Backpack" , "Bike" , "Bolt T-Shirt" , "Jacket" , "Onesie" , "(Red)"};
    }

    @DataProvider(name = "UserNames")
    public String[] getUserNames()
    {
        return new String[]{"standard_user" , "problem_user" , "performance_glitch_user"};
    }

}
