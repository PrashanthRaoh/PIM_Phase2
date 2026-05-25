package Post_ETL_Scripts;

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
import pages.DigitalAsset;
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
		test.assignAuthor(System.getProperty("user.name")) .assignCategory("Regression") .assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/CBT/TC_003_List_CBTHOLD_attribute_List_Rule_Triggered.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/CBT/TC_003_PostETL_PartialOnholdCheck.txt";

		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.log(Status.PASS, MediaEntityBuilder .createScreenCaptureFromPath(Utils.Takescreenshot(driver)) .build());
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
				searchPage.searchthingdomain_Input_Mat_Id() .sendKeys(Keys.ENTER);
				test.pass("Material id " + Matid + " is searched in Search thing domain");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath( Utils.Takescreenshot(driver)).build());
				Thread.sleep(5000);

				Map<String, String> selectedRecord = cbtUtils .selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
				String matid = selectedRecord.get("Material Id");
				data.put("Material ID", matid);
				Thread.sleep(1000);
				utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
				Thread.sleep(3000);
			} catch (Exception ex) {
				ex.printStackTrace();
				test.fail("Material id -- " + Matid + " details was not retrieved");
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
			/***********************************************
			 * The entity should be removed from Catalog Bearing Tool Use Case Approval workflow
			 **********************************************/
			String activeWorkflow = CBT_Utils.getActiveWorkflowAfterRefresh(driver, cbtpage, test);
			System.out.println("Active workflow is: " + activeWorkflow);
			data.put("Active Workflow", activeWorkflow == null ? "" : activeWorkflow);

			/******************************************
			 * Get Record status
			 ******************************************/
			String recordStatus = cbtUtils.getOpenedRecordStatus(summaryPage, cbtpage, BSAPIE_PO, test);
			String normalizedStatus = recordStatus == null ? "" : recordStatus.trim();

			data.put("Material ID", Matid);
			data.put("CBT Sellable Product Status", normalizedStatus);

		// Validate status must be Approved
			Assert.assertEquals(normalizedStatus, "Approved", "Expected 'Catalog Bearing Tool Sellable Product Status' to be Approved, but got: " + normalizedStatus);
		// Validate active workflow should NOT contain this workflow
			String forbiddenWorkflow = "Catalog Bearing Tool Use Case Approval";
			Assert.assertTrue( activeWorkflow == null || !activeWorkflow.contains(forbiddenWorkflow),
					"Active workflow should not contain '" + forbiddenWorkflow + "', but got: " + activeWorkflow);

			if ("Approved".equalsIgnoreCase(normalizedStatus)) {
				test.pass("Record status is Approved.");
				System.out.println("Record status is Approved: " + normalizedStatus);
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} else {
				test.fail("Record status is not Approved: " + normalizedStatus);
				System.out.println("Record status is not Approved: " + normalizedStatus);
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			if (activeWorkflow == null || !activeWorkflow.contains(forbiddenWorkflow)) {
				test.pass("Active workflow does not contain forbidden workflow: " + forbiddenWorkflow);
			} else {
				test.fail("Active workflow contains forbidden workflow: " + activeWorkflow);
			}

			List<String> onHoldItems = fetchOnHoldItems(actions, cbtpage, summaryPage);
			if (onHoldItems.size() > 0) {
				test.warning("Approved BUT still has OnHold items");
				System.out.println("Approved BUT still has OnHold items: " + onHoldItems);
				data.put("CBT Approved but OnHold items", onHoldItems);
				data.put("Total OnHold Items", onHoldItems.size());
				test.log(Status.WARNING, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} else {
				test.pass("No OnHold items found for APPROVED record");
				System.out.println("No OnHold items found for APPROVED record");
				data.put("CBT Approved but OnHold items", "No OnHold items");
				data.put("Total OnHold Items", 0);
			}
			NotepadManager.ReadWriteNotepad(POST_ETL_Filename, data);
			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);
			
			/*********************************************
			 * Verify Engineering Part is present for the record
			******************************************/
			cbtpage.CBT_RelationshipTab().click();
			Thread.sleep(500);
			utils.waitForElement(() -> cbtpage.CBT_EngineeringPartNumber(), "visible");
			test.pass("Has Enginerring PartNumber clicked to check if the record has engineering part or not");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			String Eng_PartNumber = cbtpage.CBT_EngineeringPartNumber().getText();
			System.out.println("Enginerring part number for the record is  " + Eng_PartNumber );
			
			test.pass("Enginerring PartNumber for the record is: " + Eng_PartNumber);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
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