import Actions.SeleniumBrowserActions;
import POM.Cart;
import POM.Login;
import POM.Products;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest
{
    @BeforeMethod
    void setup()
    {
        // Using HashMap:
        String threadID = String.valueOf(Thread.currentThread().threadId());
        SeleniumBrowserActions.createWebDriver(SeleniumBrowserActions.BrowserName.CHROME , threadID);
        SeleniumBrowserActions.maximizeWindow(threadID);
        SeleniumBrowserActions.deleteCookies(threadID);

        //Using ThreadLocal:
        //SeleniumBrowserActions.createWebDriver(SeleniumBrowserActions.BrowserName.CHROME);
        //SeleniumBrowserActions.maximizeWindow();
        //SeleniumBrowserActions.deleteCookies();
    }
    @AfterMethod
    void tearDown()
    {
        // Using HashMap:
        String threadID = String.valueOf(Thread.currentThread().threadId());
        SeleniumBrowserActions.closeWebDriver(threadID);

        //Using ThreadLocal
        //SeleniumBrowserActions.closeWebDriver();
    }
    @Test(dataProvider = "validUserNames")
    public void testValidLogin(String validUsername)
    {
        String threadID = String.valueOf(Thread.currentThread().threadId());
        Login login = new Login(threadID);

        String actualURL;
        String expectedURL = "https://www.saucedemo.com/v1/inventory.html";
        login.navigateToLoginPage();
        actualURL = login.login_hashMap(validUsername , "secret_sauce").getPageURL();
        Assert.assertEquals(actualURL , expectedURL , "Invalid Credentials!");
    }
    @Test
    public void testInvalidLogin()
    {
        String threadID = String.valueOf(Thread.currentThread().threadId());
        Login login = new Login(threadID);

        String actualErrorMessage;
        String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";

        login.navigateToLoginPage();
        login.login_hashMap("locked_out_user" , "secret_sauce");
        actualErrorMessage = login.getErrorMessage();
        Assert.assertEquals(actualErrorMessage , expectedErrorMessage , "locked_out_user can NOT Login");
    }

    @DataProvider(name = "validUserNames", parallel = true)
    public String[] getValidUserNames()
    {
        return new String[]{"standard_user" , "problem_user" , "performance_glitch_user"};
    }

}
