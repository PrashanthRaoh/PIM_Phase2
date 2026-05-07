package common_functions;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import pages.BSAPIE_Page;
import pages.CBT_Page;
import pages.SearchPage2;
import pages.SummaryPage;

public class CBT_Utils {
    private final WebDriver driver;
    private final Utils utils;
    private final CBT_Page cbtPage;
    private final SearchPage2 searchPage;
    private ExtentTest test;

    // Backward-compatible constructor
    public CBT_Utils(WebDriver driver) {
        this(driver, BaseTest.utils);
    }

    public CBT_Utils(WebDriver driver, Utils utils) {
        this.driver = driver;
        this.utils = utils;
        this.cbtPage = new CBT_Page(driver);
        this.searchPage = new SearchPage2(driver);
    }
    public static Map<String, Object> getWorkflowDetails(WebDriver driver) {
        Map<String, Object> data = new HashMap<>();
        List<String> allSteps = new ArrayList<>();
        List<String> completedSteps = new ArrayList<>();
        String activeStep = "<none>";
        int totalSteps = 0;

        try {
            WebElement host = driver.findElement(By.cssSelector("#app")).getShadowRoot()
                    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
                    .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
                    .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
                    .findElement(By.cssSelector("#workflowStepper_newsellablematerial_workflowDefinition"));

            List<WebElement> steps = host.findElements(By.cssSelector("pebble-step"));
            totalSteps = steps.size();

            for (WebElement step : steps) {
                try {
                    String txt = step.getShadowRoot().findElement(By.cssSelector("#textLabel")).getText();
                    allSteps.add(txt);
                } catch (Exception ignored) {
                }
            }

            try {
                WebElement active = host.findElement(By.cssSelector("pebble-step.inprogress, pebble-step.iron-selected"));
                if (active != null) {
                    activeStep = active.getShadowRoot().findElement(By.cssSelector("#textLabel")).getText();
                }
            } catch (Exception ignored) {
            }

            try {
                List<WebElement> completed = host.findElements(By.cssSelector("pebble-step.completed"));
                for (WebElement c : completed) {
                    try {
                        String txt = c.getShadowRoot().findElement(By.cssSelector("#textLabel")).getText();
                        completedSteps.add(txt);
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }

        } catch (Exception e) {
            System.out.println("Workflow panel not found");
        }
        data.put("totalSteps", totalSteps);
        data.put("allSteps", allSteps);
        data.put("activeStep", activeStep);
        data.put("completedSteps", completedSteps);
        return data;
    }

    public static String getRecordStatus(WebDriver driver) {
        WebElement targetElement = null;
        String recordStatus = null;

        try {
            targetElement = driver.findElement(By.cssSelector("#app")).getShadowRoot()
                    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
                    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
                    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
                    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
                    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
                    .findElement(By.cssSelector("#input")).getShadowRoot()
                    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
                    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
                    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
        } catch (Exception e) {
            System.out.println("Neither attribute list nor 'attributes not available' element found.");
        }

        if (targetElement != null) {
            recordStatus = targetElement.getText();
        } else {
            System.out.println("Status element not found...");
        }

        return recordStatus;
    }
	/***************************************************
	Filter application
	**************************************************/
    private void clickWhenClickable(Supplier<WebElement> elementSupplier) {
        utils.waitForElement(elementSupplier, "clickable").click();
    }
    private void applySearchFilter(
            String filterName,
            Supplier<WebElement> thingDomainSupplier,
            Supplier<WebElement> optionSupplier,
            Supplier<WebElement> applyButtonSupplier, long delayMs) {

        clickWhenClickable(() -> searchPage.getFilterButton());

        WebElement filterInput = utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
        filterInput.clear();
        filterInput.sendKeys(filterName);

        clickWhenClickable(thingDomainSupplier);
        clickWhenClickable(optionSupplier);
        if (delayMs > 0) {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting before applying filter", e);
            }
        }
        clickWhenClickable(applyButtonSupplier);
    }
    
    public void applyCatalogUsecaseIntYesFilter() {
        applySearchFilter(
                "Catalog Bearing Tool Usecase[Int]?",
                () -> cbtPage.thingDomainOptionByText("Catalog Bearing Tool Usecase[Int]?"),
                () -> cbtPage.CBTUcasecaseInt_Yes_Option(),
                () -> cbtPage.CBTUsecase_IntApply_btn(),
                2000);
    }
    public void applySellableProductStatusApprovedFilter() {
        applySearchFilter(
                "Catalog Bearing Tool Sellable Product Status",
                () -> cbtPage.thingDomainOptionByText("Catalog Bearing Tool Sellable Product Status"),
                () -> cbtPage.CBT_Approved_Option(),
                () -> cbtPage.CBTUsecase_IntApply_btn(),
                2000);
    }
    
    public String getOpenedRecordStatus(SummaryPage summaryPage,CBT_Page cbtpage,BSAPIE_Page BSAPIE_PO, ExtentTest test) throws InterruptedException, IOException {
    // Give a small pause to ensure UI is ready (prefer replace with targeted waits if possible)
    Thread.sleep(500);
    summaryPage.SearchIcon().click();
    summaryPage.SearchInputfield().clear();
    summaryPage.SearchInputfield().sendKeys("Catalog Bearing Tool Sellable Product Status");
    Thread.sleep(500);
    // press Enter using actions to avoid focus issues
    new Actions(driver).moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();

    // Allow time for attribute to appear — use utils.waitForElement which waits for visibility
    Thread.sleep(1500);
    WebElement targetElement = utils.waitForElement(() -> cbtpage.CBT_Sellable_Product_Status(), "visible");

    if (targetElement == null) {
        test.fail("CBT Sellable Product Status element is NULL");
        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        // return null or throw — returning null to let caller handle
        return null;
    }

    String recordStatus = targetElement.getText();
    test.pass("Found record status: " + recordStatus);
    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

    // Optionally close details here or leave to caller — keeping close in caller makes it explicit.
    // BSAPIE_PO.Tabclose_Xmark().click();
    // Thread.sleep(500);

    return recordStatus;
}
    
public Map<String, String> selectRandomRowAndOpenDetails(SearchPage2 searchPage, SummaryPage summaryPage,
		ExtentTest test) throws InterruptedException, IOException {

	Map<String, String> result = new LinkedHashMap<>();
	Actions actions = new Actions(driver);

	final int maxAttempts = 3;
	int attempt = 0;

	while (attempt < maxAttempts) {
		attempt++;
		try {
			utils.waitForElement(() -> searchPage.getgrid(), "clickable");

			// Re-locate grid and rows each attempt (avoid storing WebElements across DOM
			// changes)
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

			// wait until at least one row is present (simple loop—Utils doesn't expose list
			// wait)
			int tries = 0;
			while (arrrowsdefined.size() == 0 && tries < 10) {
				Thread.sleep(300);
				arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));
				tries++;
			}
			org.testng.Assert.assertTrue(arrrowsdefined.size() > 0, "There should be results after applying filters");

			// Choose random row index AFTER we have fresh rows
			Random rand = new Random();
			int randnum = rand.nextInt(arrrowsdefined.size());

			// Immediately fetch the row and cells (so references are fresh)
			WebElement rowByRow = arrrowsdefined.get(randnum);
			String sellableMaterialDescription = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
			String matid = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();

			// Click the material id (re-locate the element on the row just before clicking)
			WebElement matidElement = rowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
			actions.moveToElement(rowByRow).build().perform();
			Thread.sleep(300); // small delay to allow hover effects
			matidElement.click();

			// Wait for the summary page to be visible
			utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");

			test.pass("Material ID -- " + matid + " Material Description -- " + sellableMaterialDescription + " is selected and opened");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			result.put("Material Id", matid);
			result.put("Material Description", sellableMaterialDescription);
			return result;

		} catch (org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.NoSuchElementException ex) {
			// Retry: element was stale or not present yet — reattempt locating/retrying
			test.info("Stale/NoSuchElement on attempt " + attempt + " — retrying: ");
			continue;
		}
	}

