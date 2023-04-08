package testBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseClass {

	public static WebDriver driver;// webdriver sould be in base class
	
	public Logger logger;// for logging on import apache log4j2
	
	public ResourceBundle rb;
	
	@BeforeClass(groups= {"Master","Sanity","Regression"})
	@Parameters("browser")
	public void setup(String br)
	{
		rb=ResourceBundle.getBundle("config");//Load config.properties file
		
		//LogManager---Pre-defined class in the log, getLogger() is statin method
		//this.getClass() it will mention the class name
		logger=LogManager.getLogger(this.getClass());
		
		//WebDriverManager.firefoxdriver().setup();
		
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");// Bypass the String io and socket exception
		//To remove message from the web browser Chrome is being controlled by automated test software.
	    options.setExperimentalOption("excludeSwitches",new String[] {"enable-automation"});
		/*	options.addArguments("--disable notifications");
		DesiredCapabilities cp=new DesiredCapabilities();
		cp.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(cp);   */
		if (br.equals("chrome"))
		{
			driver=new ChromeDriver(options); 
		}
		else if (br.equals("edge"))
		{
			driver=new EdgeDriver();
		}
		else
		{
			driver=new FirefoxDriver();
		}		
		driver.manage().deleteAllCookies();// delete all the browser specific cookies
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get(rb.getString("appURL"));
		driver.manage().window().maximize();
	}
	
	
	@AfterClass(groups= {"Master","Sanity","Regression"})
	public void tearDown()
	{
	driver.quit();
	}
	
	public String randomString() {
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber() {
		String generatedString2=RandomStringUtils.randomNumeric(10);
		return generatedString2;
	}	
	
	public String randomAlphaNumeric() {                 //for both char and numbers
		String str=RandomStringUtils.randomAlphabetic(4);
		String num=RandomStringUtils.randomAlphanumeric(3);
		return (str+"@"+num); 
	}


	public String captureScreen(String tname) {
		
	/*	SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
		Date dt=new Date();
		String timestamp=df.format(dt);   */
	    //Instead 3 lines of code following is only 1 line to create a time stamp
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		//converting TakesScreenshot interface to driver instance
		TakesScreenshot takescreenshot=(TakesScreenshot)driver;
		File source = takescreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		try {
			FileUtils.copyFile(source,new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		  return destination;    
		
		
	}	
}











