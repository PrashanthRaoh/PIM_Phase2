package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CBT_Page {
	
	public WebDriver driver;
	
	public CBT_Page(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement Common_Summary_items1() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
		.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
		.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
		.findElement(By.cssSelector("#rockDetailTabs"));
	}
	
	public WebElement Common_Summary_items2() {
		return Common_Summary_items1().getShadowRoot()
			 .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			 .findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']")).getShadowRoot()
			 .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			 .findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
			 .findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']"));
	}
	
	public WebElement SellableMaterialTabcontent() {
		return  driver.findElement(By.cssSelector("#app"))
			    .getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='currentApp_home_rs']"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='app-dashboard-component-rs']"))
			    .getShadowRoot().findElement(By.cssSelector("rock-layout > rock-dashboard-widgets"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='rs']"))
			    .getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']"))
			    .getShadowRoot().findElement(By.cssSelector("#rock-my-todos-tabs"))
			    .getShadowRoot().findElement(By.cssSelector("#tab-content"));
	}
	
	public WebElement SellableMaterial_needtofix_grid() {
		return Common_Summary_items2().getShadowRoot()
				.findElement(By.cssSelector("#accordion\\ 0 > div"));
	}
	public WebElement Summarythings_General() {
		return Common_Summary_items2().getShadowRoot()
				.findElement(By.cssSelector("#accordion\\ 1 > div"));
	}

	public WebElement Catalog_Bearing_Tool_Usecase_Int_CommonElement(){
		return Common_Summary_items1().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#input")).getShadowRoot()
				.findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				.findElement(By.cssSelector("#collectionContainer"));
	}
	
	public WebElement CBT_Override1() {
		return Common_Summary_items1().getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElements(By.cssSelector("[id^='rs']")).get(2);
	}

	public WebElement CBT_Override2() {
		return CBT_Override1().getShadowRoot()
				.findElement(By.cssSelector("#input")).getShadowRoot()
				.findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				.findElement(By.cssSelector("#collection_container_wrapper"))
				.findElement(By.cssSelector(".d-flex"));
	}

	public WebElement Override_Yes_Option() {
		return CBT_Override1().getShadowRoot()
				.findElement(By.cssSelector("#input")).getShadowRoot()
				.findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				.findElement(By.cssSelector("#lov")).getShadowRoot()
				.findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot()
				.findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > [row-index='1']"))
				;
	}
	public WebElement host() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
		    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
		    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
		    .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
		    .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
		    .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
		    .findElement(By.cssSelector("#workflowStepper_newsellablematerial_workflowDefinition"));
	}
	
	public WebElement Searchthing_BreadCrum() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityTitlebar")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockTitle > div.title-text > div.breadcrumb-wrapper > rock-breadcrumb"))
				.getShadowRoot().findElement(By.cssSelector(
						"div > div > span.breadcrumb-link.flex-nowrap.item-2 > span.breadcrumb-data.text-ellipsis"));
	}
	
	public WebElement CBT_Workflow_Refresh_btn() {
	 return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
			    .findElement(By.cssSelector("#entityActions")).getShadowRoot()
			    .findElement(By.cssSelector("#toolbar")).getShadowRoot()
			    .findElement(By.cssSelector("#refresh")); 
	}
	
}
