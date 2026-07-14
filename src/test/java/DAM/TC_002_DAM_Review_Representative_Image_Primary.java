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
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/******************************************************************************
* TC_002_DAM_Review_Representative_Image_Primary
*  Description:
	 * My To-Do's -> Digital Assets Enrichment tab -> Enrich Digital Assets state -> More Details ->
	 * Click "DAM: Review Representative Image (Primary)" link -> View pending entities ->
	 * Filter "Image Required? (Auto)" = Has No Values -> Filter "Image Required? (Override)" = Has No Values ->
	 * Open entity via Sellable Material ID -> Entity Manage screen -> Summary tab ->
	 * Verify "Things I Need Fix" widget shows all 4 DAM review conditions ->
	 * Open "DAM: Review Representative Image (Primary)" DQ check -> Verify "Image Required?" is Blank ->
	 * Set "Image Required? (Override)" to Yes and Save -> Verify error "At least One Image should Linked
	 * To Sellable Product" on "Image Required?" ->
	 * Asset tab -> More Actions -> Add -> Filter Asset Type "Representative Image(Primary)" -> Apply ->
	 * Select asset and Save -> Verify "image has errors" message ->
	 * Summary tab -> Open DQ check -> Verify "Image Required?" error is cleared and
	 * "Approve Representative Image (Primary)?" shows "image has been deleted, added, or updated" error ->
	 * Set "Approve Representative Image (Primary)?" to Approve and Save -> Verify error is cleared ->
	 * Summary tab -> Verify "DAM: Review Representative Image (Primary)" DQ check passes and shows in green.
*****************************************************************************/
@Test(groups = { "DigitalAssetowner" })
public class TC_002_DAM_Review_Representative_Image_Primary extends BaseTest {
	
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	
	public void ReviewRep_Image() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name"))
		.assignCategory("Regression")
		.assignDevice(System.getenv("COMPUTERNAME"));
		
		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);

		/**************************************************************************
		 * // Step 1: Login to PIM with valid credentials // Verify user lands
		 * on Home Dashboard after successful login
		 *************************************************************************/
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/DAM/" + className + ".txt";
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
		/**************************************************************************
		    // Step 4: Click on "DAM: Review Representative Image (Primary)" link
		    // Verify all entities pending for this business condition are listed
		 *************************************************************************/	
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
		 * Verify in which row DAM: DAM: Review Representative Image (Primary) was found
		**********************************************/
		int matchedRowIndex = -1; 
		for (int i = 0; i < detailItems.size(); i++) {
		    WebElement summary = detailItems.get(i);
		    WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#button-text-box"));
		    String actualText = innerDiv.getAttribute("title").trim().replaceFirst("^\\d+\\s", "");
		    System.out.println("Item " + (i + 1) + ":--" + actualText);
		    Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
		    if (actualText.contains("DAM: Review Representative Image (Primary)")) {
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
		    System.out.println("Found 'DAM: Review Representative Image (Primary)' in row: " + matchedRowIndex);
		} else {
		    System.out.println("'DAM: Review Representative Image (Primary)' not found in any row.");
		}
		test.pass("Clicked on DAM: Review Representative Image (Primary) which is found at row -- " + matchedRowIndex);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/**************************************************************************
		 * Step 5: Click "Filter" option, search "Image Required? (Auto)",
		 * select "Has No Values" 
		 * Verify sellable entities are filtered accordingly
		 *************************************************************************/
//		searchPage.getFilterButton().click();
//		Thread.sleep(2000);
		searchPage.getFilterButton().click();
		String filterName = "Image Required? (Auto)";
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		WebElement materialTypeSearch = searchPage.Search_MaterialType();
		materialTypeSearch.clear();
		materialTypeSearch.sendKeys(filterName);
		Thread.sleep(1000);
		utils.clickFilterAttribute(filterName);
		Thread.sleep(1000);
		
		/**************************************************************************
		    // Step 6: Click "Filter" option, search "Image Required? (Override)", select "Has No Values"
		    // Verify sellable entities are filtered accordingly
		*************************************************************************/	
		utils.waitForElement(() -> digitalssetPage.Imagerequired_Auto_HasImagesDropdown(), "clickable");
		digitalssetPage.Imagerequired_Auto_HasImagesDropdown().click();
		Thread.sleep(1000);
		
		/**************************************************************************
		    // Step 7: Click on "Sellable Material ID" link to open the entity
		    // Verify entity opens and navigates to "Entity Manage" screen
		*************************************************************************/	
		/**************************************************************************
		    // Step 8: Click on "Summary" tab
		    // Verify "Things I Need Fix" widget shows all 4 DAM review business conditions:
		    //   i) DAM: Review 2D Line Drawing
		    //   ii) DAM: Review Representative Image (Primary)
		    //   iii) DAM: Review Secondary Image
		    //   iv) DAM: Review Unclassified Images
		*************************************************************************/	
		/**************************************************************************
		    // Step 9: Click on "DAM: Review Representative Image (Primary)" data quality check
		    // Verify "Image Required?" attribute has "Blank" value
		*************************************************************************/	
		/**************************************************************************
		    // Step 10: Set "Image Required? (Override)" to "Yes" and click "Save"
		    // Verify override value is saved
		    // Verify error "At least One Image should Linked To Sellable Product" appears on "Image Required?"
		*************************************************************************/	
		/**************************************************************************
		    // Step 11: Click on "Asset" tab
		    // Verify "Has Image(s)" option/section is open
		*************************************************************************/	
		/**************************************************************************
		    // Step 12: Click "More Actions" button and select "Add" option
		    // Verify "Add Relationship" window opens
		*************************************************************************/	
		/**************************************************************************
		    // Step 13: Click "Filter" option, search "Asset Type", click on it
		    // Verify Asset filter panel opens
		*************************************************************************/	
		/**************************************************************************
		    // Step 14: Select "Representative Image(Primary)" option and click "Apply"
		    // Verify all "Representative Image(Primary)" asset type results are displayed
		*************************************************************************/	
		/**************************************************************************
		    // Step 15: Select an asset via checkbox and click "Save"
		    // Verify asset is added without error
		    // Verify message "image has errors. Please review the errors by clicking this record" is shown
		*************************************************************************/	
		/**************************************************************************
		    // Step 16: Click on "Summary" tab
		    // Verify "DAM: Review Representative Image (Primary)" data quality check is visible
		*************************************************************************/	
		/**************************************************************************
		    // Step 17: Click on "DAM: Review Representative Image (Primary)" data quality check
		    // Verify "At least One Image should Linked To Sellable Product" error is removed from "Image Required?"
		    // Verify "Approve Representative Image (Primary)?" shows message:
		    //   "Representative Image (Primary) has been deleted, added, or image has been updated"
		*************************************************************************/	
		/**************************************************************************
		    // Step 18: Click "Approve Representative Image (Primary)?" attribute, set value to "Approve", click "Save"
		    // Verify the "has been deleted, added, or image has been updated" error is removed
		*************************************************************************/
		/**************************************************************************
		    // Step 19: Click on "Summary" tab
		    // Verify "DAM: Review Representative Image (Primary)" data quality check passes and shows in green
		*************************************************************************/	
		}
		
	}