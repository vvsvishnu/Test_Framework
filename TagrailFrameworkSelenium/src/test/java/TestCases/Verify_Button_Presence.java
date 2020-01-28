package TestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.BaseClass;
import Common.WriteExcelData;;

public class Verify_Button_Presence extends BaseClass {
	WebDriverWait wait;
	String rouNumFull;
	int actualRowNum;

	@Test(dataProvider = "ObjectRepository")
	public void Verify_Button_Presence_All(String Main_URL, String Dealer_Name, String Dealer_Name_Xpath,
			String New_Inventory_Page_URL, String DealBtn_Xpath, String Total_Page_Text_Xpath,
			String Next_Page_Btn_Xpath, String Used_Inventory_Page_URL, String VDP_Click_Xpath,
			String VDP_New_Cars_Deal_Btn, String MSRP_Xpath, String Price_Xpath, String Frame_Id, String Get_Start_Btn,
			String Total_Inventory_New, String rowNumber) throws Exception {
		WriteExcelData report = new WriteExcelData();
		rouNumFull = rowNumber.substring(rowNumber.lastIndexOf(" ") + 1);
		actualRowNum = Integer.parseInt(rouNumFull);
		driver.get(Main_URL);
		wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Dealer_Name_Xpath)));
		/*
		 * String dealerName =
		 * driver.findElement(By.xpath(Dealer_Name_Xpath)).getText();
		 * System.out.println(dealerName); if (dealerName.equals(Dealer_Name)) {
		 * Assert.assertEquals(dealerName, Dealer_Name);
		 * System.out.println("User has logged in to " + dealerName +
		 * " application successful"); } else { Assert.fail();
		 * System.out.println("Test case failed"); }
		 */

		// Verify button presence in new inventory
		driver.get(New_Inventory_Page_URL);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DealBtn_Xpath)));

		// Get total inventory count
		String totalInventoryNew = driver.findElement(By.xpath(Total_Inventory_New)).getText();
		String firstWordTextNew = totalInventoryNew.substring(0, totalInventoryNew.indexOf(" "));
		int totalCountNew = Integer.parseInt(firstWordTextNew);
		System.out.println(totalCountNew);
		// Get the page number
		String pageNoText = driver.findElement(By.xpath(Total_Page_Text_Xpath)).getText();
		String lastWord = pageNoText.substring(pageNoText.lastIndexOf(" ") + 1);
		int lastpageNumb = Integer.parseInt(lastWord);
		int totalCountAllPageNew = 0;
		for (int i = 1; i <= lastpageNumb; i++) {
			List<WebElement> dealBtnCount = driver.findElements(By.xpath(DealBtn_Xpath));
			if (dealBtnCount.size() > 0) {

				totalCountAllPageNew += dealBtnCount.size();
				Assert.assertTrue(true, "Deal button is present");
				System.out.println("Button verification of new inventories page " + i + " is successful");
				System.out.println("No of deal buttons present : " + dealBtnCount.size());
				report.SetStatus(actualRowNum, "New", "Pass");
				List<WebElement> nextBtnCount = driver.findElements(By.xpath(Next_Page_Btn_Xpath));
				if (nextBtnCount.size() > 0) {
					driver.findElement(By.xpath(Next_Page_Btn_Xpath)).click();
				} else {
					System.out.println("New inventory verification successful");
				}

			} else {
				Assert.fail("Test Failed in page " + i);
				report.SetStatus(actualRowNum, "New", "Fail");
				System.out.println("Button not present in page : " + i);
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
			}

		}
		System.out.println("Total button count = " + totalCountAllPageNew);
		if (totalCountAllPageNew == totalCountNew) {
			Assert.assertEquals(totalCountAllPageNew, totalCountNew);
			System.out.println("Test case passed");
			report.SetStatus(actualRowNum, "New", "sdsd");
			
		} else {
			
			Assert.fail();
			report.SetStatus(actualRowNum, "New", "Fail");
			
		}
		// Verify button presence in used inventory
		driver.get(Used_Inventory_Page_URL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DealBtn_Xpath)));

		// Get total inventory count
		String totalInventoryUsed = driver.findElement(By.xpath(Total_Inventory_New)).getText();
		String firstWordTextUsed = totalInventoryUsed.substring(0, totalInventoryUsed.indexOf(" "));
		int totalCountUsed = Integer.parseInt(firstWordTextUsed);
		System.out.println(totalCountUsed);

		// Get the page number
		String pageNoText1 = driver.findElement(By.xpath(Total_Page_Text_Xpath)).getText();
		String lastWord1 = pageNoText1.substring(pageNoText1.lastIndexOf(" ") + 1);
		int lastpageNumb1 = Integer.parseInt(lastWord1);
		int totalCountAllPageUsed = 0;
		for (int j = 1; j <= lastpageNumb1; j++) {
			List<WebElement> dealBtnCount1 = driver.findElements(By.xpath(DealBtn_Xpath));
			if (dealBtnCount1.size() > 0) {

				totalCountAllPageUsed += dealBtnCount1.size();
				Assert.assertTrue(true, "Deal button is present");
				System.out.println("Button verification of used inventory page " + j + " is successful");
				System.out.println("No of deal buttons present : " + dealBtnCount1.size());
				report.SetStatus(actualRowNum, "Used", "Pass");
				List<WebElement> nextBtnCount1 = driver.findElements(By.xpath(Next_Page_Btn_Xpath));
				if (nextBtnCount1.size() > 0) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DealBtn_Xpath)));
					driver.findElement(By.xpath(Next_Page_Btn_Xpath)).click();
					
				} else {
					System.out.println("Pre-owned inventory verification successful");

				}
			} else {
				Assert.fail();
				report.SetStatus(actualRowNum, "Used", "Fail");
				System.out.println("Button not present in page : " + j);

			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
			}
		}
		System.out.println("Total button count = " + totalCountAllPageUsed);
		if (totalCountAllPageUsed == totalCountUsed) {
			Assert.assertEquals(totalCountAllPageUsed, totalCountUsed);
			System.out.println("Test case passed");
			System.out.println(actualRowNum);
			report.SetStatus(actualRowNum, "Used", "Pass");
		} else {
			report.SetStatus(actualRowNum, "Used", "Fail");
			Assert.fail();
		}

	}
}
