package Phase2_Test_Scripts;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import common_functions.BaseTest;
import common_functions.CBT_Utils;
import common_functions.Utils;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/****************************************
 * TC_001_UseCaseReview_Override
 * Description : Review Use case selection record by overriding Catalog Bearing Tool Use case[Int]? (Override) attribute
 * and checks if record has moved out of Review Use case workflow
 ***************************************/
public class TC_001_UseCaseReview_Override extends BaseTest {
	
	public ExtentTest test;
	@Test(groups = { "CBTUseCaseOwner" })
	public void OverrideUsecaseReview() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		Thread.sleep(5000);
		
		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		test.pass("Home Page of CBT is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);

		/*********************************************
		 Get names of items under New Sellable Material list
		 ********************************************/
		List<WebElement> summaryElements = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().
				 findElement(By.cssSelector("#rock-my-todos")).getShadowRoot().
				 findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));

		System.out.println("Total items: " + summaryElements.size());
		List<String> expectedItems = Arrays.asList("Pending Engineered Product Data","Review Usecase Selection");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		/******************************************
			Click on Review Use case Selection
		******************************************/
		for (int i = 0; i < summaryElements.size(); i++) {
			WebElement summary = summaryElements.get(i);
			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String actualText = innerDiv.getText().trim();
			System.out.println("Item " + (i + 1) + ":--" + actualText);
			
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
		test.pass("Review Usecase Selection selected ");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(10000);

		/***************************************
		 * ***** Click on Review Usecase Selection ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after Review Usecase Selection");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/**************************************************
		 * --------- Get Row count------- *
		 ********************************************************/
		Actions actions = new Actions(driver);
		WebElement rowsredefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid"));

		utils.waitForElement(() -> searchPage.getgrid(), "clickable");

		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE Inprogress status -- " + arrrowsdefined.size());
		test.pass("Rows after after clicking on Pending Usecase Approval - BSA PIE Inprogress status appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters with Inprogress status", arrrowsdefined.size() > 0);
		/********************************************************
		 * Random number generator to click on row within row count
		 ********************************************************/
		Random rand = new Random();
		int min = 0;
		int max = arrrowsdefined.size();
		int randnum = rand.nextInt(max - min)  + min;
		System.out.println("Row chosen is " + randnum);

		WebElement RowByRow = arrrowsdefined.get(randnum);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);
		
		/*************************************************
		 * --------- Click on the material id from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);
		/*************************************************
		 * --------- Wait till Summary tab ------- *
		 ************************************************/
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription + " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************
		 * --------- Get all the Workflow before override  ------- *
		 ************************************************/
		Map<String, Object> before = CBT_Utils.getWorkflowDetails(driver);
		System.out.println("Before CBT Override: " + before);
		/*************************************************
		 * --------- Get the list under New Sellable Material  ------- *
		 ************************************************/
		boolean ucoFound = false;
		WebElement ucoElement = null;
		String ucoLocation = "";
		String CondtoFind = "UCO: Review Selection Discrepancies - Catalog Bearing Tool";
		
		List<String> summaryLines = new ArrayList<>();
		System.out.println("************ New Sellable Material section in summary section are :- ************\n");
		List<WebElement> conditions = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
		for (int i = 0; i < conditions.size(); i++) {
			WebElement cond = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list")).get(i);
			String busscondname = cond.findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
			System.out.println("Sellable material condition " + (i + 1) + " -- " + busscondname);
			summaryLines.add("Condition " + " -- " + "<b>" + busscondname + "<b>");
			
			if (busscondname.toLowerCase().contains(CondtoFind.toLowerCase())) {
		        ucoFound = true;
		        ucoElement = cond;
		        ucoLocation = "New Sellable Material";
		    }
		}
		Markup summaryMarkup = MarkupHelper.createOrderedList(summaryLines);
		test.log(Status.PASS, "************ New Sellable Material need to fix are :- ************");
		test.log(Status.PASS, summaryMarkup);

				/******************
				 * General Conditions
				******************/
		System.out.println("************ General Conditions are :- ************");
		List<WebElement> Generalconditions = cbtpage.Summarythings_General().findElements(By.cssSelector(".data-list"));
		List<String> genConditions = new ArrayList<>();
		
		for (int i = 0; i < Generalconditions.size(); i++) {
			WebElement cond1 =cbtpage.Summarythings_General().findElements(By.cssSelector(".data-list")).get(i);
			String busscondname1 = cond1.findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
			System.out.println("General Condition " + (i + 1) + " -- " + busscondname1);
			genConditions.add("General Condition " + " -- " + "<b>" + busscondname1 + "<b>");
			
			if (busscondname1.toLowerCase().contains(CondtoFind.toLowerCase())) {
		        ucoFound = true;
		        ucoElement = cond1;
		        ucoLocation = "General Conditions";
		    }
		}
		Markup GensummaryMarkup = MarkupHelper.createOrderedList(genConditions);
		test.log(Status.PASS, "************ General things need to fix are :- ************");
		test.log(Status.PASS, GensummaryMarkup);
		    
		if (ucoFound && ucoElement != null) {
//			************Click on Review Selection Discrepancies - Catalog Bearing Tool***********************
		    ucoElement.click();
		    System.out.println(CondtoFind + " found under : " + ucoLocation);
		    test.log(Status.PASS, "UCO: Review Selection Discrepancies - Catalog Bearing Tool was found and clicked under: " + ucoLocation);
		} else {
		    System.out.println("No UCO: Review Selection Discrepancies - Catalog Bearing Tool found in Summary or General conditions.");
		    test.log(Status.WARNING, "No UCO: Review Selection Discrepancies - Catalog Bearing Tool found in Summary or General conditions.");
		}
		/******************************
		 * Wait for review selection page
		 ******************************/
		utils.waitForElement(() -> cbtpage.Catalog_Bearing_Tool_Usecase_Int_CommonElement(), "visible");
		System.out.println("Review selection page is is displayed");
		test.pass("Review selection page is is displayed");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		
		/************************************************************************************************
		 * If CBT Int value is there then get it ...else report there is no value in it
		************************************************************************************************/
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		try {
			WebElement tag0List = wait2.until(driver ->
	        cbtpage.Catalog_Bearing_Tool_Usecase_Int_CommonElement()
	            .getShadowRoot()
	            .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container > pebble-tags")).getShadowRoot()
	            .findElement(By.cssSelector("#tag0")));
		    if (tag0List.isDisplayed()) {
		        String tagValue = tag0List.getText();
		        System.out.println("Catalog Bearing Tool Usecase[Int] value is : " + tagValue);
		        test.log(Status.PASS, "Catalog Bearing Tool Usecase[Int] value is : " + tagValue);
		        test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    }
			} catch (Exception e) {
			    System.out.println("Catalog Bearing Tool Usecase[Int] does not have any value");
			    test.log(Status.INFO, "Catalog Bearing Tool Usecase[Int] does not have any value");
			    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} 
		/************************************************************************************************
		 * Click override drop down and select yes
		************************************************************************************************/
		cbtpage.CBT_Override2().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> cbtpage.Override_Yes_Option(), "visible");
		System.out.println("Override option displayed");
		test.pass("Override option displayed");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		cbtpage.Override_Yes_Option().click();
		Thread.sleep(6000);
		
		searchPage.SaveTransaction_btn().click();
		Thread.sleep(3000);
		/*************************************************
		 * --------- Wait for the banner to appear
		 ************************************************/
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

	    WebElement banner = wait.until(drv -> {
	        WebElement el = getBannerElement.apply(drv);
	        return (el != null && el.isDisplayed()) ? el : null;
	    });

	    String bannerText = banner.getText();
	    System.out.println("✅ Banner appeared with the text : " + bannerText);
		
	    Thread.sleep(6000);
	    test.pass("Data Saved");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		test.pass("Saved the transaction");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
		 * --------- Refresh to get the updated workflow ------- *
		 ************************************************/
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
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	
				/************************
				 * Navigate to Search thing domain
				 **********************/
			cbtpage.Searchthing_BreadCrum().click();
			utils.waitForElement(() -> searchPage.searchthingdomain_Input_Mat_Id(), "visible");
			System.out.println("Clicked on Search thing breadcrum");
	
	/************************
	 * Verify if the record has moved out of review selection
	 **********************/	
	searchPage.searchthingdomain_Input_Mat_Id().click();
	searchPage.searchthingdomain_Input_Mat_Id().clear();
	searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
	searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
	Thread.sleep(5000);

	try {
		String txt = searchPage.rowsdisplayedtext().getText();
		String result = txt.split(" / ")[1];
		int zerorows = Integer.parseInt(result);
		System.out.println("Total records after completing the review use case selection override is " + zerorows);
		if (zerorows == 0) {
			test.pass(matid + " Record is moved out of Review selection. Hence not visible");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.fail(matid + " Record is NOT moved out of Review selection. Please verify the data");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.assertEquals(zerorows, 0);
		}
		
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
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
	}
}
}