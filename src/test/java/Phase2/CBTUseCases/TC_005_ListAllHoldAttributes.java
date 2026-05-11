package Phase2.CBTUseCases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

@Test(groups = { "CBTUseCaseOwner" })
public class TC_005_ListAllHoldAttributes extends BaseTest {
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
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/TC_005_ListAllHoldAttributes.txt";
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		
		// target tab text (exact match)
		final String targetTab = "Catalog Bearing Tool Use Case Approval";
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		wait.until(d -> cbtpage.CBT_HomePage_Tabs() != null && cbtpage.CBT_HomePage_Tabs().size() > 0);
		List<WebElement> tabs = cbtpage.CBT_HomePage_Tabs();
		System.out.println("There are " + tabs.size() + " tabs at CBT home page");

		boolean clicked = false;
		for (WebElement tab : tabs) {
			try {
				WebElement titleSpan = tab.findElement(By.cssSelector(".tab-title-content .tab-title > span"));
				String tabName = titleSpan.getText().trim();
				System.out.println("Found tab: '" + tabName + "'");
				if (tabName.equals(targetTab) || tabName.equalsIgnoreCase(targetTab) || tabName.contains("Catalog Bearing Tool Use Case Approval")) {
					js.executeScript("arguments[0].scrollIntoView({block: 'center'});", titleSpan);
					wait.until(ExpectedConditions.elementToBeClickable(titleSpan));
					try {
						titleSpan.click();
						System.out.println("Clicked '" + targetTab + "' via normal click");
						test.pass("Clicked '" + targetTab + "' via normal click");
						test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					} catch (Exception e) {
						System.out.println("Normal click failed, using JS click: " + e.getMessage());
						js.executeScript("arguments[0].click();", titleSpan);
					}
					wait.until(d -> {
						return true;
					});
					clicked = true;
					break;
				}
			} catch (Exception e) {
				System.out.println("Exception while inspecting tab element: " + e.getMessage());
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
		Assert.assertTrue(clicked, "Tab '" + targetTab + "' not found or could not be clicked");
		/**********************
		 * Select On Hold - Catalog Bearing Tool (User Selected)
		 *********************/
		List<WebElement> summaryElements = cbtpage.CBT_UsecaseapprovalTab_Items();
		String target = "On Hold - Catalog Bearing Tool (User Selected)";
		boolean clicked1 = false;

		for (WebElement summary : summaryElements) {
			WebElement labelElem = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String text = labelElem.getText().trim();
			System.out.println("Found: " + text);
			if (text.equals(target) || text.contains("On Hold - Catalog Bearing Tool (User Selected)")) {
				js.executeScript("arguments[0].scrollIntoView({block:'center'});", labelElem);
				try {
					labelElem.click();
					System.out.println("Clicked " + target + "  without retrial");
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				} catch (Exception ex) {
					System.out.println("Normal click failed, using JS click: ");
					js.executeScript("arguments[0].click();", labelElem);
				}
				clicked1 = true;
				break;
			}
		}
		Assert.assertTrue(clicked1,"Failed to click the 'On Hold - Catalog Bearing Tool (User Selected)' workflow item");
		Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		/***************************************************************
		 * From the rows pick one record and Edit it
		 **************************************************************/
		Actions actions = new Actions(driver);
		Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
		String matid = selectedRecord.get("Material Id");
		String sellableMaterialDescription = selectedRecord.get("Material Description");
		test.pass("Material ID -- " + matid + " Material Description --" + sellableMaterialDescription + " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		data.put("Material ID", matid);
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
			System.out.println("Status is : " + targetElement.getText());
			RecordStatus = targetElement.getText();
			test.pass("Status of the " + matid + " is  : - <b>" + RecordStatus + "</b>");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);
		} else {
			System.out.println("🔴 There was no status field found for the record");
			test.fail("No status field found found");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		data.put("CBT Sellable Product Status", RecordStatus);
		
		/**********************
		 * Search Catalog Bearing Tool - Select Attributes for Hold and get the list of hold attributes
		 *********************/
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool - Select Attributes for Hold");
		Thread.sleep(1000);
		actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);

		WebElement CBTHoldList = null;
		try {
			CBTHoldList = utils.waitForElement(() -> {
				try {
					List<WebElement> holdLists = cbtpage.AllHoldAttributeList();
					return (holdLists != null && !holdLists.isEmpty()) ? holdLists.get(0) : null;
				} catch (Exception ignored) {
					return null;
				}
			}, "visible");
		} catch (Exception e) {
			CBTHoldList = null;
		}
		if (CBTHoldList == null) {
			String msg = "Hold attributes is not visible/not available for this record.";
			System.out.println(msg);
			test.warning(msg);
			data.put("Total OnHold Items", 0);
			data.put("CBT Sellable Product Status on Hold items", new ArrayList<String>());
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			List<String> onHoldTags = cbtUtils.getOnHoldAttributeTags(CBTHoldList, "OnHold", test);
			System.out.println("CBT Sellable Product Status on Hold items: " + onHoldTags);
			data.put("Total OnHold Items", onHoldTags.size());
			data.put("CBT Sellable Product Status on Hold items", onHoldTags);
			test.pass("On Hold Items Listed <b>" + onHoldTags + "</b>");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, data);
	}
}