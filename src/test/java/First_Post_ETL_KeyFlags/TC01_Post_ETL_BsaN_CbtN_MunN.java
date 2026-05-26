package First_Post_ETL_KeyFlags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.openqa.selenium.Keys;
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
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;
/******************************
 Validates "Munitions Indicator = Yes" for both BSAPIE owner and CBT owner logins.
 ****************************/
public class TC01_Post_ETL_BsaN_CbtN_MunN extends BaseTest{
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	@Test
	@Parameters("UseCaseOwner")
	public void Post_ETL_BsaN_CbtN_MunN() throws IOException, InterruptedException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));
		
		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		
		String PRE_ETL_Filename =  "Pre_ETL_Artifacts/Key_Flags/TC01_BsaN_CbtN_MunN.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/Key_Flags/TC01_Post_BsaN_CbtN_MunN.txt";

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
	        test.pass("Material ID -- " + matid + " Material Description --" + sellableMaterialDescription + " is selected for verification");
	        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	        Thread.sleep(3000);
	        utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
	        Thread.sleep(3000);
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	    test.fail("Exception occurred while opening entity for Material ID -- " + Matid);
	    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	}
		
	String munitionIndicatorValue = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "Munitions Indicator", test);
	if (munitionIndicatorValue.isEmpty()) {
		test.fail("Munitions Indicator value is empty / not found.");
	} else if (!"Yes".equalsIgnoreCase(munitionIndicatorValue)) {
		test.pass("Munitions Indicator is " + munitionIndicatorValue +  " as expected.");
		test.fail("Expected Munitions Indicator = Yes, but got: " + munitionIndicatorValue);
		test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	} else {
		test.pass("Munitions Indicator is Yes as expected.");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	}
	data.put("Munitions Indicator", munitionIndicatorValue);
	BSAPIE_PO.Tabclose_Xmark().click();
	Thread.sleep(4000);
	}
}
