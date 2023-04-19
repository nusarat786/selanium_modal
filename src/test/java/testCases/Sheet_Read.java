package testCases;

import java.io.IOException;

import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utilities.XLUtils;

public class Sheet_Read extends BaseClass {

	
	@Test(dataProvider = "TempData")
	public void TC_0018_Deposite_Money_Invalid_Value(
			String No 
			
			
			) throws InterruptedException, IOException {

		logger.info(No);
	
				
	
	
	}
	
	
	public boolean isAlertPresent() // user defined method created to check alert is presetn or not
	{
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}

	}
	
	@DataProvider(name = "TempData")
	String[][] getData() throws IOException {
		String path = System.getProperty("user.dir") + "/src/test/java/testData/InputData.xlsx";

		int rownum = XLUtils.getRowCount(path, "Temp");
		int colcount = XLUtils.getCellCount(path, "Temp", 1);

		System.out.println(rownum);
		String DepositeMoneyData[][] = new String[rownum][colcount];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colcount; j++) {
				
				DepositeMoneyData[i - 1][j] = XLUtils.getCellData(path, "Temp", i, j);// 1 0
				System.out.print(XLUtils.getCellData(path, "Temp", i, j));

			}

		}

		System.out.print(DepositeMoneyData);
		return DepositeMoneyData;
	}
}
