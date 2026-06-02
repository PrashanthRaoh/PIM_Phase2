package First_Post_ETL_KeyFlags;

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
 Check if the sellable has updated value  
 ****************************************************************************************/
public class TC10_Post_ETL_BsaY_CbtN_PropN extends BaseTest{
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
//		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		
		String PRE_ETL_Filename =  "Pre_ETL_Artifacts/Key_Flags/TC10_BsaY_CbtN_PropN.txt";
//		String POST_ETL_Filename = "/Post_ETL_Artifacts/Key_Flags/TC05_BsaN_CbtY_PropN.txt";

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
		  Check if the sellable has updated value  
		 "Proprietary Indicator" value to "Yes"
		 **************************************************************** */		
		String ProprietaryIndicatorValue = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "Proprietary Indicator", test);
		System.out.println("Proprietary Indicator is " + ProprietaryIndicatorValue + " Expected is YES");
		if ("Yes".equalsIgnoreCase(ProprietaryIndicatorValue == null ? "" : ProprietaryIndicatorValue.trim()))
		{
			test.pass("Proprietary Indicator Value is " + ProprietaryIndicatorValue +  " as expected.");
		}
		else {
			test.fail("Proprietary Indicator Value is NOT Yes. Actual: " + ProprietaryIndicatorValue);
		}
//		Assert.assertEquals(ProprietaryIndicatorValue == null ? "" : ProprietaryIndicatorValue.trim(), "Yes", "Expected Proprietary Indicator to be Yes, but got: " + ProprietaryIndicatorValue);
		
		/*****************************************************************
		  2) BSA PIE Sellable Product Status = Approved
		 **************************************************************** */
		String bsaPieStatus = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "BSA PIE Sellable Product Status", test);
		System.out.println("BSA PIE Sellable Product Status " + bsaPieStatus + " as expected");
		if ("Approved".equalsIgnoreCase(bsaPieStatus == null ? "" : bsaPieStatus.trim())) {
			test.pass("BSA PIE Sellable Product Status is Approved as expected.");
		}
		else
		{
			test.fail("BSA PIE Sellable Product Status is NOT Approved. Actual: " + bsaPieStatus);
		}
		//Assert.assertEquals(bsaPieStatus == null ? "" : bsaPieStatus.trim(), "Approved", "Expected BSA PIE Sellable Product Status to be Approved, but got: " + bsaPieStatus);
		
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);
	}
}