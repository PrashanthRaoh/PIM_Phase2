package Post_ETL_Scripts;

import static common_functions.BaseTest.utils;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import common_functions.CBT_Utils;
import common_functions.NotepadManager;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/*************************************************************************
 * TC_003_PostETL_PartialOnholdCheck After
 * TC_003_List_CBTHOLD_attribute_List_Rule_Triggered is run Post ETL is run to
 * fix on hold attributes partially. If still entity is in On Hold, report
 * existing on Hold attributes
 *************************************************************************/
@Test(groups = { "CBTUseCaseOwner" })
public class TC_003_PostETL_PartialOnholdCheck extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	public void Check_Partial_OnHoldAttrbutes() throws Exception {
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
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_003_List_CBTHOLD_attribute_List_Rule_Triggered.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/TC_003_PostETL_PartialOnholdCheck.txt";

		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

		List<String> Matids = NotepadManager.GetMaterialIDs(PRE_ETL_Filename);

		for (int i = 0; i < Matids.size(); i++) {
			String Matid = Matids.get(i);
			System.out.println(Matid);
			homePage.clickSearch_Products_Button().click();
			Thread.sleep(3000);
			Actions actions = new Actions(driver);
			try {
				utils.waitForElement(() -> searchPage.getgrid(), "clickable");
				searchPage.searchthingdomain_Input_Mat_Id().click();
				searchPage.searchthingdomain_Input_Mat_Id().clear();
				searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
				searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
				test.pass("Material id " + Matid + " is searched in Search thing domain");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Thread.sleep(5000);

				Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
				String matid = selectedRecord.get("Material Id");
				data.put("Material ID", matid);
				Thread.sleep(3000);
				utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
				Thread.sleep(3000);
			} catch (Exception ex) {
				ex.printStackTrace();
				test.fail("Material id -- " + Matid + " details was not retrieved");
				test.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
			/***********************************************
			 * The entity should be removed from  Catalog Bearing Tool Use Case Approval workflow 
			 **********************************************/
			String RecordStatus = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
			data.put("Material ID", Matid);
			data.put("CBT Sellable Product Status", RecordStatus == null ? "" : RecordStatus);
			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);

			if (RecordStatus != null) {
				List<String> onHoldItems = new ArrayList<>();
				String normalizedStatus = RecordStatus.trim();

				if ("Approved".equalsIgnoreCase(normalizedStatus)) {
					test.pass("Record status is APPROVED as expected.");
					data.put("CBT Sellable Product Status", "Approved");

					test.info( "Validating if any OnHold items still exist for APPROVED record");
					onHoldItems = fetchOnHoldItems(actions, cbtpage, summaryPage);

					if (onHoldItems.size() > 0) {
						test.warning("Approved BUT still has OnHold items");
						data.put("CBT Approved but OnHold items", onHoldItems);
						data.put("Total OnHold Items", onHoldItems.size());
						test.log(Status.WARNING, MediaEntityBuilder .createScreenCaptureFromPath( Utils.Takescreenshot(driver)) .build());
					} else {
						test.pass("No OnHold items found for APPROVED record");
						data.put("CBT Approved but OnHold items", "No OnHold items");
						data.put("Total OnHold Items", 0);
					}

					String activeWorkflow = CBT_Utils .getActiveWorkflowAfterRefresh(driver, cbtpage, test);
					data.put("Active Workflow", activeWorkflow == null ? "" : activeWorkflow);

					if (activeWorkflow == null || activeWorkflow.trim().isEmpty()) {
						test.warning("Active workflow is empty/not found.");
					} else {
						test.pass("Active workflow: <b>" + activeWorkflow + "</b>");
					}
					NotepadManager.ReadWriteNotepad(POST_ETL_Filename, data);

				} else {
					test.fail("Record status is not Approved. Actual status: " + normalizedStatus);
					data.put("CBT Sellable Product Status", normalizedStatus);

					String activeWorkflow = CBT_Utils .getActiveWorkflowAfterRefresh(driver, cbtpage, test);
					data.put("Active Workflow", activeWorkflow == null ? "" : activeWorkflow);

					if (activeWorkflow == null || activeWorkflow.trim().isEmpty()) {
						test.pass("Active workflow is empty/not found.");
					} else {
						test.info("Active workflow: " + activeWorkflow);
					}
					NotepadManager.ReadWriteNotepad(POST_ETL_Filename, data);
				}
				BSAPIE_PO.Tabclose_Xmark().click();
				Thread.sleep(4000);
			}
	}
}	
	
	
	
	
/***********************************************
Function to fetch On Hold Items
**********************************************/
	private List<String> fetchOnHoldItems(Actions actions, CBT_Page cbtpage, SummaryPage summaryPage) throws Exception {
	    summaryPage.SearchIcon().click();
	    summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool - HOLD attribute List (Rule Triggered)");
	    Thread.sleep(1000);
	    actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
	    Thread.sleep(3000);
	    // Wait for the element conditionally
	    boolean isElementPresent = utils.isElementPresent(() -> cbtpage.CBT_Hold_Attributes_list(), "visible");

	    if (isElementPresent) {
	        utils.waitForElement(() -> cbtpage.CBT_Hold_Attributes_list(), "visible");
	        WebElement CBTHoldList = cbtpage.CBT_Hold_Attributes_list();

	        try {
	            WebElement moreValuesList = CBTHoldList.getShadowRoot().findElement(By.cssSelector("div > .more-values-message"));
	            if (moreValuesList.isDisplayed()) {
	                String msg = moreValuesList.getText();
	                System.out.println("There are " + msg + " listed");
	                moreValuesList.click();
	                Thread.sleep(5000);
	                test.pass("Onhold items Expanded");
	            }
	        } catch (Exception ignored) {
	        }
	        test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	        List<WebElement> tagElements = CBTHoldList.getShadowRoot().findElements(By.cssSelector("[id^='tag']"));

	        List<String> tagTexts = new ArrayList<>();
	        int i = 1;
	        for (WebElement tag : tagElements) {
	            String text = tag.getText().trim();
	            System.out.println("On Hold Item " + i + " -- " + text);
	            tagTexts.add(text);
	            i++;
	        }

	        return tagTexts;
	    } else {
	        // Handle case where the "OnHold" items list is not visible or doesn't exist
	        System.out.println("No OnHold items found.");
	        return new ArrayList<>(); // Return an empty list, as there are no OnHold items
	    }
	}
}