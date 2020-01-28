package TestCases;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.BaseClass;
import Common.StringHelper;

public class V3_VDP_New_Inventory extends BaseClass {
	WebDriverWait wait;
	String inventoryName = null;
	Select select;

	@Test(dataProvider = "ObjectRepository")
	public void Verify_Button_Presence_All(String Main_URL, String Dealer_Name, String Dealer_Name_Xpath,
			String New_Inventory_Page_URL, String DealBtn_Xpath, String Total_Page_Text_Xpath,
			String Next_Page_Btn_Xpath, String Used_Inventory_Page_URL, String VDP_Click_Xpath,
			String VDP_New_Cars_Deal_Btn, String MSRP_Xpath, String Price_Xpath, String Frame_Id, String Get_Start_Btn)
			throws Exception {
		// Navigate to new inventory page
		driver.get(New_Inventory_Page_URL);
		wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(VDP_Click_Xpath)));
		// Get the list of deal button
		List<WebElement> dealBtnCount = driver.findElements(By.xpath(VDP_Click_Xpath));
		Random r = new Random();
		int randomValue = r.nextInt(dealBtnCount.size());
		dealBtnCount.get(randomValue).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Price_Xpath)));

		// capture MSRP value_VDP page
		String msrp_VDP_Full = driver.findElement(By.xpath(MSRP_Xpath)).getText();
		double msrp_VDP_Initial = StringHelper.getDoubleValue(msrp_VDP_Full);
		// capture MSRP value_VDP page
		String price_VDP_Full = driver.findElement(By.xpath(Price_Xpath)).getText();
		double price_VDP_Initial = StringHelper.getDoubleValue(price_VDP_Full);
		List<WebElement> _VDP_New_dealBtnCount = driver.findElements(By.xpath(VDP_New_Cars_Deal_Btn));
		// Click on deal button
		if (_VDP_New_dealBtnCount.size() > 0) {
			Assert.assertTrue(true, "Deal button is present");
			driver.findElement(By.xpath(VDP_New_Cars_Deal_Btn)).click();
			Thread.sleep(2000);
			driver.switchTo().frame(Frame_Id);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Get_Start_Btn)));
			driver.findElement(By.xpath(Get_Start_Btn)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Inventory_Name_Xpath"))));
			// V3 verification
			// Get the inventory details
			String inventoryName = driver.findElement(By.xpath(prop.getProperty("Inventory_Name_Xpath"))).getText();
			String stockIdFullText = driver.findElement(By.xpath(prop.getProperty("Stock_Id_Xpath"))).getText();
			String stockIdActual = stockIdFullText.substring(stockIdFullText.indexOf(":") + 2,
					stockIdFullText.length());
			String vinFullText = driver.findElement(By.xpath(prop.getProperty("VIN_Xpath"))).getText();
			String vinNumberActual = vinFullText.substring(vinFullText.indexOf(":") + 2, vinFullText.length());

			// Enter the delivery details

			driver.findElement(By.xpath(prop.getProperty("First_Name_Field_Xpath")))
					.sendKeys(prop.getProperty("FirstName"));
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("Last_Name_Xpath"))).sendKeys(prop.getProperty("LastName"));
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("Phone_Number_Field_Xpath")))
					.sendKeys(prop.getProperty("PhoneNmuber"));
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("EmailField_Xpath"))).sendKeys(prop.getProperty("Email"));
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("Address_Field_Xpath"))).sendKeys(prop.getProperty("Address"));
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("Address_Field_Xpath"))).sendKeys(Keys.DOWN);
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("Address_Field_Xpath"))).sendKeys(Keys.ENTER);
			Thread.sleep(5000);

			// Capture amount
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("Main_Amount_TopBar_Xpath"))));
			Thread.sleep(5000);
			String headerAmtFull = driver.findElement(By.xpath(prop.getProperty("Main_Amount_TopBar_Xpath"))).getText();
			// Convert to double
			double mainAmtTop = StringHelper.getDoubleValue(headerAmtFull);

			// capture values in delivery page after entering details
			// discounted price
			String discountedPriceFull = driver.findElement(By.xpath(prop.getProperty("Discounted_Price_Xpath")))
					.getText();
			double discountedPrice = StringHelper.getDoubleValue(discountedPriceFull);

			// Saved price
			String savedAmtFull = driver.findElement(By.xpath(prop.getProperty("Saved_Amount_Xpath"))).getText();
			double savedAmt = StringHelper.getDoubleValue(savedAmtFull);

			// verification

			if (savedAmt == (msrp_VDP_Initial - discountedPrice)) {
				Assert.assertEquals(savedAmt, (msrp_VDP_Initial - discountedPrice));
				System.out.println(
						"Test case passed and the discounted price is : " + discountedPrice + " for " + inventoryName);
			} else {
				Assert.fail("Verification failed");
			}
			
			//Verify finance deal
			driver.findElement(By.xpath(prop.getProperty("NextBtn_Xpath"))).click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("Finance_CheckBox_Xpath"))));
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("Finance_CheckBox_Xpath"))).click();
			String finance_Amount_Page1_Full = driver.findElement(By.xpath(prop.getProperty("Finance_Deal_Page_Amount_Xpath"))).getText();
			double finance_Amount_Page1_Initial = StringHelper.getDoubleValue(finance_Amount_Page1_Full);
			
			//Edit the finance downpayment and term
			driver.findElement(By.xpath(prop.getProperty("Edit_Finance_Payment_Btn_Xpath"))).click();
			driver.findElement(By.xpath(prop.getProperty("Finance_Down_Payment_Field_Xpath"))).clear();
			driver.findElement(By.xpath(prop.getProperty("Finance_Down_Payment_Field_Xpath"))).sendKeys(prop.getProperty("DownPayment_Amount_Finance"));
			select = new Select(driver.findElement(By.xpath(prop.getProperty("Finance_Down_Payment_Months_Xpath")))); 
			select.selectByIndex(1);
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("Finance_Apply_Payment_Xpath"))).click();
			Thread.sleep(5000);
			String finance_Amount_Page1_Full_Final = driver.findElement(By.xpath(prop.getProperty("Finance_Deal_Page_Amount_Xpath"))).getText();
			double finance_Amount_Page1_Final = StringHelper.getDoubleValue(finance_Amount_Page1_Full_Final);
			
			//Top value
			String finance_Amount_Top_AfterEdit_Full = driver.findElement(By.xpath(prop.getProperty("Finance_Deal_Page_Amount_Xpath"))).getText();
			double finance_Amount_Top_AfterEdit_Value = StringHelper.getDoubleValue(finance_Amount_Top_AfterEdit_Full);
			
			if((finance_Amount_Top_AfterEdit_Value==finance_Amount_Page1_Final)||(finance_Amount_Page1_Initial!=finance_Amount_Page1_Final)) {
				Assert.assertEquals(finance_Amount_Page1_Final, finance_Amount_Top_AfterEdit_Value);
				Assert.assertNotEquals(finance_Amount_Page1_Initial, finance_Amount_Page1_Final);
				System.out.println("Testcase passed");
				System.out.println(finance_Amount_Page1_Initial);
				System.out.println(finance_Amount_Page1_Final);
				System.out.println(finance_Amount_Top_AfterEdit_Value);
}
			else {
				Assert.fail("Fail");
				System.out.println("Fail");
			}
			
		} else {
			Assert.fail("Deal button not present in VDP for " + inventoryName);
		}
	}
}
