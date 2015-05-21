package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Brand wrapper in filter
 */
public class FilterWrapper {

    private final BaseFunctions baseFunctions;
    private final WebElement rootElement;
    private static final By FILTER_NAME = By.xpath(".//*[@class = 'FilterName']");

    public FilterWrapper(BaseFunctions baseFunctions, WebElement element) {
        rootElement = element;
        this.baseFunctions = baseFunctions;
    }

    public String getFilterName() {
        return rootElement.findElement(FILTER_NAME).getText();
    }

    public void selectFilter() {
        rootElement.findElement(FILTER_NAME).click();
    }
}
