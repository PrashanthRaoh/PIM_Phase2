package DAM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.CBT_Utils;
import common_functions.Utils;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/*****************************************************************************
* TC_009_Validate_Image_Auto_Assignment_Sellable_Materialdescription Has No value
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
*     Image Required? = Yes
*     Sellable Material Description Has No Value  = V
*     Cage Material Type = Steel ->
* Verify sellable material IDs are filtered based on the selected criteria ->
* Click on Material ID from the filtered results ->
* Verify the entity opens in a new Entity Manage screen ->
* Navigate to Assets menu ->
* Select Has Images relationship ->
* Verify Image Relationship Grid is displayed ->
* Verify the image asset is auto-assigned to the selected material ->
* Verify the asset name matches:
*     "timken-ADAPT-misalignment-and-float-bearing-for-steel-making-applications.png" ->
* Verify the image is correctly associated with the entity ->
* Verify no application errors are displayed.
*
* Expected Result:
* User successfully logs into PIM and accesses the Home Dashboard.
* User navigates to Search Thing Domain and filters Sellable Products.
* Matching Material IDs are displayed based on the search criteria.
* Selected Material ID opens in Entity Manage screen.
* Assets > Has Images relationship grid is displayed.
* Image relationship exists for the selected material.
* Asset name displayed is:
*     "timken-ADAPT-misalignment-and-float-bearing-for-steel-making-applications.png".
* The image is automatically assigned to the material as per RTM.
* The image is correctly linked to the entity.
* No application errors are displayed during the validation process.
*****************************************************************************/

public class TC_009_Image_auto_assignment_Cage_Steel_HasNoValue extends BaseTest {
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
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		Thread.sleep(5000);
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
		 * Step 6: Verify the Search Thing Domain screen is displayed
		 * and all sellable products are available.
		 *************************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search Products enities displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
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
		test.pass("PIM Attribute Taxonomy filter applied");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************************
		 * Step 8: Apply the "Image Required?" filter with value "No".
		 *************************************************************/
		Map<String, String> filters = new LinkedHashMap<>();
		filters.put("Image Required", "Yes");
		
		utils.applyBinaryFilter("Image Required", "Yes", searchPage, digitalssetPage);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Image Required -  Yes - filter applied");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************************
		 * Step 8: Apply the "Sellable Material Description" filter with value "V".
		 *************************************************************/
		boolean autoApplied = digitalssetPage.Applyfilter_HasNoImagesandApply( searchPage,"Sellable Material Description)", () -> digitalssetPage.ImageRequired_Auto_Dropdown(), "Has No Value");
		Assert.assertTrue(autoApplied, "Failed to apply filter: Sellable Material Description -> Has No Value");
		test.pass("Filter: Sellable Material Description -> Has No Value applied");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> searchPage.getFilterButton(), "clickable");
		/*************************************************************
		 * Select cage material type as Steel
		 *************************************************************/
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
			test.pass("Cage material type Steel filter selected");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		digitalssetPage.Status_Apply_btn().click();
		Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.getFilterButton(), "clickable");
		test.pass("Applied the Cage Material as Steel");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************************
		 * Step 10: Verify the sellable material IDs are filtered
		 * based on the selected criteria.
		 *************************************************************/
		String txt = searchPage.rowsdisplayedtext().getText().trim();
		String result = txt.split(" / ")[1].trim();
		int rowsDisplayed = Integer.parseInt(result);
		System.out.println("Rows displayed after applying filters: " + rowsDisplayed);

		if (rowsDisplayed == 0) {
			System.out.println("As expected there are no rows displayed");
			test.pass("As expected there are no rows displayed");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
			String matid = selectedRecord.get("Material Id");
			System.out.println("Selected Material ID: " + matid);
			/*************************************************************
			 * Step 11: Click on a Material ID from the filtered search results.
			 *************************************************************/
			String appliedFiltersText = filters.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(java.util.stream.Collectors.joining(", "));
			data.put("Applied Filters", appliedFiltersText);
			data.put("Material ID", matid);
			Thread.sleep(4000);
			/*************************************************************
			 * Step 12: Verify the selected entity opens in the Entity Manage
			 * screen.
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
				String msg = "Failed: Unable to verify image count. Error: " + e.getMessage();
				test.fail(msg);
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				throw e;
			}
			/*************************************************************
			 * Step 17: Verify the message "No Has Image(s) relationships exist"
			 * is displayed.
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
		}
	}	
}