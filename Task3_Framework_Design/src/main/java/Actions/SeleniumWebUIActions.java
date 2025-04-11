package Actions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class SeleniumWebUIActions
{
    // Supporting Parallel Execution by 2 initializing the driver in 2 Ways:
    // 1-ThreadLocal<>, 2-HashMap(Key: ThreadId)

    private WebDriver driver;
    public enum LocatorTypes
    {
        XPATH,
        CSS,
        ID,
        CLASS_NAME,
        TAG_NAME
    }
    public enum ExpectedConditionsEnum
    {
        ELEMENT_CLICKABLE,
        ELEMENT_PRESENCE,
        ELEMENT_VISIBLE
    }

    public SeleniumWebUIActions(String key)
    {
        this.driver = SeleniumBrowserActions.getWebDriver(key);
    }
    public SeleniumWebUIActions()
    {
        this.driver = SeleniumBrowserActions.getWebDriver();
    }

    public void navigateToPage(String url)
    {
        driver.get(url);
    }

    public void navigateToPage(String url , String selector , LocatorTypes locator , long timeOut , ExpectedConditionsEnum expectedConditions)
    {
        driver.get(url);
        //Post Conditions:
        boolean postCondition = validateOnElement(selector, locator , timeOut , expectedConditions);
        if(!postCondition)
            throw new NoSuchElementException("Navigation Failed to this website: "+ url);

    }
    public String getPageURL()
    {
        return driver.getCurrentUrl();
    }

    // Click without Pre or Post Conditions
    public void clickOn(String selector , LocatorTypes locator)
    {
        getWebElement(selector,locator).click();
    }

    // Click with PreConditions
    public void clickOn(String selector , LocatorTypes locator , long preTimeOut , ExpectedConditionsEnum preExpectedConditions)
    {
        //PreConditions
        boolean preConditions = validateOnElement(selector , locator , preTimeOut , preExpectedConditions);
        if(!preConditions)
            throw new RuntimeException("Pre Conditions: " + preExpectedConditions.toString() + "Failed to Click on Element Selector: "+ selector);
        else
        {
            getWebElement(selector,locator).click();
            // try Handling Alerts if alerts appear
//            try
//            {
//                driver.switchTo().alert().accept();
//            } catch (NoAlertPresentException e)
//            {
//                //System.out.println("No alert was present.");
//            }

        }
    }

    // Click with PreConditions + PostConditions
    public void clickOn(String selector , LocatorTypes locator , long preTimeOut , ExpectedConditionsEnum preExpectedConditions , String expectedSelector , LocatorTypes expectedLocator , int postTimeOut , ExpectedConditionsEnum postExpectedConditions)
    {
        boolean preConditions , postConditions;
        //PreConditions:
        preConditions = validateOnElement(selector , locator , preTimeOut , preExpectedConditions);
        if(!preConditions)
            throw new RuntimeException("Pre Conditions: " + preExpectedConditions.toString() + "Failed to Click on Element Selector: " + selector);
        else
        {
            getWebElement(selector,locator).click();
            // Post Conditions:
            postConditions = validateOnElement(expectedSelector,expectedLocator , postTimeOut , postExpectedConditions);
            if(!postConditions)
            {
                throw new RuntimeException("Post Conditions: " + postExpectedConditions.toString() + "Failed after Click on Element Selector: " + expectedSelector);
            }
        }
    }

    // ============================= Double Click =============================

    // Double Click without Pre or Post Conditions
    public void doubleClickOn(String selector , LocatorTypes locator)
    {
        new Actions(driver).doubleClick(getWebElement(selector,locator)).build().perform();
    }

    // Double Click with PreConditions
    public void doubleClickOn(String selector , LocatorTypes locator , long preTimeOut , ExpectedConditionsEnum preExpectedConditions)
    {
        //PreConditions
        boolean preConditions = validateOnElement(selector , locator , preTimeOut , preExpectedConditions);
        if(!preConditions)
            throw new RuntimeException("Pre Conditions: " + preExpectedConditions.toString() + "Failed to Double Click on Element Selector: " + selector);
        else
        {
            new Actions(driver).doubleClick(getWebElement(selector,locator)).build().perform();
        }
    }

    // Click with PreConditions + PostConditions
    public void doubleClickOn(String selector , LocatorTypes locator , long preTimeOut , ExpectedConditionsEnum preExpectedConditions , String expectedSelector , LocatorTypes expectedLocator , int postTimeOut , ExpectedConditionsEnum postExpectedConditions)
    {
        boolean preConditions , postConditions;
        //PreConditions
        preConditions = validateOnElement(selector , locator , preTimeOut , preExpectedConditions);
        if(!preConditions)
            throw new RuntimeException("Pre Conditions: " + preExpectedConditions.toString() + "Failed to Double Click on Element Selector: " + selector);
        else
        {
            new Actions(driver).doubleClick(getWebElement(selector,locator)).build().perform();
            // Post Conditions:
            postConditions = validateOnElement(expectedSelector,expectedLocator , postTimeOut , postExpectedConditions);
            if(!postConditions)
            {
                throw new RuntimeException("Post Conditions: " + postExpectedConditions.toString() + "Failed after Double Click on Element Selector: " + expectedSelector);
            }
        }
    }

    // ============================= Send Text =============================

    // Send Text without Pre or Post Conditions
    public void sendText(String selector , LocatorTypes locator , String text)
    {
        getWebElement(selector,locator).sendKeys(text);
    }

    // Send Text with Pre Conditions
    public void sendText(String text , String selector , LocatorTypes locator , long preTimeOut , ExpectedConditionsEnum preExpectedConditions)
    {
        //PreConditions
        boolean preConditions = validateOnElement(selector , locator , preTimeOut , preExpectedConditions);
        if(!preConditions)
            throw new RuntimeException("Pre Conditions: " + preExpectedConditions.toString() + "Failed to Send Text on Element Selector: " + selector);
        else
        {
            getWebElement(selector,locator).sendKeys(text);
        }
    }

    // ============================= get Text =============================

    // get Text without Pre or Post Conditions
    public String getText(String selector , LocatorTypes locator)
    {
        return getWebElement(selector,locator).getText();
    }

    // get Text with Pre Conditions
    public String getText(String selector , LocatorTypes locator , long preTimeOut , ExpectedConditionsEnum preExpectedConditions)
    {
        //PreConditions
        boolean preConditions = validateOnElement(selector , locator , preTimeOut , preExpectedConditions);
        if(!preConditions)
            throw new RuntimeException("Pre Conditions: " + preExpectedConditions.toString() + "Failed to Get Text on Element Selector: " + selector);
        else
        {
            return getWebElement(selector,locator).getText();
        }
    }

    // ============================= ClearText =============================

    // Clear Text without Pre or Post Conditions
    public void clearText(String selector , LocatorTypes locator)
    {
        getWebElement(selector,locator).clear();
    }
    // Clear Text with Pre Conditions
    public void clearText(String selector , LocatorTypes locator , long preTimeOut , ExpectedConditionsEnum preExpectedConditions)
    {
        //PreConditions
        boolean preConditions = validateOnElement(selector , locator , preTimeOut , preExpectedConditions);
        if(!preConditions)
            throw new RuntimeException("Pre Conditions: " + preExpectedConditions.toString() + "Failed to Clear Text on Element Selector: " + selector);
        else
        {
            getWebElement(selector,locator).clear();
        }
    }

    public boolean validateOnElement(String selector , LocatorTypes locator , long timeOut , ExpectedConditionsEnum expectedConditions)
    {
        By elementLocator = getWebElementLocator(selector,locator);
        WebElement element = waitExplicitlyOnElement(elementLocator , timeOut , expectedConditions);
        if(element != null)
            return true;
        else
            return false;
    }


    /*
     *  Private Methods used ONLY in Implementation of this Class,
     *  Where They Should NOT be used in POM since they are based on Selenium Library (return WebElement and By)
     *  That's to Maintain the Full Abstraction between (TestClasses & POM) and The Classes using Selenium Library
     */

    private WebElement waitExplicitlyOnElement(By elementLocator , long timeOut, ExpectedConditionsEnum expectedConditions)
    {
        WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        switch (expectedConditions)
        {
            case ELEMENT_VISIBLE:   return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            case ELEMENT_CLICKABLE: return wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
            case ELEMENT_PRESENCE:  return wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            default: return null;
        }
    }

    private WebElement getWebElement(String selector , LocatorTypes locator)
    {
        return driver.findElement(getWebElementLocator(selector,locator));
    }

    private By getWebElementLocator(String selector , LocatorTypes locator)
    {
        switch (locator)
        {
            case ID:    return new By.ById(selector);
            case XPATH: return new By.ByXPath(selector);
            case CSS:   return new By.ByCssSelector(selector);
            case CLASS_NAME: return new By.ByClassName(selector);
            case TAG_NAME:  return new By.ByTagName(selector);
            default:    return null;
        }
    }

}
