package common_functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CBT_Utils {
	

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
            } catch (Exception ignored) 
            {}
        }

        try {
            WebElement active = host.findElement(By.cssSelector("pebble-step.inprogress, pebble-step.iron-selected"));
            if (active != null) {
                activeStep = active.getShadowRoot().findElement(By.cssSelector("#textLabel")).getText();
            }
        } catch (Exception ignored) {}

        try {
            List<WebElement> completed = host.findElements(By.cssSelector("pebble-step.completed"));
            for (WebElement c : completed) {
                try {
                    String txt = c.getShadowRoot().findElement(By.cssSelector("#textLabel")).getText();
                    completedSteps.add(txt);
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}

    } catch (Exception e) {
        System.out.println("Workflow panel not found");
    }

    data.put("totalSteps", totalSteps);
    data.put("allSteps", allSteps);
    data.put("activeStep", activeStep);
    data.put("completedSteps", completedSteps);

    return data;
}

}
