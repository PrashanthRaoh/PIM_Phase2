package Phase2.CBTUseCases;

import static org.junit.Assert.assertTrue;
import common_functions.NotepadManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/*************************************************************************
 * List_CBTHOLD_attribute_List_Rule_Triggered At Home page clicks on Catalog
 * Bearing Tool Use Case Approval". Click on the "On Hold - Catalog Bearing Tool
 * (Rule Triggered)" Catalog Bearing Tool Usecase[Int]? Is "Yes" "Catalog
 * Bearing Tool Sellable Product Status" is "OnHoldSystem" Lists the values
 * present in attribute Catalog Bearing Tool - HOLD attribute List (Rule
 * Triggered)
 *************************************************************************/
@Test(groups = { "CBTUseCaseOwner" })
public class TC_003_List_CBTHOLD_attribute_List_Rule_Triggered extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

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
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_003_List_CBTHOLD_attribute_List_Rule_Triggered.txt";

		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		test.pass("Home Page of CBT is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);

		/************************************
		 * Click on CBT Use Case Approval tab
		 *********************************/
		cbtpage.CBTUsecase_Tab_Homescreen().click();
		test.pass("Clicked on CBT Use Case Approval tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);

		/************************************
		 * Click on On Hold - Catalog Bearing Tool (Rule Triggered)
		 *********************************/
		cbtpage.CBTRuleTriggered_HoldAttribute().click();
		Thread.sleep(3000);
		test.pass("Clicked on Catalog Bearing Tool (Rule Triggered) workflow");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		/**************************************************************
		 * Get the number of rows after applying the filter
		 **************************************************************/
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

		System.out.println("Total rows after entering the material ID is  -- " + arrrowsdefined.size());
		test.pass("Rows after after clicking on CBT Approved status filter is -- " + arrrowsdefined.size());
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters with Inprogress status", arrrowsdefined.size() > 0);

		/********************************
		 * Random number generator to click on row within row count
		 ************/
		Random rand = new Random();
		int min = 0;
		int max = arrrowsdefined.size();
		int randnum = rand.nextInt(max - min) + min;

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
		Thread.sleep(500);
		matidElement.click();
		Thread.sleep(3000);
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for verification");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		data.put("Material ID", matid);
		Thread.sleep(1000);
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool Sellable Product Status");
		Thread.sleep(1000);
		actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		WebElement targetElement = null;
		String RecordStatus = null;
		try {
			targetElement = utils.waitForElement(() -> cbtpage.CBT_Sellable_Product_Status(), "visible");
		} catch (Exception e) {
			test.fail("CBT Sellable Product Status element did NOT appear");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.fail("CBT Sellable Product Status element not found");
		}
		if (targetElement == null) {
			test.fail("CBT Sellable Product Status element is NULL");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.fail("CBT Sellable Product Status element is null");
		}

		test.pass("CBT Product status shown up");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		RecordStatus = targetElement.getText();
		System.out.println("Status is : " + RecordStatus);
		test.pass("Status of the " + matid + " is  : - <b>" + RecordStatus + "</b>");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);

		data.put("CBT Sellable Product Status", RecordStatus);

		if (RecordStatus != null && RecordStatus.trim().equalsIgnoreCase("OnHoldSystem")) {
			test.info("Since the record is in On Hold System status, fetching the hold attributes for the record");
			summaryPage.SearchIcon().click();
			summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool - HOLD attribute List (Rule Triggered)");
			Thread.sleep(1000);
			actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(3000);

			utils.waitForElement(() -> cbtpage.CBT_Hold_Attributes_list(), "visible");
			WebElement CBTHoldList = cbtpage.CBT_Hold_Attributes_list();

			try {
				WebElement moreValuesList = CBTHoldList.getShadowRoot().findElement(By.cssSelector("div > .more-values-message"));
				if (moreValuesList.isDisplayed()) {
					String onholdMessage = moreValuesList.getText();
					System.out.println("There are " + onholdMessage + " listed: ");
					moreValuesList.click();
					Thread.sleep(5000);
					test.pass("Onhold items Expanded");
				} else {
					test.pass("Onhold items listed directly");
				}

				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				List<WebElement> tagElements = CBTHoldList.getShadowRoot().findElements(By.cssSelector("[id^='tag']"));

				System.out.println("There are " + tagElements.size() + " on Hold Items");
				data.put("Total OnHold Items", tagElements.size());

				List<String> tagTexts = new ArrayList<>();

				int tagIndex = 1;
				for (WebElement tag : tagElements) {
					String tagText = tag.getText().trim();
					System.out.println("On Hold Item " + tagIndex + " -- " + tagText);
					tagTexts.add(tagText);
					tagIndex++;
				}
				data.put("CBT Sellable Product Status on Hold items", tagTexts);
				test.info("Total OnHold Items " + tagElements.size());
				test.pass("Onhold items listed are \n" + tagTexts);
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			} catch (Exception e) {
				System.out.println("No Hold items listed");
			}

			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);
			NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, data);

		}
	}
}
