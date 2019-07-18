/**
 * 
 */
package test;

/**
 * @author Fredrick
 *
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class UITests {
	public static Properties config = new Properties();

	public static FileInputStream fis;
	public static WebDriverWait wait;
	public static String browser;

	       WebDriver driver;

	   @Test(priority=1)
	       public void openBrowser() throws InterruptedException, IOException {
	              if (driver == null) {
	                     fis = new FileInputStream(
	                                  System.getProperty("user.dir") + "\\Config\\Config.properties");
	                     config.load(fis);
	                     if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
	                           browser = System.getenv("browser");
	                     } else {
	                           browser = config.getProperty("browser");
	                     }
	                     config.setProperty("browser", browser);
	                     if (config.getProperty("browser").equals("firefox")) {
	                           System.setProperty("webdriver.gecko.driver", "\\resources\\geckodriver.exe");

	                           driver = new FirefoxDriver();
	                     } else if (config.getProperty("browser").equals("chrome")) {
	                           System.setProperty("webdriver.chrome.driver",
	                                         System.getProperty("user.dir") + "\\resources\\chromedriver.exe");
	                           driver = new ChromeDriver();
	                     } else if (config.getProperty("browser").equals("IE")) {
	                           System.setProperty("webdriver.ie.driver",
	                                         System.getProperty("user.dir") + "\\resources\\IEDriverServer.exe");
	                           driver = new InternetExplorerDriver();
	                     }
	                     driver.get(config.getProperty("baseURL"));
	                     driver.manage().window().maximize();

	             driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
                 wait = new WebDriverWait(driver, 5);
	              }
	       }
			//Verify Homepage Title
			@Test(priority = 2)
			public void VerifyHomepageTitle() throws InterruptedException {
				String expectedPageTitle = "Filling Out Forms - Ultimate QA";
				String actualPageTitle = "";
				System.out.println(expectedPageTitle);
				actualPageTitle = driver.getTitle();
				System.out.println(actualPageTitle);
				if (actualPageTitle.contains(expectedPageTitle)){
					System.out.println("Test Pass");
					}
				else {
					System.out.println("Test Fail");		
				}
				Thread.sleep(10);
			}
			//Fill Form
			@Test(priority = 3)
			public void FillForms() throws InterruptedException
				{
					//Name
					driver.findElement(By.xpath("//*[@id='et_pb_contact_name_0']")).sendKeys("Fredrick");
					//Message
					driver.findElement(By.xpath("//*[@id='et_pb_contact_message_0']")).sendKeys("Testing UI");
					//Submit
					driver.findElement(By.xpath("//*[@id='et_pb_contact_form_0']/div[2]/form/div/button")).click();
		            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
	                wait = new WebDriverWait(driver, 5);
				}
				
			//Verify success text message
		    @Test(priority = 4)
			public void VerifySuccessMessage() throws InterruptedException 
			    {
				    WebElement element=driver.findElement(By.xpath("//*[@id='et_pb_contact_form_0']/div/p"));
				    //To store the text of a WebElement in a string
				    String text = element.getText();
				    //For printing the text in console output
				    System.out.println("Text obtained is  "+ text);
				    if(text.contains("Form filled out successfully"))
						{
				    System.out.println("Expected text is obtained");
				    }else{
				    System.out.println("Expected text is not obtained");

				}
	       }
		    //Close Browser
	        @Test(priority = 5)
	        public void closeBrowser() throws InterruptedException 
	             {
	              driver.close();
	             }

}
