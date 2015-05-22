package core;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Catalog Subcategory page
 */
public class SubCategoryPage {

    BaseFunctions baseFunctions;
    private static final By MENU_SUBCATEGORY = By.xpath("//*[contains(@class, 'hidden-xs col-sm-2')]//li");
    private static final Logger LOGGER = Logger.getLogger(SubCategoryPage.class);

    public SubCategoryPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Subcategory page loaded");
    }

    /**
     * Method collects all subcategories menu items
     *
     * @return - List of menu items wrappers
     */
    private List<SubCategoryMenuWrapper> getAllMenuItems() {
        List<WebElement> menuItems = baseFunctions.findElements(MENU_SUBCATEGORY);
        List<SubCategoryMenuWrapper> result = new ArrayList<SubCategoryMenuWrapper>();
        Iterables.addAll(result, Iterables.transform(menuItems, new Function<WebElement, SubCategoryMenuWrapper>() {
            public SubCategoryMenuWrapper apply(WebElement webElement) {
                return new SubCategoryMenuWrapper(baseFunctions, webElement);
            }
        }));
        return result;
    }

    /**
     * Method returns menu item wrapper by name
     *
     * @param name of menu item
     *
     * @return - selected menu item wrapper
     */
    public SubCategoryMenuWrapper getSubMenuItemByName(final String name) {
        Optional<SubCategoryMenuWrapper> wrapper = Iterables.tryFind(getAllMenuItems(), new Predicate<SubCategoryMenuWrapper>() {
            public boolean apply(SubCategoryMenuWrapper subCategoryMenuWrapper) {
                return subCategoryMenuWrapper.getSubMenuName().equals(name);
            }
        });
        return wrapper.get();
    }

    /**
     * Method open sales item catalog by category
     *
     * @param name category name
     * @return catalog page
     */
    public CatalogPage openCatalogByCategory(String name) {
        SubCategoryMenuWrapper menu = getSubMenuItemByName(name);
        menu.clickSubMenuItem();
        LOGGER.info("User clicked on the " + name + " Menu Item");
        return new CatalogPage(baseFunctions);
    }
}