package Key_Flags;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.CBT_Utils;
import common_functions.NotepadManager;
import common_functions.Utils;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

/****************************************************************************
 * filter attribute 
 * "BSA PIE Usecase? =No", 
 * Catalog Bearing Tool Usecase[Int]? =No and 
 * Munitions Indicator="No".
 ****************************************************************************/

public class TC01_BsaN_CbtN_MunN extends BaseTest {
    ExtentTest test;
    Utils utils;
    HomePage homePage;
    SearchPage2 searchPage;
    DigitalAsset digitalssetPage;


    @Test(groups = {"BSAPIEowner"})
    public void BsaN_CbtN_MunN() throws Exception {
        Map<String, Object> data = new LinkedHashMap<>();
        String className = this.getClass().getSimpleName();
        System.out.println("Executing Test  ---  " + className);
        test = BaseTest.extentreport.createTest(className);
        test.assignAuthor(System.getProperty("user.name")).assignDevice(System.getenv("COMPUTERNAME"));
        homePage = new HomePage(driver);
        SummaryPage summaryPage = new SummaryPage(driver);
        SearchPage2 searchPage = new SearchPage2(driver);
        DigitalAsset digitalssetPage = new DigitalAsset(driver);
        utils = new Utils(driver, test);
        CBT_Utils cbtUtils = new CBT_Utils(driver, utils);
        String PRE_ETL_Filename = "/Pre_ETL_Artifacts/Key_Flags/" + className + ".txt";

        /*********************************************
         Navigate to Search Page
         ********************************************/
        utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
        homePage.clickSearch_Products_Button().click();
        utils.waitForElement(() -> searchPage.getgrid(), "clickable");
        test.pass("Search thing domain displayed");
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

        /*********************************************
         Apply the filters
         ********************************************/
        Map<String, String> filters = new LinkedHashMap<>();
        filters.put("BSA PIE Usecase?", "No");
        filters.put("Catalog Bearing Tool Usecase[Int]?", "No");
        filters.put("Munitions Indicator", "No");
        utils.applyBinaryFilters(filters, searchPage, digitalssetPage);

        /*********************************************
         Select the record and update to Notepad
         ********************************************/
        Map<String, String> selectedRecord = cbtUtils.selectRandomRowAndOpenDetails(searchPage, summaryPage, test);
        String matid = selectedRecord.get("Material Id");
        System.out.println("Selected Material ID: " + matid);
        
        String appliedFiltersText = filters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(java.util.stream.Collectors.joining(", "));
        data.put("Applied Filters", appliedFiltersText);
        data.put("Material ID", matid);
        NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, data);

//            utils.removeAllAppliedFilterTabs();
        homePage.clickSearch_Products_Button().click();
        utils.waitForElement(() -> searchPage.getgrid(), "clickable");
        test.pass("Search thing domain displayed");
        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    }
}