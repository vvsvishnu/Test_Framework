package TestCases;

import org.testng.annotations.Test;

import Common.BaseClass;

public class Testing extends BaseClass {


	@Test(dataProvider = "ObjectRepository")
	public void test(String cell1,String cell2,String cell3,String cell4,String cell5,String cell6,String cell7,String cell8) throws Exception {
System.out.println(cell1);
System.out.println(cell2);
System.out.println(cell3);
System.out.println(cell4);
System.out.println(cell5);
System.out.println(cell6);
System.out.println(cell7);
System.out.println(cell8);
System.out.println("#######################################");
driver.get(cell1);

	}



}
