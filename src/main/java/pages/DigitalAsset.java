package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

public class DigitalAsset {
	private WebDriver driver;
	private By searchInputField = By.cssSelector("#app");

	public DigitalAsset(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement common_element() {
		return driver.findElement(searchInputField).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs"));
	}

	public WebElement TotalRowsText() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot().findElement(By
						.cssSelector("#gridHeader > div.grid-actions.row > span.text-ellipsis.m-r-5.m-l-5.page-range"));
	}

	public WebElement AdvanceSearch_Dropdown() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#simpleButton > pebble-icon"));
	}

	public WebElement generalDropdown_First() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#queryBuilder"));
	}

	public WebElement generalDropdown_second() {
		return generalDropdown_First().getShadowRoot().findElement(By.cssSelector("#operators"));
	}

	public WebElement HavingNoImagesFilterDropdownValue() {
		return generalDropdown_second().getShadowRoot()
				.findElement(By.cssSelector("#eds_pebbleItem_itemSelected_havingno > div"));
	}

	public WebElement RelationshipMaindropdown_Obj() {
		return generalDropdown_First().getShadowRoot().findElement(By.cssSelector("#relationshipButton"))
				.getShadowRoot().findElement(By.cssSelector("#simpleButton"));
	}
	
	public WebElement HasEnginerringPartNumber_dropdown() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
	    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
	    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
	    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
	    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
	    .findElement(By.cssSelector("#relationships-hasengineeredpartowned"));
	}

	public WebElement HasImagesDropdownvalue() {
		return generalDropdown_First().getShadowRoot().findElement(By.cssSelector("#relationshipModelLov")) .getShadowRoot()
				.findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector("#lit-grid"))
				.findElement(By.cssSelector("[ref='centerContainer'] > [ref='eViewport'] > [ref='eContainer']"))
				.findElement(By.cssSelector("[row-index='1']"));
	}

	public WebElement Apply_btn_onFilter() {
		return generalDropdown_First().getShadowRoot()
				.findElement(By.cssSelector("#confirmButton")).getShadowRoot()
				.findElement(By.cssSelector("#simpleButton"));
	}

	public WebElement Save_2d_Line_Drawring() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#next")).getShadowRoot()
				.findElement(By.cssSelector("#simpleButton"));
	}

	public WebElement Next_btn() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#next"));
	}

	public WebElement MoreActions_Dropdown_1() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-relationship-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-relationship-container > rock-relationship-manage")).getShadowRoot()
				.findElement(By.cssSelector("#entityRelationshipSearchResult_hasimages")).getShadowRoot()
				.findElement(By.cssSelector("div > div.base-grid-structure-child-2 > rock-relationship-grid")).getShadowRoot()
				.findElement(By.cssSelector("#relationship_actions"));
	}

	public WebElement MoreActions_Dropdown_2() {
		return MoreActions_Dropdown_1().getShadowRoot()
				.findElement(By.cssSelector("#actions")).getShadowRoot()
				.findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement AddImage_dropdownValue() {
		return MoreActions_Dropdown_1().getShadowRoot()
				.findElement(By.cssSelector("#pebbleActionDropdown")).getShadowRoot()
				.findElement(By.cssSelector("#actionItem"));
	}

	public WebElement common_elementon_searchImage() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#contextDialog")).getShadowRoot()
				.findElement(By.cssSelector("#rockWizardManage"));
	}

	public WebElement Search_Images_input() {
		return common_elementon_searchImage().getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#searchBar")).getShadowRoot().findElement(By.cssSelector("#input"));
	}

//	public WebElement Totalsearch_Result() {
//		return common_elementon_searchImage().getShadowRoot()
//				.findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']")).getShadowRoot()
//				.findElement(By.cssSelector("#relatedEntitySearchGrid")).getShadowRoot()
//				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
//				.findElement(By.cssSelector("#gridHeader > div.grid-actions.row > span.text-ellipsis.m-r-5.m-l-5.page-range"));
//	}

	public WebElement First_Image_checkbox() {
		return common_elementon_searchImage().getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#relatedEntitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#gridTileView")).getShadowRoot()
				.findElement(By.cssSelector("#gridItem1 > div > div.photocontent-header > pebble-checkbox")).getShadowRoot()
				.findElement(By.cssSelector("#checkboxContainer"));
	}

	public WebElement Save_btn_Add_Image() {
		return common_elementon_searchImage().getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#next")).getShadowRoot().findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement txt_Images_Selected() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-relationship-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-relationship-container > rock-relationship-manage")).getShadowRoot()
				.findElement(By.cssSelector("#entityRelationshipSearchResult_hasimages")).getShadowRoot()
				.findElement(By.cssSelector("div > div.base-grid-structure-child-2 > rock-relationship-grid")).getShadowRoot()
				.findElement(By.cssSelector("#bedrock_grid_hasimages")).getShadowRoot()
				.findElement(By.cssSelector("#gridHeader > div > span.text-ellipsis.m-r-5.m-l-5.page-range"));
	}

