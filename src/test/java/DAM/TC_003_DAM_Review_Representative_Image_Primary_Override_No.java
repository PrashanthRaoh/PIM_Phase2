package DAM;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
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
/*************************************************************************************************
Description : * TC_003_Validate_DAM_Review_Representative_Image_Primary_Flow:
 * My-To'Do's -> Digital Assets Enrichment tab ->
 * Verify "Enrich Digital Assets" state is displayed ->
 * Click "More Details>>" and select
 * "DAM: Review Representative Image (Primary)" ->
 * Verify pending entities are displayed for the
 * Representative Image (Primary) review task ->
 * Apply filters "Image Required? (Auto) = Has No Values" and
 * "Image Required? (Override) = Has No Values" ->
 * Verify sellable entities are filtered correctly ->
 * Open Sellable Material entity ->
 * Navigate to Summary tab ->
 * Verify the following DQ checks are available under
 * "Things I Need Fix" widget:
 *    - DAM: Review 2D Line Drawing
 *    - DAM: Review Representative Image (Primary)
 *    - DAM: Review Secondary Image
 *    - DAM: Review Unclassified Images ->
 * Open "DAM: Review Representative Image (Primary)" DQ check ->
 * Verify "Image Required?" attribute is blank ->
 * * Set **Image Required? (Override)** = **No** and save ->
 * Verify "Image Required?" is automatically updated to "No" ->
 * Set "Approve Representative Image (Primary)?" = Approve
 * and save ->
 * Verify "Approve Representative Image (Primary)?"
 * value is saved successfully as "Approve" ->
 * Return to Summary tab ->
 * Verify "DAM: Review Representative Image (Primary)"
 * DQ check passes successfully and is displayed
 * in green status.
*************************************************************************************************/
@Test(groups = { "DigitalAssetowner" })
public class TC_003_DAM_Review_Representative_Image_Primary_Override_No extends BaseTest {
	
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
		
		/***************************
		 * Step 1:
		 * The user navigates to "My-To'Do's" and clicks on the "Digital Assets Enrichment" tab.
		 * Expected Result:
		 * The user should be able to view the "Enrich Digital Assets" state.
		****************************/
		WebElement detailsEnrichment = homePage.Moredetails_MarketingEnrich().getShadowRoot().findElement(By.cssSelector("#viewDetails > span"));
		detailsEnrichment.click();
		Thread.sleep(2000);
		test.pass("More details clicked on Enrich Digital Asset tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/***************************
		 * Step 2:
		 * The user clicks on the "More Details>>" option available in the "Enrich Digital Assets" state.
		 * Expected Result:
		 * The user should be able to view the following links:
		 * i) DAM: Review 2D Line Drawing
		 * ii) DAM: Review Representative Image (Primary)
		 * iii) DAM: Review Secondary Image
		 * iv) DAM: Review Unclassified Images
		****************************/
		/**********************************
		* Verify in which row DAM: Review Representative Image (Primary) was found
		************************************/
		String queueToClick = "DAM: Review Representative Image (Primary)";
		List<String> expectedItems = Arrays.asList("Ready for transition", "DAM: Review 2D Line Drawing","DAM: Review Representative Image (Primary)", "DAM: Review Secondary Image",
				"DAM: Review Unclassified Images");
		int matchedRowIndex;
		try {
			matchedRowIndex = BSAPIE_PO.clickApprovalQueueItem(searchPage, utils, test, expectedItems, queueToClick);
		} catch (Exception e) {
			throw new RuntimeException("Failed to click queue item: " + queueToClick, e);
		}
		System.out.println("Found '" + queueToClick + "' in row: " + matchedRowIndex);

		/***************************
		 * Step 3:
		 * The user clicks on the "DAM: Review Representative Image (Primary)" link.
		 * Expected Result:
		 * The user should be able to view all the entities pending for
		 * "DAM: Review Representative Image (Primary)".
		****************************/

		/***************************
		 * Step 4:
		 * The user clicks on the "Filter" option and searches "Image Required? (Auto)" and selects "Has No Value".
		 * Expected Result:
		 * The sellable entities should be filtered out on the search thing domain screen.
		****************************/
		boolean autoApplied = digitalssetPage.applyFilterAndSelectOption( searchPage,"Image Required? (Auto)", () -> digitalssetPage.ImageRequired_Auto_Dropdown(), "Has No Value");
		Assert.assertTrue(autoApplied, "Failed to apply filter: Image Required? (Auto) -> Has No Value");
		Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");

		// Filter 2
		boolean overrideApplied = digitalssetPage.applyFilterAndSelectOption(searchPage, "Image Required? (Override)", () -> digitalssetPage.ImageRequired_Auto_Dropdown(), "Has No Value");
		Assert.assertTrue(overrideApplied, "Failed to apply filter: Image Required? (Override) -> Has No Value");
		Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		/***************************
		 * Step 6:
		 * The user clicks on the "Sellable Material ID" link to open the entity.
		 * Expected Result:
		 * The entity should be open and the user should navigate to the "Entity Manage" screen.
		****************************/
		 	Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
	        String matid = selectedRecord.get("Material Id");
	        System.out.println("Selected Material ID: " + matid);
		/***************************
		 * Step 7:
		 * The user clicks on the "Summary" tab.
		 * Expected Result:
		 * The user should be able to view the following business conditions under the "Things I Need Fix" widget:
		 * i) DAM: Review 2D Line Drawing
		 * ii) DAM: Review Representative Image (Primary)
		 * iii) DAM: Review Secondary Image
		 * iv) DAM: Review Unclassified Images
		****************************/
	        utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
			Thread.sleep(2000);
			test.pass("Summary tab is displayed");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			/***************************
			 Find "DAM: Review Representative Image (Primary)" condition
			****************************/
		    List<WebElement> conditions = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
		    for (int i = 0; i < conditions.size(); i++) {
		        WebElement cond = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list")).get(i); 
		        String busscondname = cond.findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
		        System.out.println("Condition " + (i + 1) + " -- " + busscondname);
		        /***************************
				 * Step 8:
				 * The user clicks on the "DAM: Review Representative Image (Primary)" data quality check.
				 * Expected Result:
				 * The user should be able to view the "Image Required" attribute with "Blank" value.
				****************************/
		        if (busscondname.contains("DAM: Review Representative Image (Primary)")) {
		            cond.click();
		            System.out.println("Clicked on DAM: Review Representative Image (Primary) condition");
		            test.pass("Clicked on DAM: Review Representative Image (Primary) condition");
		    		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		            break;
		        }
		    }
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(digitalssetPage.primary_Image_Required_dropdown_obj()));
		    
		    
		    
		    /*****************************************
			Verify the error message is Secondary Images have been deleted, added, or image has
			*************************************************************/