	// If we exit loop without returning, fail the test
	test.fail("Unable to select a stable row after " + maxAttempts + " attempts");
	test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	Assert.fail("Unable to select a stable row from grid");
	return result;
}

public static String getActiveWorkflowAfterRefresh(WebDriver driver, CBT_Page cbtpage, ExtentTest test)
		throws IOException {
	/*******************************************
	 * Click refresh and wait for UI update
	 ******************************************/
	cbtpage.CBT_Workflow_Refresh_btn().click();
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	}

	test.pass("Refreshed transaction to get the latest workflow status");
	test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

	/*******************************************
	 * Re-fetch workflow steps after refresh
	 ******************************************/
	List<WebElement> allStepsAfterRefresh = driver.findElement(By.cssSelector("#app")).getShadowRoot()
			.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
			.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
			.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
			.findElements(By.cssSelector("pebble-step"));

	System.out.println("✅ Workflow steps after refresh: " + allStepsAfterRefresh.size());
	String activeStepTitle = null;

	/*******************************************
	 * Identify active step
	 ******************************************/
	for (WebElement step : allStepsAfterRefresh) {
		SearchContext stepShadow = step.getShadowRoot();

		String actualTitle = stepShadow
				.findElement(
						By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span"))
				.getAttribute("title");
		boolean isActive = step.getAttribute("class") != null && step.getAttribute("class").contains("iron-selected");

		if (isActive) {
			activeStepTitle = actualTitle;
			test.pass("Active workflow step after refresh is: " + actualTitle);
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			System.out.println("🟢 Active Step: " + actualTitle);
		} else {
			System.out.println("Step: " + actualTitle);
		}
	}
	return activeStepTitle;
}
/*************************************************************************
 * Returns hold-attribute tag texts after expanding/clicking "more values" when needed.
 *************************************************************************/
public List<String> getOnHoldAttributeTags(WebElement holdListContainer, String contextName, ExtentTest test) {
    List<String> tagTexts = new ArrayList<>();
    try {
        List<WebElement> moreValuesElems = new ArrayList<>();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            moreValuesElems = shortWait.until(d -> {
                List<WebElement> elems = holdListContainer.getShadowRoot().findElements(By.cssSelector("div > .more-values-message"));
                return elems.isEmpty() ? null : elems;
            });
        } catch (Exception ignored) {
            // Optional element; continue with direct tag read
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); 
        }
        if (!moreValuesElems.isEmpty() && moreValuesElems.get(0).isDisplayed()) {
            WebElement moreValuesList = moreValuesElems.get(0);
            try {
                moreValuesList.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", moreValuesList);
            }
            test.pass(contextName + " items expanded");
        } else {
            test.pass(contextName + " items listed directly");
        }
        List<WebElement> tagElements = holdListContainer.getShadowRoot().findElements(By.cssSelector("#pebble-tag .attribute-name, .tag-item-container .attribute-name"));
        for (WebElement tag : tagElements) {
            String text = tag.getText().trim();
            if (!text.isEmpty()) {
                tagTexts.add(text);
            }
        }
        test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
    } catch (Exception e) {
        System.out.println("Failed to read " + contextName + " items: " + e.getMessage());
    }
    return tagTexts;
}
}
