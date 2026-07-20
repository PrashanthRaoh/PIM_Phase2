package DAM;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
* TC_007_Reject_to_Representative_Image_Primary
* Description:
     * Login as BSA PIE End Use Case Owner -&gt;
     * Navigate to My To-Do's -&gt; BSAPIE Usecase Approval workflow -&gt;
     * Open "Pending Usecase Approval - BSA PIE" state through More Details -&gt;
     * Access entity from Ready for Transition queue -&gt;
     * Verify entity is in "Pending for Usecase Approval - BSAPIE" state -&gt;
     * Verify available workflow actions:
     * Approve, Reject to Marketing Enrichment,
     * Reject to Representative Image (Primary), and Terminate -&gt;
     * Enter comments and select "Reject to Representative Image (Primary)" -&gt;
     * Verify entity is moved from "Pending Usecase Approval" state to
     * "Enrich Digital Assets" state of Digital Assets Enrichment workflow -&gt;
     * Capture Material ID for further validation -&gt;
     * Login as Attribute Owner - Digital Assets (Primary) -&gt;
     * Navigate to My To-Do's -&gt; Digital Assets Enrichment tab -&gt;
     * Open "Enrich Digital Assets" state and select
     * "DAM: Review Representative Image (Primary)" queue -&gt;
     * Search entity using captured Material ID -&gt;
     * Open Sellable Material ID and navigate to Entity Manage screen -&gt;
     * Verify entity is available in "Enrich Digital Assets" workflow state -&gt;
     * Navigate to Summary tab -&gt;
     * Verify "DAM: Review Representative Image (Primary)"
     * data quality check is displayed in red color -&gt;
     * Open "DAM: Review Representative Image (Primary)"
     * business condition -&gt;
     * Verify "Approve Representative Image (Primary)?"
     * attribute displays message:
     * "Representative Image (Primary) was rejected by
     * BSA PIE Usecase Owner" -&gt;
     * Set "Approve Representative Image (Primary)?"
     * attribute value to "Approve" and Save -&gt;
     * Verify approval value is saved successfully -&gt;
     * Verify entity is automatically submitted from
     * "Enrich Digital Assets" state when all Digital Asset
     * review business conditions are satisfied:
     * DAM: Review 2D
  *****************************************************************************/
public class TC_007_Reject_to_Representative_Image_Primary extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	@Test
	public void getReviewUnclassifiedImageTab_zero_Count() throws Exception {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		/***************************
		 * Step 1: Login as BSA PIE End Use Case Owner. Expected Result: User
		 * should be logged in successfully and landed on the Home page.
		 ***************************/
		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);

		/***************************
		 * Step 2: Navigate to "My To-Do's" and locate the "BSAPIE Usecase
		 * Approval" workflow section. Expected Result: "Pending Usecase Approval - BSA PIE" state should be visible.
		 ***************************/
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		/**************************************************
		 * ***** Click on Use case ApprovalTab
		 **************************************************/
		Thread.sleep(3000);
		homePage.BSAPIEUsecaseApprovalTab().click();
		Thread.sleep(5000);
		test.pass("Clicked on Approval tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

		/********************************************
		 * Get number of items under use case approvals
		 ***************************************/
		List<WebElement> summaryElements = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));

		System.out.println("Total items: " + summaryElements.size());

		List<String> expectedItems = Arrays.asList("Pending Usecase Approval - BSA PIE","On Hold - BSA PIE (User Selected)", "On Hold - BSA PIE (Rule Triggered)");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
		/***************************
		 * Step 3: Click on "More Details" for the "Pending Usecase Approval BSA PIE" state. Expected Result: The queue details for pending
		 * entities should be displayed.
		 ***************************/
		WebElement detailsEnrichment = homePage.Moredetails_MarketingEnrich().getShadowRoot().findElement(By.cssSelector("#viewDetails > span"));
		try {
			detailsEnrichment.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", detailsEnrichment);
		}
		Thread.sleep(2000);
		test.pass("Clicked More Details for Pending Usecase Approval - BSA PIE");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
