package pages;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import common_functions.Utils;

public class BSAPIE_Page {

	private WebDriver driver;
	private By searchInputField = By.cssSelector("#app");

	public BSAPIE_Page(WebDriver driver) {
		this.driver = driver;
	}
		public WebElement common_element() {
			return driver.findElement(searchInputField).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#rockDetailTabs"));
	}
		
		public WebElement BSAPIESelectAttributesforHold() {
			return common_element().getShadowRoot()
				    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#input")).getShadowRoot()
				    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		}
		
		public WebElement BSAPIEUsecaseSalesOrgRegions_Auto () {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
			    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			    .findElements(By.cssSelector("[id^='rs']")).get(8).getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
			    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
			    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container > pebble-tags")).getShadowRoot()
			    .findElement(By.cssSelector("#tag0")).getShadowRoot()
			    .findElement(By.cssSelector("#pebble-tag"))
			    .findElement(By.cssSelector(".tag-item > .tag-name-value > .attribute-name"));
		}
		
		public WebElement Workflow_Refresh_btn() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
				    .findElement(By.cssSelector("#entityActions")).getShadowRoot()
				    .findElement(By.cssSelector("#toolbar")).getShadowRoot()
				    .findElement(By.cssSelector("#refresh"));
		}
		
		public WebElement getInProgressWorkflowStep(List<WebElement> steps, String expectedTitle) {
		    System.out.println("****** Workflows listed in the Workflow tab are *****");
		    for (int i = 0; i < steps.size(); i++) {
		        WebElement step = steps.get(i);
		        SearchContext stepShadow = step.getShadowRoot();
		        String actualTitle = stepShadow.findElement(By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span"))
		            .getAttribute("title");

		        boolean inProgress = step.getAttribute("class") != null && step.getAttribute("class").contains("iron-selected");
		        System.out.println((i + 1) + ": " + actualTitle + (inProgress ? " (In Progress)" : ""));
		        if (expectedTitle.equals(actualTitle)) {
		            if (inProgress) {
		                System.out.println("✅ As Expected: '" + expectedTitle + "' is In Progress.");
		                return step;
		            } else {
		                throw new AssertionError("❌ '" + expectedTitle + "' is found but not in progress.");
		            }
		        }
		    }
		    throw new AssertionError("❌ '" + expectedTitle + "' not found in the workflow.");
		}
		
		public WebElement BSAPIE_Record_Status() {
			return driver.findElement(By.cssSelector("#app"))
					.getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
					.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
					.getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
					.getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"))
					.getShadowRoot().findElement(By.cssSelector("#rockTabs"))
					.getShadowRoot().findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']"))
					.getShadowRoot().findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']"))
					.getShadowRoot().findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list"))
					.getShadowRoot().findElement(By.cssSelector("[id^='rs']"))
					.getShadowRoot().findElement(By.cssSelector("#input"))
					.getShadowRoot().findElement(By.cssSelector("bedrock-lov"))
					.getShadowRoot().findElement(By.cssSelector("#collectionContainer"))
					.getShadowRoot().findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		}
		
		/*******************
		 * Close the last tab
		*******************/
		public WebElement Tabclose_Xmark() {
		return driver.findElement(By.cssSelector("#app"))
			    .getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
			    .getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"))
			    .getShadowRoot().findElement(By.cssSelector("#rockTabs"))
			    .getShadowRoot().findElement(By.cssSelector("#tab-QuickSearchAttributes > div > div.tab-title > span.dynamic-close"));
		}
		
		public WebElement dropdownWrapper() {
		 return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("#tab-attributes")).getShadowRoot()
				.findElement(By.cssSelector("#dropdown-wrapper"));
		}
		public WebElement Attributenotdisplayed() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector(".base-grid-structure > .base-grid-structure-child-2 > #wizard-container > .base-grid-structure-child-2 > div > .base-grid-structure-child-2 > #step-container-manage > [name='QuickSearchAttributes']")).getShadowRoot()
				    .findElement(By.cssSelector(".base-grid-structure > div.base-grid-structure-child-1 > [align='center']"));
		}
		
		public WebElement Searchelement() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
		        	  .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
		        	  .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='rs']"));
		}
		
		public List<WebElement> Workflows() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
	            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
	            .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
	            .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
	            .findElements(By.cssSelector("pebble-step"));
		}
		
		public WebElement Refresh_btn() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
					.findElement(By.cssSelector("#entityActions")).getShadowRoot()
					.findElement(By.cssSelector("#toolbar")).getShadowRoot().findElement(By.cssSelector("#refresh"))
					.getShadowRoot().findElement(By.cssSelector("#simpleButton > pebble-icon"));
		}
		
		public WebElement common_Munition_Indicatortab() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
			        .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
			        .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
			        .findElement(By.cssSelector("#search-filter")).getShadowRoot()
			        .findElement(By.cssSelector("#filter-tags")).getShadowRoot()
			        .findElement(By.cssSelector("#tag1"));
		}
		
		public WebElement MunitionIndicator_Tab() {
			return common_Munition_Indicatortab().getShadowRoot()
	        .findElement(By.cssSelector("#pebble-tag"));
		}
		
		public WebElement MunitionIndiacator_Yes_Remove_Icon() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
			        .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
			        .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
			        .findElement(By.cssSelector("#search-filter")).getShadowRoot()
			        .findElement(By.cssSelector("#filter-tags")).getShadowRoot()
			        .findElement(By.cssSelector("#tag1")).getShadowRoot()
			        .findElement(By.cssSelector("#pebble-tag > div.hoveredActions > span:nth-child(3) > pebble-icon"));
		}
		// ------------------- Common Shadow DOM path -------------------
		public WebElement commonMunitionCheckboxParent() {
		    return driver.findElement(By.cssSelector("#app")).getShadowRoot()
		            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		            .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
		            .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
		            .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
		            .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
		            .findElement(By.cssSelector("#search-filter")).getShadowRoot()
		            .findElement(By.cssSelector("#rockEntityLov")).getShadowRoot()
		            .findElement(By.cssSelector("#entityLov")).getShadowRoot()
		            .findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).getShadowRoot()
		            .findElement(By.cssSelector("#grid"));
		}
		
		public WebElement MunitionIndicator_NO_Checkbox() {
		    return commonMunitionCheckboxParent().getShadowRoot()
		            .findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div.ag-row.ag-row-no-focus.ag-row-even.ag-row-level-0.ag-row-position-absolute.ag-row-first > div > pebble-lov-item"))
		            .getShadowRoot()
		            .findElement(By.cssSelector("div > div"));
		}
		
		public WebElement MunitionIndicator_YES_Checkbox() {
		    return commonMunitionCheckboxParent()
		            .getShadowRoot().findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div.ag-row.ag-row-no-focus.ag-row-odd.ag-row-level-0.ag-row-position-absolute.ag-row-last"));
		}
		
		public WebElement BSAUseCase_YES_Checkbox() {
			return 
					driver.findElement(By.cssSelector("#app")).getShadowRoot()
					    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					    .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
					    .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
					    .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
					    .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
					    .findElement(By.cssSelector("#search-filter")).getShadowRoot()
					    .findElement(By.cssSelector("#rockEntityLov")).getShadowRoot()
					    .findElement(By.cssSelector("#entityLov")).getShadowRoot()
					    .findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).getShadowRoot()
					    .findElement(By.cssSelector("#grid")).getShadowRoot()
					    .findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div.ag-row.ag-row-no-focus.ag-row-odd.ag-row-level-0.ag-row-position-absolute.ag-row-last"));
	}
		
		public WebElement Taxonomy_Dialog() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
            	    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
            	    .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
            	    .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
            	    .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
            	    .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
            	    .findElement(By.cssSelector("#search-filter")).getShadowRoot()
            	    .findElement(By.cssSelector("#pathSelector"));
		}
		
		public WebElement filterbox() {
			return Taxonomy_Dialog().getShadowRoot()
     	    .findElement(By.cssSelector("#classification-contextTree")).getShadowRoot()
     	    .findElement(By.cssSelector("#filter-text")).getShadowRoot()
     	    .findElement(By.cssSelector("#pebble__textbox"));
		}
		
		public WebElement taxonomy_Apply_btn() {
			return Taxonomy_Dialog().getShadowRoot()
					.findElement(By.cssSelector("#download")).getShadowRoot()
					.findElement(By.cssSelector("#buttonTextBox"));
		}

		public List<WebElement> taxonomyOperatorItems() {
			return Taxonomy_Dialog().getShadowRoot()
					.findElement(By.cssSelector("#classification-contextTree")).getShadowRoot()
					.findElement(By.cssSelector("#operators")).getShadowRoot()
					.findElement(By.cssSelector("div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).getShadowRoot()
					.findElement(By.cssSelector("#grid")).getShadowRoot()
					.findElements(By.cssSelector("pebble-lov-item"));
		}
		public WebElement Comments_input() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
	        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
	        .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
	        .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='stepComments-']")).getShadowRoot()
	        .findElement(By.cssSelector("#textarea")).getShadowRoot()
	        .findElement(By.cssSelector("#input-1")).getShadowRoot()
	        .findElement(By.cssSelector("#textarea"));
		}
		
		public List<WebElement> BSA_ApprovalTab_Items() {
			return  driver.findElement(By.cssSelector("#app")).getShadowRoot()
			        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
			        .findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			        .findElement(By.cssSelector("#rock-my-todos")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
			        .findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
			        .findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary")).getShadowRoot()
			        .findElement(By.cssSelector("#moreDetails"))
			        .findElements(By.cssSelector("my-todo-detail-view-list-item"));
		}

		public int clickApprovalQueueItem(SearchPage2 searchPage, Utils utils, ExtentTest test, List<String> expectedItems, String queueToClick) throws InterruptedException, IOException {
			List<WebElement> detailItems = BSA_ApprovalTab_Items();
			Assert.assertFalse(detailItems.isEmpty(), "No queue items found under More Details");
			utils.waitForElement(() -> detailItems.get(0), "clickable");
			System.out.println("There are " + detailItems.size() + " elements");

			Assert.assertEquals(detailItems.size(), expectedItems.size(), "Item count mismatch");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			int matchedRowIndex = -1;

			for (int i = 0; i < detailItems.size(); i++) {
				WebElement summary = detailItems.get(i);
				WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#button-text-box"));
				String actualText = innerDiv.getAttribute("title").trim().replaceFirst("^\\d+\\s", "");
				System.out.println("Item " + (i + 1) + ":--" + actualText);
				Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));

				if (actualText.contains(queueToClick)) {
					matchedRowIndex = i + 1;
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

			if (matchedRowIndex == -1) {
				Assert.fail("'" + queueToClick + "' not found in More Details list.");
			}

			test.pass("Clicked on " + queueToClick + " which is found at row -- " + matchedRowIndex);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			test.pass("Clicked on " + queueToClick);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			return matchedRowIndex;
		}

		public List<WebElement> BSAPIE_SummaryElements(){
		return  driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));
		}
		

		public void verifyMaterialIsNotListed(SearchPage2 searchPage, String matid, Utils utils, ExtentTest test) throws Exception {
			searchPage.Search_things_BreadCrum().click();
			Thread.sleep(2000);
			utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			test.pass("Navigated back to search thing");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			searchPage.searchthingdomain_Input_Mat_Id().click();
			searchPage.searchthingdomain_Input_Mat_Id().clear();
			searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
			searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
			Thread.sleep(5000);

			try {
				String txt = searchPage.rowsdisplayedtext().getText();
				String result = txt.split(" / ")[1];
				int zeroRows = Integer.parseInt(result);
				System.out.println(zeroRows);
				Assert.assertEquals(zeroRows, 0);
				test.pass(matid + " completion is 100%. Hence not visible");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			} catch (Exception e) {
				WebElement rowsRedefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
						.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
						.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
						.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
						.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
						.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
						.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
						.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
						.findElement(By.cssSelector("#grid"));
				List<WebElement> displayedRows = rowsRedefined.getShadowRoot().findElements(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div> div.ag-row.ag-row-even.ag-row-level-0"));

				if (!displayedRows.isEmpty()) {
					System.out.println("Records found for the search criteria");
					test.fail(matid + " completion is NOT 100%. Please verify");
					test.log(Status.FAIL,
							MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					Assert.fail(matid + " is still visible after rejection.");
				}
			}
		}
}