package Admin_Module;

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
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/******************************
 Validates "Munitions Indicator = Yes" for both BSAPIE owner and CBT owner logins.
 ****************************/
public class TC10_Admin_BsaY_CbtN_PropN extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	@Test
	@Parameters("SystemAdmin")
	public void Post_ETL_BsaN_CbtN_MunN() throws IOException, InterruptedException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression") .assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");

		String PRE_ETL_Filename = "Pre_ETL_Artifacts/Key_Flags/TC10_BsaY_CbtN_PropN.txt";
		String Matid = NotepadManager.FetchMaterialID(PRE_ETL_Filename);
		String matid = null;
		System.out.println("Fetched Material ID: " + Matid);
		homePage.clickSearch_Products_Button_Admin().click();
		Thread.sleep(3000);

		try {
			Map<String, String> selectedRecord = cbtUtils.searchMaterialIdAndOpenDetails(Matid, searchPage, summaryPage, test);
			if (selectedRecord.isEmpty()) {
				test.warning("Could not proceed because no rows were available for Material ID: " + Matid);
			} else {
				matid = selectedRecord.get("Material Id");
				String sellableMaterialDescription = selectedRecord.get("Material Description");
				System.out.println("Material ID -- " + matid + " Material Description --" + sellableMaterialDescription);
				data.put("Material ID", matid);
				Thread.sleep(3000);
				utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
				Thread.sleep(3000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			test.fail("Exception occurred while opening entity for Material ID -- " + Matid);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}

		Assert.assertNotNull(matid, "Material ID should be captured before deletion verification");
		String confirmationText = cbtUtils.clickDeleteAndConfirm(homePage, test);
		data.put("Delete Confirmation Text", confirmationText);
		
		boolean hasNoElements = cbtUtils.verifyDeletedMaterialNotListed(searchPage, matid, test);
		Assert.assertTrue(hasNoElements, "Deleted record is still listed in search results for Material ID: " + matid);
	}
}