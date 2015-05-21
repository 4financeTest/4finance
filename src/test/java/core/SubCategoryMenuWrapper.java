package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Subcategory menu item wrapper
 */
public class SubCategoryMenuWrapper {

    private final BaseFunctions baseFunctions;
    private final WebElement rootElement;
    private static final By MENU_NAME = By.xpath(".//a");

    public SubCategoryMenuWrapper(BaseFunctions baseFunctions, WebElement element) {
        rootElement = element;
        this.baseFunctions = baseFunctions;
    }

    public String getSubMenuName() {
        return rootElement.findElement(MENU_NAME).getText();
    }

    public void clickSubMenuItem() {
        rootElement.findElement(MENU_NAME).click();
    }
}
