package Common;

public class ReadExcelData {
	
	public static Object[][] testData(String excelPath, String sheetName) {
		

	ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
	int rowCount = excel.getRowCount();
	int colCount = excel.getcolCount();
	Object data[][] = new Object[rowCount-1][colCount];
	
	for(int i=1; i<rowCount; i++) {
		for(int j=0; j<colCount; j++) {
			String getData = excel.getCellDataString(i, j);
			data[i-1][j] = getData;
		}
		
	}
	return data;
	}

}
