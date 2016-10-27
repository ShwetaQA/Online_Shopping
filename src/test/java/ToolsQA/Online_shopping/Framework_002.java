package ToolsQA.Online_shopping;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ToolsQA.Online_shopping.appModules.CheckOut_Action;
import ToolsQA.Online_shopping.appModules.Confirmation_Action;
import ToolsQA.Online_shopping.appModules.PaymentDetails_Action;
import ToolsQA.Online_shopping.appModules.ProductSelect_Action;
import ToolsQA.Online_shopping.appModules.SignIn_Action;
import ToolsQA.Online_shopping.appModules.Verification_Action;
import ToolsQA.Online_shopping.pageObjects.BaseClass;
import ToolsQA.Online_shopping.pageObjects.ProductListing_Page;
import ToolsQA.Online_shopping.utility.Constant;
import ToolsQA.Online_shopping.utility.ExcelUtils;
import ToolsQA.Online_shopping.utility.Log;
import ToolsQA.Online_shopping.utility.Utils;



public class Framework_002 {
	public WebDriver driver;
	private String sTestCaseName;
	private int iTestCaseRow;
	
  @BeforeMethod
  public void beforeMethod() throws Exception {
	  	DOMConfigurator.configure("log4j.xml");
	  	sTestCaseName = this.toString();
	  	sTestCaseName = Utils.getTestCaseName(this.toString());
		Log.startTestCase(sTestCaseName);
		ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"Sheet1");
		iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,Constant.Col_TestCaseName);
		driver = Utils.OpenBrowser(iTestCaseRow);
		new BaseClass(driver);  
        }
  
  @Test
  public void f() throws Exception {
	  try{
		SignIn_Action.Execute(iTestCaseRow);
		ProductSelect_Action.productType(iTestCaseRow);
		ProductSelect_Action.productNumber(iTestCaseRow);
		ProductListing_Page.PopUpAddToCart.btn_GoToCart().click();
		CheckOut_Action.Execute();
		PaymentDetails_Action.execute(iTestCaseRow);
		Confirmation_Action.Execute();
		Verification_Action.Execute();
		ExcelUtils.setCellData("Pass", iTestCaseRow, Constant.Col_Result);
	  }catch (Exception e){
		  ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.Col_Result);
		  Utils.takeScreenshot(driver, sTestCaseName);
		  Log.error(e.getMessage());
		  throw (e);
	  }
		
  }
		
		
  @AfterMethod
  public void afterMethod() {
	    Log.endTestCase(sTestCaseName);
	    driver.close();
  		}
}
