package DAM;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import common_functions.BaseTest;
import common_functions.CBT_Utils;
import common_functions.Utils;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/******************************************************************************
* TC_003_DAM_Review_2D_Line_Drawing
* Description:
     * My To-Do's -> Digital Assets Enrichment tab -> Enrich Digital Assets state ->
     * More Details -> Click "DAM: Review 2D Line Drawing" link ->
     * View all pending entities for "DAM: Review 2D Line Drawing" ->
     * Open entity using Sellable Material ID ->
     * Entity Manage screen -> Verify "Approve 2D Line Drawing?" attribute shows
     * error "Secondary Images have been deleted, added, or image has been updated" ->
     * Navigate to Asset tab -> Verify "Has Image(s)" relationship section is available ->
     * More Actions -> Add -> Open Add Relationship window ->
     * Filter -> Asset Type -> Select "2D Line Drawing" -> Apply ->
     * Verify only 2D Line Drawing assets are displayed ->
     * Select required asset and Save ->
     * Verify asset is added successfully without any error ->
     * Click Asset Name -> Navigate to DAM Summary tab ->
     * Summary tab -> Verify "DAM: 2D Line Drawing" Data Quality Check is displayed ->
     * Open "DAM: Review 2D Line Drawing" DQ check ->
     * Verify following errors are displayed:
     * "Required"
     * "DAM: Review 2D Line Drawing Images have been deleted, added, or image has been updated" ->
     * Set "Approve 2D Line Drawing?" attribute to "Approve" and Save ->
     * Verify all validation errors are cleared ->
     * Verify "DAM: Review Secondary Image" DQ check passes successfully and
     * displays green status indicating successful completion.
*****************************************************************************/
@Test(groups = { "DigitalAssetowner" })
public class TC_004_Validate_DAM_Review_2D_Line_Drawing extends BaseTest {
	
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	
	public void Validate2dLine_Drawing() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name"))
		.assignCategory("Regression")
		.assignDevice(System.getenv("COMPUTERNAME"));
		
