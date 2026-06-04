package First_Post_ETL_KeyFlags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
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
/*****************************************************************************************
 Validates post-ETL key flags for a selected material (BSAPIE and CBT logins):
 Check if the sellable Bearing dimension values are empty
 ****************************************************************************************/
public class TC11_Post_ETL_BsaY_CbtY_InterCompany extends BaseTest{
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	@Test
	@Parameters("UseCaseOwner")
	public void Post_ETL_BsaY_CbtN_MunN(String useCaseOwner) throws IOException, InterruptedException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));
		
		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
		System.out.println("Executing Test For User : " + useCaseOwner);
		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		String PRE_ETL_Filename =  "Pre_ETL_Artifacts/Key_Flags/TC11_BsaY_CbtY_InterCompany.txt";

		String Matid = NotepadManager.FetchMaterialID(PRE_ETL_Filename);
		System.out.println("Fetched Material ID: " + Matid);
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		
		try {
		    Map<String, String> selectedRecord = cbtUtils.searchMaterialIdAndOpenDetails(Matid, searchPage, summaryPage, test);
		    if (selectedRecord.isEmpty()) {
		        test.warning("Could not proceed because no rows were available for Material ID: " + Matid);
		    } else {
		        String matid = selectedRecord.get("Material Id");
		        String sellableMaterialDescription = selectedRecord.get("Material Description");
		        System.out.println("Material ID -- " + matid + " Material Description --" + sellableMaterialDescription);
		        data.put("Material ID", matid);
		        Thread.sleep(3000);
		        utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		        Thread.sleep(5000);
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		    test.fail("Exception occurred while opening entity for Material ID -- " + Matid);
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		/*****************************************************************
		  Check if the sellable has updated value  
		 "Application Type Code" value to "55 or 56"
		 **************************************************************** */		
		String Applicationtypecodevalue = cbtUtils.getApplicationTypCodevalue(summaryPage, "Application Type Code", test);
		String normalizedApplicationTypeCode = Applicationtypecodevalue == null ? "" : Applicationtypecodevalue.trim();
		if (!normalizedApplicationTypeCode.isEmpty()) {
			test.pass("Application type code value for the record is : " + Applicationtypecodevalue);
			System.out.println("Application type code value for the record is : " + Applicationtypecodevalue);
			data.put("Application Type Code", Applicationtypecodevalue);
		} else {
			test.info("Application typecode value for the record is not available or blank ");
		}
//		Assert.assertTrue( "55".equals(normalizedApplicationTypeCode) || "56".equals(normalizedApplicationTypeCode),
//				"Expected Application Type Code to be 55 or 56, but got: " + Applicationtypecodevalue);
		/*****************************************************************
		  1) BSA PIE Usecase? = No
		 **************************************************************** */
		String bsaPieUsecaseStatus = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "BSA PIE Usecase?", test);
		String normalizedBsaPieUsecase = bsaPieUsecaseStatus == null ? "" : bsaPieUsecaseStatus.trim();
		System.out.println("BSA PIE Usecase? value: " + bsaPieUsecaseStatus);
		if ("No".equalsIgnoreCase(normalizedBsaPieUsecase)) {
			test.pass("BSA PIE Usecase? value is No as expected.");
		} else {
			test.fail("BSA PIE Usecase? value is not No. Actual: " + bsaPieUsecaseStatus);
		}
//		Assert.assertEquals(normalizedBsaPieUsecase, "No", "Expected BSA PIE Usecase? to be No, but got: " + bsaPieUsecaseStatus);
		/*****************************************************************
		  2) BSA PIE Sellable Product Status = Approved
		 **************************************************************** */
		String bsaPieStatus = cbtUtils.getAttributeLovValueBySearchLabel(summaryPage, "BSA PIE Sellable Product Status", test);
		System.out.println("BSA PIE Sellable Product Status " + bsaPieStatus + " as expected");
		if ("Approved".equalsIgnoreCase(bsaPieStatus == null ? "" : bsaPieStatus.trim())) {
			test.pass("BSA PIE Sellable Product Status is Approved as expected.");
		}
		else {
			test.fail("BSA PIE Sellable Product Status is NOT Approved. Actual: " + bsaPieStatus);
		}
//		Assert.assertEquals(bsaPieStatus == null ? "" : bsaPieStatus.trim(), "Approved", "Expected BSA PIE Sellable Product Status to be Approved, but got: " + bsaPieStatus);
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
	
	    boolean hasBearingDimensions = attributesDropDownOptions.stream()
	        .anyMatch(option -> "Bearing Dimensions".equalsIgnoreCase(option.trim()));
	
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
		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
			String header = entry.getKey();
			String value = entry.getValue();
			String normalizedValue = value == null ? "" : value.trim();
			if (normalizedValue.isEmpty()) {
				test.pass("Bearing Dimension '" + header + "' is empty as expected.");
				System.out.println("Bearing Dimension '" + header + "' is empty as expected.");
			} else {
				test.fail("Bearing Dimension '" + header + "' is not empty. Actual: " + value);
				System.out.println("Bearing Dimension '" + header + "' is NOT empty. Actual: " + value);
			}
//			Assert.assertTrue(normalizedValue.isEmpty(), "Expected Bearing Dimension '" + header + "' to be empty, but got: " + value);
		}
	}
}