package Actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;

public class SeleniumBrowserActions
{
    // Supporting Parallel Execution by 2 initializing the driver in 2 Ways:
    // 1-ThreadLocal<>, 2-HashMap(Key: ThreadId)
    private static HashMap<String,WebDriver> driversHashMap = new HashMap<>();
    private static ThreadLocal<WebDriver> driversThreadLocal = new ThreadLocal<>();
    public static enum BrowserName
    {
        CHROME,
        FIREFOX,
        EDGE
    }

    // using HashMap
    public static void createWebDriver(BrowserName browser ,String key)
    {
        switch (browser)
        {
            case CHROME :
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                driversHashMap.put(key , new ChromeDriver());
                break;
            case FIREFOX: driversHashMap.put(key, new FirefoxDriver());
            break;
            case EDGE:   driversHashMap.put(key , new EdgeDriver());
            break;
        }
    }
    public static void closeWebDriver(String key)
    {
        driversHashMap.get(key).quit();
        driversHashMap.remove(key);
    }

    // using ThreadLocal<WebDriver>
    public static void createWebDriver(BrowserName browser)
    {
        switch (browser)
        {
            case CHROME :
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                driversThreadLocal.set(new ChromeDriver(options));
                break;
            case FIREFOX: driversThreadLocal.set(new FirefoxDriver());
                break;
            case EDGE:    driversThreadLocal.set(new EdgeDriver());
                break;
        }
    }
    public static void closeWebDriver()
    {
        driversThreadLocal.get().quit();
    }

    // drivers Getters to use it in WebUIActions Class (Encapsulation)
    public static WebDriver getWebDriver(String key)
    {
        return driversHashMap.get(key);
    }
    public static WebDriver getWebDriver()
    {
        return driversThreadLocal.get();
    }

    public static void maximizeWindow()
    {
        driversThreadLocal.get().manage().window().maximize();
    }

    public static void maximizeWindow(String key)
    {
        driversHashMap.get(key).manage().window().maximize();
    }

    public static void deleteCookies(String key)
    {
        driversHashMap.get(key).manage().deleteAllCookies();
    }

    public static void deleteCookies()
    {
        driversThreadLocal.get().manage().deleteAllCookies();
    }
}
