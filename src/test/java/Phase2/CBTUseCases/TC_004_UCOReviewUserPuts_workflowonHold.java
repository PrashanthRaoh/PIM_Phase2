package Phase2.CBTUseCases;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

@Test(groups = { "CBTUseCaseOwner" })
public class TC_004_UCOReviewUserPuts_workflowonHold extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	@Test()
	public void ListHoldAttributes_RuleTriggered() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_004_UCOReviewUserPuts_workflowonHold.txt";

		utils.waitForElement(cbtpage::SellableMaterialTabcontent, "clickable");
		test.pass("Home Page of CBT is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(searchPage::getgrid, "clickable");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

		cbtUtils.applyCatalogUsecaseIntYesFilter();
		Thread.sleep(2000);
		cbtUtils.applySellableProductStatusApprovedFilter();
		Thread.sleep(2000);
		 utils.waitForElement(searchPage::getgrid, "clickable");
		 
		Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
		String matid = selectedRecord.get("Material Id");
		System.out.println("Selected Material ID: " + matid);
		String RecordStatus = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
		System.out.println("Selected Record Status: " + RecordStatus);

		data.put("Material Id", matid);
		data.put("CBT Sellable Product Status", RecordStatus);
		
		// 4) Close the details tab (explicitly by test)
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(1000);
		/*************************************************
		 * --------- Search Catalog Bearing Tool - Select Attributes for Hold
		 ************************************************/
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool - Select Attributes for Hold", Keys.ENTER);
		Thread.sleep(3000);
		
		List<String> autoRegionList = new ArrayList<>();
		autoRegionList.add("Construction Style Code");
		autoRegionList.add("Bearing Type");
		autoRegionList.add("Bearing Series");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_dropdown()));
		Assert.assertTrue(dropdown.isDisplayed(), "Dropdown element 'Catalog Bearing Tool - Select Attributes for Hold' should be visible");
		dropdown.click();
		
		for (String region : autoRegionList) {
			digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_Search_Input().clear();
			Thread.sleep(500);
		    digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_Search_Input().sendKeys(region);
		    Thread.sleep(500);
		    utils.waitForElement(digitalssetPage::Total_Checkboxes, "clickable");
		    List<WebElement> totalcbs = digitalssetPage.Total_Checkboxes().findElements(By.cssSelector("[ref='eBodyViewport'] > [name='left'] > [role='row']"));
		    System.out.println("There are " + totalcbs.size() + " Regions for the search " + region);
		    assertTrue("There should be results after searching for " + region, totalcbs.size() > 0);
		    test.pass("Regions fetched after searching " + region);
		    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    totalcbs.get(0).click();
		    Thread.sleep(1000);
		    test.pass("Selected " + region + " as search value");
		    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		Thread.sleep(1000);
		/************************************************
			Save and get the status of the record after refresh
		***********************************************/
		cbtUtils.Save_CBT_Transactions().click();
		Thread.sleep(3000);
		cbtpage.CBT_Workflow_Refresh_btn().click();
	    Thread.sleep(5000);
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
	    System.out.println("Banner appeared with the text : " + bannerText);
	    test.pass("Banner appeared with the text : " + bannerText);
	    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

	    Thread.sleep(5000);
	    Assert.assertTrue(bannerText != null && bannerText.toLowerCase().contains("data saved"),"Expected banner text to contain 'data saved', but got: " + bannerText);
		/*************************************************
		 * ---------Get All the Hold list attributes
		 ************************************************/
		List<String> tagNames = new ArrayList<>();
		List<WebElement> tags = cbtpage.AllHoldAttributeList();
		for (WebElement tag : tags) {
		    String text = tag.getText().trim();
		    if (!text.isEmpty()) {
		        tagNames.add(text);
		    }
		}
		System.out.println("Attributes on hold are " + tagNames);
		test.pass("Attributes list updated \n " + tagNames);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************
		 * ---------Get the Status
		 ************************************************/
		String recordStatus = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
		String normalizedStatus = recordStatus == null ? "" : recordStatus.trim();
		Assert.assertEquals(normalizedStatus, "OnHoldUser", "Expected status to be OnHoldUser, but got: " + normalizedStatus);

	if ("OnHoldUser".equalsIgnoreCase(normalizedStatus)) {
		System.out.println("Status of the record after putting hold attributes is " + normalizedStatus);
		test.pass("Status is OnHoldUser as expected.");
		data.put("CBT Sellable Product Status", normalizedStatus);
	} else {
		System.out.println("Status of the record after putting hold attributes is " + normalizedStatus + " Expected was OnHoldUser ");
		test.warning("Status is not OnHoldUser: " + normalizedStatus);
		data.put("CBT Sellable Product Status", normalizedStatus);
	}
	BSAPIE_PO.Tabclose_Xmark().click();
	Thread.sleep(4000);
		/*************************************************
		 * ---------Get the latest update of workflow
		 ************************************************/
		String expectedWorkflow = "On Hold - Catalog Bearing Tool (User Selected)";
		String activeWorkflow = CBT_Utils.getActiveWorkflowAfterRefresh(driver, cbtpage, test);
		System.out.println("Active workflow: " + activeWorkflow);

		if (activeWorkflow == null || activeWorkflow.trim().isEmpty()) {
			test.fail("Active workflow is NULL/empty after refresh.");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else if (!activeWorkflow.equals(expectedWorkflow)) {
			test.fail("Active workflow did not match expected workflow. Expected: <b>" + expectedWorkflow + "</b>, Actual: <b>" + activeWorkflow + "</b>");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.pass("Active workflow matched expected workflow: <b>" + activeWorkflow + "</b>");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		/*******************************************
		 * Final Assertions
		 ******************************************/
		test.pass("Active workflow appeared with active workflow: " + activeWorkflow);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Assert.assertNotNull(activeWorkflow, "❌ Active workflow is NULL after refresh");
		if (activeWorkflow == null || !activeWorkflow.equals(expectedWorkflow)) {
			test.fail("Active workflow did not match expected workflow after max retries. Expected: <b>"
					+ expectedWorkflow + "</b>, Actual: <b>" + activeWorkflow + "</b>");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.pass("Active workflow matched expected workflow: <b>" + activeWorkflow + "</b>");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		/*******************************************
		 * Send Back to Usecase
		 ******************************************/
		cbtpage.btn_SendBacktoUsecase().click();
		Thread.sleep(6000);
		/*************************************************
		 * --------- Wait for the banner to appear
		 ************************************************/
		Function<WebDriver, WebElement> getBannerElement2 = drv -> {
			try {
				return drv.findElement(By.cssSelector("#app")).getShadowRoot().findElement(By.cssSelector("[id^='rs']"))
						.getShadowRoot().findElement(By.cssSelector("#pebbleAppToast > pebble-echo-html"))
						.getShadowRoot().findElement(By.cssSelector("#bind-html"));
			} catch (Exception e) {
				return null;
			}
		};
		WebElement banner2 = wait.until(drv -> {
			WebElement el = getBannerElement2.apply(drv);
			return (el != null && el.isDisplayed()) ? el : null;
		});
		String bannerText2 = banner2.getText();
		String normalizedBanner = bannerText2 == null ? "" : bannerText2.replaceAll("\\s+", " ").trim().toLowerCase();
		System.out.println("Normalized banner text : " + normalizedBanner);

		boolean isSendBackSuccess = normalizedBanner.contains("send back for usecase approval") && (normalizedBanner.contains("successful") || normalizedBanner.contains("success"));

		if (isSendBackSuccess) {
			test.pass("Banner validation passed: " + bannerText2);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} 
		Thread.sleep(3000);

	String recordStatusApproved = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
	String normalizedStatusApproved = recordStatusApproved == null ? "" : recordStatusApproved.trim();
	Assert.assertEquals(normalizedStatusApproved, "Approved", "Expected status to be Approved, but got: " + normalizedStatusApproved);

	if ("Approved".equalsIgnoreCase(normalizedStatusApproved)) {
		System.out.println("Status of the record after sending back to Use case is " + normalizedStatusApproved);
		test.pass("Status is Approved as expected.");
		data.put("CBT Sellable Product Status", normalizedStatusApproved);
	} else {
		System.out.println("Status of the record after sending back to Use case is " + normalizedStatusApproved + " Expected was Approved");
		test.warning("Status is not Approved: " + normalizedStatusApproved);
		data.put("CBT Sellable Product Status", normalizedStatusApproved);
	}
	}
}