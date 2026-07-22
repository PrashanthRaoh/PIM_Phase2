package DAM;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;
/******************************************************************************
* TC_005_DAM_Review_Secondary_Image
* Description:
     * My To-Do's -> Digital Assets Enrichment tab -> Enrich Digital Assets state ->
     * More Details -> Click "DAM: Review Secondary Image" link ->
     * View all pending entities for "DAM: Review Secondary Image" ->
     * Open entity using Sellable Material ID ->
     * Entity Manage screen -> Navigate to Summary tab ->
     * Verify "DAM: Review Secondary Image" Data Quality Check is displayed
     * in red color ->
     * Open "DAM: Review Secondary Image" business condition ->
     * Verify "Approve Secondary Image?" attribute displays error:
     * "Secondary Image has been deleted, added, or image has been updated" ->
     * Navigate to Asset tab ->
     * Verify "Has Image(s)" relationship section is available and expanded ->
     * More Actions -> Add -> Open Add Relationship window ->
     * Filter -> Asset Type -> Select "Secondary Image" -> Apply ->
     * Verify only Secondary Image assets are displayed ->
     * Select required Secondary Image asset and Save ->
     * Verify asset is added successfully without any error ->
     * Re-open "DAM: Review Secondary Image" business condition ->
     * Verify "Approve Secondary Image?" attribute displays error:
     * "Secondary Image has been deleted, added, or image has been updated" ->
     * Set "Approve Secondary Image?" attribute to "Approve" and Save ->
     * Verify "Approve Secondary Image?" value is saved successfully ->
     * Navigate back to Summary tab ->
     * Verify "DAM: Review Secondary Image" business condition passes
     * successfully and displays green status indicating successful completion.
*****************************************************************************/
@Test(groups = { "DigitalAssetowner" })
public class TC_005_Validate_DAM_Review_Secondary_Image extends BaseTest {
	
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	
	public void ValidateReview_Secondary_Image() throws InterruptedException, IOException {
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
		Thread.sleep(1000);
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
		
		List<WebElement> detailItems = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos")) .getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
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
		 * Verify in which row DAM: Review Secondary Image was found
		**********************************************/
		int matchedRowIndex = -1; 
		for (int i = 0; i < detailItems.size(); i++) {
		    WebElement summary = detailItems.get(i);
		    WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#button-text-box"));
		    String actualText = innerDiv.getAttribute("title").trim().replaceFirst("^\\d+\\s", "");
		    System.out.println("Item " + (i + 1) + ":--" + actualText);
		    Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
		    if (actualText.contains("DAM: Review Secondary Image")) {
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
		    System.out.println("Found 'DAM: Review Secondary Image' in row: " + matchedRowIndex);
		} else {
		    System.out.println("'DAM: Review Secondary Image' not found in any row.");
		}
		test.pass("Clicked on DAM: Review Secondary Image which is found at row -- " + matchedRowIndex);
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
	Navigate to the "DAM: Review Secondary Image" section within the entity.
	Verify that the "Approve 2D Line Drawing?" attribute is displayed along with the validation error:
	"Secondary Images have been deleted, added, or image has been updated."
	************************/
        utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		Thread.sleep(2000);
		test.pass("Summary tab is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		List<WebElement> conditions = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
	    for (int i = 0; i < conditions.size(); i++) {
	        WebElement cond = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list")).get(i); 
	        String busscondname = cond.findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
	        System.out.println("Condition " + (i + 1) + " -- " + busscondname);
	        /***************************
			 * Step 8:
			 * The user clicks on the "DAM: Review Secondary Image" data quality check.
			 * Expected Result:
			 * The user should be able to view the "Image Required" attribute with "Blank" value.
			****************************/
	        if (busscondname.contains("DAM: Review Secondary Image")) {
	            cond.click();
	            System.out.println("Clicked on DAM: Review Secondary Image condition");
	            test.pass("Clicked on DAM: Review Secondary Image condition");
	    		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	            break;
	        }
	    }
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(digitalssetPage.common_ele_2dlinedrawingDropdown()));
		/*****************************************
		Verify the error message is Secondary Images have been deleted, added, or image has
		*************************************************************/
	    String expectedErrorText = "Secondary Images have been deleted, added, or image has been updated";
	    String actualErrorText = wait.until(
	            ExpectedConditions.visibilityOf(digitalssetPage.DA_error_Message_Secondary_Image()))
	            .getText()
	            .replace('\u00A0', ' ')
	            .replaceAll("\\s+", " ")
	            .trim();

		System.out.println("DA error text: " + actualErrorText);
		System.out.println("Expected error text " + expectedErrorText);
		test.pass("DA error message displayed: " + actualErrorText);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		Assert.assertTrue(actualErrorText.equalsIgnoreCase(expectedErrorText) || actualErrorText.equalsIgnoreCase(expectedErrorText.replace("Secondary Images have", "Secondary Image has")), "Mismatch in DA error message text. Actual=[" + actualErrorText + "]");
	/*********************
	Step 6:
	Click on the "Asset" tab from the Entity Manage screen.
	Verify that the "Has Image(s)" relationship section is expanded and displayed.
	************************/
	    digitalssetPage.Assets_dropdownWrapper().click();
		Thread.sleep(3000);
		digitalssetPage.Assets_dropdown_Has_Images_Option().click();
		Thread.sleep(3000);
	/*********************
	Step 7:
	Click on the "More Actions" button and select the "Add" option.
	Verify that the "Add Relationship" popup/window is opened successfully.
	************************/
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
	/*********************
	Step 8:
	Within the Add Relationship window, click on the "Filter" option.
	Search for the "Asset Type" filter and select it.
	Verify that the Asset Type filter panel is displayed successfully.
	************************/
		digitalssetPage.AssetType_SearchImage_Window().click();
		Thread.sleep(2000);
		digitalssetPage.AssetType_SearchImage_Window_Inputbox().sendKeys("Asset Type");
		Thread.sleep(1000);
		utils.waitForElement(() -> digitalssetPage.AssetType_SearchList_element(), "clickable");
		if (digitalssetPage.AssetType_SearchList_element().isDisplayed()) {
			test.pass("Asset Type filter is displayed");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			digitalssetPage.AssetType_SearchList_element().click();
			Thread.sleep(2000);
		}
		wait.until(driver-> digitalssetPage.getGridShadowRoot().findElements(By.cssSelector("pebble-lov-item")).size() > 0);
		
		List<WebElement> items = digitalssetPage.getGridShadowRoot().findElements( By.cssSelector("pebble-lov-item"));
		System.out.println("Total Items: " + items.size());
	/*********************
	Step 9:
	Select the "Secondary Image" option from the Asset Type filter and click on the "Apply" button.
	Verify that only assets with Asset Type as "2D Line Drawing" are displayed in the results.
	************************/
		for (int i = 0; i < items.size(); i++) {
		    WebElement itemText = items.get(i) .getShadowRoot() .findElement(By.cssSelector("div > div"));
		    String value = itemText.getText().trim();
		    System.out.println("Row " + (i + 1) + ": " + value);
		    if (value.equalsIgnoreCase("Secondary Image")) {
		        itemText.click();
		        Thread.sleep(1000);
		        break;
		    }
		}
		digitalssetPage.Primary_Image_Confirm_button().click();
		Thread.sleep(2000);
	/*********************
	Step 10:
	Select the required Secondary Image asset using the corresponding checkbox.
	Click on the "Save" button.
	Verify that the selected asset is added successfully without any validation or system errors.
	************************/
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
		
		imageCheckboxes.get(randomIndex).getShadowRoot().findElement(By.cssSelector("#checkboxContainer")).click();
		System.out.println("Checkbox clicked for: " + selectedImageName);
		data.put("Image Name", selectedImageName);
		digitalssetPage.Save_DA_Image_btn().click();
		Thread.sleep(5000);
	/*********************
	Step 11:
	Click on the newly added "Asset Name" link.
	Navigate to the DAM Summary page/tab.
	Verify that the DAM-related validation error is visible.
	************************/
		utils.waitForElement(() -> digitalssetPage.DA_MoreActions_dropdown(), "clickable");
		/*********************
		 * Step 12: Click on the "Summary" tab of the entity. Verify that
		 * the "DAM: 2D Line Drawing" Data Quality Check is displayed.
		 ************************/
		digitalssetPage.Summary_Tab().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> digitalssetPage.Summarythingsneedtofix_grid(), "clickable");
		
		 List<WebElement> conditions1 = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
		    for (int i = 0; i < conditions1.size(); i++) {
		        WebElement cond = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list")).get(i); 
		        String busscondname = cond.findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
		        System.out.println("Condition " + (i + 1) + " -- " + busscondname);
		        if (busscondname.contains("DAM: Review Secondary Image")) {
		            cond.click();
		            System.out.println("Clicked on DAM: 2D Line Drawing condition");
		            test.pass("Clicked on DAM: Review Secondary Image condition");
		    		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		            break;
		        }
		    }
		    wait.until(ExpectedConditions.visibilityOf(digitalssetPage.common_ele_2dlinedrawingDropdown()));
	/*********************
	Step 13:
	Click on the "DAM: Review Secondary Image" section from the summary page.
	Verify that the following validation messages are displayed:
	1. Required
	2. DAM: Review Secondary Image Images have been deleted, added, or image has been updated
	************************/
						String expectedErrorText_after = "Secondary Image has been deleted, added, or image has been updated";
						boolean expectedBannerFound = false;
						try {
							WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
							WebElement errorHost = shortWait.until(d -> {
								try {
									WebElement el = digitalssetPage.DA_error_Message();
									return (el != null && el.isDisplayed()) ? el : null;
								} catch (Exception ignored) {
									return null;
								}
							});
							if (errorHost != null) {
								String bannerText = errorHost.getText().replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
								System.out.println("DA error text: " + bannerText);
								if (bannerText.equalsIgnoreCase(expectedErrorText_after)) {
									expectedBannerFound = true;
									actualErrorText = bannerText;
								}
							}
						} catch (Exception e) {
							// treat as no banner found within timeout
							test.log(Status.PASS, "DA error message not present (within timeout)");
							test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
						}
						if (expectedBannerFound) {
							test.fail("DA error message displayed: " + actualErrorText);
							test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
						} 
	/*********************
	Step 14:
	Update the "Approve 2D Line Drawing?" attribute by selecting the value "Approve".
	Click on the "Save" button to save the entity.
	************************/
			 WebElement approve2dlinedrawing_dropdown = digitalssetPage.common_ele_2dlinedrawingDropdown().getShadowRoot()
						.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
						.findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
				
				utils.waitForElement(() -> approve2dlinedrawing_dropdown, "visible");
				Thread.sleep(2000);
				test.pass("2d line drawing window appeared");
				test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

				approve2dlinedrawing_dropdown.click();
				Thread.sleep(2000);
				WebElement approvedropdownvalue = digitalssetPage.common_ele_2dlinedrawingDropdown().
				getShadowRoot().findElement(By.cssSelector("#lov")).
				getShadowRoot().findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).
				getShadowRoot().findElement(By.cssSelector("#grid")).
				getShadowRoot().findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item")).
				getShadowRoot().findElement(By.cssSelector("div > div > div > span"));
				/*******************
				 * Approve 2d line drawing
				*******************/
				approvedropdownvalue.click();
				Thread.sleep(2000);
				digitalssetPage.Save_2d_Line_Drawring().click();
				Thread.sleep(3000);
				utils.waitForElement(() -> digitalssetPage.Save_2d_Line_Drawring(), "clickable");
				Thread.sleep(2000);
				/*************************************************
			     * --------- Wait for the banner to appear --------
			     ************************************************/
			    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
			    Function<WebDriver, WebElement> getBannerElement = drv -> {
			        try {
			            return drv.findElement(By.cssSelector("#app")).getShadowRoot()
			                    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			                    .findElement(By.cssSelector("#pebbleAppToast > pebble-echo-html")).getShadowRoot()
			                    .findElement(By.cssSelector("#bind-html"));
			        } catch (Exception e) {
			            return null;
			        }
			    };
			    WebElement banner = wait1.until(drv -> {
			        WebElement el = getBannerElement.apply(drv);
			        return (el != null && el.isDisplayed()) ? el : null;
			    });

			    String bannerText = banner.getText();
			    System.out.println("✅ Banner appeared with the text : " + bannerText);
			    Thread.sleep(3000);
			    
				test.pass("Approved 2d Line drawing");
				test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				/*********************
				 * Step 15: Verify that all validation errors are removed
				 * successfully after saving. Verify that the "DAM: Review
				 * Secondary Image" Data Quality Check passes successfully.
				 * Verify that the DQ check status is displayed in green color,
				 * indicating successful completion.
				 ************************/
				digitalssetPage.Workflow_Refresh_btn().click();
				Thread.sleep(5000);
				test.pass("Refreshed transaction to get the latest workflow status");
				test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				utils.waitForElement(() -> digitalssetPage.common_ele_2dlinedrawingDropdown(), "clickable");
				/****************************************
						Validate the error message is not displayed
				**************************************/		
				boolean errorVisible = false;
				String postRefreshErrorText = "";
				try {
					WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
					WebElement errorHost = shortWait.until(d -> {
						try {
							WebElement el = digitalssetPage.DA_error_Message_Secondary_Image();
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