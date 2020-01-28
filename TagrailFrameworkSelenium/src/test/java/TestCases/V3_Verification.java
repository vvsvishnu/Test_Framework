package TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.BaseClass;
import Common.StringHelper;

public class V3_Verification extends BaseClass {
	WebDriverWait wait;

	@Test(dataProvider = "V3_ObjectRepository")
	public void Enter_Delivery_Details(String Inventory_Name_Xpath, String Stock_Id_Xpath, String VIN_Xpath,
			String First_Name_Field_Xpath, String First_Name, String Last_Name_Xpath, String Last_Name,
			String Phone_Number_Field_Xpath, String Phone_Number, String EmailField_Xpath, String Email,
			String Address_Field_Xpath, String Address, String Main_Amount_TopBar_Xpath, String Discounted_Price_Xpath, String Saved_Amount_Xpath) throws Exception {
		driver.get(
				"https://www.kunilexusofgreenwoodvillage.com/inventory/new-2020-lexus-nx-300h-awd-4d-sport-utility-jtjgjrdz0l5001193/");
		// capture msrp
		String msrp_VDP_Full = driver.findElement(By.xpath("//*[@id=\"price-box\"]/div[1]/div[1]/div[1]/span/span[2]"))
				.getText();
		double msrp_VDP_Initial = StringHelper.getDoubleValue(msrp_VDP_Full);
		System.out.println(msrp_VDP_Initial);
		// capture price
		String price_VDP_Full = driver.findElement(By.xpath("//*[@id=\"price-box\"]/div[1]/div[1]/div[2]/span[2]"))
				.getText();
		double price_VDP_Initial = StringHelper.getDoubleValue(price_VDP_Full);
		System.out.println(price_VDP_Initial);
		// to be included in the previous method
		wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tgbtn\"]/a")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"tgbtn\"]/a")).click();
		Thread.sleep(2000);
		driver.switchTo().frame("popupWebdeskingFrame_v_3");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='appWrapper']/div/div[3]")));
		driver.findElement(By.xpath("//div[@id='appWrapper']/div/div[3]")).click();
		Thread.sleep(2000);
		// V3 verification
		// Get the inventory details
		String inventoryName = driver.findElement(By.xpath(Inventory_Name_Xpath)).getText();
		System.out.println(inventoryName);
		String stockIdFullText = driver.findElement(By.xpath(Stock_Id_Xpath)).getText();
		String stockIdActual = stockIdFullText.substring(stockIdFullText.indexOf(":") + 2, stockIdFullText.length());
		System.out.println(stockIdActual);
		String vinFullText = driver.findElement(By.xpath(VIN_Xpath)).getText();
		String vinNumberActual = vinFullText.substring(vinFullText.indexOf(":") + 2, vinFullText.length());
		System.out.println(stockIdActual);

		// Enter the delivery details

		driver.findElement(By.xpath(First_Name_Field_Xpath)).sendKeys(First_Name);
		Thread.sleep(2000);
		driver.findElement(By.xpath(Last_Name_Xpath)).sendKeys(Last_Name);
		Thread.sleep(2000);
		driver.findElement(By.xpath(Phone_Number_Field_Xpath)).sendKeys(Phone_Number);
		Thread.sleep(2000);
		driver.findElement(By.xpath(EmailField_Xpath)).sendKeys(Email);
		Thread.sleep(2000);
		driver.findElement(By.xpath(Address_Field_Xpath)).sendKeys(Address);
		Thread.sleep(2000);
		driver.findElement(By.xpath(Address_Field_Xpath)).sendKeys(Keys.DOWN);
		driver.findElement(By.xpath(Address_Field_Xpath)).sendKeys(Keys.ENTER);
		System.out.println("Details filled successfully");

		// Capture amount
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Main_Amount_TopBar_Xpath)));
		Thread.sleep(5000);
		String headerAmtFull = driver.findElement(By.xpath(Main_Amount_TopBar_Xpath)).getText();
		// Convert to double
		double mainAmtTop = StringHelper.getDoubleValue(headerAmtFull);
		System.out.println(mainAmtTop);
		
		//capture values in delivery page after entering details
		//discounted price
		String discountedPriceFull = driver.findElement(By.xpath(Discounted_Price_Xpath)).getText();
		double discountedPrice = StringHelper.getDoubleValue(discountedPriceFull);
		System.out.println(discountedPrice);
		
		//Saved price
				String savedAmtFull = driver.findElement(By.xpath(Saved_Amount_Xpath)).getText();
				double savedAmt = StringHelper.getDoubleValue(savedAmtFull);
				System.out.println(savedAmt);
				
				//verification
				
				if(savedAmt==(msrp_VDP_Initial-discountedPrice)) {
					Assert.assertEquals(savedAmt, (msrp_VDP_Initial-discountedPrice));
					System.out.println("Test case passed and the discounted price is : "+discountedPrice);
				}
				else {
					Assert.fail("Verification failed");
				}
		
		
	

	}

}
