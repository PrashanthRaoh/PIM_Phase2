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
 Validate BSA PIE Usecase? = No
BSA PIE Sellable Product Status = MunitionSyndicated
 ****************************************************************************************/
public class TC03_Second_Post_ETL_BsaY_CbtN_MunN extends BaseTest{
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
		
		String PRE_ETL_Filename =  "Pre_ETL_Artifacts/Key_Flags/TC03_BsaY_CbtN_MunN.txt";

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
		  1) Validate BSA PIE Usecase? = No
		 **************************************************************** */		
		String BSAPIEUsecase_Value = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "BSA PIE Usecase?", test);
		System.out.println("BSA PIE Usecase? is " + BSAPIEUsecase_Value + " Expected is No");
		if ("No".equalsIgnoreCase(BSAPIEUsecase_Value == null ? "" : BSAPIEUsecase_Value.trim()))
		{
			test.pass("BSA PIE Usecase? is " + BSAPIEUsecase_Value +  " as expected.");
		}
		else {
			test.fail("BSA PIE Usecase? is NOT No . Actual: " + BSAPIEUsecase_Value);
		}
//		Assert.assertEquals(BSAPIEUsecase_Value == null ? "" : BSAPIEUsecase_Value.trim(), "No", "Expected Munitions Indicator to be No, but got: " + BSAPIEUsecase_Value);
		/*****************************************************************
		  2) BSA PIE Sellable Product Status = MunitionSyndicated
		 **************************************************************** */
		String bsaPieStatus = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "BSA PIE Sellable Product Status", test);
		System.out.println("BSA PIE Sellable Product Status " + bsaPieStatus + " as expected");
		if ("MunitionSyndicated".equalsIgnoreCase(bsaPieStatus == null ? "" : bsaPieStatus.trim())) {
			test.pass("BSA PIE Sellable Product Status is MunitionSyndicated as expected.");
		}
		else
		{
			test.fail("BSA PIE Sellable Product Status is NOT MunitionSyndicated. Actual: " + bsaPieStatus);
		}
		Assert.assertEquals(bsaPieStatus == null ? "" : bsaPieStatus.trim(), "MunitionSyndicated", "Expected BSA PIE Sellable Product Status to be MunitionSyndicated, but got: " + bsaPieStatus);
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);
	}
}