package Phase2.CBTUseCases;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.By;
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
    test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression") .assignDevice(System.getenv("COMPUTERNAME"));

    homePage = new HomePage(driver);
    CBT_Page cbtpage = new CBT_Page(driver);
    SummaryPage summaryPage = new SummaryPage(driver);
    SearchPage2 searchPage = new SearchPage2(driver);
    BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
    DigitalAsset digitalssetPage = new DigitalAsset(driver);
    CBT_Utils cbtUtils = new CBT_Utils(driver, utils);

    /******************************************************************
     * STEP 1: Search Products and apply filter
     * Catalog Bearing Tool Usecase[Int]? (Auto) = Yes
     ******************************************************************/
    homePage.clickSearch_Products_Button().click();
    Thread.sleep(5000);
    utils.waitForElement(searchPage::getgrid, "clickable");
    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    cbtUtils.applyCatalogUsecaseIntFilterByLabelAndValue("Catalog Bearing Tool Usecase[Int]? (Auto)", "Yes");
    /******************************************************************
     * STEP 2: Open a random material from filtered results
     ******************************************************************/
    utils.waitForElement(searchPage::getgrid, "clickable");

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

    Assert.assertTrue(arrrowsdefined.size() > 0, "There should be results after applying Auto=Yes filter");
    test.pass("Rows appeared after applying Auto=Yes filter");
    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    Actions actions = new Actions(driver);
    Random rand = new Random();
    int randnum = rand.nextInt(arrrowsdefined.size());

    WebElement rowByRow = arrrowsdefined.get(randnum);
