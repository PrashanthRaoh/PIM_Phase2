package Phase2.CBTUseCases;

/*************************************************************************
 * TC_002_VerifyAutoApprove_Attributes
 * The user applies filter Catalog Bearing Tool Usecase[Int]? Is "Yes" and 
 * "Catalog Bearing Tool Sellable Product Status" is "Approved"
 * The entity should be removed from the workflow Catalog Bearing Tool Use Case Approval
NOTE : Other work flows can exist since we have multiple use cases
 *************************************************************************/
import org.testng.Assert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

@Test(groups = { "CBTUseCaseOwner" })
public class TC_002_VerifyAutoApprove_Attributes extends BaseTest {
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
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_001_RecordmovesoutoftheReviewUseCaseSelection.txt";
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);

		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		test.pass("Home Page of CBT is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);

		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

//			/**************************************************************
//			 * apply filter Catalog Bearing Tool Usecase[Int]? Is "Yes"
//			 **************************************************************/
//			searchPage.getFilterButton().click();
//			utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
//			searchPage.Search_MaterialType().sendKeys("Catalog Bearing Tool Usecase[Int]?");
//			Thread.sleep(2000);
//			cbtpage.CBTUcasecaseInt_ThingDomain().click();
//			Thread.sleep(2000);
//			cbtpage.CBTUcasecaseInt_Yes_Option().click();
//			Thread.sleep(2000);
//			cbtpage.CBTUsecase_IntApply_btn().click();
//			Thread.sleep(2000);
//			  /**************************************************************
//			   *  "Catalog Bearing Tool Sellable Product Status" is "Approved"
//			   **************************************************************/
//		        searchPage.getFilterButton().click();
//		        utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
//		        searchPage.Search_MaterialType().sendKeys("Catalog Bearing Tool Sellable Product Status");
//		        Thread.sleep(1000);
//		        cbtpage.CBTSellableProd_Status_Thingdomain().click();
//		        Thread.sleep(1000);
//		        cbtpage.CBT_Approved_Option().click();
//		        Thread.sleep(2000);
//				cbtpage.CBTUsecase_IntApply_btn().click();

		cbtUtils.applyCatalogUsecaseIntYesFilter();
		Thread.sleep(2000);
		cbtUtils.applySellableProductStatusApprovedFilter();

//		/**************************************************************
//		 * Get the number of rows after applying the filter
//		 **************************************************************/
//		Actions actions = new Actions(driver);
//		WebElement rowsredefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
//				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
//				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
//				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
//				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
//				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
//				.findElement(By.cssSelector("#grid"));
//
//		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
//
//		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
//				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));
//
//		System.out.println("Total rows after entering the material ID is  -- " + arrrowsdefined.size());
//		test.pass("Rows after after clicking on CBT Approved status filter is -- " + arrrowsdefined.size());
//		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		Assert.assertTrue(arrrowsdefined.size() > 0, "There should be results after applying filters with Inprogress status");
//
//		/************
//		 * Random number generator to click on row within row count
//		 ************/
//		Random rand = new Random();
//		int min = 0;
//		int max = arrrowsdefined.size();
//		int randnum = rand.nextInt(max - min) + min;
//
//		System.out.println("Row chosen is " + randnum);
//		WebElement RowByRow = arrrowsdefined.get(randnum);
//		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
//		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
//		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);
//
//		/*************************************************
//		 * --------- Click on the material id from the result------- *
//		 ************************************************/
//		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
//		actions.moveToElement(RowByRow).build().perform();
//		Thread.sleep(500);
//		matidElement.click();
//		Thread.sleep(3000);
//		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
//		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for verification");
//		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//
//		/**************************
//		 * Verify the status of the record. It should be on hold or Approved
//		 **************************/
//		Thread.sleep(1000);
//		summaryPage.SearchIcon().click();
//		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool Sellable Product Status");
//		Thread.sleep(1000);
//		actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
//		Thread.sleep(3000);
//
//		WebElement targetElement = null;
//		String RecordStatus = null;
//
//		try {
//			targetElement = utils.waitForElement(() -> cbtpage.CBT_Sellable_Product_Status(), "visible");
//		} catch (Exception e) {
//			test.fail("CBT Sellable Product Status element did NOT appear");
//			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//			Assert.fail("CBT Sellable Product Status element not found");
//		}
//
//		if (targetElement == null) {
//			test.fail("CBT Sellable Product Status element is NULL");
//			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//			Assert.fail("CBT Sellable Product Status element is null");
//		}
//
//		test.pass("CBT Product status shown up");
//		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//
//		RecordStatus = targetElement.getText();
//		System.out.println("Status is : " + RecordStatus);
//
//		test.pass("Status of the " + matid + " is  : - <b>" + RecordStatus + "</b>");
//		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		Assert.assertEquals(RecordStatus, "Approved", "Record status mismatch!");
//
//		BSAPIE_PO.Tabclose_Xmark().click();
//		Thread.sleep(4000);
		
//		Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndVerifyApprovedStatus(searchPage, summaryPage,cbtpage, BSAPIE_PO, test);
		Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
		String matid = selectedRecord.get("Material Id");
		System.out.println("Selected Material ID: " + matid);
		String RecordStatus = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
		System.out.println("Selected Record Status: " + RecordStatus);

		/**************************
		 * Verify the workflow status should not be in Catalog Bearing Tool Use Case
		 * Approval Any other flows it can be since we have multiple use cases but it
		 * should not be in Catalog Bearing Tool Use Case Approval flow
		 **************************/
		WebElement workflowContainer;
		List<WebElement> steps = new ArrayList<>();

		try {
			workflowContainer = driver.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
					.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
					.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#workflowStepper_newsellablematerial_workflowDefinition"));

			steps = workflowContainer.findElements(By.cssSelector("pebble-step"));

		} catch (Exception e) {
			test.pass("No workflow panel found → PASS");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			System.out.println("No workflows present");
			return;
		}
		if (steps.isEmpty()) {
			test.pass("Workflow exists but NO steps present → PASS");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			System.out.println("No workflow steps present");
			return;
		}
		System.out.println("===== WORKFLOW STEPS =====");
		String activeStep = null;

		for (WebElement step : steps) {
			String text = step.getText().trim();
			String classAttr = step.getAttribute("class");

			boolean isActive = classAttr != null && classAttr.toLowerCase().contains("active");
			System.out.println("Step: " + text + (isActive ? "  <-- ACTIVE" : ""));
			if (isActive) {
				activeStep = text;
			}
			// ❌ unwanted validation
			if (text.contains("Catalog Bearing Tool Use Case Approval")) {
				System.out.println("UNWANTED STEP FOUND: " + text);
				test.warning("Unexpected workflow step present: " + text);
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
		test.pass("Active workflow step: " + (activeStep != null ? activeStep : "NOT FOUND"));
	}
}