/***************************
 * Step 4:	
 * Open an entity from the "Ready for Transition" queue.
 * Expected Result:
 * Selected entity should open successfully in Entity Manage screen.
 ***************************/
		List<WebElement> detailItems = driver.findElement(By.cssSelector("#app")).getShadowRoot()
        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
        .findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
        .findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
        .findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
        .findElement(By.cssSelector("#rock-my-todos")).getShadowRoot()
        .findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
        .findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
        .findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
        .findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary")).getShadowRoot()
        .findElement(By.cssSelector("#moreDetails"))
        .findElements(By.cssSelector("my-todo-detail-view-list-item"));

		for (WebElement item : detailItems) {
			WebElement buttonTextBox = item.getShadowRoot().findElement(By.cssSelector("#button-text-box"));
			String title = buttonTextBox.getAttribute("title").trim();
			System.out.println(title);

			if (title.toLowerCase().contains("ready for transition")) {
				try {
					buttonTextBox.click();
				} catch (Exception e) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonTextBox);
				}
				Thread.sleep(5000);
				break;
			}
		}
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Clicked ready for transition business condition");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
/***************************
 * Step 5:
 * Verify the entity is currently in
 * "Pending for Usecase Approval - BSAPIE" workflow state.
 * Expected Result:
 * Workflow state should match the expected BSAPIE approval stage.
 ***************************/
	Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
	String matid = selectedRecord.get("Material Id");
	System.out.println("Selected Material ID: " + matid);
	Thread.sleep(4000);
/***************************
 * Step 6:
 * Verify the following workflow actions are available:
 * 1. Approve
 * 2. Reject to Marketing Enrichment
 * 3. Reject to Representative Image (Primary)
 * 4. Terminate
 * Expected Result:
 * All expected workflow action buttons/options should be displayed.
 ***************************/
	utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
	Thread.sleep(2000);
	test.pass("Summary tab is displayed");
	test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	
	List<WebElement> buttons = driver.findElement(By.cssSelector("#app")).getShadowRoot()
	        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
	        .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
	        .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
	        .findElements(By.cssSelector("[id^='action-button-']"));

	Map<String, WebElement> workflowButtons = new LinkedHashMap<>();
	for (WebElement btn : buttons) {
		WebElement buttonTextBox = btn.getShadowRoot().findElement(By.cssSelector("#buttonTextBox"));
		String buttonText = buttonTextBox.getText().trim();
		workflowButtons.put(buttonText, buttonTextBox);
		System.out.println(buttonText);
	}
	test.info("Available workflow buttons: " + String.join(", ", workflowButtons.keySet()));
	List<String> expectedButtons = Arrays.asList("Approve", "Reject to Marketing Enrichment", "Reject to Representative Image (Primary)", "Terminate");

	for (String expected : expectedButtons) {
		Assert.assertTrue(workflowButtons.containsKey(expected), "Expected button not found: " + expected + " | Actual buttons: " + workflowButtons.keySet());
	}