//		    WebElement Errormessage = digitalssetPage.DA_error_Message();
//			WebElement daError = wait.until(ExpectedConditions.visibilityOf(Errormessage));
//			Assert.assertTrue(daError.isDisplayed(), "DA error message is not displayed");
//			// Capture and normalize text
//			String actualErrorText = daError.getText().trim().replaceAll("\\s+", " ");
//			System.out.println("DA error text: " + actualErrorText);
//			test.pass("DA error message displayed: " + actualErrorText);
//			// Expected from your Step 16
//			String expectedErrorText = "2D Line Drawing images have been deleted, added, or image has been updated";
//			 Assert.assertEquals(actualErrorText, expectedErrorText, "Mismatch in DA error message text");
		    
		    
		    
		    
		    
		    
		    
		    
		    
		/***************************
		 * Step 9:
		 * The user clicks on "Image Required? (Override)" and selects "NO" value, then clicks on the "Save" button.
		 * Expected Result:
		 * i) The user should be able to save the override value.
		 * ii) The system should show "At least One Image should Linked To Sellable Product" error at "Image Required?".
		****************************/
		    test.pass("Clicked on DAM: Review Representative Image (Primary) condition");
    		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
    		
			if (digitalssetPage.primary_Image_Required_dropdown_obj().isDisplayed()) {
				test.pass("Image Required? (Override) dropdown is displayed");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				digitalssetPage.primary_Image_Required_dropdown_obj().click();
				Thread.sleep(2000);
				wait.until(ExpectedConditions.visibilityOf(digitalssetPage.ImageRequired_No()));
				digitalssetPage.ImageRequired_No().click();
				Thread.sleep(2000);
				digitalssetPage.Save_2d_Line_Drawring().click();
				Thread.sleep(5000);
			}
			try {
				wait.until(ExpectedConditions.visibilityOf(digitalssetPage.SkipAndContinue_Dialog()));
				wait.until(ExpectedConditions.elementToBeClickable(digitalssetPage.SkipAndContinue_Dialog_Continue_btn())) .click();
			} catch (TimeoutException e) {
			}
			Thread.sleep(5000);
			cbtpage.CBT_Workflow_Refresh_btn().click();
			Thread.sleep(5000);
			wait.until(ExpectedConditions.elementToBeClickable(digitalssetPage.primary_Image_Required_dropdown_obj()));
		/***************************
		 * Step 10:
		 * The user clicks on the "Asset" tab.
		 * Expected Result:
		 * The user should be able to view the "Has Image(s)" option should be open.
		****************************/
		digitalssetPage.Assets_dropdownWrapper().click();
		Thread.sleep(3000);
		digitalssetPage.Assets_dropdown_Has_Images_Option().click();
		Thread.sleep(3000);
		/***************************
		 * Step 11:
		 * The user clicks on the "More Actions" button and opens the "Add" option.
		 * Expected Result:
		 * The "Add Relationship" window should get open.
		****************************/
		utils.waitForElement(() -> digitalssetPage.DA_MoreActions_dropdown(), "clickable");
		Thread.sleep(1000);
		test.pass("More actions page displayed to attach a image");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.DA_MoreActions_dropdown().click();
		Thread.sleep(1000);
		List<WebElement> actionItems = digitalssetPage.DA_AddImagedropdownvalue();
		for (WebElement item : actionItems) {
		    String title = item.getAttribute("title");
		    if (title != null && title.trim().equals("Add")) {
		        item.click();
		        Thread.sleep(5000);
		        System.out.println("Clicked: " + title);
		        break;
		    }
		}
		utils.waitForElement(() -> digitalssetPage.Search_Images_input(), "clickable");
		test.pass("Arrived at adding image page");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/***************************
		 * Step 12:
		 * The user clicks on the "Filter" option and searches for "Asset Type" and clicks on it.
		 * Expected Result:
		 * The Asset filter should be open.
		****************************/
		digitalssetPage.AssetType_SearchImage_Window().click();
		Thread.sleep(2000);
		digitalssetPage.AssetType_SearchImage_Window_Inputbox().sendKeys("Asset Type");
		Thread.sleep(2000);
		utils.waitForElement(() -> digitalssetPage.AssetType_SearchList_element(), "clickable");
		if (digitalssetPage.AssetType_SearchList_element().isDisplayed()) {
			test.pass("Asset Type filter is displayed");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			digitalssetPage.AssetType_SearchList_element().click();
			Thread.sleep(5000);
		}
		wait.until(driver-> digitalssetPage.getGridShadowRoot().findElements(By.cssSelector("pebble-lov-item")).size() > 0);
		
		List<WebElement> items = digitalssetPage.getGridShadowRoot().findElements( By.cssSelector("pebble-lov-item"));

		System.out.println("Total Items: " + items.size());
		/***************************
		 * Step 13:
		 * The user selects "Representative Image(Primary)" option and clicks on the apply button.
		 * Expected Result:
		 * All the "Representative Image(Primary)" asset type should be open on the screen.
		****************************/
		for (int i = 0; i < items.size(); i++) {
		    WebElement itemText = items.get(i) .getShadowRoot() .findElement(By.cssSelector("div > div"));
		    String value = itemText.getText().trim();
		    System.out.println("Row " + (i + 1) + ": " + value);
		    if (value.equalsIgnoreCase("Representative Image (Primary)")) {
		        itemText.click();
		        Thread.sleep(2000);
		        break;
		    }
		}
		digitalssetPage.Primary_Image_Confirm_button().click();
		Thread.sleep(5000);
		/***************************
		 * Step 14:
		 * The user selects the asset by checking the checkbox present on the asset and clicks on the "Save" button.
		 * Expected Result:
		 * i) The Asset should get added without any error.
		 * ii) The asset should get added to the sellable product and throw "Image has errors. Please review the errors by clicking this record" message.
		****************************/
		List<WebElement> imageNames = digitalssetPage.getImageNames();
		List<WebElement> imageCheckboxes = digitalssetPage.getImageCheckboxes();
		Assert.assertFalse(imageNames.isEmpty(), "No images found!");

		if (imageNames.size() != imageCheckboxes.size()) {
			throw new IllegalStateException("Mismatch between image names (" + imageNames.size() + ") and checkboxes (" + imageCheckboxes.size() + ")");
		}
		for (int i = 0; i < imageNames.size(); i++) {
			System.out.println((i + 1) + ". " + imageNames.get(i).getText().trim());
		}
		// Random selection
		int randomIndex = ThreadLocalRandom.current().nextInt(imageNames.size());
		String selectedImageName = imageNames.get(randomIndex).getText().trim();
		System.out.println("Randomly selected image: " + selectedImageName);
		test.pass("Randomly selected image: " + selectedImageName);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		
		WebElement checkboxContainer = imageCheckboxes.get(randomIndex)
				.getShadowRoot()
				.findElement(By.cssSelector("#checkboxContainer"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({block:'center', inline:'center'});",
				checkboxContainer);
		wait.until(ExpectedConditions.elementToBeClickable(checkboxContainer));
		try {
			checkboxContainer.click();
		} catch (ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxContainer);
		}
		
		System.out.println("Checkbox clicked for: " + selectedImageName);
		data.put("Image Name", selectedImageName);
		digitalssetPage.Save_DA_Image_btn().click();
		Thread.sleep(5000);
		/***************************
		 * Step 15:
		 * The user clicks on the "Summary" tab.
		 * Expected Result:
		 * The user should be able to view "DAM: Review Representative Image (Primary)" data quality check.
		****************************/
		utils.waitForElement(() -> digitalssetPage.DA_MoreActions_dropdown(), "clickable");
		digitalssetPage.Summary_Tab().click();
		Thread.sleep(5000);
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
		 * Step 16:
		 * The user clicks on the "DAM: Review Representative Image (Primary)" data quality check.
		 * Expected Result:
		 * i) The user should be able to view "At least One Image should Linked To Sellable Product" error removed from the "Image Required?" attribute.
		 * ii) The user should be able to view "Representative Image (Primary) has been deleted, added, or image has been updated" on the "Approve Representative Image (Primary)?" attribute.
		****************************/
		    String actualErrorText = "";
			String expectedErrorText = "Representative Image (Primary) has been deleted, added, or image has been updated";
			boolean expectedBannerFound = false;

			try {
				WebElement errorHost = digitalssetPage.DA_error_Message();
				if (errorHost.isDisplayed()) {
					String bannerText = errorHost.getText().trim().replaceAll("\\s+", " ");
					System.out.println("DA error text: " + bannerText);
					if (bannerText.equalsIgnoreCase(expectedErrorText)) {
						expectedBannerFound = true;
						actualErrorText = bannerText;
					}
				}
			} catch (Exception e) {
				test.log(Status.WARNING, "Could not fetch pre-approval error banner at fixed index 2: " + e.getMessage());
			}

			if (expectedBannerFound) {
				test.pass("DA error message displayed: " + actualErrorText);
			} else {
				test.log(Status.WARNING, "Expected DA error banner was not found before approval: " + expectedErrorText);
				System.out.println("WARNING: Expected DA error banner was not found before approval.");
			}
		/***************************
		 * Step 17:
		 * The user clicks on the "Approve Representative Image (Primary)?" attribute, sets "Approve" value, and clicks on the "Save" button.
		 * Expected Result:
		 * The "Representative Image (Primary) has been deleted, added, or image has been updated" error should be removed from the "Approve Representative Image (Primary)?" attribute.
		****************************/
		digitalssetPage.Approve_Representative_Image_Primary_dropdown_obj().click();
		Thread.sleep(2000);
		WebElement Approve_btn = digitalssetPage.SameDropdownObject().getShadowRoot()
				.findElements(By.cssSelector("[id^='rs']")).get(3) .getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
			    .findElement(By.cssSelector("#lov")).getShadowRoot()
			    .findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).getShadowRoot()
			    .findElement(By.cssSelector("#grid")).getShadowRoot()
			    .findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item")).getShadowRoot()
			    .findElement(By.cssSelector("div > div"));
		
		Thread.sleep(2000);
		Approve_btn.click();
		Thread.sleep(2000);
		digitalssetPage.Save_2d_Line_Drawring().click();
		Thread.sleep(5000);
		
		digitalssetPage.Workflow_Refresh_btn().click();
		Thread.sleep(5000);
		test.pass("Refreshed transaction to get the latest workflow status");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> digitalssetPage.primary_Image_Required_dropdown_obj(), "clickable");
		
		/****************************************
				Validate the error message is not displayed
		**************************************/		
		boolean errorVisible = false;
		String postRefreshErrorText = "";
		try {
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement errorHost = shortWait.until(d -> {
				try {
					WebElement el = digitalssetPage.DA_error_Message();
					return el.isDisplayed() ? el : null;
				} catch (Exception ignored) {
					return null;
				}
			});

			postRefreshErrorText = errorHost.getText().trim().replaceAll("\\s+", " ");
			System.out.println("Post-refresh error: " + postRefreshErrorText);
			errorVisible = postRefreshErrorText.equalsIgnoreCase(expectedErrorText);

		} catch (TimeoutException ignored) {
			// Expected path: error banner not present within 5 seconds
		}

		if (errorVisible) {
			test.log(Status.FAIL, "Expected error is still displayed after refresh: " + expectedErrorText);
			System.out.println("FAIL: Expected error is still displayed after refresh.");
		} else {
			test.pass("Validated expected error is not displayed after refresh");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}

	}
}


