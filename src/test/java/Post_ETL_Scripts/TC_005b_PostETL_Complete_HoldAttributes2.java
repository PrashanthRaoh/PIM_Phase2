package Post_ETL_Scripts;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.CBT_Utils;
import common_functions.NotepadManager;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

@Test(groups = { "CBTUseCaseOwner" })
public class TC_005b_PostETL_Complete_HoldAttributes2 extends BaseTest {
	
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	public void ListALlHoldAttributes() throws InterruptedException, IOException {
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
		homePage = new HomePage(driver);
		
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_005_List_HoldAttributes.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/TC_005b_PostETL_Complete_HoldAttributes2.txt";

		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		List<String> Matids = NotepadManager.GetMaterialIDs(PRE_ETL_Filename);
		Actions actions = new Actions(driver);
		for (int i = 0; i < Matids.size(); i++) {
			String Matid = Matids.get(i);
			utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			try {
				searchPage.searchthingdomain_Input_Mat_Id().click();
				searchPage.searchthingdomain_Input_Mat_Id().clear();
				searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
				searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
				test.pass("Material id " + Matid + " is searched in Search thing domain");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Thread.sleep(5000);
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

				System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE Inprogress status -- "+ arrrowsdefined.size());
				test.pass("Rows after after clicking on Pending Usecase Approval - BSA PIE Inprogress status appeared");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				assertTrue("There should be results after applying filters with Inprogress status",arrrowsdefined.size() > 0);

				WebElement RowByRow = arrrowsdefined.get(0);
				String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
				String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
				System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

				WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
				actions.moveToElement(RowByRow).build().perform();
				Thread.sleep(500);
				matidElement.click();
				Thread.sleep(3000);
				utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
				test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for verification");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Thread.sleep(3000);
				data.put("Material ID", matid);
			} catch (Exception ex) {
				ex.printStackTrace();
				test.fail("Material id -- " + Matid + " details was not retrieved");
				test.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
			/**********************
			 * Search for Catalog Bearing Tool Sellable Product Status It should be On Hold User
			 *********************/
			Thread.sleep(1000);
			summaryPage.SearchIcon().click();
			summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool Sellable Product Status");
			Thread.sleep(1000);
			actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(3000);
			utils.waitForElement(() -> cbtpage.CBT_Sellable_Product_Status(), "visible");
			test.pass("CBT Product status shown up");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			String RecordStatus = null;
			WebElement targetElement = cbtpage.CBT_Sellable_Product_Status();

			if (targetElement != null) {
				RecordStatus = targetElement.getText() != null ? targetElement.getText().trim() : "";
				System.out.println("Status is : " + RecordStatus);
				test.pass("Status of the " + Matid + " is  : - <b>" + RecordStatus + "</b>");
				test.log(Status.INFO,
						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

				if (!"Approved".equalsIgnoreCase(RecordStatus)) {
					String failMsg = "FAIL: Status is other than Approved (" + RecordStatus + "), but hold attributes will still be validated.";
					System.out.println(failMsg);
					test.fail(failMsg);
				}

				BSAPIE_PO.Tabclose_Xmark().click();
				Thread.sleep(4000);
			} else {
				System.out.println("There was no status field found for the record");
				test.fail("No status field found");
				test.log(Status.FAIL,
						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
			data.put("CBT Sellable Product Status", RecordStatus);
			data.put("CBT Sellable Product Status", RecordStatus);
			/**********************
			 * Search Catalog Bearing Tool - Select Attributes for Hold and get the list of hold attributes
			 *********************/
			summaryPage.SearchIcon().click();
			summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool - Select Attributes for Hold");
			Thread.sleep(1000);
			actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(3000);
			WebElement CBTHoldList = utils.waitForElement(() -> cbtpage.AllHoldAttributeList().get(0), "visible");
			List<String> onHoldTags = cbtUtils.getOnHoldAttributeTags(CBTHoldList, "OnHold", test);
			System.out.println("CBT Sellable Product Status on Hold items: " + onHoldTags);
			data.put("Total OnHold Items", onHoldTags.size());
			data.put("CBT Sellable Product Status on Hold items", onHoldTags);
			test.pass("On Hold Items Listed <b>" + onHoldTags + "</b>");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);
			NotepadManager.ReadWriteNotepad(POST_ETL_Filename, data);
		}
	}
}