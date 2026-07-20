package DAM;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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

 * TC_009_Validate_Image_Auto_Assignment
 *
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

@Test(groups = {"DigitalAssetowner"})
public class TC_010_Image_auto_assignment extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	@Test
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
		Thread.sleep(10000);
		
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
		Thread.sleep(10000);
		Search_Adapt(digitalssetPage);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		/*************************************************************
		 * Step 8: Apply the "Image Required?" filter with value "No".
		 *************************************************************/

		/*************************************************************
		 * Step 9: Apply the "Cage Material Type" filter with value
		 * "Brass".
		 *************************************************************/

		/*************************************************************
		 * Step 10: Verify the sellable material IDs are filtered
		 * based on the selected criteria.
		 *************************************************************/

		/*************************************************************
		 * Step 11: Click on a Material ID from the filtered
		 * search results.
		 *************************************************************/

		/*************************************************************
		 * Step 12: Verify the selected entity opens in the
		 * Entity Manage screen.
		 *************************************************************/

		/*************************************************************
		 * Step 13: Navigate to the Assets menu.
		 *************************************************************/

		/*************************************************************
		 * Step 14: Select the "Has Images" relationship.
		 *************************************************************/

		/*************************************************************
		 * Step 15: Verify the Image Relationship Grid is displayed.
		 *************************************************************/

		/*************************************************************
		 * Step 16: Verify no images are attached to the selected
		 * material.
		 *************************************************************/

		/*************************************************************
		 * Step 17: Verify the message
		 * "No Has Image(s) relationships exist" is displayed.
		 *************************************************************/

		/*************************************************************
		 * Step 18: Verify no image has been auto-assigned to the
		 * material.
		 *************************************************************/

		/*************************************************************
		 * Step 19: Verify no validation or application errors
		 * are displayed.
		 *************************************************************/
		
		
		
		
		
		
		
		
		
		
		
		

}
	
	
	public void Search_Adapt(DigitalAsset digitalssetPage) throws IOException, InterruptedException {
		utils.waitForElement(() -> digitalssetPage.Adapt_Input(), "clickable");
		test.pass("Refine by window appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Adapt_Input().sendKeys("ADAPT");
		digitalssetPage.Adapt_Input().sendKeys(Keys.ENTER);
		utils.waitForElement(() -> digitalssetPage.Adapt_Tree(), "clickable");
		test.pass("Selection tree displayed after entering the search Keyword Adapt");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Adapt_Applybutton().click();
		Thread.sleep(5000);
		
			
	}
	
}
