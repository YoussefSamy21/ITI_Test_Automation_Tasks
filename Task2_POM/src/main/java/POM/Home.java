package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.testng.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Home
{
    WebDriver driver;
    String homeURL = "https://www.levelset.com/";

    // Locators:
    String getPaid = "//a[contains(text(),'Get paid')]";

    public Home(WebDriver driver) // from TestClass
    {
        this.driver = driver;
    }
    public void navigateToHomePage()
    {
        driver.get(homeURL);
        By expectedElement = new By.ByXPath(getPaid);
        // Post Validation
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(expectedElement));
    }
    public void clickOnGetPaid()
    {
        By getPaidElement = new By.ByXPath(getPaid);
        // Pre Validation:
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(getPaidElement));

        driver.findElement(getPaidElement).click();

        // Post Validation: (Checking on Existence of WebElement in Another Page: "Exchange a Waiver document")
        SelectDocument selectDocObj = new SelectDocument(driver);
        String expectedElementString = String.format(selectDocObj.doc , "Exchange a Waiver");
        By expectedElement = new By.ByXPath(expectedElementString);
        try
        {
            new WebDriverWait(driver,Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(expectedElement));
        }
        catch (Exception e)
        {
            // Exception Handling: Try Double Click using Action Class in Selenium
            Actions action = new Actions(driver);
            action.doubleClick(driver.findElement(getPaidElement)).perform();

            // Check if expected element is Still not Found (by returning Null from the explicit wait)
            WebElement element = new WebDriverWait(driver,Duration.ofSeconds(5))
                                    .until(ExpectedConditions.presenceOfElementLocated(expectedElement));
            if(element == null)
            {
                System.out.println("Expected Element NOT Found");
            }
        }

    }

}
