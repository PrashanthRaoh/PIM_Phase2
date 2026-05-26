package common_functions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.util.function.Function;
import java.util.function.Supplier;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import pages.DigitalAsset;
import pages.SearchPage2;

public class Utils  {
	private WebDriver driver;
	public WebDriverWait wait;
	final Exception[] lastException = { null };
	private ExtentTest test;
	public static String filepath = "src/test/resources/Test.xlsx";

	public Utils(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	/*****************************************************
	 ************* Updated code  ************************
	 ******************************************************/
	public WebElement waitForElement(Supplier<WebElement> elementSupplier, String conditionType) {
		try {
			switch (conditionType.toLowerCase()) {
				case "clickable":
					return wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return (el != null && el.isDisplayed() && el.isEnabled()) ? el : null;
						} catch (Exception ignored) {
							return null;
						}
					});

				case "visible":
					return wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return (el != null && el.isDisplayed()) ? el : null;
						} catch (Exception ignored) {
							return null;
						}
					});

				case "invisibility":
					wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return el == null || !el.isDisplayed();
						} catch (Exception ignored) {
							return true;
						}
					});
					return null;

				default:
					throw new IllegalArgumentException("Invalid wait condition type: " + conditionType);
			}
		} catch (Exception e) {
			try {
				String screenshotPath = Takescreenshot(driver);
				if (test != null) {
					test.fail("Wait failed: " + e.getMessage(),MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
				}
			} catch (IOException io) {
				io.printStackTrace();
			}
			throw new RuntimeException("waitForElement failed: " + e.getMessage(), e);
		}
	}
	
	/*******************
	 * Taking screenshot method
	*******************/
	public static String Takescreenshot(WebDriver driver) throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMM_dd_HHmmss").format(new Date());
		String screenshotFileName = "screenshot_" +  timestamp + ".png";
		String relativePath = "Screenshots/" + screenshotFileName;
		
		String fullPath = BaseTest.reportDirPath + "/" + relativePath;
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(src, new File(fullPath));
	    return relativePath;
	}

	/*******************
	 * Get Class Name method
	*******************/
	public String getClassName(Object obj) {
		return obj.getClass().getSimpleName();
	}
	/*******************
	 * Wait for the banner to appear and get text
	*******************/
	public static String waitForBannerAndGetText(WebDriver driver, Duration timeout) {
	    WebDriverWait wait = new WebDriverWait(driver, timeout);
	    Function<WebDriver, WebElement> getBannerElement = drv -> {
	        try {
	            return drv.findElement(By.cssSelector("#app")).getShadowRoot()
	                .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
	                .findElement(By.cssSelector("#pebbleAppToast > pebble-echo-html")).getShadowRoot()
	                .findElement(By.cssSelector("#bind-html"));
	        } catch (Exception e) {
	            return null;
	        }
	    };

	    WebElement banner = wait.until(drv -> {
	        WebElement el = getBannerElement.apply(drv);
	        return (el != null && el.isDisplayed()) ? el : null;
	    });

	    String bannerText = banner.getText();
	    System.out.println("✅ Banner appeared with the text : " + bannerText);

	    // Best-effort wait: proceed if toast disappears OR if a new toast replaces it.
	    String capturedText = bannerText == null ? "" : bannerText.trim();
	    try {
	    	new WebDriverWait(driver, Duration.ofSeconds(10)).until(drv -> {
	    		WebElement el = getBannerElement.apply(drv);
	    		if (el == null || !el.isDisplayed()) {
	    			return true;
	    		}
	    		String currentText = el.getText();
	    		String normalizedCurrentText = currentText == null ? "" : currentText.trim();
	    		return !normalizedCurrentText.equals(capturedText);
	    	});
	    	System.out.println("✅ Banner disappeared or changed.");
	    } catch (TimeoutException ignored) {
	    	System.out.println("⚠ Banner did not disappear/change within wait window. Proceeding with captured text.");
	    }

	    return bannerText;
	}
	
	/*********************************************
	 * Get the difference between 2 Lists
	*********************************************/
	public static <T> List<T> Show_List_Differences(List<T> list1, List<T> list2) {
        Set<T> allItems = new HashSet<>(list1);
        allItems.addAll(list2);

        List<T> differences = new ArrayList<>();
        for (T item : allItems) {
            boolean inList1 = list1.contains(item);
            boolean inList2 = list2.contains(item);
            if (inList1 != inList2) {
                differences.add(item);
            }
        }
        return differences;
    }
	
	public static <T> Map<String, List<T>> ShowList_Item_Differences(List<T> pre, List<T> post) {
		 List<T> removed = new ArrayList<>(pre);
		    removed.removeAll(post);  
		    List<T> added = new ArrayList<>(post);
		    added.removeAll(pre);
		    List<T> retained = new ArrayList<>(pre);
		    retained.retainAll(post); 
		    Map<String, List<T>> result = new HashMap<>();
		    result.put("removed", removed);
		    result.put("added", added);
		    result.put("retained", retained);
		    return result;
	}
	
	public WebElement findShadowElement(String... selectors) {
	    WebElement element = driver.findElement(By.cssSelector(selectors[0]));
	    for (int i = 1; i < selectors.length; i++) {
	        element = element.getShadowRoot().findElement(By.cssSelector(selectors[i]));
	    }
	    return element;
	}
	
	public boolean isElementPresent(Supplier<WebElement> elementSupplier, String visibility) {
	    try {
	        WebElement element = elementSupplier.get();
	        if (element != null && element.isDisplayed()) {
	            return true;
	        }
	    } catch (NoSuchElementException | StaleElementReferenceException ignored) {
	    }
	    return false;
	}
	

	public void applyBinaryFilters(Map<String, String> filters, SearchPage2 searchPage, DigitalAsset digitalAsset) throws Exception {
		for (Map.Entry<String, String> entry : filters.entrySet()) {
			applyBinaryFilter(entry.getKey(), entry.getValue(), searchPage, digitalAsset);
		}
	}
	
	public void applyBinaryFilter(String filterName, String filterValue, SearchPage2 searchPage, DigitalAsset digitalAsset) throws Exception {
		String normalizedValue = normalizeBinaryValue(filterValue);
		searchPage.getFilterButton().click();
		waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		WebElement materialTypeSearch = searchPage.Search_MaterialType();
		materialTypeSearch.clear();
		materialTypeSearch.sendKeys(filterName);
		Thread.sleep(1000);

		clickFilterAttribute(filterName);
		Thread.sleep(1000);
		clickYesNoValue(normalizedValue);
		Thread.sleep(1000);
		if (test != null) {
			test.pass("Selected filter: " + filterName + " = " + normalizedValue);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Takescreenshot(driver)).build());
		}

		digitalAsset.Status_Apply_btn().click();
		Thread.sleep(2000);
		waitForElement(() -> searchPage.getgrid(), "clickable");
		Thread.sleep(2000);
		if (test != null) {
			test.pass("Applied filter: " + filterName + " = " + normalizedValue);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Takescreenshot(driver)).build());
		}
	}
	private void clickFilterAttribute(String filterName) throws InterruptedException {
		WebElement attributeGrid = findShadowElement(
				"#app",
				"#contentViewManager",
				"[id^='currentApp_search-thing_']",
				"[id^='app-entity-discovery-component-']",
				"#entitySearchDiscoveryGrid",
				"#entitySearchFilter",
				"#search-filter",
				"#attributeModelLov_thing",
				"#modelLov_thing",
				"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid",
				"#grid"
		);
		List<WebElement> items = attributeGrid.getShadowRoot().findElements(By.cssSelector("pebble-lov-item"));
		if (items.isEmpty()) {
			throw new RuntimeException("No filter attributes displayed for: " + filterName);
		}
		Actions actions = new Actions(driver);
		for (WebElement item : items) {
			String text = item.getText().trim();
			if (text.equalsIgnoreCase(filterName) || text.toLowerCase().contains(filterName.toLowerCase())) {
				actions.moveToElement(item).perform();
				item.click();
				return;
			}
		}
		actions.moveToElement(items.get(0)).perform();
		items.get(0).click();
		Thread.sleep(1000);
	}

	private void clickYesNoValue(String filterValue) {
		WebElement valueGrid = findShadowElement(
				"#app",
				"#contentViewManager",
				"[id^='currentApp_search-thing_']",
				"[id^='app-entity-discovery-component-']",
				"#entitySearchDiscoveryGrid",
				"#entitySearchFilter",
				"#search-filter",
				"#rockEntityLov",
				"#entityLov",
				"div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid",
				"#grid"
		);

		List<WebElement> items = valueGrid.getShadowRoot().findElements(By.cssSelector("pebble-lov-item"));
		if (items.isEmpty()) {
			throw new RuntimeException("No Yes/No values displayed for filter");
		}
		for (WebElement item : items) {
			if (item.getText().trim().equalsIgnoreCase(filterValue)) {
				item.click();
				return;
			}
		}
		throw new RuntimeException("Filter value not found in LOV: " + filterValue);
	}
	private String normalizeBinaryValue(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Filter value cannot be null. Allowed values: Yes/No");
		}
		if (value.equalsIgnoreCase("Yes")) {
			return "Yes";
		}
		if (value.equalsIgnoreCase("No")) {
			return "No";
		}
		throw new IllegalArgumentException("Unsupported filter value: " + value + ". Allowed values: Yes/No");
	}

	public void removeAllAppliedFilterTabs() throws IOException {
		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		int removedCount = 0;

		for (int attempt = 0; attempt < 10; attempt++) {
			try {
				WebElement filterTags = findShadowElement(
						"#app",
						"#contentViewManager",
						"[id^='currentApp_search-thing_']",
						"[id^='app-entity-discovery-component-']",
						"#entitySearchDiscoveryGrid",
						"#entitySearchFilter",
						"#search-filter",
						"#filter-tags"
				);

				List<WebElement> tags = filterTags.getShadowRoot().findElements(By.cssSelector("[id^='tag']"));
				if (tags.isEmpty()) {
					break;
				}

				boolean removedInThisPass = false;
				Actions actions = new Actions(driver);
				for (WebElement tag : tags) {
					try {
						WebElement pebbleTag = tag.getShadowRoot().findElement(By.cssSelector("#pebble-tag"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", pebbleTag);
						actions.moveToElement(pebbleTag).pause(Duration.ofMillis(300)).perform();

						List<WebElement> closeIcons = tag.getShadowRoot().findElements(By.cssSelector("#pebble-tag > div.hoveredActions > span:nth-child(3) > pebble-icon"));
						if (closeIcons.isEmpty()) {
							continue;
						}
						WebElement closeIcon = closeIcons.get(0);
						shortWait.until(ExpectedConditions.elementToBeClickable(closeIcon)).click();
						removedCount++;
						removedInThisPass = true;
						break;
					} catch (Exception ignored) {
					}
				}
				if (!removedInThisPass) {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}
		if (test != null) {
			if (removedCount > 0) {
				test.info("Removed " + removedCount + " applied filter tab(s)");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Takescreenshot(driver)).build());
			} else {
				test.info("No applied filter tabs found to remove");
				test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Takescreenshot(driver)).build());
			}
		}
	}
}