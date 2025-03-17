package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SelectDocument
{
    WebDriver driver;
    String selectDocumentURL = "https://app.levelset.com/wizard/SelectDocument/";

    // ============= Locators ============= //
    // input: none , returns: doc names which are free
    String freeDocNameSelector = "//span[@class='price-amount' and text()='Free']/ancestor::div[@class='panel-heading left-right-pair']//div[@class='left']";

    // input: price range(%d) , returns: doc names
    String priceRangeDocNameSelector = "//span[@class='price-amount' and number(translate(text(),'$',''))>=%d and number(translate(text(),'$',''))<=%d]/ancestor::div[@class='panel-heading left-right-pair']//div[@class='left']";


    String doc = "//div[@class='left' and contains(text(),'%s')]";

    public SelectDocument(WebDriver driver)
    {
        this.driver = driver;
    }

    private boolean validateOnElement(By element , String s , long timeOutSeconds)
    {
        WebElement state = null;
        if(s.equalsIgnoreCase("Visible"))
        {
            state = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(element));
        }
        else if(s.equalsIgnoreCase("Clickable"))
        {
            state = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds))
                    .until(ExpectedConditions.elementToBeClickable(element));
        }
        else if(s.equalsIgnoreCase("Located"))
        {
            state = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds))
                    .until(ExpectedConditions.presenceOfElementLocated(element));
        }

        if(state != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void navigateToSelectDocumentPage()
    {
        driver.get(selectDocumentURL);
        By expectedElement = new By.ByXPath(String.format(doc,"Exchange a Waiver"));
        // Post Validation:
        validateOnElement(expectedElement , "Clickable" , 3);
    }
    public int getFreeDocumentsCount()
    {
        int count = 0;
        By expectedElement = new By.ByXPath(String.format(doc,"Exchange a Waiver"));
        //Pre Validation: Check any Document is Visible
        if(validateOnElement(expectedElement , "Visible" , 3))
        {
            count = driver.findElements(By.xpath(freeDocNameSelector)).size();
        }
        return count;
    }
    public String[] getFreeDocumentsName()
    {
        int count = getFreeDocumentsCount();
        String[] names = new String[count];

        By expectedElement = new By.ByXPath(String.format(doc,"Exchange a Waiver"));
        //Pre Validation: Check any Document is Visible
        if(validateOnElement(expectedElement , "Visible" , 3))
        {
            List<WebElement> elements = driver.findElements(By.xpath(freeDocNameSelector));

            for(int i = 0 ; i<elements.size(); i++)
            {
                names[i] = elements.get(i).getText();
            }
        }

        return names;
    }

    public int getDocumentsWithPriceRangeCount(int min , int max)
    {
       int count = 0;
       By pricedDocumentsLocator = new By.ByXPath(String.format(priceRangeDocNameSelector,min , max));

        By expectedElement = new By.ByXPath(String.format(doc,"Exchange a Waiver"));
        //Pre Validation: Check any Document is Visible
        if(validateOnElement(expectedElement , "Visible" , 3))
        {
            count = driver.findElements(pricedDocumentsLocator).size();
        }
       return count;
    }
    public String[] getDocumentsWithPriceRangeName(int x , int y)
    {
        int count = getDocumentsWithPriceRangeCount(x,y);
//        System.out.println(count);
        String[] names = new String[count];

        By pricedDocumentsLocator = new By.ByXPath(String.format(priceRangeDocNameSelector, x , y));

        By expectedElement = new By.ByXPath(String.format(doc,"Exchange a Waiver"));
        //Pre Validation: Check any Document is Visible
        if(validateOnElement(expectedElement, "Visible", 3))
        {
            List<WebElement> elements = driver.findElements(pricedDocumentsLocator);
            for(int i = 0 ; i<elements.size() ;i++)
            {
                names[i] = elements.get(i).getText();
            }
        }
        return names;
    }
}
