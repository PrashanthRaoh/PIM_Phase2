package DAM;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import org.openqa.selenium.By;
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

		utils.waitForElement(() -> detailItems.get(0), "clickable");
		System.out.println("There are " + detailItems.size() + " elements ");
		
		List<String> expectedItems = Arrays.asList("Ready for transition", "DAM: Review 2D Line Drawing","DAM: Review Representative Image (Primary)", "DAM: Review Secondary Image",
				"DAM: Review Unclassified Images");
		Assert.assertEquals(detailItems.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		/**********************************************
		 * Verify in which row DAM: Review Representative Image (Primary) was found
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

		/***************************************
		 * ****Clicked on DAM: Review Representative Image (Primary)  ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Clicked on DAM: Review Representative Image (Primary)");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/***************************
		 * Step 3:
		 * The user clicks on the "DAM: Review Representative Image (Primary)" link.
		 * Expected Result:
		 * The user should be able to view all the entities pending for
		 * "DAM: Review Representative Image (Primary)".
		****************************/

		/***************************
		 * Step 4:
		 * The user clicks on the "Filter" option and searches "Image Required? (Auto)"
		 * and selects "Has No Value".
		 * Expected Result:
		 * The sellable entities should be filtered out on the search thing domain screen.
		****************************/
		applyFilterAndSelectOption(searchPage, digitalssetPage, "Image Required? (Auto)",
				() -> digitalssetPage.ImageRequired_Auto_Dropdown(), "Has No Value");

		/***************************
		 * Step 5:
		 * The user clicks on the "Filter" option and searches "Image Required? (Override)"
		 * and selects "Has No Value".
		 * Expected Result:
		 * The sellable entities should be filtered out on the search thing domain screen.
		****************************/
		applyFilterAndSelectOption(searchPage, digitalssetPage, "Image Required? (Override)",
				() -> digitalssetPage.ImageRequired_Auto_Dropdown(), "Has No Value");
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
		/***************************
		 * Step 9:
		 * The user clicks on "Image Required? (Override)" and selects "Yes" value, then clicks on the "Save" button.
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
				wait.until(ExpectedConditions.visibilityOf(digitalssetPage.ImageRequired_Yes()));
				digitalssetPage.ImageRequired_Yes().click();
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
//		wait.until(ExpectedConditions.visibilityOf(digitalssetPage.Next_btn()));
//		digitalssetPage.Next_btn().click();
//		Thread.sleep(3000);
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
		// Get all items
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
		// Click corresponding checkbox
		imageCheckboxes.get(randomIndex).getShadowRoot().findElement(By.cssSelector("#checkboxContainer")).click();
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
		WebElement daError = wait.until(ExpectedConditions.visibilityOf(digitalssetPage.DA_error_Message()));
		Assert.assertTrue(daError.isDisplayed(), "DA error message is not displayed");
		// Capture and normalize text
		String actualErrorText = daError.getText().trim().replaceAll("\\s+", " ");
		System.out.println("DA error text: " + actualErrorText);
		test.pass("DA error message displayed: " + actualErrorText);
		// Expected from your Step 16
		String expectedErrorText = "Representative Image (Primary) has been deleted, added, or image has been updated";
		Assert.assertEquals(actualErrorText, expectedErrorText, "Mismatch in DA error message text");
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
	}

	/**********************************************
		Generic function to apply filter and select option from dropdown
	********************************************/
	private void applyFilterAndSelectOption(SearchPage2 searchPage, DigitalAsset digitalssetPage, String filterName,
			Supplier<WebElement> dropdownSupplier, String optionText) throws InterruptedException, IOException {
		searchPage.getFilterButton().click();
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		WebElement materialTypeSearch = searchPage.Search_MaterialType();
		materialTypeSearch.clear();
		materialTypeSearch.sendKeys(filterName);
		Thread.sleep(1000);
		utils.clickFilterAttribute(filterName);
		Thread.sleep(1000);

		utils.waitForElement(dropdownSupplier::get, "clickable");
		test.pass(filterName + " filter is selected");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		dropdownSupplier.get().click();
		Thread.sleep(1000);

		List<WebElement> lovRows = digitalssetPage.Dropdown_BaseObject()
				.findElements(By.cssSelector("#lit-grid div.ag-row[row-index] pebble-lov-item"));
		List<String> optionTexts = new ArrayList<>();
		WebElement optionToClick = null;

		for (WebElement lovRow : lovRows) {
			WebElement innerDiv = lovRow.getShadowRoot().findElement(By.cssSelector("div > div"));
			String text = innerDiv.getText().trim();
			optionTexts.add(text);
			System.out.println("Row text: " + text);

			if (text.equalsIgnoreCase(optionText)) {
				optionToClick = lovRow;
			}
		}
		System.out.println("All dropdown options: " + optionTexts);
		test.pass(filterName + " dropdown options: " + optionTexts);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		if (optionToClick == null) {
			test.fail("Selected " + optionText + " option for " + filterName);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.fail("'" + optionText + "' not found in dropdown options for " + filterName + ": " + optionTexts);
		}

		WebElement clickableDiv = optionToClick.getShadowRoot().findElement(By.cssSelector("div > div"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", clickableDiv);
		clickableDiv.click();
		System.out.println("Clicked dropdown option: " + optionText);
		test.pass("Selected " + optionText + " option for " + filterName);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Status_Apply_btn().click();
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.pollingEvery(Duration.ofMillis(500));

		Boolean rowsLoaded = wait.until(driver -> {
			try {
				WebElement rowContainer = driver.findElement(By.cssSelector("#app")).getShadowRoot()
						.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
						.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
						.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
						.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
						.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
						.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
						.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
						.findElement(By.cssSelector("#grid")).getShadowRoot()
						.findElement(By.cssSelector(".ag-center-cols-container"));

				List<WebElement> rows = rowContainer.findElements(By.cssSelector("div.ag-row[row-index]"));
				System.out.println("Current row count = " + rows.size());
				return rows.size() > 0;

			} catch (Exception e) {
				return false;
			}
		});
		Assert.assertTrue(rowsLoaded, "Rows did not appear after Apply for: " + filterName + " -> " + optionText);
		System.out.println("Rows loaded successfully.");
		test.pass("Applied " + optionText + " option for " + filterName);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	}
}
