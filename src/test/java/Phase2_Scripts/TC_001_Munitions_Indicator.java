package Phase2_Scripts;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import common_functions.BaseTest;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;

/****************************************************************************
 * TC_001_Munitions_Indicator 
 * Description: BSA PIE filter with Munition Indicator YES/NO. 
 * For both cases, verifies no rows are displayed. 
 * Reports how many records are listed.
 ****************************************************************************/

public class TC_001_Munitions_Indicator extends BaseTest {

    ExtentTest test;
    Utils utils;
    HomePage homePage;
    SearchPage2 searchPage;
    BSAPIE_Page BSAPIE_PO;
    DigitalAsset digitalssetPage;
    Actions actions;

   
    @Test(groups = { "BSAPIEowner" })
    public void MunitionsIndicator_Verification() throws Exception {
        // Initialize page objects
    	 String className = this.getClass().getSimpleName();
    	    System.out.println("Executing Test  ---  " + className);
    	    test = BaseTest.extentreport.createTest("Munitions Indicator Test");
    	    test.assignAuthor(System.getProperty("user.name")).assignDevice(System.getenv("COMPUTERNAME"));
    	    homePage = new HomePage(driver);
    	    searchPage = new SearchPage2(driver);
    	    BSAPIE_PO = new BSAPIE_Page(driver);
    	    digitalssetPage = new DigitalAsset(driver);
    	    utils = new Utils(driver, test);
    	    actions = new Actions(driver);
    	    // Navigate to Search Page
    	    utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
    	    homePage.clickSearch_Products_Button().click();
    	    utils.waitForElement(() -> searchPage.getgrid(), "clickable");
    	    test.pass("Search thing domain displayed"); 
            test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
    	    // Apply BSA PIE filter ONCE
    	    selectBSAPIEUsecase();
    	    utils.waitForElement(() -> searchPage.getgrid(), "clickable");
    	    test.pass("Search thing domain after filtered with BSA PIE Usecase? "); 
            test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    	    // Now loop only the indicator
    	    verifyMunitionIndicator("Yes");
    	    verifyMunitionIndicator("No");
    }

	/************************************************
	 * - Reusable Method --
	 **********************************************/    
    public void selectBSAPIEUsecase() throws Exception {
        searchPage.getFilterButton().click();
        utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
        searchPage.Search_MaterialType().sendKeys("BSA PIE Usecase?");
        Thread.sleep(1000);

        WebElement usecase = driver.findElement(By.cssSelector("#app")).getShadowRoot()
                .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
                .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
                .findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
                .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
                .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
                .findElement(By.cssSelector("#search-filter")).getShadowRoot()
                .findElement(By.cssSelector("#attributeModelLov_thing")).getShadowRoot()
                .findElement(By.cssSelector("#modelLov_thing")).getShadowRoot()
                .findElement(By.cssSelector("div.base-grid-structure.p-relative.hideLovHeader>div.base-grid-structure-child-2.overflow-auto.p-relative>pebble-grid"))
                .getShadowRoot()
                .findElement(By.cssSelector("#grid")).getShadowRoot()
                .findElement(By.cssSelector("#lit-grid>div"))
                .findElement(By.cssSelector(".ag-root-wrapper-body.ag-layout-normal.ag-focus-managed"))
                .findElement(By.cssSelector("[role='grid']"))
                .findElement(By.cssSelector(".ag-body-viewport.ag-layout-normal.ag-row-no-animation"))
                .findElement(By.cssSelector(".ag-center-cols-clipper>div>div"))
                .findElement(By.cssSelector("[row-index='6']"));

        actions.moveToElement(usecase).perform();
        usecase.click();
        test.pass("BSA PIE Usecase? selected in search bar"); 
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        utils.waitForElement(() -> BSAPIE_PO.BSAUseCase_YES_Checkbox(), "clickable");
        BSAPIE_PO.BSAUseCase_YES_Checkbox().click();
        Thread.sleep(1000);
        test.pass("BSA PIE Usecase? selected Yes"); 
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        digitalssetPage.Status_Apply_btn().click();
        Thread.sleep(3000);
    }
    
    
    public void verifyMunitionIndicator(String indicator) throws Exception {
    	searchPage.getFilterButton().click();
        Thread.sleep(3000);
        utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
        searchPage.Search_MaterialType().sendKeys("Munitions Indicator");
        test.pass("Entered Munitions Indicator in the search bar"); 
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        Thread.sleep(3000);
        // Locate Munitions Indicator element using Shadow DOM utility
        WebElement munitionsIndicator = utils.findShadowElement(
                "#app",
                "#contentViewManager",
                "[id^='currentApp_search-thing_rs']",
                "[id^='app-entity-discovery-component-rs']",
                "#entitySearchDiscoveryGrid",
                "#entitySearchFilter",
                "#search-filter",
                "#attributeModelLov_thing",
                "#modelLov_thing",
                "div.base-grid-structure-child-2 pebble-grid",
                "#grid",
                "pebble-lov-item",
                "div > div"
        );
        utils.waitForElement(() -> munitionsIndicator, "clickable");
        munitionsIndicator.click();

        // Select YES or NO dynamically
		if (indicator.equalsIgnoreCase("Yes")) {
			utils.waitForElement(() -> BSAPIE_PO.MunitionIndicator_YES_Checkbox(), "clickable");
			BSAPIE_PO.MunitionIndicator_YES_Checkbox().click();
			test.pass("Munitionsindicator YES option selected"); 
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			utils.waitForElement(() -> BSAPIE_PO.MunitionIndicator_NO_Checkbox(), "clickable");
			BSAPIE_PO.MunitionIndicator_NO_Checkbox().click();
			 Thread.sleep(2000);
			test.pass("Munitionsindicator NO option selected"); 
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}

        digitalssetPage.Status_Apply_btn().click();
        test.pass("Applied Munitions Indicator = " + indicator, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        // Validate rows
        try {
            utils.waitForElement(() -> searchPage.getgrid(), "visible");
            String txt = searchPage.rowsdisplayedtext().getText();
            String result = txt.split(" / ")[1];
            test.fail(result + " rows appeared when Munition Indiactor selected as " + indicator + "which is NOT expected",MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
            System.out.println();
        } catch (Exception e) {
        	
            test.pass("No rows appeared as expected for indicator = " + indicator,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        }
        // Validate tab name
        String tabname = BSAPIE_PO.MunitionIndicator_Tab().getAttribute("title");
        Assert.assertEquals(tabname, "Munitions Indicator = \"" + indicator + "\"");
        removeMunitionTab();
        Thread.sleep(2000);
    }

    // ------------------- Tab Removal -------------------
	public void removeMunitionTab() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement tab = wait.until(ExpectedConditions.visibilityOf(BSAPIE_PO.MunitionIndicator_Tab()));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
            actions.moveToElement(tab).pause(Duration.ofSeconds(1)).perform();

            wait.until(ExpectedConditions.elementToBeClickable(BSAPIE_PO.MunitionIndiacator_Yes_Remove_Icon())).click();
            System.out.println("Munition Indicator tab removed");
            test.info("Munition Indicator tab removed");
            test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        } catch (Exception e) {
            test.info("Remove icon not present, skipping removal");
            test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        }
    }
}