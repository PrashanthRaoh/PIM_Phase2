package Phase2.CBTUseCases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.common.base.Function;
import common_functions.BaseTest;
import common_functions.NotepadManager;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/*************************************************************************
 * TC_001_RecordmovesoutoftheReviewUseCaseSelection Description:
 * Review Usecase Selection -> Apply "Catalog Bearing Tool Usecase[Int]?" filter ->
 * Pick a record -> Set "Catalog Bearing Tool Usecase[Int]?" (Override) to Yes ->
 * Save changes -> Verify success banner -> Refresh workflow ->
 * Entity moves from "Review Usecase Selection" to "Pending Usecase Approval - Catalog Bearing Tool"
 * -> status is updated to Yes.
 *************************************************************************/
@Test(groups = { "CBTUseCaseOwner" })
public class TC_001_RecordmovesoutoftheReviewUseCaseSelection extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	public void RecordmovestoPendingUsecaseApproval() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_001_RecordmovesoutoftheReviewUseCaseSelection.txt";

		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		test.pass("Home Page of CBT is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);

		List<WebElement> tabs = cbtpage.CBT_HomePage_Tabs();
		System.out.println("There are " + tabs.size() + " tabs at CBT home page");

		List<String> tabNames = new ArrayList<>();
		for (int i = 0; i < tabs.size(); i++) {
			WebElement spanElement = tabs.get(i);
			WebElement titleSpan = spanElement.findElement(By.cssSelector(".tab-title-content .tab-title > span"));
			String tabName = titleSpan.getText();
			System.out.println("Tab " + (i + 1) + " name: " + tabName);
			tabNames.add(tabName);
		}
		/*********************************
		 * Get Tab names in CBT home page
		 ************************************/
		System.out.println("All tab names: " + tabNames);
		for (int i = 0; i < tabNames.size(); i++) {
			if (tabNames.contains("New Sellable Material(s)")) {
				WebElement spanElement = tabs.get(tabNames.indexOf("New Sellable Material(s)"))
						.findElement(By.cssSelector("[class='tab-title-content'] > [class='tab-title'] > span"));
				spanElement.click();
				System.out.println("Clicked on 'New Sellable Material(s)' tab");
				Thread.sleep(1000);
				break;
			} else {
				System.out.println("'New Sellable Material(s)' tab not found");
			}
		}
		List<WebElement> summaryElements = cbtpage.CBT_UsecaseapprovalTab_Items();
		System.out.println("Total items: " + cbtpage.CBT_UsecaseapprovalTab_Items().size());

		List<String> expectedItems = Arrays.asList("Pending Engineered Product Data", "Review Usecase Selection");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int i = 0; i < summaryElements.size(); i++) {
			WebElement summary = summaryElements.get(i);
			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String actualText = innerDiv.getText().trim();
			System.out.println("Item " + (i + 1) + ":--" + actualText);
			Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
			/***************************************************************
			 * Click on Review Use case Selection
			 **************************************************************/
			if (actualText.contains("Review Usecase Selection")) {
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
		test.pass("Review Usecase Selection entities listed ");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/***************************************************************
		 * Wait till rows appears
		 **************************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		/***************************************************************
		 * Apply the filter Catalog Bearing Tool Use case[Int]?
		 **************************************************************/
		searchPage.getFilterButton().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		searchPage.Search_MaterialType().sendKeys("Catalog Bearing Tool Usecase[Int]?");
		Thread.sleep(2000);
		utils.waitForElement(() -> digitalssetPage.SellableProductStatus(), "clickable");
		test.pass("Catalog Bearing Tool Usecase[Int]? filter applied successfully");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.SellableProductStatus().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> digitalssetPage.Status_Approved_dropdownvalue(), "clickable");
		cbtpage.CBTIntFilterElement().click();
		Thread.sleep(2000);
		cbtpage.HasNoItemsOption().click();
		Thread.sleep(2000);
		test.pass("Catalog Bearing Tool Usecase[Int]? has no items filter applied successfully");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Status_Apply_btn().click();
		/***************************************************************
		 * Wait till rows appears after applying Yes filter
		 **************************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("After applying Has No value filter-- entities listed ");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/***************************************************************
		 * From the rows pick one record and Edit it
		 **************************************************************/
		WebElement rowsredefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid"));

		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		System.out.println("Total rows after clicking on Review Usecase -- " + arrrowsdefined.size());
		test.pass("Rows after after clicking on Review Usecase appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Assert.assertTrue(arrrowsdefined.size() > 0, "There should be results after applying filters");

		/************
		 * Random number generator to click on row within row count
		 ************/
		Actions actions = new Actions(driver);
		Random rand = new Random();
		int min = 0;
		int max = arrrowsdefined.size();
		int randnum = rand.nextInt(max - min) + min;

		System.out.println("Row chosen is " + randnum);

		WebElement RowByRow = arrrowsdefined.get(randnum);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);

		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription + " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		data.put("Material ID", matid);

		/*******************************************
		 * Get work flows that are visible after approval and print the names of the work flows
		 ******************************************/
		List<WebElement> allSteps = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
				.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-step"));

		List<WebElement> visibleSteps = allSteps.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
		int visibleCount = visibleSteps.size();
		System.out.println("✅ Workflow that appeared after approval are : " + visibleCount);

		// Print the names/titles of the visible workflow steps
		String expectedTitle = "Review Usecase Selection";
		WebElement activeStep = null;

		System.out.println("**********Listing all workflow steps:**********");
		for (int i = 0; i < allSteps.size(); i++) {
			WebElement step = allSteps.get(i);
			SearchContext stepShadow = step.getShadowRoot();
			String actualTitle = stepShadow.findElement(By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span")).getAttribute("title");

			boolean isActive = step.getAttribute("class") != null && step.getAttribute("class").contains("iron-selected");
			System.out.println((i + 1) + ": " + actualTitle + (isActive ? " (🟢 Active)" : ""));

			if (isActive && actualTitle.equals(expectedTitle)) {
				activeStep = step;
			}
		}

		if (activeStep == null) {
			throw new AssertionError("❌ Expected active step '" + expectedTitle + "' not found.");
		}
		System.out.println("As Expected active workflow step is: " + expectedTitle);
		test.pass("As Expected active workflow step is: " + expectedTitle);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/**********************************
		 * Verify in which row UCO: Review Selection Discrepancies - Catalog Bearing Tool was found
		 ************************************/
		WebElement shadowRootElement = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']"));

		WebElement dataListElement = shadowRootElement.getShadowRoot().findElement(By.cssSelector(".tofix-data-container > pebble-accordion > div > div .data-list"));
		WebElement accordionElement = shadowRootElement.getShadowRoot().findElement(By.cssSelector("#accordion\\ 1 > div > div .data-list"));

		boolean flagfound = false;
		if (dataListElement != null) {
			System.out.println("Found the data list in New Sellable Material(s) section : ");
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dataListElement);
			dataListElement.click();
			Thread.sleep(2000);
			test.pass("Clicked on UCO: Review Selection Discrepancies - Catalog Bearing Tool");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			flagfound = true;

		} else {
			System.out.println("Data list not found in New Sellable Material(s) section .");
		}
		if (accordionElement != null) {
			System.out.println("Found the data list in : " + "General section");
		} else {
			System.out.println("Data list not found in General section.");
		}
		if (flagfound = false) {
			throw new AssertionError("❌ 'UCO: Review Selection Discrepancies - Catalog Bearing Tool' not found in any section.");
		}

		/*******************************************
		 * Catalog Bearing Tool Use case[Int]? (Override) to Yes and save
		 ******************************************/
		digitalssetPage.primary_Image_Required_dropdown_obj().click();
		Thread.sleep(2000);
		digitalssetPage.ImageRequired_Yes().click();
		Thread.sleep(2000);
		/*************************************************
		 * --------- Save ------ *
		 ************************************************/
		digitalssetPage.AddPrimaryImage_Save_btn().click();
		Thread.sleep(5000);
		/*************************************************
		 * --------- Wait for the banner to appear --------
		 ************************************************/
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		Function<WebDriver, WebElement> getBannerElement = drv -> {
			try {
				return drv.findElement(By.cssSelector("#app")).getShadowRoot().findElement(By.cssSelector("[id^='rs']"))
						.getShadowRoot().findElement(By.cssSelector("#pebbleAppToast > pebble-echo-html"))
						.getShadowRoot().findElement(By.cssSelector("#bind-html"));
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
		test.pass("Banner appeared with the text : " + bannerText);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);
		// Hard assert: fail if banner does not contain "data saved"
		Assert.assertTrue( bannerText != null && bannerText.toLowerCase().contains("data saved"),"❌ Expected banner text to contain 'data saved', but got: " + bannerText);
		/*******************************************
		 * Refresh the record
		 ******************************************/
		WebElement Workflow_Refresh_btn = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
				.findElement(By.cssSelector("#entityActions")).getShadowRoot()
				.findElement(By.cssSelector("#toolbar")).getShadowRoot()
				.findElement(By.cssSelector("#refresh"));

		
		Workflow_Refresh_btn.click();
		Thread.sleep(5000);
		test.pass("Refreshed transaction to get the latest workflow status");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*******************************************
		 * After clicking the refresh button and waiting for the page to update  Re-fetch workflow steps
		 ******************************************/
		List<WebElement> allStepsAfterRefresh = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
				.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-step"));

		List<WebElement> visibleStepsAfterRefresh = allStepsAfterRefresh.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
		System.out.println("✅ Workflow steps after refresh: " + visibleStepsAfterRefresh.size());

		/*******************************************
		 * Find and print the active step after refresh
		 ******************************************/
		for (int i = 0; i < allStepsAfterRefresh.size(); i++) {
			WebElement step = allStepsAfterRefresh.get(i);
			SearchContext stepShadow = step.getShadowRoot();
			String actualTitle = stepShadow.findElement(By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span")).getAttribute("title");

			boolean isActive = step.getAttribute("class") != null && step.getAttribute("class").contains("iron-selected");
			System.out.println((i + 1) + ": " + actualTitle + (isActive ? " (🟢 Active)" : ""));

			if (isActive) {
				test.pass("Active workflow step after refresh is: " + actualTitle);
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}

		/*******************************************
		 * Catalog Bearing Tool Use case[Int]? has now Yes as value
		 ******************************************/
		String ucsecaseint_Readonly = cbtpage.CBTUsecase_Int__Readonly().getText();
		try {
		    Assert.assertTrue(ucsecaseint_Readonly.equals("Yes"),"❌ Catalog Bearing Tool Use case[Int]? value did not change to Yes after saving, current value: " + ucsecaseint_Readonly);
		    System.out.println("✅ Catalog Bearing Tool Use case[Int]? value is changed to Yes after saving");
		    test.pass("Catalog Bearing Tool Use case[Int]? value is changed to Yes after saving");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} catch (AssertionError e) {
			 System.out.println("✅ Catalog Bearing Tool Use case[Int]? value is NOT changed to Yes after saving and is still: " + ucsecaseint_Readonly);
		    test.fail("Catalog Bearing Tool Use case[Int]? value did not change to Yes after saving. Actual value: " + ucsecaseint_Readonly);
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    throw e;
		}
		/**************************
		 * Verify the status of the record. It should be on hold or Approved
		 **************************/
		Thread.sleep(1000);
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool Sellable Product Status");
		Thread.sleep(1000);
		actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		utils.waitForElement(() -> cbtpage.CBT_Sellable_Product_Status(), "visible");
		test.pass("CBT Product status shown up");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		String RecordStatus = null;
		
		WebElement targetElement = cbtpage.CBT_Sellable_Product_Status();
		if (targetElement != null) {
			System.out.println("Status is : " + targetElement.getText());
			RecordStatus = targetElement.getText();
			test.pass("Status of the " + matid + " is  : - <b>" + RecordStatus + "</b>");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);

		} else {
			System.out.println("🔴 There was no status field found for the record");
			test.fail("No status field found found");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		data.put("CBT Sellable Product Status", RecordStatus);
		List<WebElement> lasttabs = driver.findElement(By.cssSelector("#app"))
			    .getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
			    .getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"))
			    .getShadowRoot().findElement(By.cssSelector("#rockTabs"))
			    .getShadowRoot().findElements(By.cssSelector("[class='base-grid-structure-child-1'] > #rockTabs > pebble-tab"));

			// Get the last tab
			WebElement lastTab = lasttabs.get(lasttabs.size() - 1);

			// Find the close button within the last tab and click it
			WebElement closeButton = lastTab.findElement(By.cssSelector(".tab-title-content > .tab-title > span.dynamic-close"));
			closeButton.click();
			Thread.sleep(4000);

		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename,data);
	}
}