//    String sellableMaterialDescription = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
//    String matid = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
    
    String sellableMaterialDescription = "";
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
			rowByRow = freshRows.get(randnum);
			sellableMaterialDescription = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
			matid = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
			break; // success
		} catch (org.openqa.selenium.StaleElementReferenceException | IndexOutOfBoundsException e) {
			attempts++;
			Thread.sleep(1000);
		}
	}
	if (sellableMaterialDescription == null || sellableMaterialDescription.isEmpty() || matid == null || matid.isEmpty()) {
		throw new RuntimeException("Unable to read material details after retries due to stale elements or missing data");
	}
	System.out.println("Material ID -- " + matid + " Material Description --" + sellableMaterialDescription);

    WebElement matidElement = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
    actions.moveToElement(rowByRow).build().perform();
    Thread.sleep(1000);
    matidElement.click();
    Thread.sleep(5000);

    utils.waitForElement(() -> digitalssetPage.Summarythingsneedtofix_grid(), "visible");
    test.pass("Material ID -- " + matid + " Material Description -- " + sellableMaterialDescription + " is selected");
    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
    data.put("Material ID", matid);
    /******************************************************************
     * STEP 3: Click UCO - Review Selection Discrepancies - Catalog Bearing Tool
     ******************************************************************/
    boolean ucoClicked = cbtUtils.clickReviewSelectionDiscrepanciesUco(test);
    Assert.assertTrue(ucoClicked, "Could not find/click UCO: Review Selection Discrepancies - Catalog Bearing Tool");
    /******************************************************************
     * STEP 4: Read values before overriding
     ******************************************************************/
    utils.waitForElement(() -> cbtpage.CBTOverrideElements().get(0), "visible");

    String CBTIntVal = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");
    String CBTIntautoVal = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");

    test.pass("Before overriding:<br>" +
            "Catalog Bearing Tool Usecase[Int]? = <b>" + CBTIntVal + "</b><br>" +
            "Catalog Bearing Tool Usecase[Int]? = <b>" + CBTIntautoVal + "</b>");
    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    /******************************************************************
     * STEP 5: Set Override = No and Save
     ******************************************************************/
    List<WebElement> rsItems = cbtpage.CBTOverrideElements();

    WebElement overrideElementNo = rsItems.get(2).getShadowRoot()
            .findElement(By.cssSelector("#input")).getShadowRoot()
            .findElement(By.cssSelector(".attribute-control")).getShadowRoot()
            .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
            .findElement(By.cssSelector(".d-flex"));

    overrideElementNo.click();
    Thread.sleep(2000);
    utils.waitForElement(() -> cbtpage.DropdownCommonElement(), "visible");
    cbtpage.NoOption().click();
    Thread.sleep(2000);

    cbtUtils.saveAndRefreshRecord(digitalssetPage, cbtpage, test);

    /******************************************************************
     * STEP 6: Validate values after Override = No
     ******************************************************************/
    String CBTIntVal_Refresh = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");
    String cbtIntOverrideVal = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]? (Override)");
    System.out.println("Override value = " + cbtIntOverrideVal);

    test.pass("After overriding to No and refreshing:<br>" +
            "Catalog Bearing Tool Usecase[Int]? = <b>" + CBTIntVal_Refresh + "</b><br>" +
            "Catalog Bearing Tool Usecase[Int]? (Override) = <b>" + cbtIntOverrideVal + "</b>");
    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    /******************************************************************
     * STEP 7: Status should be empty
     ******************************************************************/
    String recordStatus = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
    Assert.assertTrue(recordStatus == null || recordStatus.trim().isEmpty(),"Expected status to be empty, but got: " + recordStatus);

    if (recordStatus == null || recordStatus.trim().isEmpty()) {
        test.pass("Status is empty as expected.");
        data.put("CBT Sellable Product Status", "");
    } else {
        test.warning("Status is not empty: " + recordStatus);
        data.put("CBT Sellable Product Status", recordStatus);
    }
    BSAPIE_PO.Tabclose_Xmark().click();
    Thread.sleep(4000);
    /******************************************************************
     * STEP 8: Remove Override = No and Save
     ******************************************************************/
    cbtpage.CBT_UsecaseInt_Override_RemoveNoOption().click();
    Thread.sleep(1000);

    cbtUtils.saveAndRefreshRecord(digitalssetPage, cbtpage, test);

    /******************************************************************
     * STEP 9: Verify values after removing No option
     ******************************************************************/
    String CBTIntVal_AfterRemovingNoOption = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");
    String CBTIntautoVal_AfterRemovingNoOption = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");

    test.pass("After removing No option:<br>" +
            "Catalog Bearing Tool Usecase[Int]? = <b>" + CBTIntVal_AfterRemovingNoOption + "</b><br>" +
            "Catalog Bearing Tool Usecase[Int]? = <b>" + CBTIntautoVal_AfterRemovingNoOption + "</b>");
    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    Assert.assertEquals(CBTIntVal_AfterRemovingNoOption, CBTIntVal, "After removing No option, 'Catalog Bearing Tool Usecase[Int]?' did not revert to original value.");
    /******************************************************************
     * STEP 10: Go back to Search Products
     ******************************************************************/
    Thread.sleep(4000);
    homePage.clickSearch_Products_Button().click();
    Thread.sleep(3000);
    utils.waitForElement(searchPage::getgrid, "clickable");
    test.pass("Navigated back to Search Products");
    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
    /******************************************************************
     * STEP 11: Apply filter Auto = No
     ******************************************************************/
    cbtUtils.applyCatalogUsecaseIntFilterByLabelAndValue("Catalog Bearing Tool Usecase[Int]? (Auto)", "No");
    /******************************************************************
     * STEP 12: Open a record from Auto = No results
     ******************************************************************/
    Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
    String matid2 = selectedRecord.get("Material Id");
    String desc2 = selectedRecord.get("Material Description");
    data.put("Material ID - Auto No", matid2);
    /******************************************************************
     * STEP 13: Click same UCO again
     ******************************************************************/
    boolean ucoClicked2 = cbtUtils.clickReviewSelectionDiscrepanciesUco(test);
    Assert.assertTrue(ucoClicked2, "Could not find/click UCO after applying Auto=No filter");
    /******************************************************************
     * STEP 14: Capture values before Override = Yes
     ******************************************************************/
    String cbtIntValBeforeYesOverride = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");
    String cbtIntAutoValBeforeYesOverride = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]? (Auto)");

    test.pass("Before overriding to Yes:<br>" +
            "Catalog Bearing Tool Usecase[Int]? = <b>" + cbtIntValBeforeYesOverride + "</b><br>" +
            "Catalog Bearing Tool Usecase[Int]? (Auto) = <b>" + cbtIntAutoValBeforeYesOverride + "</b>");
    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    /******************************************************************
     * STEP 15: Set Override = Yes and Save
     ******************************************************************/
    List<WebElement> rsItemsAfterAutoNo = cbtpage.CBTOverrideElements();
    WebElement overrideElementYes = rsItemsAfterAutoNo.get(2).getShadowRoot()
            .findElement(By.cssSelector("#input")).getShadowRoot()
            .findElement(By.cssSelector(".attribute-control")).getShadowRoot()
            .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
            .findElement(By.cssSelector(".d-flex"));

    overrideElementYes.click();
    Thread.sleep(2000);
    utils.waitForElement(() -> cbtpage.DropdownCommonElement(), "visible");
    cbtpage.YesOption().click();
    Thread.sleep(2000);

    cbtUtils.saveAndRefreshRecord(digitalssetPage, cbtpage, test);

    /******************************************************************
     * STEP 16: Validate final values after Override = Yes
     ******************************************************************/
	String cbtIntVal_AfterYesOverride = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]?");
	String cbtIntOverrideVal_AfterYesOverride = cbtUtils.getCbtAttributeValue("Catalog Bearing Tool Usecase[Int]? (Override)");
	test.pass("After overriding to Yes:<br>" +
	        "Catalog Bearing Tool Usecase[Int]? = <b>" + cbtIntVal_AfterYesOverride + "</b><br>" +
	        "Catalog Bearing Tool Usecase[Int]? (Override) = <b>" + cbtIntOverrideVal_AfterYesOverride + "</b>");
	test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

