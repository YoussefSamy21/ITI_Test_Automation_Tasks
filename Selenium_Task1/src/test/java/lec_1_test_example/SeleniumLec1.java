package lec_1_test_example;


import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Month;
import java.util.List;

public class SeleniumLec1
{
    WebDriver mydriver = new ChromeDriver();
    By text_id = By.id("my-text-id");
    By header_xpath = By.xpath("/html/body/main/div/div/div/h1");
    By password_xpath = By.xpath("/html/body/main/div/form/div/div[1]/label[2]/input");
    By textarea_xpath = By.xpath("/html/body/main/div/form/div/div[1]/label[3]/textarea");
    By submit_button_xpath = By.xpath("/html/body/main/div/form/div/div[2]/button");
    By success_xpath = By.xpath("/html/body/main/div/div[1]/div/h1");
    @Test
    public void task1()
    {
        mydriver.get("https://www.selenium.dev/selenium/web/web-form.html");
        mydriver.manage().window().maximize();
        mydriver.findElement(text_id).click();
        mydriver.findElement(text_id).sendKeys("youssef samy");
        String acual_header_text = mydriver.findElement(header_xpath).getText();

        // Password and Text area fields
        mydriver.findElement(password_xpath).click();
        mydriver.findElement(password_xpath).sendKeys("Youssef_2001");

        mydriver.findElement(textarea_xpath).click();
        mydriver.findElement(textarea_xpath).sendKeys("I'm gonna be an SDET");
        
//        mydriver.close();

    }
    @Test
    public void task2()
    {
        mydriver.get("https://www.selenium.dev/selenium/web/web-form.html");
        mydriver.manage().window().maximize();

        // Dropdown menu
        WebElement myelement = mydriver.findElement(new By.ByXPath("/html/body/main/div/form/div/div[2]/label[1]/select"));
        Select mydropwdown = new Select(myelement);
        mydropwdown.selectByVisibleText("Two");


        // Dropdown (DataList)
        WebElement inputdatalist = mydriver.findElement(By.xpath("/html/body/main/div/form/div/div[2]/label[2]/input"));
        List<WebElement> mydatalist = mydriver.findElements(By.xpath("//*[@id=\"my-options\"]/*"));

        // handling the NotFound in datalist scenario
        boolean found = false;
        for(int i = 0 ; i<mydatalist.size() ; i++)
        {
            String text =mydatalist.get(i).getDomAttribute("value");
            if(text.equalsIgnoreCase("Seattle"))
            {
                inputdatalist.sendKeys("Seattle");
                found = true;
                break;
            }
        }
        // Handling Invalid option
        if(!found)
            System.out.println("Not Found in DataList");

        // CheckBoxes
        mydriver.findElement(new By.ById("my-check-2")).click();
        // Radio Button
        mydriver.findElement(new By.ById("my-radio-1")).click();
        // File Input
        WebElement fileinput = mydriver.findElement(new By.ByXPath("/html/body/main/div/form/div/div[2]/label[3]/input"));
        fileinput.sendKeys("C:\\Users\\Youssef Samy\\Desktop\\youssef\\Me.jpg");

//        WebElement colorpicker = mydriver.findElement(new By.ByXPath("/html/body/main/div/form/div/div[3]/label[1]/input"));
//        colorpicker.sendKeys("#FF0000");

        // Click on Submit Button
        mydriver.findElement(submit_button_xpath).click();

        String actual_success_msg = mydriver.findElement(success_xpath).getText();
        Assert.assertEquals(actual_success_msg , "Form submitted");

    }
    @Test
    public void task3() throws InterruptedException {
        mydriver.get("https://www.selenium.dev/selenium/web/dynamic.html");
        mydriver.manage().window().maximize();
        WebElement revealbutton = mydriver.findElement(By.id("reveal"));
        WebElement inputtextbox = mydriver.findElement(By.id("revealed"));

        revealbutton.click();
        WebDriverWait wait = new WebDriverWait(mydriver , Duration.ofSeconds(100));
        WebElement myelement = wait.until(ExpectedConditions.visibilityOf(inputtextbox));
        inputtextbox.sendKeys("Automation :)");
    }
    @Test
    public void bonusTask()
    {
        mydriver.get("https://www.selenium.dev/selenium/web/web-form.html");
        mydriver.manage().window().maximize();
        // colorpicker
        WebElement colorpicker = mydriver.findElement(new By.ByXPath("/html/body/main/div/form/div/div[3]/label[1]/input"));
        colorpicker.sendKeys("#FF0000");


        // ########## Date Picker ##########
        WebElement dateinputfield = mydriver.findElement(By.xpath("/html/body/main/div/form/div/div[3]/label[2]/input"));
        dateinputfield.click();
        WebElement previousButton = mydriver.findElement(By.xpath("/html/body/div/div[1]/table/thead/tr[2]/th[1]"));
        WebElement nextButton = mydriver.findElement(By.xpath("/html/body/div/div[1]/table/thead/tr[2]/th[3]"));
        WebElement monthyearText = mydriver.findElement(By.xpath("/html/body/div/div[1]/table/thead/tr[2]/th[2]"));

        // Choosing the Month & Year : August 2024
        int monthTestData = 8;
        int yearTestData = 2024;
        int dayTestData = 21;
        while(true)
        {
            String month_year_str = monthyearText.getText();
            // Split the String by month & year
            String[] parts = month_year_str.split(" ");
            if(Integer.parseInt(parts[1]) == yearTestData)
            {
                int month = Month.valueOf(parts[0].toUpperCase()).getValue();
                if(month > monthTestData)
                    previousButton.click();
                else if(month < monthTestData)
                    nextButton.click();
                else
                    break;
            }
            else
            {
                if(Integer.parseInt(parts[1]) > yearTestData)
                    previousButton.click();
                else
                    nextButton.click();
            }
        }
        // Choosing the day
        List <WebElement> daytable = mydriver.findElements(By.cssSelector("td.day:not(.old):not(.new)"));
        for(int i = 0 ; i<daytable.size() ; i ++)
        {
            int day = Integer.parseInt(daytable.get(i).getText());
            if(day == dayTestData)
            {
                daytable.get(i).click();
                break;
            }
        }
        // DeClicking on ANY Outer Element
        mydriver.findElement(By.cssSelector("body")).click();



        // ######## Example Range ########
        WebElement rangeelement = mydriver.findElement(By.xpath("/html/body/main/div/form/div/div[3]/label[3]/input"));
        Actions slideraction = new Actions(mydriver);
        // Range Based Calculations
        int sliderwidth = rangeelement.getSize().getWidth();
        int sliderMax = Integer.parseInt(rangeelement.getDomAttribute("max"));
        int sliderMin = Integer.parseInt(rangeelement.getDomAttribute("min"));
        int sliderStep = Integer.parseInt(rangeelement.getDomAttribute("step"));
        int sliderStepsCount = (sliderMax - sliderMin) / sliderStep;
        int pixelsPerStep = sliderwidth / sliderStepsCount;

        int stepsTestData = 4 * pixelsPerStep;
        slideraction.clickAndHold(rangeelement).moveByOffset(stepsTestData, 0).release().perform();
    }
}
