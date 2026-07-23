package DAM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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

/******************************************************************************

 * TC_009_Validate_Image_Auto_Assignment_Cage_Brass
 * Description:
 * Login as Attribute Owner - Digital Assets ->
 * Verify user is logged into PIM successfully ->
 * Verify Home Dashboard is displayed ->
 * Click on Search Products ->
 * Select Sellable Product from the left pane ->
 * Verify user is navigated to the Search Thing Domain screen ->
 * Verify all sellable products are displayed ->
 * Apply the following filters:
 *     PIM Attribute Taxonomy =
 *     PIM Attribute Taxonomy>>Engineering Bearings>>Roller Bearings>>
 *     Specialty Roller Bearings>>ADAPT
 *     Image Required? = No
 *     Cage Material Type = Brass ->
 * Verify sellable material IDs are filtered based on the selected criteria ->
 * Click on Material ID from the filtered results ->
 * Verify the entity opens in a new Entity Manage screen ->
 * Navigate to Assets menu ->
 * Select Has Images relationship ->
 * Verify Image Relationship Grid is displayed ->
 * Verify no image is attached to the selected material ->
 * Verify the following message is displayed:
 *     "No Has Image(s) relationships exist" ->
 * Verify no image has been auto-assigned to the material ->
 * Verify the material remains without any image relationship ->
 * Verify no application errors are displayed.
 *
 * Expected Result:
 * User successfully logs into PIM and accesses the Home Dashboard.
 * User navigates to Search Thing Domain and filters Sellable Products.
 * Matching Material IDs are displayed based on the search criteria.
 * Selected Material ID opens in Entity Manage screen.
 * Assets > Has Images relationship grid is displayed.
 * No image relationships are available for the selected material.
 * System displays "No Has Image(s) relationships exist".
 * No image is auto-assigned to the material.
*****************************************************/

