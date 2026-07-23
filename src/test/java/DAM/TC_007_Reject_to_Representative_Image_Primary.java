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
	 * Login as BSA PIE End Use Case Owner
	 * Navigate to My To-Do's, then open the BSAPIE Usecase Approval workflow
	 * Open "Pending Usecase Approval - BSA PIE" state through More Details
	 * Access an entity from the Ready for Transition queue
	 * Verify entity is in "Pending for Usecase Approval - BSAPIE" state
     * Verify available workflow actions:
     * Approve, Reject to Marketing Enrichment,
	 * Reject to Representative Image (Primary), and Terminate
	 * Enter comments and select "Reject to Representative Image (Primary)"
     * Verify entity is moved from "Pending Usecase Approval" state to
	 * "Enrich Digital Assets" state of Digital Assets Enrichment workflow
	 * Capture Material ID for further validation
	 * Login as Attribute Owner - Digital Assets (Primary)
	 * Navigate to My To-Do's, then open the Digital Assets Enrichment tab
     * Open "Enrich Digital Assets" state and select
	 * "DAM: Review Representative Image (Primary)" queue
	 * Search entity using captured Material ID
	 * Open Sellable Material ID and navigate to Entity Manage screen
	 * Verify entity is available in "Enrich Digital Assets" workflow state
	 * Navigate to Summary tab
     * Verify "DAM: Review Representative Image (Primary)"
	 * data quality check is displayed in red color
     * Open "DAM: Review Representative Image (Primary)"
	 * business condition
     * Verify "Approve Representative Image (Primary)?"
     * attribute displays message:
     * "Representative Image (Primary) was rejected by
	 * BSA PIE Usecase Owner"
     * Set "Approve Representative Image (Primary)?"
	 * attribute value to "Approve" and Save
	 * Verify approval value is saved successfully
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
		BSAPIE_PO.runUsecaseApprovalRepeatedSteps(homePage, searchPage, utils, test);

		Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
		String matid = selectedRecord.get("Material Id");
		System.out.println("Selected Material ID: " + matid);
		data.put("First BSA PIE Material ID", matid);
		Thread.sleep(4000);

		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		Thread.sleep(2000);
		test.pass("Summary tab is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		String expectedTitle = "Pending Usecase Approval - BSA PIE";
		List<String> expectedButtons = Arrays.asList("Approve", "Reject to Marketing Enrichment", "Reject to Representative Image (Primary)", "Terminate");
		String firstActiveStepName = BSAPIE_PO.verifyInProgressWorkflow(expectedTitle, test);
		Map<String, WebElement> firstWorkflowButtons = BSAPIE_PO.verifyWorkflowButtons(expectedButtons, test);
		data.put("First BSA PIE Active Workflow", firstActiveStepName);
		data.put("First BSA PIE Workflow Buttons", String.join(", ", firstWorkflowButtons.keySet()));
		
		///***************************
		// * Step 7:
		// * Enter comments in the workflow comments box.
		// * Select "Reject to Representative Image (Primary)" action.
		// * Expected Result:
		// * Entity should be rejected successfully to
		// * "Representative Image (Primary)" review stage.
		// ***************************/
			BSAPIE_PO.Comments_input().sendKeys("Rejection from BSA PIE");
			WebElement rejectToRepresentativeImageButton = firstWorkflowButtons.get("Reject to Representative Image (Primary)");
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
		switchUser("DigitalAssetowner", cbtpage);
		System.out.println("Logged in as Digital Asset owner");
		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		System.out.println("Home Page of Digital Asset is displayed");
		test.pass("Home Page of Digital Asset is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
/***************************
 * Step 11:
 * Navigate to "My To-Do's" and open the
 * "Digital Assets Enrichment" tab.
 * Expected Result:
 * "Enrich Digital Assets" state should be visible.
 ***************************/
		WebElement detailsEnrichment_Again = homePage.Moredetails_MarketingEnrich().getShadowRoot().findElement(By.cssSelector("#viewDetails > span"));
		detailsEnrichment_Again.click();
		Thread.sleep(2000);
		test.pass("More details clicked on Enrich Digital Asset tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
/***************************
 * Step 12:
 * Click "More Details" for "Enrich Digital Assets"
 * and open "DAM: Review Representative Image (Primary)" queue.
 * Expected Result:
 * Queue for representative image review should be displayed.
 ***************************/
		String queueToClick = "DAM: Review Representative Image (Primary)";
		List<String> expectedItems_Repeat = Arrays.asList("Ready for transition", "DAM: Review 2D Line Drawing","DAM: Review Representative Image (Primary)", "DAM: Review Secondary Image",
				"DAM: Review Unclassified Images");
		int matchedRowIndex;
		try {
			matchedRowIndex = BSAPIE_PO.clickApprovalQueueItem(searchPage, utils, test, expectedItems_Repeat, queueToClick);
		} catch (Exception e) {
			throw new RuntimeException("Failed to click queue item: " + queueToClick, e);
		}
		System.out.println("Found '" + queueToClick + "' in row: " + matchedRowIndex);
/***************************
 * Step 13:
 * Search the entity using the captured Material ID.
 * Expected Result:
 * Matching entity should be displayed in the search results.
 ***************************/
		try {
			utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			searchPage.searchthingdomain_Input_Mat_Id().click();
			searchPage.searchthingdomain_Input_Mat_Id().clear();
			searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
			searchPage.searchthingdomain_Input_Mat_Id() .sendKeys(Keys.ENTER);
			test.pass("Material id " + matid + " is searched in Search thing domain");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath( Utils.Takescreenshot(driver)).build());
			Thread.sleep(5000);

			Map<String, String> selectedRecord_Again = cbtUtils .selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
			System.out.println("Selected Record is  - " +selectedRecord_Again);
			data.put("Material ID", matid);
			Thread.sleep(1000);
			/***************************
			 * Step 14:
			 * Open the Sellable Material ID link from the search results.
			 * Navigate to Entity Manage screen.
			 * Expected Result:
			 * Correct entity should open successfully.
			 ***************************/
			utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
			Thread.sleep(3000);
		} catch (Exception ex) {
			ex.printStackTrace();
			test.fail("Material id -- " + matid + " details was not retrieved");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
/***************************
 * Step 15:
 * Verify the entity is available in
 * "Enrich Digital Assets" workflow state.
 * Expected Result:
 * Entity should be routed correctly to Digital Assets workflow.
 ***************************/
		List<WebElement> steps_DigitalAssets = digitalssetPage.DA_WorkflowSteps();
		System.out.println("Total Digital Asset workflow steps: " + steps_DigitalAssets.size());
		test.info("Total Digital Asset workflow steps: " + steps_DigitalAssets.size());
		List<String> workflowLabels = digitalssetPage.DA_WorkflowStepLabels();
		for (int i = 0; i < workflowLabels.size(); i++) {
			System.out.println("Digital Asset step " + (i + 1) + ": " + workflowLabels.get(i));
		}
		String expectedTitle_Digitalworkflow = "Enrich Digital Assets";
		String activeStepName_Digitalworkflow = digitalssetPage.getInProgressWorkflowTitle();

		System.out.println("✅ Active workflow is : " + activeStepName_Digitalworkflow);
		
		if (activeStepName_Digitalworkflow.equals(expectedTitle_Digitalworkflow)) {
		    test.pass("Active workflow is : " + activeStepName_Digitalworkflow + " as expected");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    Assert.assertEquals(activeStepName_Digitalworkflow, expectedTitle_Digitalworkflow, "Active step  does not match expected title");
		} else {
		    test.fail("Active workflow in Digital Asset is NOT : " + activeStepName_Digitalworkflow + " as expected");
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
/***************************
 * Step 16:
 * Navigate to the "Summary" tab.
 * Verify "DAM: Review Representative Image (Primary)" data quality check is displayed in red color.
 * Expected Result:
 * Business condition should be shown as failed/pending review.
 ***************************/
	 List<WebElement> conditions1 = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
	    for (int i = 0; i < conditions1.size(); i++) {
	        WebElement cond = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list")).get(i); 
	        String busscondname = cond.findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
	        System.out.println("Condition " + (i + 1) + " -- " + busscondname);
	        if (busscondname.contains("DAM: Review Representative Image (Primary)")) {
	            cond.click();
	            System.out.println("Clicked on DAM: Review Representative Image (Primary) condition");
	            test.pass("Clicked on DAM: Review Representative Image (Primary) condition");
	    		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	            break;
	        }
	    }
	    wait.until(ExpectedConditions.visibilityOf(digitalssetPage.primary_Image_Required_dropdown_obj()));
/***************************
 * Step 18:
 * Verify the "Approve Representative Image (Primary)?"
 * attribute displays the message:
 * "Representative Image (Primary) was rejected by BSA PIE Usecase Owner".
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
	String expectedErrorText = "Representative Image (Primary) was rejected by BSA PIE End use case owner. Please review and approve again";
	digitalssetPage.approvePrimaryImageAndVerifyErrorCleared(expectedErrorText, test);
/***************************
 * Step 20:
 * Verify the entity is automatically submitted from "Enrich Digital Assets" state once all required
 * Digital Asset review business conditions are satisfied.
 * Expected Result:
 * Entity should progress automatically in the workflow.
 ***************************/
	int workflowCountAfterSubmit = -1;
	boolean noWorkflowFound = false;
	for (int attempt = 1; attempt <= 2; attempt++) {
		try {
			workflowCountAfterSubmit = digitalssetPage.getActiveWorkflowCount();
		} catch (Exception ignored) {
			workflowCountAfterSubmit = 0;
		}
		if (workflowCountAfterSubmit == 0) {
			noWorkflowFound = true;
			test.pass("No workflows are present after refreshing on attempt " + attempt + ".");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			break;
		}
		test.log(Status.WARNING,"Attempt " + attempt + ": workflows still present (count: " + workflowCountAfterSubmit + "). Refreshing and retrying.");
		digitalssetPage.Workflow_Refresh_btn().click();
		Thread.sleep(5000);
		utils.waitForElement(() -> digitalssetPage.primary_Image_Required_dropdown_obj(), "clickable");
	}
	if (!noWorkflowFound) {
		test.fail("Expected no workflows after 2 attempts, but found: " + workflowCountAfterSubmit);
		test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	}
	Assert.assertTrue(noWorkflowFound, "Expected no workflows after 2 attempts.");

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
		switchUser("BSAPIEowner", BSAPIE_PO);
		System.out.println("Logged in as BSA PIE owner");
		BSAPIE_PO.runUsecaseApprovalRepeatedSteps(homePage, searchPage, utils, test);
		
		searchPage.searchthingdomain_Input_Mat_Id().click();
		searchPage.searchthingdomain_Input_Mat_Id().clear();
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
		searchPage.searchthingdomain_Input_Mat_Id() .sendKeys(Keys.ENTER);
		test.pass("Material id " + matid + " is searched in Search thing domain");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath( Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);
		
		Map<String, String> selectedRecord_Again = cbtUtils .selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
		System.out.println("Selected Record is  - " +selectedRecord_Again);
		data.put("Material ID", matid);
		Thread.sleep(1000);
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		Thread.sleep(2000);
		test.pass("Summary tab is displayed for second BSA PIE attempt");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		String secondActiveStepName = BSAPIE_PO.verifyInProgressWorkflow(expectedTitle, test);
		Map<String, WebElement> secondWorkflowButtons = BSAPIE_PO.verifyWorkflowButtons(expectedButtons, test);
		data.put("Second BSA PIE Active Workflow", secondActiveStepName);
		data.put("Second BSA PIE Workflow Buttons", String.join(", ", secondWorkflowButtons.keySet()));
		test.pass("Action buttons displayed for BSA PIE");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		BSAPIE_PO.Comments_input().sendKeys("Approving the record");
		WebElement ApproveRepresentativeImageButton = secondWorkflowButtons.get("Approve");
		Assert.assertNotNull(ApproveRepresentativeImageButton, "Approve button not found");
		try {
			ApproveRepresentativeImageButton.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ApproveRepresentativeImageButton);
		}
		Thread.sleep(5000);
		test.pass("Entered comments and clicked Approve the record");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	
		WebElement banner1 = wait.until(d -> {
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
		String bannerText1 = banner1.getText().trim();
		System.out.println("Banner text: " + bannerText1);

		int bsapieWorkflowCountAfterApproval = -1;
		boolean noBSAPIEWorkflowFound = false;
		for (int attempt = 1; attempt <= 2; attempt++) {
			try {
				BSAPIE_PO.Workflow_Refresh_btn().click();
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", BSAPIE_PO.Workflow_Refresh_btn());
			}
			Thread.sleep(5000);
			utils.waitForElement(() -> BSAPIE_PO.Workflow_Refresh_btn(), "clickable");
			try {
				bsapieWorkflowCountAfterApproval = BSAPIE_PO.getActiveBSAPIEWorkflowCount();
			} catch (Exception ignored) {
				bsapieWorkflowCountAfterApproval = 0;
			}

			if (bsapieWorkflowCountAfterApproval == 0) {
				noBSAPIEWorkflowFound = true;
				test.pass("No BSA PIE workflows are listed after refreshing on attempt " + attempt + ".");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				break;
			}

			if (attempt < 2) {
				test.log(Status.WARNING, "Attempt " + attempt + ": BSA PIE workflows still present (count: "
						+ bsapieWorkflowCountAfterApproval + "). Refreshing and retrying.");
			}
		}
		if (!noBSAPIEWorkflowFound) {
			test.fail("Expected no BSA PIE workflows after 2 refresh attempts, but found: " + bsapieWorkflowCountAfterApproval);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		Assert.assertTrue(noBSAPIEWorkflowFound, "Expected no BSA PIE workflows after 2 refresh attempts.");
}


	
/************************
Function to switch user
************************/
private void switchUser(String userKey, Object pageObject) throws Exception {
    homePage = new HomePage(driver);
    try {
        homePage.AppHeader_Administrator().click();
        Thread.sleep(500);
        homePage.Logout_btn().click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id='username']")));
        Thread.sleep(1000);
    } catch (Exception ignored) {
    }
    loginPage.LogintoPIM(userKey);

	// Generic post-login wait based on requested landing page type.
	if (pageObject instanceof CBT_Page) {
		CBT_Page cbtPage = (CBT_Page) pageObject;
		utils.waitForElement(() -> cbtPage.SellableMaterialTabcontent(), "clickable");
	} else if (pageObject instanceof BSAPIE_Page) {
		homePage = new HomePage(driver);
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
	}
}
}