		homePage = new HomePage(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		/**************************************************************************
		 * // Step 1: Login to PIM with valid credentials // Verify user lands
		 * on Home Dashboard after successful login
		 *************************************************************************/
//		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/DAM/" + className + ".txt";
		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		System.out.println("Home Page of Digital Asset is displayed");
		test.pass("Home Page of Digital Asset is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		/**************************************************************************
		 * // Step 2: Navigate to "My To-Do's" and click on "Digital Assets  Enrichment" tab 
		 * // Verify "Enrich Digital Assets" state is displayed
		 *************************************************************************/	
		
		/**************************************************************************
		    // Step 3: Click on "More Details >>" option in "Enrich Digital Assets" state
		    // Verify the following links are visible:
		    //   i) DAM: Review 2D Line Drawing
		    //   ii) DAM: Review Representative Image (Primary)
		    //   iii) DAM: Review Secondary Image
		    //   iv) DAM: Review Unclassified Images
 *************************************************************************/	
		WebElement detailsEnrichment = homePage.Moredetails_MarketingEnrich().getShadowRoot().findElement(By.cssSelector("#viewDetails > span"));
		detailsEnrichment.click();
		Thread.sleep(2000);
		test.pass("More details clicked on Enrich Digital Asset tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/******************************************************************************
		* TC_003_DAM_Review_2D_Line_Drawing
		* Description:
		     * My To-Do's -> Digital Assets Enrichment tab -> Enrich Digital Assets state ->
		     * More Details -> Click "DAM: Review 2D Line Drawing" link ->
		     * Verify all entities pending for "DAM: Review 2D Line Drawing" are displayed.
		*****************************************************************************/
		List<WebElement> detailItems = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary")).getShadowRoot()
				.findElement(By.cssSelector("#moreDetails"))
				.findElements(By.cssSelector("my-todo-detail-view-list-item"));

		utils.waitForElement(() -> detailItems.get(0), "clickable");
		System.out.println("There are " + detailItems.size() + " elements ");
		
		List<String> expectedItems = Arrays.asList("Ready for transition", "DAM: Review 2D Line Drawing","DAM: Review Representative Image (Primary)", "DAM: Review Secondary Image",
				"DAM: Review Unclassified Images");
		Assert.assertEquals(detailItems.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		/**********************************************
		 * Verify in which row DAM: Review 2D Line Drawing was found
		**********************************************/
		int matchedRowIndex = -1; 
		for (int i = 0; i < detailItems.size(); i++) {
		    WebElement summary = detailItems.get(i);
		    WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#button-text-box"));
		    String actualText = innerDiv.getAttribute("title").trim().replaceFirst("^\\d+\\s", "");
		    System.out.println("Item " + (i + 1) + ":--" + actualText);
		    Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
		    if (actualText.contains("DAM: Review 2D Line Drawing")) {
		        matchedRowIndex = i + 1; 
		        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
		        try {
		            innerDiv.click();
		        } catch (Exception e) {
		            js.executeScript("arguments[0].click();", innerDiv);
		        }
		        Thread.sleep(5000);
		        break;
		    }
		}
		if (matchedRowIndex != -1) {
		    System.out.println("Found 'DAM: Review 2D Line Drawing' in row: " + matchedRowIndex);
		} else {
		    System.out.println("'DAM: Review 2D Line Drawing' not found in any row.");
		}
		test.pass("Clicked on DAM: Review 2D Line Drawing which is found at row -- " + matchedRowIndex);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	/*********************
	Step 4:
	Select and click the required "Sellable Material ID" link from the pending entities list.
	Verify that the selected entity opens successfully and user is navigated to the "Entity Manage" screen.
	************************/
		Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
        String matid = selectedRecord.get("Material Id");
        System.out.println("Selected Material ID: " + matid);
	/*********************
	Step 5:
	Navigate to the "DAM: Review 2D Line Drawing" section within the entity.
	Verify that the "Approve 2D Line Drawing?" attribute is displayed along with the validation error:
	"Secondary Images have been deleted, added, or image has been updated."
	************************/
        utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		Thread.sleep(2000);
		test.pass("Summary tab is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	/*********************
	Step 6:
	Click on the "Asset" tab from the Entity Manage screen.
	Verify that the "Has Image(s)" relationship section is expanded and displayed.
	************************/

	/*********************
	Step 7:
	Click on the "More Actions" button and select the "Add" option.
	Verify that the "Add Relationship" popup/window is opened successfully.
	************************/

	/*********************
	Step 8:
	Within the Add Relationship window, click on the "Filter" option.
	Search for the "Asset Type" filter and select it.
	Verify that the Asset Type filter panel is displayed successfully.
	************************/

	/*********************
	Step 9:
	Select the "2D Line Drawing" option from the Asset Type filter and click on the "Apply" button.
	Verify that only assets with Asset Type as "2D Line Drawing" are displayed in the results.
	************************/

	/*********************
	Step 10:
	Select the required 2D Line Drawing asset using the corresponding checkbox.
	Click on the "Save" button.
	Verify that the selected asset is added successfully without any validation or system errors.
	************************/

	/*********************
	Step 11:
	Click on the newly added "Asset Name" link.
	Navigate to the DAM Summary page/tab.
	Verify that the DAM-related validation error is visible.
	************************/

	/*********************
	Step 12:
	Click on the "Summary" tab of the entity.
	Verify that the "DAM: 2D Line Drawing" Data Quality Check is displayed.
	************************/

	/*********************
	Step 13:
	Click on the "DAM: Review 2D Line Drawing" section from the summary page.
	Verify that the following validation messages are displayed:
	1. Required
	2. DAM: Review 2D Line Drawing Images have been deleted, added, or image has been updated
	************************/

	/*********************
	Step 14:
	Update the "Approve 2D Line Drawing?" attribute by selecting the value "Approve".
	Click on the "Save" button to save the entity.
	************************/

	/*********************
	Step 15:
	Verify that all validation errors are removed successfully after saving.
	Verify that the "DAM: Review Secondary Image" Data Quality Check passes successfully.
	Verify that the DQ check status is displayed in green color, indicating successful completion.
	************************/
}
}