//	public WebElement Close_2d_lineDrawingtab() {
//		return common_element().getShadowRoot()
//				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
//				.findElement(By.cssSelector("#tab-review2dlinedrawingbc_businessCondition > div > div.tab-title > span.dynamic-close"));
//	}

	public WebElement Close_DAM_review_Primary_tab() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot().findElement(By
				.cssSelector("#tab-reviewprimaryimagebc_businessCondition > div > div.tab-title > span.dynamic-close"));
	}

//	public WebElement SummaryTab() {
//		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
//				.findElement(By.cssSelector("#tab-summary > div > div.tab-title > span"));
//	}

	public WebElement Summarythingsneedtofix_grid() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#accordion\\ 0 > div"));
	}

	public WebElement common_ele_2dlinedrawingDropdown() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#input")).getShadowRoot()
				.findElement(By.cssSelector("bedrock-lov"));
	}

	public WebElement primary_Image_Required_dropdown_obj() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElements(By.cssSelector("[id^='rs']")).get(2).getShadowRoot()
				.findElement(By.cssSelector(".attribute-edit > #input")).getShadowRoot()
				.findElement(By.cssSelector(".attribute-control")).getShadowRoot()
				.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				.findElement(By.cssSelector(".d-flex"));
	}

	public WebElement ImageRequired_Yes() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElements(By.cssSelector("[id^='rs']")).get(2).getShadowRoot()
				.findElement(By.cssSelector("#input")).getShadowRoot().findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				.findElement(By.cssSelector("#lov")).getShadowRoot()
				.findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector("#lit-grid")).findElement(By.cssSelector("[ref='eRootWrapper']"))
				.findElement(By.cssSelector("[ref='gridBody']")).findElement(By.cssSelector("[ref='eBodyViewport']"))
				.findElement(By.cssSelector("[ref='centerContainer']"))
				.findElement(By.cssSelector("[ref='eContainer']")).findElement(By.cssSelector("[row-index='1']"));
	}

