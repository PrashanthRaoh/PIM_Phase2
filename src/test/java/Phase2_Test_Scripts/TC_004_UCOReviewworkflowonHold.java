package Phase2_Test_Scripts;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import org.openqa.selenium.By;
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
import common_functions.BaseTest;
import common_functions.CBT_Utils;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/******************************************************************************************************************
 * TC_004_UCOReviewworkflowonHold
 * Description : Manually changes the approved record to User hold by entering the hold attributes manually
 *****************************************************************************************************************/
public class TC_004_UCOReviewworkflowonHold extends BaseTest {
	public ExtentTest test;
    @Test(groups = { "CBTUseCaseOwner" })

    public void OverrideUsecaseReview() throws InterruptedException, IOException {
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
        
        utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
        test.pass("Home Page of CBT is displayed");
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        Thread.sleep(3000);
        
        homePage.clickSearch_Products_Button().click();
        Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search thing domain button clicked");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/**************************************************
		 * --------- Apply filter for BSA PIE with staus Approved ------- *
		 ************************************************/
		searchPage.getFilterButton().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		searchPage.Search_MaterialType().sendKeys("Catalog Bearing Tool Sellable Product Status");
		Thread.sleep(2000);
		utils.waitForElement(() -> digitalssetPage.SellableProductStatus(), "clickable");
		digitalssetPage.SellableProductStatus().click();
		Thread.sleep(4000);
		utils.waitForElement(() -> digitalssetPage.Status_InProgress_dropdownvalue(), "clickable");
		/**************************************************
		 * --------- Click on Approved filter------- *
		 ********************************************************/
		digitalssetPage.Status_Approved_dropdownvalue().click();
		Thread.sleep(2000);
		test.pass("Catalog Bearing Tool Sellable Product Status is set to Approved");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Status_Apply_btn().click();
		Thread.sleep(3000);
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

		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		System.out.println("Total rows after clicking on Catalog Bearing Tool Sellable Product Status - Approved is -- d" + arrrowsdefined.size());
		test.pass("Rows appeared after clicking on Catalog Bearing Tool Sellable Product Status - Approved");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after selecting Approved Filter", arrrowsdefined.size() > 0);
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
		System.out.println("Material ID -- " + matid + " --  Material Description --" + SellableMaterialDescription);

		/*************************************************
		 * --------- Click on the material id from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);

		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Summary page for Material ID -- " + "<b>" +  matid + "</b>"+ " -- Material Description --" + SellableMaterialDescription+ " displayed");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		
		/*************************************************
		 * --------- Click on search icon and get the Status of the record before manually putting on hold ------- *
		 ************************************************/
		Actions actions2 = new Actions(driver);
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool Sellable Product Status");
		actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(5000);
		
		String status1 = null;
		try {
			status1 = CBT_Utils.getRecordStatus(driver);
			System.out.println("Status of the " + matid + " before manually entering the hold items is : - " + status1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (status1 != null) {
		    test.pass("Status of the " + matid + " before manually entering the hold items is : - " + status1);
		    Assert.assertTrue("Approved".equalsIgnoreCase(status1),"Material status should be Approved");
		} else {
		    test.fail("No element found");
		    test.fail("Status element could not be found");
		}
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		BSAPIE_PO.Tabclose_Xmark().click();
        Thread.sleep(2000);
		
		/*************************************************
		 * --------- Click on search icon and enter Catalog Bearing Tool - Select Attributes for Hold ------- *
		 ************************************************/
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool - Select Attributes for Hold");
		actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(5000);
		try {
			utils.waitForElement(() -> cbtpage.SelectAttrib_Hold_Block(), "visible");
			test.pass("Catalog Bearing Tool - Select Attributes for Hold displayed");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Thread.sleep(2000);
			cbtpage.SelectAttrib_Hold_Block().click();
			Thread.sleep(2000);
			utils.waitForElement(() -> digitalssetPage.Total_Checkboxes(), "clickable");
	        List<WebElement> totalcbs = digitalssetPage.Total_Checkboxes().findElements(By.cssSelector("[ref='eBodyViewport'] > [name='left'] > [role='row']"));
	        for (int i = 0; i < 3; i++) {
	        	 totalcbs.get(i).click();
	             Thread.sleep(2000);
			}
	        Thread.sleep(2000);
	        test.pass("Selected Hold attributes");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Thread.sleep(2000);
			digitalssetPage.Save_btn_BSA_PIE_Transaction().click();
			test.pass("Transaction saved ");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
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
		    BSAPIE_PO.Tabclose_Xmark().click();
	        Thread.sleep(2000);
	    } catch (Exception e) {
		    test.fail("❌ Failed during region override selection: " + e.getMessage());
		    Assert.fail("Error during region override selection");
		    test.log(Status.FAIL,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		/*************************************************
	     * --------- Search for the status of record again --------
	     ************************************************/
		Actions actions3 = new Actions(driver);
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool Sellable Product Status");
		actions3.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(5000);
		
		String status2 = null;
		try {
			status2 = CBT_Utils.getRecordStatus(driver);
			test.pass("Status of the " + matid + " After manually entering the hold items is : - " + status2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (status2 != null) {
		    test.pass("Status of the " + matid + " After manually entering the hold items is : - " + status2);
		    Assert.assertTrue("OnHoldUser".equalsIgnoreCase(status2),"Material status should be OnHoldUser");

		} else {
		    test.fail("No element found");
		    test.fail("Status element could not be found");
		}
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		BSAPIE_PO.Tabclose_Xmark().click();
        Thread.sleep(2000);
}
}