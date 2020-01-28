package Common;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class BaseClass {
	ChromeDriverService Capabilities;

	public static WebDriver driver;
	public static Properties prop;

	@BeforeMethod
	public void InitializeBrowser() throws Exception {

		// Read data from excel
		String projectpath = System.getProperty("user.dir");
		// Read data from config file

		prop = new Properties();

		FileInputStream fis = new FileInputStream(projectpath + "/Configuration/Config.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");

		// Configuring Browser
		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", projectpath + "/Drivers/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();

		} else if (browserName.equals("FireFox")) {
			System.setProperty("webdriver.gecko.driver", projectpath + "/Drivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}

		else if (browserName.equals("Mobile")) {
			System.out.println(browserName);
			System.setProperty("webdriver.chrome.driver", projectpath + "/Drivers/chromedriver.exe");

			 ChromeOptions options = new ChromeOptions();
		        options.addArguments("window-size=700,850");
		        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
		                          UnexpectedAlertBehaviour.IGNORE);
		        driver = new ChromeDriver(options);

				
		}
		// driver.get(prop.getProperty("URL"));

		// manage windows

		// Implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@DataProvider(name = "ObjectRepository")
	public Object[][] locators() throws Exception {
		Object[][] data = ReadExcelData.testData(
				"D:/Users/Vishnu VS/Work/Selenium/TagrailAutomation/TagrailFrameworkSelenium/ExcelUtils/Configuration.xlsx",
				"TestData");
		return data;
	}


	@AfterMethod
	public void tearDown() {

		 driver.quit();

	}

}
