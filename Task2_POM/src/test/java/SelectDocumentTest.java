import POM.Home;
import POM.SelectDocument;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SelectDocumentTest
{
    WebDriver driver;
    Home home;
    SelectDocument selectdoc;

    String browser = "Chrome";

    @BeforeTest
    public void setup()
    {
        if(browser.equalsIgnoreCase("Chrome"))
        {
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("Firefox"))
        {
            driver = new FirefoxDriver();
        }
        else if(browser.equalsIgnoreCase("Edge"))
        {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        home = new Home(driver);
        selectdoc = new SelectDocument(driver);
    }
    @AfterTest
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void validateFreeDocumentsCount()
    {
        selectdoc.navigateToSelectDocumentPage();
        int actualCount = selectdoc.getFreeDocumentsCount();
        int expectedCount = 2;

        Assert.assertEquals(actualCount , expectedCount , "Free Documents Count Not Matches");
    }
    @Test
    public void validateFreeDocumentsNames()
    {
        selectdoc.navigateToSelectDocumentPage();
        String[] actualNames = selectdoc.getFreeDocumentsName();
        String[] expectedNames = {"Exchange a Waiver" , "Send a Payment Document"};

        Assert.assertEquals(actualNames[0],expectedNames[0]);
        Assert.assertEquals(actualNames[1],expectedNames[1]);
    }
    @Test
    public void validatePricedDocumentsCount()
    {
        selectdoc.navigateToSelectDocumentPage();
        int actualCount = selectdoc.getDocumentsWithPriceRangeCount(30 , 60);
        int expectedCount = 1;
        Assert.assertEquals(actualCount , expectedCount , "Priced Documents Count Not Matches");

    }
    @Test
    public void validatePricedDocumentsNames()
    {
        selectdoc.navigateToSelectDocumentPage();
        String[] actualNames = selectdoc.getDocumentsWithPriceRangeName(30,60);
        String expectedName = "Send a Warning";

        Assert.assertEquals(actualNames[0],expectedName);
    }

}
