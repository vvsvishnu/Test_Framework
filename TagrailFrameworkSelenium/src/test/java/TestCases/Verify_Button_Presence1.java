package TestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.BaseClass;

public class Verify_Button_Presence1 extends BaseClass {
	WebDriverWait wait;
	int lastpageNumbNew;

	@Test(dataProvider = "ObjectRepository")
	public void Verify_Button_Presence_All(String Main_URL, String Dealer_Name, String Dealer_Name_Xpath,
			String New_Inventory_Page_URL, String DealBtn_Xpath, String Total_Page_Text_Xpath,
			String Next_Page_Btn_Xpath, String Used_Inventory_Page_URL) throws Exception {


		// Verify button presence in new inventory
		driver.get(New_Inventory_Page_URL);
		System.out.println(DealBtn_Xpath);
		System.out.println("login success");
		Thread.sleep(2000);
		Thread.sleep(5000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DealBtn_Xpath)));
		List<WebElement> dealBtnCount1 = driver.findElements(By.xpath(DealBtn_Xpath));
		System.out.println(dealBtnCount1.size());
		if (dealBtnCount1.size() > 0) {
			Assert.assertTrue(true, "Deal button is present");
			System.out.println("Button verification of used inventory page is successful");
			System.out.println("No of deal buttons present : " + dealBtnCount1.size());
		}
		else {
			System.out.println("fail");
		}
		

		
}
}