//	public WebElement ImageRequired_No() {
//		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
//				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
//				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
//				.findElements(By.cssSelector("[id^='rs']")).get(2).getShadowRoot().findElement(By.cssSelector("#input"))
//				.getShadowRoot().findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
//				.findElement(By.cssSelector("#lov")).getShadowRoot()
//				.findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
//				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot()
//				.findElement(By.cssSelector("#lit-grid")).findElement(By.cssSelector("[ref='eRootWrapper']"))
//				.findElement(By.cssSelector("[ref='gridBody']")).findElement(By.cssSelector("[ref='eBodyViewport']"))
//				.findElement(By.cssSelector("[ref='centerContainer']"))
//				.findElement(By.cssSelector("[ref='eContainer']")).findElement(By.cssSelector("[row-index='0']"));
//	}

	public WebElement ApprovePrimaryImagedropdown_commonobj() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElements(By.cssSelector("[id^='rs']")).get(3);
	}

	public WebElement ApprovePrimaryImagedropdown_obj() {
		return ApprovePrimaryImagedropdown_commonobj().getShadowRoot()
				.findElement(By.cssSelector("#input")).getShadowRoot()
				.findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				.findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
	}

	public WebElement Approve_Primary_Image_dropdownvalue() {
		return ApprovePrimaryImagedropdown_commonobj().getShadowRoot()
				.findElement(By.cssSelector("#input")).getShadowRoot()
				.findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				.findElement(By.cssSelector("#lov")).getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot()
				.findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item"));
	}

	public WebElement AddPrimaryImage_Save_btn() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#next")).getShadowRoot().findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement Back_btn() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#back")).getShadowRoot().findElement(By.cssSelector("#simpleButton"));
	}

	public WebElement SellableProductStatus() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter")).getShadowRoot()
				.findElement(By.cssSelector("#attributeModelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector("#modelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item"))
				.getShadowRoot().findElement(By.cssSelector("div > div > div > span"));
	}

	public WebElement OnHoldSystem_dropdownvalue() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter")).getShadowRoot()
				.findElement(By.cssSelector("#rockEntityLov")).getShadowRoot()
				.findElement(By.cssSelector("#entityLov")).getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div:nth-child(6) > div > pebble-lov-item"))
				.getShadowRoot().findElement(By.cssSelector("div > div > div > span.has-only-option"));
	}

	public WebElement Status_InProgress_dropdownvalue() {
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
				.findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div:nth-child(3) > div > pebble-lov-item"))
				.getShadowRoot().findElement(By.cssSelector("div > div > div > span.has-only-option"));
	}
	public WebElement Status_Approved_dropdownvalue() {
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
				.findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div:nth-child(2) > div > pebble-lov-item"))
				.getShadowRoot().findElement(By.cssSelector("div > div > div > span.has-only-option"));
	}

	public WebElement Status_Apply_btn() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter")).getShadowRoot()
				.findElement(By.cssSelector("#rockEntityLov")).getShadowRoot()
				.findElement(By.cssSelector("#entityLov")).getShadowRoot()
				.findElement(By.cssSelector("#confirmButton")).getShadowRoot()
				.findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement Use_Case_Attributes_selection() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("#attributes-Use\\ Case"));
	}

	public WebElement Pending_Use_Case_Approval_Commentinputbox() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
				.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#stepComments-pendingusecaseapprovalbsapie")).getShadowRoot()
				.findElement(By.cssSelector("#textarea")).getShadowRoot()
				.findElement(By.cssSelector("#input-1")).getShadowRoot()
				.findElement(By.cssSelector("#textarea"));
	}

	public WebElement Pending_Use_Case_Approval_Approve_btn() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
				.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#action-button-approve")).getShadowRoot()
				.findElement(By.cssSelector("#simpleButton"));
	}
	
	/****************** 
	 * BSAPIEUsecaseSalesOrgRegions_Override drop down element 
	 * *************** */
	public WebElement ParentEle() {
		return  driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']"));
	}

	public WebElement BSAPIEUsecaseSalesOrgRegions_Override_dropdown() {
		return ParentEle()
				.getShadowRoot()
			    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
			    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
			    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
	}
	
	public WebElement BSAPIEUsecaseSalesOrgRegions_Override_Search_Input() {
		return ParentEle()
				.getShadowRoot()
			    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
			    .findElement(By.cssSelector("#lov")).getShadowRoot()
			    .findElement(By.cssSelector("#input"));
	}
	
	public WebElement Total_Checkboxes() {
		return ParentEle()
				.getShadowRoot()
			    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
			    .findElement(By.cssSelector("#lov")).getShadowRoot()
			    .findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).getShadowRoot()
			    .findElement(By.cssSelector("#grid")).getShadowRoot()
			    .findElement(By.cssSelector("#lit-grid"))
			    .findElement(By.cssSelector("[ref='gridBody']"));
	}
	
	public WebElement Save_btn_BSA_PIE_Transaction() {
		return ParentEle().getShadowRoot()
			    .findElement(By.cssSelector("#next")).getShadowRoot()
			    .findElement(By.cssSelector("#buttonTextBox"));

	}
	/****************** BSAPIEUsecaseSalesOrgRegions_Override drop down element *************** */
	public WebElement summarybcs() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector(".tofix-data-container > pebble-accordion"))
				.findElement(By.cssSelector("[slot='accordion-content']"));
	}
	
	public WebElement ClassifiedImages_Grid() {
		return common_element().getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-relationship-split-screen-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#undefined-relationship-container > rock-relationship-manage")).getShadowRoot()
			    .findElement(By.cssSelector("#entityRelationshipSearchResult_hasimages")).getShadowRoot()
			    .findElement(By.cssSelector("div > div.base-grid-structure-child-2 > rock-relationship-grid")).getShadowRoot()
			    .findElement(By.cssSelector("#bedrock_grid_hasimages")).getShadowRoot()
			    .findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
			    .findElement(By.cssSelector("#grid")).getShadowRoot()
			    .findElement(By.cssSelector(
			        "#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div"
			    ));
	}
	
	public List<WebElement> ImageInfo() {
		 return common_element().getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-relationship-split-screen-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#undefined-relationship-container > rock-relationship-manage")).getShadowRoot()
			    .findElement(By.cssSelector("#entityRelationshipSearchResult_hasimages")).getShadowRoot()
			    .findElement(By.cssSelector("div > div.base-grid-structure-child-2 > rock-relationship-grid")).getShadowRoot()
			    .findElement(By.cssSelector("#bedrock_grid_hasimages")).getShadowRoot()
			    .findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
			    .findElement(By.cssSelector("#grid")).getShadowRoot()
			    .findElement(By.cssSelector("#grid-row-status0")).getShadowRoot()
			    .findElements(By.cssSelector("#error-circle-grid-row-status0"));
	}
	
	public WebElement getLastTabCloseButton() {
        SearchContext tabsContainer = driver.findElement(By.cssSelector("#app")).getShadowRoot()
            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
            .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
            .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
            .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
            .findElement(By.cssSelector("#rockTabs")).getShadowRoot();

        List<WebElement> closeButtons = tabsContainer.findElements(By.cssSelector("div.tab-title span.dynamic-close"));
        return closeButtons.get(closeButtons.size() - 1);
	}
	

	/*******************************
		Phase 2 DAM objects 
	*******************************/
	public WebElement Digital_Asset_Quick_Search() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#navMenu")).getShadowRoot()
			    .findElement(By.cssSelector("#pageMenuIcon_4"));
	}
	
	public List<WebElement> getGridItems() {
	    return driver.findElement(By.cssSelector("#app")).getShadowRoot()
	            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='currentApp_search-digitalasset_rs']")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
	            .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
	            .findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
	            .findElement(By.cssSelector("#entityGrid")).getShadowRoot()
	            .findElement(By.cssSelector("#gridTileView")).getShadowRoot()
	            .findElements(By.cssSelector("[id^='gridItem'] > div > div.text > div.title.block-text > a"));
	}

	public List<String> getItemNames() {
	    List<String> names = new ArrayList<>();

	    for (WebElement item : getGridItems()) {
	        names.add(item.getText().trim());
	    }
	    return names;
	}
	
	public WebElement ImageAttributes() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']"));
	}

	/**************************************
	 * Function to search for Digital asset values 
	*************************************/
	public String getAssetAttributeValueBySearchLabel(SummaryPage summaryPage, String attributeSearchLabel, ExtentTest test) {
    String value = "";

    try {
        WebElement sf = summaryPage.SearchInputfield();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2)) .until(ExpectedConditions.elementToBeClickable(sf));
        } catch (Exception ignored) {
            summaryPage.SearchIcon().click();
            Thread.sleep(1000);
            sf = summaryPage.SearchInputfield();
        }

        sf.click();
        sf.clear();
        if (!sf.getAttribute("value").isEmpty()) {
            sf.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        }
        sf.sendKeys(attributeSearchLabel, Keys.ENTER);
        Thread.sleep(2000);

        // Asset Type / Asset Category
        if (value.isEmpty() && (
                attributeSearchLabel.equalsIgnoreCase("Asset Type")
                || attributeSearchLabel.equalsIgnoreCase("Asset Category"))) {
            try {
                value = ImageAttributes().getShadowRoot()
                        .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
                        .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
                        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
                        .findElement(By.cssSelector("#input")).getShadowRoot()
                        .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
                        .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
                        .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"))
                        .getText().trim();
            } catch (Exception e) {
            }
        }
        // Asset Alternate Text
        if (value.isEmpty() && attributeSearchLabel.equalsIgnoreCase("Asset Alternate Text")) {
            try {
                value = ImageAttributes().getShadowRoot()
                        .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
                        .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
                        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
                        .findElement(By.cssSelector("div > div > #input")).getShadowRoot()
                        .findElement(By.cssSelector(".attribute-control"))
                        .getAttribute("value").trim();
            } catch (Exception e) {
            }
        }
        // Image Height / Image Width / X Resolution / Y Resolution
        if (value.isEmpty() && (
                attributeSearchLabel.equalsIgnoreCase("Image Height")
                || attributeSearchLabel.equalsIgnoreCase("Image Width")
                || attributeSearchLabel.equalsIgnoreCase("X Resolution")
                || attributeSearchLabel.equalsIgnoreCase("Y Resolution"))) {
            try {
                value = ImageAttributes().getShadowRoot()
                        .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
                        .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
                        .findElements(By.cssSelector("[id^='rs']")).get(1).getShadowRoot()
                        .findElement(By.cssSelector("div > div > div.attribute-view.list > div > span"))
                        .getText().trim();
            } catch (Exception e) {
            }
        }
        // Original File Name Property
        if (value.isEmpty() && attributeSearchLabel.equalsIgnoreCase("Original File Name Property")) {
            try {
                value = ImageAttributes().getShadowRoot()
                        .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
                        .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
                        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
                        .findElement(By.cssSelector("div > div > .attribute-view-value"))
                        .getText().trim();
            } catch (Exception e) {
            }
        }

    } catch (Exception e) {
    }
    return value;
}

	public WebElement Avaialbe_BusinessCondition() {
		return common_element().getShadowRoot()
		        .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='rock-entity-summary-component']")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
		        .findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='rock-entity-tofix-component']")).getShadowRoot()
		        .findElement(By.cssSelector("#accordion\\ 0 > div > div > div > div.entity-content.true > div"));
	}
	
	/***************************************************************************
	 * Page Object representing the Renditions attribute grid  (Asset > Attributes tab > Renditions nested grid) and check for the rows appearing
	 ************************************************************************/
	public WebElement getRowContainer() {
        return driver.findElement(By.cssSelector("#app")).getShadowRoot()
                .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
                .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
                .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
                .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
                .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
                .findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
                .findElement(By.cssSelector("[id^='undefined-attribute-container'] > rock-attribute-manage")).getShadowRoot()
                .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
                .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
                .findElement(By.cssSelector("div > div > rock-nested-attribute-grid")).getShadowRoot()
                .findElement(By.cssSelector("#renditions-attributesGrid")).getShadowRoot()
                .findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
                .findElement(By.cssSelector("#grid")).getShadowRoot()
                .findElement(By.cssSelector(
                        "#lit-grid > div > div.ag-root-wrapper-body.ag-layout-auto-height.ag-focus-managed " +
                        "> div.ag-root.ag-unselectable.ag-layout-auto-height " +
                        "> div.ag-body-viewport.ag-layout-auto-height.ag-row-no-animation " +
                        "> div.ag-center-cols-clipper > div"));
    }
	/***************************************************************************
	 * @return list of WebElements, each representing one grid row
	 **************************************************************************/
		    public List<WebElement> getRednetionrowsRows() {
		        return getRowContainer().findElements(By.cssSelector("div.ag-row"));
		    }
		    public WebElement getRowByIndex(int rowIndex) {
		        return getRowContainer().findElement(By.cssSelector("div[row-index='" + rowIndex + "']"));
		    }

		    /*****************************************************************
		     * Extracts the Rendition ID/title value from a given row.
		     * Reads the "title" attribute of the cell mapped to col-id="renditionid"
		     * (e.g. "white_background", "transparent_background").
		     * @param row the row WebElement (obtained via getRowByIndex or getRows)
		     * @return the rendition title/id text for that row
		     ****************************************************************/
		    public String getRenditionTitleFromRow(WebElement row) {
		        return row.findElement(By.cssSelector("div[col-id='renditionid']")).getAttribute("title");
		    }
	
		    public WebElement Imagerequired_Auto_HasImagesDropdown() {
		    	return driver.findElement(By.cssSelector("#app")).getShadowRoot()
		    		    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		    		    .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
		    		    .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
		    		    .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
		    		    .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
		    		    .findElement(By.cssSelector("#search-filter")).getShadowRoot()
		    		    .findElement(By.cssSelector("#filter-text")).getShadowRoot()
		    		    .findElement(By.cssSelector("#pebble__textbox"));
		    }
	    public WebElement ImageRequired_Auto_Dropdown() {
	    	return driver.findElement(By.cssSelector("#app")).getShadowRoot()
	    		    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	    		    .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
	    		    .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
	    		    .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
	    		    .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
	    		    .findElement(By.cssSelector("#search-filter")).getShadowRoot()
	    		    .findElement(By.cssSelector("#filter-text")).getShadowRoot()
	    		    .findElement(By.cssSelector("#pebble__textbox"));
	    }
	    
	    public SearchContext Dropdown_BaseObject() {
	        return driver.findElement(By.cssSelector("#app")).getShadowRoot()
	            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='currentApp_search-thing_rs']")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='app-entity-discovery-component-rs']")).getShadowRoot()
	            .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
	            .findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
	            .findElement(By.cssSelector("#search-filter")).getShadowRoot()
	            .findElement(By.cssSelector("#operators")).getShadowRoot()
	            .findElement(By.cssSelector("div.base-grid-structure > div.base-grid-structure-child-2 > pebble-grid")).getShadowRoot()
	            .findElement(By.cssSelector("#grid")).getShadowRoot();  // ← add this
	    }
	    
	    public WebElement SkipAndContinue_Dialog() {
	    	return common_element().getShadowRoot()
	        .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='rock-wizard-manage-component']")).getShadowRoot()
	        .findElement(By.cssSelector("[id^='rock-attribute-split-screen-component']")).getShadowRoot()
	        .findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
	        .findElement(By.cssSelector("#errorsDialog")).getShadowRoot()
	        .findElement(By.cssSelector("#dialog > div"));
	    }
	    
    public WebElement SkipAndContinue_Dialog_Continue_btn() {
	    return common_element().getShadowRoot()
	    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
	    .findElement(By.cssSelector("[id^='rock-wizard-manage-component']")).getShadowRoot()
	    .findElement(By.cssSelector("[id^='rock-attribute-split-screen-component']")).getShadowRoot()
	    .findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
	    .findElement(By.cssSelector("#skip")).getShadowRoot()
	    .findElement(By.cssSelector("#simpleButton"));
	    }
    
    public WebElement Assets_dropdownWrapper() {
		return common_element().getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("#tab-assets")).getShadowRoot()
				.findElement(By.cssSelector("#dropdown-wrapper"));
	}
    public WebElement Assets_dropdown_Has_Images_Option() {
	    return common_element().getShadowRoot()
		    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
		    .findElement(By.cssSelector("#assets-hasimagesowned"));
	    }
    
    public WebElement AssetType_SearchImage_Window() {
    	return common_elementon_searchImage().getShadowRoot()
        .findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']")).getShadowRoot()
        .findElement(By.cssSelector("#searchFilter")).getShadowRoot()
        .findElement(By.cssSelector("#search-filter")).getShadowRoot()
        .findElement(By.cssSelector("#filterButton")).getShadowRoot()
        .findElement(By.cssSelector("#buttonTextBox"));
    }
    public WebElement AssetType_SearchImage_Window_Inputbox() {   
		return common_elementon_searchImage().getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#searchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter")).getShadowRoot()
				.findElement(By.cssSelector("#refineMoreSearchbox")).getShadowRoot()
				.findElement(By.cssSelector("#input"));
	}
    public WebElement AssetType_SearchList_element() {
    	return common_elementon_searchImage().getShadowRoot()
    		    .findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']")).getShadowRoot()
    		    .findElement(By.cssSelector("#searchFilter")).getShadowRoot()
    		    .findElement(By.cssSelector("#search-filter")).getShadowRoot()
    		    .findElement(By.cssSelector("#attributeModelLov_digitalAsset")).getShadowRoot()
    		    .findElement(By.cssSelector("#modelLov_digitalAsset")).getShadowRoot()
    		    .findElement(By.cssSelector( "div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")) .getShadowRoot()
    		    .findElement(By.cssSelector("#grid")).getShadowRoot()
    		    .findElement(By.cssSelector( "#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item"))
    		    .getShadowRoot()
    		    .findElement(By.cssSelector("div"));
    }
    
    public WebElement Similar_DA_Element() {
    	 return driver.findElement(By.cssSelector("#app")).getShadowRoot()
        .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
        .findElement(By.cssSelector("#contextDialog")).getShadowRoot()
        .findElement(By.cssSelector("#rockWizardManage")).getShadowRoot()
        .findElement(By.cssSelector("[id^='rock-relationship-add-component-rs']"));
    }
    public SearchContext getGridShadowRoot() {
        return Similar_DA_Element().getShadowRoot()
                .findElement(By.cssSelector("#searchFilter")).getShadowRoot()
                .findElement(By.cssSelector("#search-filter")).getShadowRoot()
                .findElement(By.cssSelector("#rockEntityLov")).getShadowRoot()
                .findElement(By.cssSelector("#entityLov")).getShadowRoot()
                .findElement(By.cssSelector(
                        "div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
                .getShadowRoot()
                .findElement(By.cssSelector("#grid"))
                .getShadowRoot();
    }
    public WebElement Primary_Image_Confirm_button() {
    	return Similar_DA_Element().getShadowRoot()
        .findElement(By.cssSelector("#searchFilter")).getShadowRoot()
        .findElement(By.cssSelector("#search-filter")).getShadowRoot()
        .findElement(By.cssSelector("#rockEntityLov")).getShadowRoot()
        .findElement(By.cssSelector("#entityLov")).getShadowRoot()
        .findElement(By.cssSelector("#confirmButton")).getShadowRoot()
        .findElement(By.cssSelector("#buttonTextBox"));
    }
    
	public WebElement DA_MoreActions_dropdown() {
		return Similar_DA_Element().getShadowRoot()
			    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-relationship-split-screen-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#undefined-relationship-container > rock-relationship-manage")).getShadowRoot()
			    .findElement(By.cssSelector("#entityRelationshipSearchResult_hasimages")).getShadowRoot()
			    .findElement(By.cssSelector("div > div.base-grid-structure-child-2 > rock-relationship-grid")).getShadowRoot()
			    .findElement(By.cssSelector("#relationship_actions")).getShadowRoot()
			    .findElement(By.cssSelector("#actions")).getShadowRoot()
			    .findElement(By.cssSelector("#simpleButton"));
	}
	
	public  List<WebElement> DA_AddImagedropdownvalue() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-relationship-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-relationship-container > rock-relationship-manage")).getShadowRoot()
				.findElement(By.cssSelector("#entityRelationshipSearchResult_hasimages")).getShadowRoot()
				.findElement(By.cssSelector("div > div.base-grid-structure-child-2 > rock-relationship-grid")).getShadowRoot()
				.findElement(By.cssSelector("#relationship_actions")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleActionDropdown")).getShadowRoot()
				.findElements(By.cssSelector("#actionItem"));
	}
	
	public List<WebElement> getImageNames() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    return wait.until(driver1 -> {
	        List<WebElement> elements = Similar_DA_Element().getShadowRoot()
	                .findElement(By.cssSelector("#relatedEntitySearchGrid")).getShadowRoot()
	                .findElement(By.cssSelector("#entityGrid")).getShadowRoot()
	                .findElement(By.cssSelector("#gridTileView")).getShadowRoot()
	                .findElements(By.cssSelector("[id^='gridItem'] > div > div.text > div.title.block-text"));

	        return elements.isEmpty() ? null : elements;
	    });
	}
	public List<WebElement> getImageCheckboxes() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    return wait.until(driver1 -> {
	        List<WebElement> elements = Similar_DA_Element().getShadowRoot()
	                .findElement(By.cssSelector("#relatedEntitySearchGrid")).getShadowRoot()
	                .findElement(By.cssSelector("#entityGrid")).getShadowRoot()
	                .findElement(By.cssSelector("#gridTileView")).getShadowRoot()
	                .findElements(By.cssSelector("[id^='gridItem'] > div > div.photocontent-header > pebble-checkbox"));
	        return elements.isEmpty() ? null : elements;
	    });
	}
	public WebElement Save_DA_Image_btn() {
		return Similar_DA_Element().getShadowRoot()
			    .findElement(By.cssSelector("#next")).getShadowRoot()
			    .findElement(By.cssSelector("#buttonTextBox"));
	}
	
	public WebElement Summary_Tab() {
		return common_element().getShadowRoot()
	    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
	    .findElement(By.cssSelector("#tab-summary"));
	}
	public WebElement SameDropdownObject() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
		.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
		.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
		.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
		.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list"));
	}
	public WebElement DA_error_Message() {
		return common_element().getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
			    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#error-display > span.error"));
	}
	
	public WebElement Approve_Representative_Image_Primary_dropdown_obj() {
		return SameDropdownObject().getShadowRoot()
				.findElements(By.cssSelector("[id^='rs']")).get(3).getShadowRoot()
				.findElement(By.cssSelector(".attribute-edit > #input")).getShadowRoot()
				.findElement(By.cssSelector(".attribute-control")).getShadowRoot()
				.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				.findElement(By.cssSelector(".d-flex"));
	}
}