Assert.assertEquals(cbtIntVal_AfterYesOverride,"Yes","Catalog Bearing Tool Usecase[Int]? should become Yes after Override is set to Yes.");
Assert.assertEquals(cbtIntVal_AfterYesOverride, cbtIntOverrideVal_AfterYesOverride, "Catalog Bearing Tool Usecase[Int]? should match Catalog Bearing Tool Usecase[Int]? (Auto) after Override=Yes.");
System.out.println("Final validation complete for Auto=No flow. Material ID: " + matid2 + ", Description: " + desc2);

// 1) Get record status
String currentRecordStatus = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
data.put("CBT Sellable Product Status", currentRecordStatus == null ? "" : currentRecordStatus);

if (currentRecordStatus == null || currentRecordStatus.trim().isEmpty()) {
	System.out.println("Record status is empty/not available.");
    test.warning("Record status is empty/not available.");
} else {
	System.out.println("Record status is " + currentRecordStatus);
    test.pass("Record status: <b>" + currentRecordStatus + "</b>");
}
/******************************************************************
 * STEP 17: Verify active workflow after refresh
 ******************************************************************/
    String activeWorkflow = CBT_Utils.getActiveWorkflowAfterRefresh(driver, cbtpage, test);
    data.put("Active Workflow", activeWorkflow == null ? "" : activeWorkflow);
    if (activeWorkflow == null || activeWorkflow.trim().isEmpty()) {
    	System.out.println("Active workflow is empty/not found. ");
        test.warning("Active workflow is empty/not found.");
    } else {
        System.out.println("Active Workflow after setting Catalog Bearing Tool Usecase[Int]? (Override) to Yes is  : " + activeWorkflow);
        test.pass("Active workflow: <b>" + activeWorkflow + "</b>");
    }
}
}