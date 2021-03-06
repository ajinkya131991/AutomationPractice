package testBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.apache.commons.io.FileUtils;

public class TestBase 
{
public static final Logger log = Logger.getLogger(TestBase.class.getName());
	
	public WebDriver driver;
	String browser = "chrome";
	String url= "http://practice.automationtesting.in/";
	
	public void Selectbrowser(String browser)
	{
		if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.firefox.driver", System.getProperty("user.dir")+"\\src\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
	    }		
	}
	
	public void getURL(String url)
	{
		driver.get(url);
		log.info("2. Enter the URL http://practice.automationtesting.in ");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
	}
	
    public void init() throws IOException
    {    	
    	Selectbrowser(browser);
    	log.info("1. Open the browser : " +browser);
    	getURL(url);
    	log.info("2. Enter the URL " +url);
    	String log4jconfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jconfPath);	
    }
    
    public void getScreenShot(String Name)   // ScreenShot
	{
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "\\src\\main\\java\\ScreenShot\\";
			File destFile = new File((String) reportDirectory +"_"+  Name + "_" + formater.format(calender.getTime()) + ".png");
			FileUtils.copyFile(src, destFile);
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "'height='100' width='100'/> </a>");
		} catch (IOException e) {
				e.printStackTrace();
		}     
	}
    
    public void waitForElement(WebDriver driver, int timeOutInSeconds, WebElement element) {     // Wait for visibility
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public WebElement waitForElement(WebDriver driver, WebElement element, long timeOutInSeconds) {    // Wait for element clickability
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}
	
	public void waitForLoad(WebDriver driver) 
	{
	        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver driver)
	                    {
	                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	                    }
	                };
	        WebDriverWait wait = new WebDriverWait(driver, 30);
	        wait.until(pageLoadCondition);
	    }			
	
    public void closeBrowser() 
    {   
		driver.quit();	
	}

}
