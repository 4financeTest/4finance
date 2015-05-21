package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Wrapper for currency item
 */
public class CurrencyWrapper {

    private final BaseFunctions baseFunctions;
    private final WebElement rootElement;
    private static final By CURRENCY_NAME = By.xpath(".//label");

    public CurrencyWrapper(BaseFunctions baseFunctions, WebElement element) {
        rootElement = element;
        this.baseFunctions = baseFunctions;
    }

    public String getCurrencyName() {
        return rootElement.findElement(CURRENCY_NAME).getText();
    }

    public void selectCurrency() {
        rootElement.findElement(CURRENCY_NAME).click();
    }
}
