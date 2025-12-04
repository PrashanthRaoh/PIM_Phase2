package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CBT_Page {
	
	public WebDriver driver;
//	private WebDriverWait wait;
	
	public CBT_Page(WebDriver driver) {
		this.driver = driver;
//		this.wait = new WebDriverWait(driver, Duration.ofSeconds(90));
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
	

}
