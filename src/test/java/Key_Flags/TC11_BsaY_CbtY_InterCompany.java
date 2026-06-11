package Key_Flags;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
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
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/****************************************************************************
 * filter attribute Apply Has image filter in the advance search. Only then
 * Bearing dimension drop down will be listed "BSA PIE Usecase? =Yes", Catalog
 * Bearing Tool Usecase[Int]? =Yes
 ****************************************************************************/
public class TC11_BsaY_CbtY_InterCompany extends BaseTest {
	ExtentTest test;
	Utils utils;
	HomePage homePage;
	SearchPage2 searchPage;
	DigitalAsset digitalssetPage;
	Actions actions;

	@Test(groups = {"BSAPIEowner"})
	public void BsaN_CbtY_MunN() throws Exception {
		// Initialize page objects
		Map<String, Object> data = new LinkedHashMap<>();
		String className = this.getClass().getSimpleName();
		System.out.println("Executing Test  ---  " + className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignDevice(System.getenv("COMPUTERNAME"));
		homePage = new HomePage(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		utils = new Utils(driver, test);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/Key_Flags/" + className + ".txt";

		/*********************************************
		 * Navigate to Search Page
		 ********************************************/
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
		homePage.clickSearch_Products_Button().click();
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search thing domain displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/********************************************************
		 * Set PIM Attribute Taxonomy filter attribute
		 *****************************************************/
		String filterName = "PIM Attribute Taxonomy";
		searchPage.getFilterButton().click();
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		WebElement materialTypeSearch = searchPage.Search_MaterialType();
		materialTypeSearch.clear();
		materialTypeSearch.sendKeys(filterName);
		Thread.sleep(3000);

		utils.clickFilterAttribute(filterName);
		Thread.sleep(1000);
		try {
			utils.waitForElement(BSAPIE_PO::Taxonomy_Dialog, "clickable");
		} catch (RuntimeException e) {
			if (test != null) {
				test.fail("Taxonomy dialog did not appear within timeout after selecting filter: " + filterName);
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
			throw e;
		}
		utils.waitForElement(() -> BSAPIE_PO.Taxonomy_Dialog(), "visible");
		BSAPIE_PO.filterbox().click();
		Thread.sleep(2000);
		String targetText = "Has Value";
		List<WebElement> items = BSAPIE_PO.taxonomyOperatorItems();
		boolean operatorClicked = false;
		for (WebElement item : items) {
			try {
				String value = item.getText() == null ? "" : item.getText().trim();
				if (value.isEmpty()) {
					value = item.getShadowRoot().findElement(By.cssSelector("div > div > div > span")).getText().trim();
				}
				if (targetText.equalsIgnoreCase(value)) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);
					operatorClicked = true;
					break;
				}
			} catch (Exception ignored) {
			}
		}
		if (!operatorClicked) {
			throw new RuntimeException("Taxonomy operator not found: " + targetText);
		}
		utils.clickElementWithRetry(BSAPIE_PO::taxonomy_Apply_btn, "taxonomy apply button");
		test.pass("Applied taxonomy filter: " + filterName + " = Has Values");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/**************************************************
		 * --------- wait for rows to appear ------* *
		 ********************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page No images records");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*********************************************
		 * Apply the filters
		 ********************************************/
		Map<String, String> filters = new LinkedHashMap<>();
		filters.put("BSA PIE Usecase?", "Yes");
		filters.put("Catalog Bearing Tool Usecase[Int]?", "Yes");
		utils.applyBinaryFilters(filters, searchPage, digitalssetPage);

		/*********************************************
		 * Select the record and update to Notepad
		 ********************************************/
		Map<String, String> selectedRecord = cbtUtils .selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
		String matid = selectedRecord.get("Material Id");
		if (selectedRecord.isEmpty()) {
			test.warning("No record selected. Skipping this iteration.");
			return;
		}
		System.out.println("Selected Material ID: " + matid);
		String appliedFiltersText = filters.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(java.util.stream.Collectors.joining(", "));
		data.put("Applied Filters", appliedFiltersText);
		data.put("Material ID", matid);
		Thread.sleep(4000);

		/*************************************************
		 * Get Application type code value
		 ****************************************************/
		String Applicationtypecodevalue = cbtUtils.getApplicationTypCodevalue(summaryPage, "Application Type Code", test);
		if (Applicationtypecodevalue != null) {
			test.pass("Application type code value for the record is : " + Applicationtypecodevalue);
			System.out.println("Application type code value for the record is : " + Applicationtypecodevalue);
			data.put("Application Type code", Applicationtypecodevalue);
		} else {
			test.info("Application typecode value for the record is not available or blank ");
		}
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, data);
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);

		/*************************************************
		 * From Attributes drop down Select Bearing Dimension
		 ****************************************************/
		summaryPage.Attributes_tab_dropdown().click();
		Thread.sleep(2000);
		/*************************************************
		 * Get Attributes drop down values
		 ****************************************************/
		List<String> attributesDropDownOptions = summaryPage.Attributes_Drop_down_element_texts();
		System.out.println("Attributes dropdown options:");
		for (String option : attributesDropDownOptions) {
			System.out.println(" - " + option);
		}
		test.info("Attributes dropdown options: " + String.join(", ", attributesDropDownOptions));

		boolean hasBearingDimensions = attributesDropDownOptions.stream() .anyMatch(option -> "Bearing Dimensions"
						.equalsIgnoreCase(option.trim()));

		if (!hasBearingDimensions) {
			String message = "No Bearing Dimensions available for this record. Exiting this test.";
			System.out.println(message);
			test.warning(message);
			return;
		}
		/*************************************************
		 * Click on Bearing Dimensions
		 ****************************************************/
		boolean bearingDimensionsClicked = summaryPage.clickAttributesDropdownOptionByText("Bearing Dimensions");
		if (bearingDimensionsClicked) {
			test.pass("Clicked attribute dropdown option: Bearing Dimensions");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			String message = "Bearing Dimensions is available but click action failed. Exiting this test.";
			test.fail(message);
			return;
		}
		/*************************************************
		 * Get Each Bearing Dimension header and its value
		 ****************************************************/
		List<WebElement> rsItems = searchPage.Bearing_Attributes();
		Map<String, String> attributeMap = new LinkedHashMap<>();

		for (WebElement el : rsItems) {
			String header = el.getShadowRoot().findElement(By.cssSelector("div > div > div.attribute-view.list > span > span")).getText().trim();
			String value = el.getShadowRoot().findElement(By.cssSelector("div > div > div.attribute-view.list > div > span")).getText().trim();
			attributeMap.put(header, value);
		}

		StringBuilder attributeReportSummary = new StringBuilder();
		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
			String attributeLine = entry.getKey() + " :- " + entry.getValue();
			System.out.println(attributeLine);
			System.out.println("-----------");

			if (attributeReportSummary.length() > 0) {
				attributeReportSummary.append("<br>");
			}
			attributeReportSummary.append(" \"").append(entry.getKey()).append("\" = \"").append(entry.getValue()).append("\"");
		}
		test.info("Attributes :<br>" + attributeReportSummary);
		/*************************************************
		 * Put Each Bearing Dimension header and its value in to a map
		 ****************************************************/
		Map<String, Object> attributeData = new LinkedHashMap<>();
		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
			attributeData.put(entry.getKey(), entry.getValue());
		}
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, attributeData);
		Thread.sleep(2000);
		homePage.clickSearch_Products_Button().click();
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search thing domain displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	}
}