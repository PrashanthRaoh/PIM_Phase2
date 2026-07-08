package DAM;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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

@Test(groups = { "DigitalAssetowner" })
public class TC_001_Validating_Asset_Enrichment_flow extends BaseTest {

	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();

	public void Validating_Asset_Enrichment() throws InterruptedException, IOException {
 		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name"))
		.assignCategory("Regression")
		.assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		CBT_Page cbtpage = new CBT_Page(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/DAM/" + className+".txt";
		utils.waitForElement(() -> cbtpage.SellableMaterialTabcontent(), "clickable");
		System.out.println("Home Page of Digital Asset is displayed");
		test.pass("Home Page of Digital Asset is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		
		digitalssetPage.Digital_Asset_Quick_Search().click();
		Thread.sleep(6000);
		
		WebElement Search_digitalImageinput = driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_search-digitalasset_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
			    .findElement(By.cssSelector("#searchBar")).getShadowRoot()
			    .findElement(By.cssSelector("#input"));
		
		utils.waitForElement(() -> Search_digitalImageinput, "clickable");
		System.out.println("Arrived at adding image page");
		test.pass("Arrived at adding image page");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		List<WebElement> items = digitalssetPage.getGridItems();

		if (items.isEmpty()) {
		    throw new NoSuchElementException("No grid items found.");
		}
		List<String> names = digitalssetPage.getItemNames();
		if (names.size() != items.size()) {
		    throw new IllegalStateException( "Mismatch between item names (" + names.size() + ") and grid items (" + items.size() + ")");
		}
		// Print all item names
		for (int i = 0; i < names.size(); i++) {
		    System.out.println((i + 1) + ". " + names.get(i));
		}
		Assert.assertFalse(names.isEmpty(), "No items found!");
		
		/***************************************************
		 * Randomly select any image
		 *************************************************/
		int randomIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(items.size());
		int clickedNumber = randomIndex + 1;

		System.out.println("Digital Asset " + clickedNumber + " th was clicked: " + names.get(randomIndex));
		test.pass("Digital Asset " + clickedNumber + " th was clicked: " + names.get(randomIndex));
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		items.get(randomIndex).click();
		test.pass(names.get(randomIndex) + " is selected and opened");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);
		data.put("Image Name" , names.get(randomIndex));
		/***************************************************
		 * Wait for the summary page
		 *************************************************/
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		utils.waitForElement(() -> digitalssetPage.Avaialbe_BusinessCondition(), "clickable");

		WebElement availableBusinessCondition = utils.waitForElement(() -> digitalssetPage.Avaialbe_BusinessCondition(), "clickable");
		availableBusinessCondition.click();
		Thread.sleep(2000);

		utils.waitForElement(() -> digitalssetPage.ImageAttributes(), "visible");
		try {
			utils.waitForElement(() -> digitalssetPage.ImageAttributes(), "visible");
			test.pass("Image Attributes section is visible");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} catch (Exception e) {
			test.fail("Image Attributes section did not appear");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.fail("Image Attributes section did not appear after clicking Available Business Condition.", e);
		}
		/*******************
		 * Click on the business rule
		 ******************/
		List<String> assetFields = Arrays.asList("Asset Type", "Asset Category", "Asset Alternate Text", "Image Height", "Image Width", "X Resolution", "Y Resolution", "Original File Name Property");
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		Map<String,String> assetfieldMap = new LinkedHashMap<>();
		for (String fieldLabel : assetFields) {
			String value = digitalssetPage.getAssetAttributeValueBySearchLabel(summaryPage, fieldLabel, test);
			assetfieldMap.put(fieldLabel, value);
			data.put(fieldLabel, value);
			if (value == null || value.trim().isEmpty()) {
				test.warning(fieldLabel + " = <no value>");
				data.put(fieldLabel, " = <no value>");
				test.log(Status.WARNING, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} else {
				test.pass(fieldLabel + " = " + value);
				data.put(fieldLabel, value);
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
		String assetType = assetfieldMap.get("Asset Type");
		String assetCategory = assetfieldMap.get("Asset Category");
		String assetAlternateText = assetfieldMap.get("Asset Alternate Text");
		String imageHeight = assetfieldMap.get("Image Height");
		String imageWidth = assetfieldMap.get("Image Width");
		String xResolution = assetfieldMap.get("X Resolution");
		String yResolution = assetfieldMap.get("Y Resolution");
		String originalFileNameProperty = assetfieldMap.get("Original File Name Property");

		System.out.println(" Asset Type: " + assetType);
		System.out.println(" Asset Category: " + assetCategory);
		System.out.println(" Asset Alternate Text: " + assetAlternateText);
		System.out.println(" Image Height: " + imageHeight);
		System.out.println(" Image Width: " + imageWidth);
		System.out.println(" X Resolution: " + xResolution);
		System.out.println(" Y Resolution: " + yResolution);
		System.out.println(" Original File Name Property: " + originalFileNameProperty);

		/*********************************************************************************
		 * // Validate searched property: Original File Name Property must start with "timken" or "EDT"
		 *********************************************************************************/		
		boolean validPrefix = originalFileNameProperty != null && (originalFileNameProperty.toLowerCase()
				.startsWith("timken") || originalFileNameProperty.startsWith("EDT"));
		if (validPrefix) {
			test.pass("Original File Name Property validation passed: " + originalFileNameProperty);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.fail("Original File Name Property validation failed. Expected prefix 'timken' or 'EDT', but was: " + originalFileNameProperty);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		Assert.assertTrue(validPrefix, "Original File Name Property should start with 'timken' or 'EDT', but was: " + originalFileNameProperty);
		/********************************************************
		 * Validate searched property: Asset Alternate Text should not be empty
		 ******************************************************/
		String assetAlternateTextTrimmed = assetAlternateText == null ? "" : assetAlternateText.trim();
		System.out.println("Asset Alternate Text (trimmed): " + assetAlternateTextTrimmed);
	
		boolean altTextNotEmpty = !assetAlternateTextTrimmed.isEmpty();
		if (altTextNotEmpty) {
			test.pass("Asset Alternate Text is not empty: " + assetAlternateTextTrimmed);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.fail("Asset Alternate Text validation failed: value is empty");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		Assert.assertFalse(assetAlternateTextTrimmed.isEmpty(), "Asset Alternate Text is empty");
		/********************************************************
		 Validate searched property: Asset Alternate Text should not contain any file extension
		 ******************************************************/
		boolean hasAnyExtension = assetAlternateTextTrimmed.matches(".*\\.[A-Za-z0-9]+$");
		System.out.println("Asset Alternate Text has extension? : " + hasAnyExtension);
		if (!hasAnyExtension) {
			test.pass("Asset Alternate Text extension validation passed (no file extension): " + assetAlternateTextTrimmed);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.fail("Asset Alternate Text extension validation failed. It should NOT contain any file extension, but was: " + assetAlternateTextTrimmed);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		Assert.assertFalse(hasAnyExtension, "Asset Alternate Text should NOT contain any file extension, but was: " + assetAlternateTextTrimmed);
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);

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
	
	boolean hasAssetRenditions = attributesDropDownOptions.stream()
	        .anyMatch(option -> "Asset Renditions".equalsIgnoreCase(option.trim()));
	if (!hasAssetRenditions) {
	    String message = "Asset Renditions not found in Attributes dropdown. Exiting this test.";
	    System.out.println(message);
	    test.warning(message);
	    test.log(Status.WARNING, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	    return;
	}
	/*******************************************************
	 * Asset Renditions found -> click it
	 ******************************************************/
	try {
		summaryPage.clickAttributesDropdownOptionByText("Asset Renditions");
		System.out.println("Clicked on Asset Renditions.");
		test.pass("Asset Renditions found and clicked successfully.");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	} catch (Exception e) {
		String msg = "Asset Renditions was listed but click/select failed.";
		System.out.println(msg + " Error: " + e.getMessage());
		test.fail(msg);
		test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Assert.fail(msg, e);
	}
	utils.waitForElement(() -> digitalssetPage.getRowContainer(), "visible");
	/*******************************************************
	 * Get total row count from currently rendered rows
	 ******************************************************/
	int totalRows = digitalssetPage.getRednetionrowsRows().size();
	test.pass("Asset Renditions table found successfully.");
	/*******************************************************
	 * Loop through each row by index and extract its Rendition title
	 ******************************************************/
	for (int i = 0; i < totalRows; i++) {
		WebElement row = digitalssetPage.getRowByIndex(i);
		String title = digitalssetPage.getRenditionTitleFromRow(row);
		System.out.println("Row " + i + " -> Rendition ID: " + title);
		test.pass("Row " + i + " -> Rendition ID: " + title);
	}
  }
}