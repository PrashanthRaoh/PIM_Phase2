package Second_Post_ETL_Key_Flags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.CBT_Utils;
import common_functions.NotepadManager;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;
/*****************************************************************************************
 Validates post-ETL key flags for a selected material (BSAPIE and CBT logins):
 Catalog Bearing Tool Usecase[Int]? = No
 Catalog Bearing Tool Sellable Product Status = MunitionSyndicated
 ****************************************************************************************/
public class TC04_Second_Post_ETL_BsaN_CbtY_MunN extends BaseTest{
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	@Test
	@Parameters("UseCaseOwner")
	public void Post_ETL_BsaY_CbtN_MunN(String useCaseOwner) throws IOException, InterruptedException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));
		
		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
		System.out.println("Executing Test For User : " + useCaseOwner);
		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		String PRE_ETL_Filename =  "Pre_ETL_Artifacts/Key_Flags/TC04_BsaN_CbtY_MunN.txt";

		String Matid = NotepadManager.FetchMaterialID(PRE_ETL_Filename);
		System.out.println("Fetched Material ID: " + Matid);
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		
		try {
		    Map<String, String> selectedRecord = cbtUtils.searchMaterialIdAndOpenDetails(Matid, searchPage, summaryPage, test);
		    if (selectedRecord.isEmpty()) {
		        test.warning("Could not proceed because no rows were available for Material ID: " + Matid);
		    } else {
		        String matid = selectedRecord.get("Material Id");
		        String sellableMaterialDescription = selectedRecord.get("Material Description");
		        System.out.println("Material ID -- " + matid + " Material Description --" + sellableMaterialDescription);
		        data.put("Material ID", matid);
		        Thread.sleep(3000);
		        utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		        Thread.sleep(5000);
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		    test.fail("Exception occurred while opening entity for Material ID -- " + Matid);
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		/*****************************************************************
		  1) Validate Catalog Bearing Tool Usecase[Int]? = No
		 **************************************************************** */		
		String cbtusecaseInt = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "Catalog Bearing Tool Usecase[Int]?", test);
		System.out.println("Catalog Bearing Tool Usecase[Int]? is " + cbtusecaseInt + " Expected is No");
		if ("No".equalsIgnoreCase(cbtusecaseInt == null ? "" : cbtusecaseInt.trim()))
		{
			test.pass("Catalog Bearing Tool Usecase[Int]? is " + cbtusecaseInt +  " as expected.");
		}
		else {
			test.fail("Catalog Bearing Tool Usecase[Int]? is NOT No. Actual: " + cbtusecaseInt);
		}
//		Assert.assertEquals(cbtusecaseInt == null ? "" : cbtusecaseInt.trim(), "No", "Expected Catalog Bearing Tool Usecase[Int]? to be No, but got: " + cbtusecaseInt);
		/*****************************************************************
		  2) Catalog Bearing Tool Sellable Product Status = MunitionSyndicated
		 **************************************************************** */
		String CBT_Status = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "Catalog Bearing Tool Sellable Product Status", test);
		System.out.println("Catalog Bearing Tool Sellable Product Status " + CBT_Status + " as expected");
		if ("MunitionSyndicated".equalsIgnoreCase(CBT_Status == null ? "" : CBT_Status.trim())) {
			test.pass("Catalog Bearing Tool Sellable Product Status is MunitionSyndicated as expected.");
		}
		else {
			test.fail("Catalog Bearing Tool Sellable Product Status expected was MunitionSyndicated.But Actual status is : " + CBT_Status);
		}
//		Assert.assertEquals(CBT_Status == null ? "" : CBT_Status.trim(), "MunitionSyndicated", "Expected Catalog Bearing Tool Sellable Product Status to be Approved, but got: " + CBT_Status);
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);
	}
}