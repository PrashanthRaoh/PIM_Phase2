package First_Post_ETL_KeyFlags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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
Munitions Indicator = Yes,
 BSA PIE Sellable Product Status = MunitionSyndicated,
 Catalog Bearing Tool Sellable Product Status = MunitionSyndicated.
 ****************************************************************************************/

public class TC02_Post_ETL_BsaY_CbtY_MunN extends BaseTest{
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	@Test
	@Parameters("UseCaseOwner")
	public void Post_ETL_BsaY_CbtY_MunN() throws IOException, InterruptedException {
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

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		
		String PRE_ETL_Filename =  "Pre_ETL_Artifacts/Key_Flags/TC02_BsaY_CbtY_MunN.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/Key_Flags/TC02_BsaY_CbtY_MunN.txt";

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
		  1) Validate Munitions Indicator = Yes
		 **************************************************************** */		
		String munitionsIndicatorValue = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "Munitions Indicator", test);
		if ("Yes".equalsIgnoreCase(munitionsIndicatorValue == null ? "" : munitionsIndicatorValue.trim()))
		{
			test.pass("Munitions Indicator is " + munitionsIndicatorValue +  " as expected.");
		}
		else {
			test.fail("Munitions Indicator is NOT Yes. Actual: " + munitionsIndicatorValue);
		}
		//Assert.assertEquals(munitionsIndicatorValue == null ? "" : munitionsIndicatorValue.trim(), "Yes", "Expected Munitions Indicator to be Yes, but got: " + munitionsIndicatorValue);
		/*****************************************************************
		  2) BSA PIE Sellable Product Status = MunitionSyndicated
		 **************************************************************** */
		String bsaPieStatus = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "BSA PIE Sellable Product Status", test);
		if ("MunitionSyndicated".equalsIgnoreCase(bsaPieStatus == null ? "" : bsaPieStatus.trim())) {
			test.pass("BSA PIE Sellable Product Status is MunitionSyndicated as expected.");
		}
		else
		{
			test.fail("BSA PIE Sellable Product Status is NOT MunitionSyndicated. Actual: " + bsaPieStatus);
		}
		//Assert.assertEquals(bsaPieStatus == null ? "" : bsaPieStatus.trim(), "MunitionSyndicated", "Expected BSA PIE Sellable Product Status to be MunitionSyndicated, but got: " + bsaPieStatus);

	/*****************************************************************
	  3) Catalog Bearing Tool Sellable Product Status = MunitionSyndicated
	 **************************************************************** */
		String cbtStatus = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "Catalog Bearing Tool Sellable Product Status", test);
		if ("MunitionSyndicated".equalsIgnoreCase(cbtStatus == null ? "" : cbtStatus.trim())) {
			test.pass("Catalog Bearing Tool Sellable Product Status is MunitionSyndicated as expected.");
		}
		else {
			test.fail("Catalog Bearing Tool Sellable Product Status is NOT MunitionSyndicated. Actual: " + cbtStatus);
		}
		//Assert.assertEquals(cbtStatus == null ? "" : cbtStatus.trim(), "MunitionSyndicated", "Expected Catalog Bearing Tool Sellable Product Status to be MunitionSyndicated, but got: " + cbtStatus);
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);
	}
}