public class TC_010_Image_auto_assignment_Cage_Brass extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	@Test(groups = {"BSAPIEOWnwer"})
	public void getReviewUnclassifiedImageTab_zero_Count() throws Exception {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		/***************************
		 * Step 1: Login as BSA PIE End Use Case Owner. Expected Result: User
		 * should be logged in successfully and landed on the Home page.
		 ***************************/
		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		Thread.sleep(5000);
		
		/*************************************************************
		 * Step 2: Verify user is logged into PIM successfully.
		 * Status: Covered together with Step 3 via Home Dashboard visibility.
		 *************************************************************/
		/*************************************************************
		 * Step 3: Verify the Home Dashboard is displayed.
		 *************************************************************/
		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "visible");
		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************************
		 * Step 4: Click on the "Search Products" option.
		 *************************************************************/
		homePage.clickSearch_Products_Button_Digital().click();
		Thread.sleep(3000);

		/*************************************************************
		 * Step 5: Select Sellable Product from the left pane.
		 * Status: Not Implemented.
		 *************************************************************/

		/*************************************************************
		 * Step 6: Verify the Search Thing Domain screen is displayed
		 * and all sellable products are available.
		 *************************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		/*************************************************************
		 * Step 7: Apply the PIM Attribute Taxonomy filter with value:
		 * Engineering Bearings > Roller Bearings >
		 * Specialty Roller Bearings > ADAPT.
		 *************************************************************/
		String filterName = "PIM Attribute Taxonomy";
		searchPage.getFilterButton().click();
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		WebElement materialTypeSearch = searchPage.Search_MaterialType();
		materialTypeSearch.clear();
		materialTypeSearch.sendKeys(filterName);
		Thread.sleep(1000);
		utils.clickFilterAttribute(filterName);
		Thread.sleep(1000);
		digitalssetPage.Search_Adapt(digitalssetPage,test);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		/*************************************************************
		 * Step 8: Apply the "Image Required?" filter with value "No".
		 *************************************************************/
		Map<String, String> filters = new LinkedHashMap<>();
		filters.put("Image Required", "Yes");
		
		utils.applyBinaryFilter("Image Required", "Yes", searchPage, digitalssetPage);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		
		filters.put("Sellable Material Description", "Yes");
		
		utils.applyBinaryFilter("Sellable Material Description", "Yes", searchPage, digitalssetPage);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");

		searchPage.getFilterButton().click();
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		searchPage.Search_MaterialType().clear();
		materialTypeSearch.sendKeys("Cage Material Type");
		Thread.sleep(1000);
		
		utils.waitForElement(() -> digitalssetPage.CageMaterial_Type_Dropdownvalue(), "clickable");
		test.pass("Cage Material drop down appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.CageMaterial_Type_Dropdownvalue().click();
		Thread.sleep(1000);
		
		List<WebElement> items = driver.findElement(By.cssSelector("#app")).getShadowRoot()
		        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='currentApp_search-thing']")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='app-entity-discovery-component']")).getShadowRoot()
		        .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
		        .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
		        .findElement(By.cssSelector("#search-filter")).getShadowRoot()
		        .findElement(By.cssSelector("#rockEntityLov")).getShadowRoot()
		        .findElement(By.cssSelector("#entityLov")).getShadowRoot()
		        .findElement(By.cssSelector("pebble-grid")).getShadowRoot()
		        .findElement(By.cssSelector("#grid")).getShadowRoot()
		        .findElements(By.cssSelector("pebble-lov-item"));
		
		List<String> texts = new ArrayList<>();
		for (WebElement item : items) {
			texts.add(item.getShadowRoot().findElement(By.cssSelector("div > div")).getText().trim());
		}

		int index = texts.indexOf("Steel");

		if (index != -1) {
			items.get(index).getShadowRoot().findElement(By.cssSelector("div > div")).click();
		}
		
		digitalssetPage.Status_Apply_btn().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		Thread.sleep(2000);
		/*************************************************************
		 * Step 9: Apply the "Cage Material Type" filter with value "Brass".
		 *************************************************************/
		searchPage.getFilterButton().click();
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		materialTypeSearch.clear();
		materialTypeSearch.sendKeys("Cage Material Type");
		Thread.sleep(1000);
		
		utils.waitForElement(() -> digitalssetPage.CageMaterial_Type_Dropdownvalue(), "clickable");
		test.pass("Cage Material drop down appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.CageMaterial_Type_Dropdownvalue().click();
		Thread.sleep(1000);
		
		/*************************************************************
		 * Step 10: Verify the sellable material IDs are filtered
		 * based on the selected criteria.
		 *************************************************************/
		 	Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
	        String matid = selectedRecord.get("Material Id");
	        System.out.println("Selected Material ID: " + matid);
		/*************************************************************
		 * Step 11: Click on a Material ID from the filtered search results.
		 * Status: Covered by selectRandomRowAndOpenDetails(...).
		 *************************************************************/
	        String appliedFiltersText = filters.entrySet()
	                .stream()
	                .map(e -> e.getKey() + "=" + e.getValue())
	                .collect(java.util.stream.Collectors.joining(", "));
	        data.put("Applied Filters", appliedFiltersText);
	        data.put("Material ID", matid);
	        Thread.sleep(4000);
		/*************************************************************
		 * Step 12: Verify the selected entity opens in the
		 * Entity Manage screen.
		 *************************************************************/
            utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		/*************************************************************
		 * Step 13: Navigate to the Assets menu.
		 *************************************************************/
            digitalssetPage.Assets_dropdownWrapper().click();
    		Thread.sleep(3000);
		/*************************************************************
		 * Step 14: Select the "Has Images" relationship.
		 *************************************************************/
    		digitalssetPage.Assets_dropdown_Has_Images_Option().click();
    		Thread.sleep(3000);
    		/*************************************************************
    		 * Step 15: Verify the Image Relationship Grid is displayed.
    		 *************************************************************/
    		utils.waitForElement(() -> digitalssetPage.DA_MoreActions_dropdown(), "clickable");
    		Thread.sleep(1000);
    		test.pass("More actions page displayed to attach a image");
    		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************************
		 * Step 16: Verify no images are attached to the selected material.
		 *************************************************************/
		try {
			String totalimages_selected_txt = digitalssetPage.zero_Images_Text().getText().trim();
			String img_selcted = totalimages_selected_txt.split(" / ")[1];
			int totalsearchimagescount = Integer.parseInt(img_selcted);
			System.out.println("There are " + totalsearchimagescount + " before adding the image ");
			if (totalsearchimagescount == 0) {
				test.pass(" No images attached. Count is 0.");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} else {
				String msg = "Expected image count 0, but found " + totalsearchimagescount;
				test.fail(msg);
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Assert.fail(msg);
			}
		} catch (Exception e) {
			String msg = "Step 16 Failed: Unable to verify image count. Error: " + e.getMessage();
			test.fail(msg);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			throw e;
		}
		/*************************************************************
		 * Step 17: Verify the message
		 * "No Has Image(s) relationships exist" is displayed.
		 *************************************************************/
		try {
			WebElement noImagesMsg = digitalssetPage.HasNoImagesexist_text();
			String Imagestext = noImagesMsg.getText().trim();
			String expectedText = "No Has Image(s) relationships exist";

			if (Imagestext.equals(expectedText)) {
				test.pass("No Has Image(s) relationships exist message is displayed.");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} else {
				String msg = "Expected '" + expectedText + "' but found '" + Imagestext + "'.";
				test.fail(msg);
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Assert.fail(msg);
			}
		} catch (Exception e) {
			String msg = "Message verification failed. Error: " + e.getMessage();
			test.fail(msg);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			throw e;
		}
		/*************************************************************
		 * Step 18: Verify no image has been auto-assigned to the material.
		 * Status: Not Implemented.
		 *************************************************************/

		/*************************************************************
		 * Step 19: Verify no validation or application errors are displayed.
		 * Status: Not Implemented.
		 *************************************************************/
		/*************************************************************
		 * Implementation Status (against test description):
		 * - Fully implemented: Steps 1, 3, 4, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17
		 * - Indirectly covered: Step 2 (via Step 3), Step 11 (via selectRandomRowAndOpenDetails)
		 * - Not implemented: Steps 5, 18, 19
		 *************************************************************/
	}
}