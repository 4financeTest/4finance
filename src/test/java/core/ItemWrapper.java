package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Wrapper for Catalog Item
 */
public class ItemWrapper {

    private final BaseFunctions baseFunctions;
    private final WebElement rootElement;
    private static final By ITEM_PRICE = By.xpath(".//*[contains(@class, 'CurrencySizeLarge')]");

    public ItemWrapper(BaseFunctions baseFunctions, WebElement element) {
        rootElement = element;
        this.baseFunctions = baseFunctions;
    }

    public double getItemPrice() {
        String priceText = rootElement.findElement(ITEM_PRICE).getText();
        priceText = priceText.replaceAll("[^0-9.,]", "");
        priceText = priceText.replaceAll(",", ".");
        return Double.parseDouble(priceText);
    }
}