/***************************
 * Step 7:
 * Enter comments in the workflow comments box.
 * Select "Reject to Representative Image (Primary)" action.
 * Expected Result:
 * Entity should be rejected successfully to
 * "Representative Image (Primary)" review stage.
 ***************************/
	BSAPIE_PO.Comments_input().sendKeys("Rejection from BSA PIE");
	WebElement rejectToRepresentativeImageButton = workflowButtons.get("Reject to Representative Image (Primary)");
	Assert.assertNotNull(rejectToRepresentativeImageButton, "Reject to Representative Image (Primary) button not found");
	try {
		rejectToRepresentativeImageButton.click();
	} catch (Exception e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", rejectToRepresentativeImageButton);
	}
	Thread.sleep(2000);
	test.pass("Entered comments and clicked Reject to Representative Image (Primary)");
	test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	WebElement banner = wait.until(d -> {
	    try {
	        WebElement el = d.findElement(By.cssSelector("#app")).getShadowRoot()
	                .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
	                .findElement(By.cssSelector("#pebbleAppToast > pebble-echo-html")).getShadowRoot()
	                .findElement(By.cssSelector("#bind-html"));
	        return el.isDisplayed() ? el : null;
	    } catch (Exception e) {
	        return null;
	    }
	});
	String bannerText = banner.getText().trim();
	System.out.println("Banner text: " + bannerText);
	test.info("Banner text: " + bannerText);
	test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	/*************************************************
	 * --------- Click on the search thing bread crum ------- *
	 ************************************************/
	searchPage.Search_things_BreadCrum().click();
	Thread.sleep(2000);
	utils.waitForElement(() ->searchPage.getgrid(), "clickable");
	test.pass("Navigated back to search thing ");
	test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

	/*************************************************
	 * --------- Verify the record again in the search thing. It should not be listed ------- *
	 ************************************************/
	searchPage.searchthingdomain_Input_Mat_Id().click();
	searchPage.searchthingdomain_Input_Mat_Id().clear();
	searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
	searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
	Thread.sleep(5000);

	try {
		String txt = searchPage.rowsdisplayedtext().getText();
		String result = txt.split(" / ")[1];
		int zerorows = Integer.parseInt(result);
		System.out.println(zerorows);
		Assert.assertEquals(zerorows, 0);
		test.pass(matid + " completion is 100%. Hence not visible");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

	} catch (Exception e) {
		WebElement rowsredefined2 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid"));
		List<WebElement> arrrowsdefined2 = rowsredefined2.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div> div.ag-row.ag-row-even.ag-row-level-0"));

		if (arrrowsdefined2.size() > 0) {
			System.out.println("Records found for the search criteria");
			test.fail(matid + " completion is NOT 100%. Pleaes verify");
			test.log(Status.FAIL,
					MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
	}
/***************************
 * Step 8:
 * Verify the entity is moved from "Pending Usecase Approval - BSA PIE" state to "Enrich Digital Assets" state under Digital Assets Enrichment workflow.
 * Expected Result:
 * Entity should no longer remain in BSAPIE approval state.
 ***************************/
//	 switch to Digital Asset owner
	switchUser("DigitalAssetowner", cbtpage);
	System.out.println("Logged in as Digital Asset owner");
/***************************
 * Step 9:
 * Capture the Sellable Material ID / Material ID
 * for further validation in Digital Asset workflow.
 * Expected Result:
 * Material ID should be stored successfully for next steps.
 ***************************/

/***************************
 * Step 10:
 * Logout from BSA PIE End Use Case Owner session.
 * Login as Attribute Owner - Digital Assets (Primary).
 * Expected Result:
 * User should be logged in successfully as Digital Asset owner.
 ***************************/

/***************************
 * Step 11:
 * Navigate to "My To-Do's" and open the
 * "Digital Assets Enrichment" tab.
 * Expected Result:
 * "Enrich Digital Assets" state should be visible.
 ***************************/

/***************************
 * Step 12:
 * Click "More Details" for "Enrich Digital Assets"
 * and open "DAM: Review Representative Image (Primary)" queue.
 * Expected Result:
 * Queue for representative image review should be displayed.
 ***************************/

/***************************
 * Step 13:
 * Search the entity using the captured Material ID.
 * Expected Result:
 * Matching entity should be displayed in the search results.
 ***************************/

/***************************
 * Step 14:
 * Open the Sellable Material ID link from the search results.
 * Navigate to Entity Manage screen.
 * Expected Result:
 * Correct entity should open successfully.
 ***************************/

/***************************
 * Step 15:
 * Verify the entity is available in
 * "Enrich Digital Assets" workflow state.
 * Expected Result:
 * Entity should be routed correctly to Digital Assets workflow.
 ***************************/

/***************************
 * Step 16:
 * Navigate to the "Summary" tab.
 * Verify "DAM: Review Representative Image (Primary)"
 * data quality check is displayed in red color.
 * Expected Result:
 * Business condition should be shown as failed/pending review.
 ***************************/

/***************************
 * Step 17:
 * Open the "DAM: Review Representative Image (Primary)"
 * business condition.
 * Expected Result:
 * Representative image review attributes should be displayed.
 ***************************/

/***************************
 * Step 18:
 * Verify the "Approve Representative Image (Primary)?"
 * attribute displays the message:
 * "Representative Image (Primary) was rejected by
 * BSA PIE Usecase Owner".
 * Expected Result:
 * Rejection message should be visible exactly as expected.
 ***************************/

/***************************
 * Step 19:
 * Set "Approve Representative Image (Primary)?"
 * attribute value to "Approve".
 * Click on Save.
 * Expected Result:
 * Approval value should be saved successfully without errors.
 ***************************/

/***************************
 * Step 20:
 * Verify the entity is automatically submitted from
 * "Enrich Digital Assets" state once all required
 * Digital Asset review business conditions are satisfied.
 * Expected Result:
 * Entity should progress automatically in the workflow.
 ***************************/

/***************************
 * Step 21:
 * Verify all applicable Digital Asset review business conditions
 * are completed successfully, including:
 * - DAM: Review Representative Image (Primary)
 * - DAM: Review 2D Line Drawing
 * - Other required DAM review checks, if applicable
 * Expected Result:
 * Entity should no longer remain pending in Digital Assets review.
 ***************************/
		
		
		
		
		
		
		
		
		
		
		
		
		


//
//
//
//
//
//
//
//
//
////
////// Login as Digital Asset owner
////loginPage.LogintoPIM("DigitalAssetowner");
////utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
////System.out.println("Logged in as Digital Asset owner");
//
//		
//		

}




private void switchUser(String userKey, CBT_Page cbtpage) throws Exception {
    homePage = new HomePage(driver);

    try {
        homePage.AppHeader_Administrator().click();
        Thread.sleep(1000);
        homePage.Logout_btn().click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id='username']")));
        Thread.sleep(1000);
    } catch (Exception ignored) {
        // already on login page
    }

    loginPage.LogintoPIM(userKey);
    utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
}
}
