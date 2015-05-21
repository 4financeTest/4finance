package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Wrapper for main menu item
 */
public class MainMenuWrapper {

    private final BaseFunctions baseFunctions;
    private final WebElement rootElement;
    private static final By MENU_NAME = By.xpath(".//a");

    public MainMenuWrapper(BaseFunctions baseFunctions, WebElement element) {
        rootElement = element;
        this.baseFunctions = baseFunctions;
    }

    public String getMenuName() {
        return rootElement.findElement(MENU_NAME).getText();
    }

    public void clickMenuItem() {
        rootElement.click();
    }
}
