package Phase2.CBTUseCases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.By;
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
import common_functions.CBT_Utils;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

@Test(groups = { "CBTUseCaseOwner" })
public class TC_007_Validating_CBTUsecasescenarios_Toggle extends BaseTest{
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	public void ToggleOverrideYesNo() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_005_ListAllHoldAttributes.txt";
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		/******************************************************************
		 * Apply the filter Catalog Bearing Tool Usecase[Int]? (Auto)" is "Yes
		 ****************************************************************/
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(5000);
		utils.waitForElement(searchPage::getgrid, "clickable");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

		cbtUtils.applyCatalogUsecaseIntFilterByLabel("Catalog Bearing Tool Usecase[Int]? (Auto)");
		
		/******************************************************************
		 * Click any material
		 ****************************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
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
		Thread.sleep(3000);
		// Read values with retry to avoid StaleElementReferenceException. We re-find the rows list
		// from the shadow root each attempt and then read the required cell values.
		String SellableMaterialDescription = "";
		String matid = "";
		int attempts = 0;
		final int maxAttempts = 3;
		while (attempts < maxAttempts) {
			try {
				List<WebElement> freshRows = rowsredefined.getShadowRoot().findElements(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));
				// If the size changed (unlikely), safeguard the index
				if (randnum >= freshRows.size()) {
					throw new IndexOutOfBoundsException("Selected row index is out of bounds after refresh");
				}
				RowByRow = freshRows.get(randnum);
				SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
				matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
				break; // success
			} catch (org.openqa.selenium.StaleElementReferenceException | IndexOutOfBoundsException e) {
				attempts++;
				Thread.sleep(1000);
			}
		}
		if (SellableMaterialDescription == null || SellableMaterialDescription.isEmpty() || matid == null || matid.isEmpty()) {
			throw new RuntimeException("Unable to read material details after retries due to stale elements or missing data");
		}
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(5000);
		utils.waitForElement(() -> digitalssetPage.Summarythingsneedtofix_grid(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription + " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		data.put("Material ID", matid);
		/******************************************************************
		 * Click on UCO: Review Selection Discrepancies - Catalog Bearing Tool business condition
		 ****************************************************************/
		 SearchContext base = driver.findElement(By.cssSelector("#app")).getShadowRoot()
		        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
		        .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
		        .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
		        .findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']")).getShadowRoot();

		List<WebElement> allItems = new ArrayList<>();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // temporarily reduce implicit wait to speed up finding elements that may not exist
		try {
			List<WebElement> acc0Host = base.findElements(By.cssSelector("#accordion\\ 0"));
			if (!acc0Host.isEmpty()) {
				allItems.addAll(base.findElements(By.cssSelector("#accordion\\ 0 > div > div .data-list")));
			}
			List<WebElement> acc1Host = base.findElements(By.cssSelector("#accordion\\ 1"));
			if (!acc1Host.isEmpty()) {
				allItems.addAll(base.findElements(By.cssSelector("#accordion\\ 1 > div > div .data-list")));
			}
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); // use your framework default
		}
		/****************************************
		 * Find Review Selection Discrepancies - Catalog Bearing Tool business condition
		 ***************************************/
		System.out.println("Total items: " + allItems.size());
		for (WebElement el : allItems) {
		    String text = el.getAttribute("innerText");
		    System.out.println(text);
		    if (text != null && text.contains("UCO: Review Selection Discrepancies - Catalog Bearing Tool")) {
		        el.click();
		        System.out.println("Clicked: " + text);
		        test.pass("Clicked on UCO: Review Selection Discrepancies - Catalog Bearing Tool");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		        break;
		    }
		}
		/****************************************************
		 * To get the labels of each and values Before overriding
		  **************************************************/
		utils.waitForElement(() -> cbtpage.CBTOverrideElements().get(0), "visible");
		List<WebElement> rsItems = cbtpage.CBTOverrideElements();
		System.out.println("Total attributes: " + rsItems.size());

		String CBTIntVal = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");
		String CBTIntautoVal = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]? (Auto)");
		test.pass("Before overriding:<br>" +
          "Catalog Bearing Tool Usecase[Int]? = <b>" + CBTIntVal + "</b><br>" +
          "Catalog Bearing Tool Usecase[Int]? (Auto) = <b>" + CBTIntautoVal + "</b>");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		System.out.println("Catalog Bearing Tool Usecase[Int]? value is " + CBTIntVal);
		System.out.println("Catalog Bearing Tool Usecase[Int]? (Auto) value is " + CBTIntautoVal);
		/******************************************************************
		 * Catalog Bearing Tool Usecase[Int]?(Override)" attribute and select "No"
		 ****************************************************************/
		WebElement Overrideelement = rsItems.get(2).getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector(".attribute-control")).getShadowRoot()
			    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
			    .findElement(By.cssSelector(".d-flex"));
		Overrideelement.click();
		Thread.sleep(2000);
		utils.waitForElement(() -> cbtpage.DropdownCommonElement(), "visible");
		cbtpage.YesOption().click();
		Thread.sleep(5000);
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
			}};
		WebElement banner = wait1.until(drv -> {
			WebElement el = getBannerElement.apply(drv);
			return (el != null && el.isDisplayed()) ? el : null;
		});

		String bannerText = banner.getText();
		System.out.println("✅ Banner appeared with the text : " + bannerText);
		test.pass("Banner appeared with the text : " + bannerText);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);
		Assert.assertTrue( bannerText != null && bannerText.toLowerCase().contains("data saved"),"❌ Expected banner text to contain 'data saved', but got: " + bannerText);
		/*******************************************
		 * Refresh the record
		 ******************************************/
		cbtpage.CBT_Workflow_Refresh_btn().click();
		Thread.sleep(5000);
		test.pass("Refreshed transaction to get the latest workflow status");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/****************************************************
		 * To get the labels of each and values After overriding
		  **************************************************/
		String CBTIntVal_Refresh = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");
		String CBTIntautoVal_Refresh = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]? (Auto)");
		test.pass("After overriding and Refreshing :<br>" +
          "Catalog Bearing Tool Usecase[Int]? = <b>" + CBTIntVal_Refresh + "</b><br>" +
          "Catalog Bearing Tool Usecase[Int]? (Auto) = <b>" + CBTIntautoVal_Refresh + "</b>");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		System.out.println("After overriding and Refreshing Catalog Bearing Tool Usecase[Int]? value is " + CBTIntVal_Refresh);
		System.out.println("After overriding and Refreshing Catalog Bearing Tool Usecase[Int]? (Auto) value is " + CBTIntautoVal_Refresh);
		/*
		 * Status of the record should be approved - Catalog Bearing Tool Sellable Product Status 

		*/	
	}

}
