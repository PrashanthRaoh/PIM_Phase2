/**
 * 
 */
package Phase2_Test_Scripts;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.Utils;
import pages.CBT_Page;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/****************************************
 * TC_001_UseCaseReview_Override
 * Description : Review Use case selection record by overriding Catalog Bearing Tool Usecase[Int]? (Override) attribute
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
		Thread.sleep(5000);
		
		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		test.pass("Home Page of CBT is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		

///********************************************
// * Get names of items under New Sellable Material list
// ********************************************/
		List<WebElement> summaryElements = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));

		System.out.println("Total items: " + summaryElements.size());
		List<String> expectedItems = Arrays.asList("Pending Engineered Product Data","Review Usecase Selection");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		/******************************************
			Click on Review Usecase Selection
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
		 * ***** Click on Pending Usecase Approval - BSA PIE ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on Pending Usecase Approval - BSA PIE");
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
		
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		
		
	}
}

