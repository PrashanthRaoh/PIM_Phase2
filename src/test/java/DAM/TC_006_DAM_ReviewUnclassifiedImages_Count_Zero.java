package DAM;

import java.io.IOException;
import java.util.Arrays;
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
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/******************************************************************************
 * TC_003_DAM_ReviewUnclassifiedImages_Count_Zero Description: Verifies that the
 * count of ReviewUnclassifiedImages_Count is 0
 *****************************************************************************/
@Test(groups = {"DigitalAssetowner"})
public class TC_006_DAM_ReviewUnclassifiedImages_Count_Zero extends BaseTest {

	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	public void getReviewUnclassifiedImageTab_zero_Count() throws InterruptedException, IOException {
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
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);

		/**************************************************************************
		 * // Step 1: Navigate to My-ToDo's page and click on the "Digital
		 * Assets Enrichment" tab.
		 *************************************************************************/
		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		System.out.println("Home Page of Digital Asset is displayed");
		test.pass("Home Page of Digital Asset is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		/**************************************************************************
		 * // Step 2: Verify that the "Enrich Digital Assets" state is displayed
		 * successfully.
		 *************************************************************************/

		/**************************************************************************
		 * // Step 3: Click on the "More Details >>" option available under the
		 * "Enrich Digital Assets" state.
		 *************************************************************************/
		WebElement detailsEnrichment = homePage.Moredetails_MarketingEnrich().getShadowRoot().findElement(By.cssSelector("#viewDetails > span"));
		detailsEnrichment.click();
		Thread.sleep(2000);
		test.pass("More details clicked on Enrich Digital Asset tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/**************************************************************************
		 * // Step 4: Verify that the following links are displayed: // 1. DAM:
		 * Review 2D Line Drawing // 2. DAM: Review Representative Image
		 * (Primary) // 3. DAM: Review Secondary Image // 4. DAM: Review
		 * Unclassified Images
		 *************************************************************************/
		List<WebElement> detailItems = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary")).getShadowRoot()
				.findElement(By.cssSelector("#moreDetails")).findElements(By.cssSelector("my-todo-detail-view-list-item"));

		Map<String, Integer> detailItemMap = new LinkedHashMap<>();

		for (WebElement item : detailItems) {
			String title = item.getShadowRoot().findElement(By.cssSelector("#button-text-box")).getAttribute("title").trim();
			String[] parts = title.split("\\s+", 2);
			if (parts.length < 2) {
				continue;
			}
			try {
				int workflowCount = Integer.parseInt(parts[0]);
				String workflowTitle = parts[1].trim();
				detailItemMap.put(workflowTitle, workflowCount);
			} catch (NumberFormatException e) {
			}
		}

		for (Map.Entry<String, Integer> entry : detailItemMap.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		/**************************************************************************
		 * // Step 5: Verify that the count of pending entities for "DAM: Review
		 * Unclassified Images" is displayed as 0.
		 *************************************************************************/
		String targetWorkflow = "DAM: Review Unclassified Images";
		Assert.assertTrue(detailItemMap.containsKey(targetWorkflow), "Workflow not found in detail items: " + targetWorkflow + ". Parsed values: " + detailItemMap);
		Assert.assertEquals(detailItemMap.get(targetWorkflow).intValue(), 0, "Count mismatch for workflow: " + targetWorkflow);
		test.pass("Verified '" + targetWorkflow + "' count is 0");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	}
}