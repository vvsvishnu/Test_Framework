package TestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.BaseClass;
import Common.ExcelConfiguration;

public class Login_Dashboard extends BaseClass {
	static ExcelConfiguration excel;
	WebDriverWait wait;

	@Test(priority = 1)
	public void verifyLogin() throws Exception {
		ExcelConfiguration excel = new ExcelConfiguration();
		driver.get(excel.getData("ObjectRepository", 1, 1));
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("verifyDealerName_xpath"))));
		String dealerName = driver.findElement(By.xpath(prop.getProperty("verifyDealerName_xpath"))).getText();
		System.out.println(dealerName);
		if (dealerName.equals(prop.getProperty("dealerNameOriginal"))) {
			Assert.assertEquals(dealerName, prop.getProperty("dealerNameOriginal"));
			System.out.println("User has logged in to " + dealerName + " application successfully");
		} else {
			Assert.fail();
			System.out.println("Test case failed");
		}

	}

	@Test(priority = 2, dependsOnMethods = { "verifyLogin" }, alwaysRun = true)
	public void New_Vehicle_Button_Verification() throws InterruptedException {
		driver.findElement(By.xpath(prop.getProperty("newCarsBtn_xpath"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("dealBtn_xpath"))));
		// Get the page number
		String pageNoText = driver.findElement(By.xpath(prop.getProperty("totalPageText_xpath"))).getText();
		String lastWord = pageNoText.substring(pageNoText.lastIndexOf(" ") + 1);
		String firstword = pageNoText.substring(0, pageNoText.indexOf(" "));
		int firstpageNumb = Integer.parseInt(firstword);
		int lastpageNumb = Integer.parseInt(lastWord);
		System.out.println(lastpageNumb);

		for (int i = firstpageNumb; i <= lastpageNumb; i++) {
			List<WebElement> dealBtnCount = driver.findElements(By.xpath(prop.getProperty("dealBtn_xpath")));
			if (dealBtnCount.size() > 0) {
				Assert.assertTrue(true, "Deal button is present");
				System.out.println("Button verification of page " + i + " successful");
				System.out.println("No of deal buttons present : " + dealBtnCount.size());
			} else {
				Assert.fail();
			}
			driver.findElement(By.xpath(prop.getProperty("nextPageBtn_xpath"))).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("dealBtn_xpath"))));

		}

		driver.findElement(By.xpath(prop.getProperty("verifyDealerName_xpath"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("newCarsBtn_xpath"))));

	}
	
	@Test(priority = 3, dependsOnMethods = { "New_Vehicle_Button_Verification" }, alwaysRun = true)
	public void Preowned_Vehicle_Button_Verification() throws InterruptedException {
		driver.findElement(By.xpath(prop.getProperty("preownedCarsBtn_xpath"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("dealBtn_xpath"))));
		// Get the page number
		String pageNoText = driver.findElement(By.xpath(prop.getProperty("totalPageText_xpath"))).getText();
		String lastWord = pageNoText.substring(pageNoText.lastIndexOf(" ") + 1);
		String firstword = pageNoText.substring(0, pageNoText.indexOf(" "));
		int firstpageNumb = Integer.parseInt(firstword);
		int lastpageNumb = Integer.parseInt(lastWord);
		System.out.println(lastpageNumb);

		for (int i = firstpageNumb; i <= lastpageNumb; i++) {
			List<WebElement> dealBtnCount = driver.findElements(By.xpath(prop.getProperty("dealBtn_xpath")));
			if (dealBtnCount.size() > 0) {
				Assert.assertTrue(true, "Deal button is present");
				System.out.println("Button verification of page " + i + " successful");
				System.out.println("No of deal buttons present : " + dealBtnCount.size());
			} else {
				Assert.fail();
			}
			driver.findElement(By.xpath(prop.getProperty("nextPageBtn_xpath"))).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("dealBtn_xpath"))));

		}

		driver.findElement(By.xpath(prop.getProperty("verifyDealerName_xpath"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("newCarsBtn_xpath"))));

	}


}
