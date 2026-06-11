package First_Post_ETL_KeyFlags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.NotepadManager;
import common_functions.Utils;
import pages.HomePage;
import pages.SearchPage2;
/*****************************************************************************************
 Validates post-ETL key flags for a selected material (BSAPIE and CBT logins):
 Check if the sellable Bearing dimension values are empty
 ****************************************************************************************/
public class TC14_Post_ETL_BsaN_CbtN_InterCompany extends BaseTest{
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
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
		System.out.println("Executing Test For User : " + useCaseOwner);
		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		String PRE_ETL_Filename =  "Pre_ETL_Artifacts/Key_Flags/TC14_BsaN_CbtN_InterCompany.txt";

		String Matid = NotepadManager.FetchMaterialID(PRE_ETL_Filename);
		System.out.println("Fetched Material ID: " + Matid);
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		/*************************************************
		 * --------- Verify the record again in the search thing. It should not be listed ------- *
		 ************************************************/
		searchPage.searchthingdomain_Input_Mat_Id().click();
		searchPage.searchthingdomain_Input_Mat_Id().clear();
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
		Thread.sleep(5000);

		int rowCount;
		try {
			String txt = searchPage.rowsdisplayedtext().getText();
			String result = txt.split(" / ")[1];
			rowCount = Integer.parseInt(result);
		} catch (Exception e) {
			WebElement rowsredefined2 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
					.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
					.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
					.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
					.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
					.findElement(By.cssSelector("#grid"));
			List<WebElement> arrrowsdefined2 = rowsredefined2.getShadowRoot().findElements(By.cssSelector(
					"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div> div.ag-row.ag-row-even.ag-row-level-0"));
			rowCount = arrrowsdefined2.size();
		}
		System.out.println(rowCount);
		if (rowCount == 0) {
			test.pass(Matid + " has been deleted from the system");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.fail(Matid + " has not been deleted from the system. Rows found: " + rowCount);
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.fail(Matid + " expected 0 rows, but found " + rowCount);
		}
	}
}