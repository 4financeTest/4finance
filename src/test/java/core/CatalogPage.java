package core;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Page for Sportsdirect item catalog
 */
public class CatalogPage {

    BaseFunctions baseFunctions;
    private static final By FIRST_SLIDER = By.xpath("//*[contains(@class, 'ui-slider-handle')][1]");
    private static final By SECOND_SLIDER = By.xpath("//*[contains(@class, 'ui-slider-handle')][2]");
    private static final By CATALOG_ITEM = By.xpath("//*[contains(@class, 'AspNet-DataList-Item')]");
    private static final By FILTER_ITEM = By.xpath("//*[@class = 'SelectableFilter']");
    private static final By CURRENCY_SELECTOR = By.id("currencyLanguageSelector");
    private static final By CURRENCY_ITEM = By.xpath("//*[@class = 'currencySelector']//li");
    private static final By AMOUNT = By.id("amount");
    private static final Logger LOGGER = Logger.getLogger(CatalogPage.class);

    public CatalogPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Catalog page is loaded");
    }

    /**
     * Method collects all currency items
     *
     * @return - List of currencies
     */
    private List<CurrencyWrapper> getAllCurrencies() {
        baseFunctions.click(CURRENCY_SELECTOR);
        List<WebElement> items = baseFunctions.findElements(CURRENCY_ITEM);
        List<CurrencyWrapper> result = new ArrayList<CurrencyWrapper>();
        Iterables.addAll(result, Iterables.transform(items, new Function<WebElement, CurrencyWrapper>() {
            public CurrencyWrapper apply(WebElement element) {
                return new CurrencyWrapper(baseFunctions, element);
            }
        }));
        return result;
    }

    /**
     * Method returns currency item by name
     *
     * @param name of filter
     *
     * @return - selected currency wrapper
     */
    public CurrencyWrapper getCurrencyByName(final String name) {
        Optional<CurrencyWrapper> wrapper = Iterables.tryFind(getAllCurrencies(), new Predicate<CurrencyWrapper>() {
            public boolean apply(CurrencyWrapper currencyWrapper) {
                return currencyWrapper.getCurrencyName().contains(name);
            }
        });
        return wrapper.get();
    }

    /**
     * Method collects all filter items
     *
     * @return - List of filter wrappers
     */
    private List<FilterWrapper> getAllFilterItems() {
        List<WebElement> items = baseFunctions.findElements(FILTER_ITEM);
        List<FilterWrapper> result = new ArrayList<FilterWrapper>();
        Iterables.addAll(result, Iterables.transform(items, new Function<WebElement, FilterWrapper>() {
            public FilterWrapper apply(WebElement webElement) {
                return new FilterWrapper(baseFunctions, webElement);
            }
        }));
        return result;
    }

    /**
     * Method returns filter item by name
     *
     * @param name of filter
     *
     * @return - selected filter wrapper
     */
    public FilterWrapper getFilterByName(final String name) {
        Optional<FilterWrapper> wrapper = Iterables.tryFind(getAllFilterItems(), new Predicate<FilterWrapper>() {
            public boolean apply(FilterWrapper filterWrapper) {
                return filterWrapper.getFilterName().equals(name);
            }
        });
        return wrapper.get();
    }

    /**
     * Method select filter options
     *
     * @param name filter name
     */
    public void selectFilter(String name) {
        LOGGER.info("User selects filter: " + name);
        FilterWrapper item = getFilterByName(name);
        item.selectFilter();
        baseFunctions.pause(300);
    }

    /**
     * Method is getting all sales item wrappers
     *
     * @return seles item wrapper
     */
    private List<ItemWrapper> getItems() {
        List<WebElement> catalogItem = baseFunctions.findElements(CATALOG_ITEM);
        List<ItemWrapper> result = new ArrayList<ItemWrapper>();
        Iterables.addAll(result, Iterables.transform(catalogItem, new Function<WebElement, ItemWrapper>() {
            public ItemWrapper apply(WebElement webElement) {
                return new ItemWrapper(baseFunctions, webElement);
            }
        }));
        return result;
    }

    /**
     * Method moves price slider to select price range in item filter
     * 1.84 - is a pixels for 1% of maximum rate
     *
     * @param from price From
     * @param to price to
     */
    public void selectPriceRange(int from, int to) {
        LOGGER.info("Counting max price");
        String rangeString = baseFunctions.getElement(AMOUNT).getText();
        rangeString = rangeString.replaceAll("[^0-9]", "");
        int maxPrice = Integer.parseInt(rangeString.substring(1));
        LOGGER.info("Max price is " + maxPrice);

        LOGGER.info("Counting selector positions");
        double fromPosition = from * 100 / maxPrice * 1.84;
        double toPosition = (100 - (to * 100 / maxPrice)) * 1.84;
        toPosition = -toPosition;

        baseFunctions.move(FIRST_SLIDER, (int)fromPosition);
        baseFunctions.pause(2000);
        baseFunctions.move(SECOND_SLIDER, (int)toPosition);
        baseFunctions.pause(300);
    }

    /**
     * Method select page currency
     *
     * @param currency currency to be selected
     */
    public void selectCurrency(String currency) {
        LOGGER.info("User is selecting currency: " + currency);
        CurrencyWrapper wrapper = getCurrencyByName(currency);
        wrapper.selectCurrency();
        baseFunctions.pause(1000);
    }

    /**
     * Method checks if pricee displayed on page fits selected ranges
     *
     * @param from prices from
     * @param to prices to
     */
    public void checkPriceRange(double from, double to) {
        LOGGER.info("Checking prices");
        for (int i = 0; i < getItems().size(); i++) {
            ItemWrapper item = getItems().get(i);
            double itemPrice = item.getItemPrice();
            Assert.assertTrue("Price does not match the range", itemPrice >= from && itemPrice <= to);
        }
    }
}