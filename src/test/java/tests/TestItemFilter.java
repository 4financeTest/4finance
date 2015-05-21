package tests;

import core.*;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

/**
 * This test selects men's shoes subcategory, chose Brand and price range. Check whether filter working properly
 */
public class TestItemFilter {

    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final String WEB_SITE_URL = "sportsdirect.com";
    private static final String SUBCATEGORY = "Shoes";
    private static final String BRAND = "Karrimor";
    private static final int PRICE_FROM = 30;
    private static final int PRICE_TO = 60;
    private static final String CURRENCY = "EUR";
    private static final Logger LOGGER = Logger.getLogger(TestItemFilter.class);

    @Test
    public void checkItemFilter() {
        LOGGER.info("This test selects men's shoes subcategory, chose Brand and price range. Check whether filter working properly");
        baseFunctions.goToUrl(WEB_SITE_URL);
        HomePage homePage = new HomePage(baseFunctions);

        LOGGER.info("User selects catalog category MENS");
        SubCategoryPage subCategoryPage = homePage.openCatalogCategory(MainCategories.MENS.getCategory());

        LOGGER.info("User selects subcategory: " + SUBCATEGORY);
        CatalogPage catalogPage = subCategoryPage.openCatalogByCategory(SUBCATEGORY);

        LOGGER.info("User specifies currency");
        catalogPage.selectCurrency(CURRENCY);

        LOGGER.info("User is selecting brand: " + BRAND);
        catalogPage.selectFilter(BRAND);

        LOGGER.info("User selects price range from " + PRICE_FROM + " to " + PRICE_TO);
        catalogPage.selectPriceRange(PRICE_FROM, PRICE_TO);

        LOGGER.info("User is checking prices");
        catalogPage.checkPriceRange((double)PRICE_FROM,(double)PRICE_TO);

        LOGGER.info("All tests passed successfully!");
    }

    /**
     * Stopping webDriver
     */
    @After
    public void closeDriver() {
        baseFunctions.stopDriver();
    }
}
