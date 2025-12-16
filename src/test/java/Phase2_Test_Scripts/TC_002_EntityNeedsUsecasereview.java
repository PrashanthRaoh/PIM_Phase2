package Phase2_Test_Scripts;

import static org.junit.Assert.assertTrue;
import common_functions.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.NotepadManager;
import pages.CBT_Page;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/****************************************
 * TC_002_EntityNeedsUsecasereview
 * Description : Opens an On hold CBT Rule Triggered entity and reports hold items.
 ***************************************/

public class TC_002_EntityNeedsUsecasereview extends BaseTest{

    public ExtentTest test;
    Map<String, Object> data = new LinkedHashMap<>();
    @Test(groups = { "CBTUseCaseOwner" })

    public void OverrideUsecaseReview() throws InterruptedException, IOException {
        String className = this.getClass().getSimpleName();
        System.out.println(className);
        test = BaseTest.extentreport.createTest(className);
        test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

        homePage = new HomePage(driver);
        CBT_Page cbtpage = new CBT_Page(driver);
        SummaryPage summaryPage = new SummaryPage(driver);
    	SearchPage2 searchPage = new SearchPage2(driver);
        
        String PRE_ETL_Filename = "/Pre_ETL_Artifacts/EntityNeedsReviewHold_Items.txt";

        utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
        test.pass("Home Page of CBT is displayed");
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        Thread.sleep(3000);

        List<WebElement> tabs = cbtpage.CBT_HomePage_Tabs();
        System.out.println("There are " + tabs.size() + " tabs at CBT home page");

        List<String> tabNames = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            WebElement spanElement = tabs.get(i);
            WebElement titleSpan = spanElement.findElement(By.cssSelector(".tab-title-content .tab-title > span"));

            String tabName = titleSpan.getText();
            System.out.println("Tab " + (i + 1) + " name: " + tabName);
            tabNames.add(tabName);
        }
        /*********************************
		 *Get Tab names in CBT home page
		************************************/
        System.out.println("All tab names: " + tabNames);
        for (int i = 0; i < tabNames.size(); i++) {
            if (tabNames.contains("Catalog Bearing Tool Use Case Approval")) {
                WebElement spanElement = tabs.get(tabNames.indexOf("Catalog Bearing Tool Use Case Approval")).findElement(By.cssSelector("[class='tab-title-content'] > [class='tab-title'] > span"));
                spanElement.click();
                System.out.println("Clicked on 'Catalog Bearing Tool Use Case Approval' tab");
                Thread.sleep(6000);
                break;
            } else {
                System.out.println("'Catalog Bearing Tool Use Case Approval' tab not found");
            }
        }
        
        List<WebElement> summaryElements = cbtpage.CBT_UsecaseapprovalTab_Items();
    	System.out.println("Total items: " + cbtpage.CBT_UsecaseapprovalTab_Items().size());

		List<String> expectedItems = Arrays.asList("Pending Usecase Approval - Catalog Bearing Tool",
				"On Hold - Catalog Bearing Tool (User Selected)", "On Hold - Catalog Bearing Tool (Rule Triggered)");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int i = 0; i < summaryElements.size(); i++) {
			WebElement summary = summaryElements.get(i);
			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String actualText = innerDiv.getText().trim();
			System.out.println("Item " + (i + 1) + ":--" + actualText);
			Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
			/***************************************************************
			 * Click on On Hold - Catalog Bearing Tool (Rule Triggered)
			**************************************************************/
			if (actualText.contains("On Hold - Catalog Bearing Tool (Rule Triggered)")) {
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
		test.pass("On Hold - Catalog Bearing Tool (Rule Triggered) entities listed ");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/***************************************************************
		 * Wait till rows appears
//		**************************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
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
		
		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		System.out.println("Total rows after clicking on On Hold - Catalog Bearing Tool (Rule Triggered) -- " + arrrowsdefined.size());
		test.pass("Rows after after clicking on On Hold - Catalog Bearing Tool (Rule Triggered) appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after clicking on On Hold - Catalog Bearing Tool (Rule Triggered)", arrrowsdefined.size() > 0);
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
		
		data.put("Material ID", matid);
		/*************************************************
		 * --------- Click on the material id from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);
		/*************************************************
		 * --------- Wait till Summary tab ------- *
		 ************************************************/
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription + " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
		 * --------- Search for On hold items  ------- *
		 ************************************************/
		Thread.sleep(2000);
		Actions actions2 = new Actions(driver);
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		summaryPage.SearchInputfield().sendKeys("HOLD attribute List (Rule Triggered)");
		Thread.sleep(1000);
		actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(2000);
		utils.waitForElement(() -> cbtpage.OnholdRuletriggeredblock(), "visible");
		
		test.pass("On Hold Rule triggered elemets shown up");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		int totalonholditems = 0;
		try {
			WebElement HasMorevalues = cbtpage.OnholdRuletriggeredblock().findElement(By.cssSelector(".more-values-message"));
			if (HasMorevalues.isDisplayed()) {
				String onholdMessage = HasMorevalues.getText();
				System.out.println("There are " + onholdMessage + "  listed: ");
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(onholdMessage);

				if (matcher.find()) {
					totalonholditems = Integer.parseInt(matcher.group());
				}
				System.out.println("Number is " + totalonholditems);
				HasMorevalues.click();
				Thread.sleep(5000);
				test.pass("Onhold items Expanded ");
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} else {
				System.out.println("There are no More elements text.On hold items directly listed");
				test.pass("Onhold items listed directly ");
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			/**************************
			 * Get List of all On Hold items *****
			 **************************/
			List<WebElement> tagElements = cbtpage.OnholdRuletriggeredblock().findElements(By.cssSelector("[id^='tag']"));
			System.out.println("There are " + tagElements.size() + " on Hold Items");
			data.put("Total On hold Items ", tagElements.size());
			List<String> tagTexts = new ArrayList<>();

			int tagIndex = 1;
			for (WebElement tag : tagElements) {
				String tagText = tag.getText().trim();
				System.out.println("On Hold Item  " + tagIndex + " -- " + tagText + ": " + tagText);
				tagTexts.add(tagText);
				tagIndex++;
			}
			data.put("On Hold Items ", tagTexts);
			test.pass("Onhold items listed are \n" + tagTexts);
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			
		} catch (Exception e) {
			System.out.println("No Hold items listed");
			test.info("No Hold items listed ");
			test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		cbtpage.Tabclose_Xmark().click();
		Thread.sleep(2000);
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename,data);
    }
}
