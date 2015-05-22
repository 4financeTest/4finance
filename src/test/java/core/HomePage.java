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
 * Sportsdirect Home Page class
 */
public class HomePage{

    BaseFunctions baseFunctions;
    private static final By SIGNIN_BTN = By.id("dnn_LOGIN_loginLink");
    private static final By ACCOUNT_BTN = By.id("dnn_Sd_account1_lnkMAccount");
    private static final By MENU_ITEM = By.xpath("//*[contains(@class, 'mmHasChild root')]");
    private static final Logger LOGGER = Logger.getLogger(HomePage.class);

    public HomePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Home page is loaded");
    }

    /**
     * Method to open Sign In page
     *
     * @return sign In page
     */
    public SignInPage clickSignInButton() {
        baseFunctions.click(SIGNIN_BTN);
        LOGGER.info("User clicks Sign In button");
        return new SignInPage(baseFunctions);
    }

    /**
     * Method indicates if Account button presents on page
     * this indicates if user is logged in
     *
     * @return true or false
     */
    public boolean isPresentAccountBtn() {
        LOGGER.info("Checking if Account button presents on page");
        baseFunctions.waitForElement(ACCOUNT_BTN, 500);
        return baseFunctions.isPresentElement(ACCOUNT_BTN);
    }

    /**
     * Method collects all main menu items
     *
     * @return - List of menu items wrappers
     */
    private List<MainMenuWrapper> getAllMenuItems() {
        List<WebElement> menuItems = baseFunctions.findElements(MENU_ITEM);
        List<MainMenuWrapper> result = new ArrayList<MainMenuWrapper>();
        Iterables.addAll(result, Iterables.transform(menuItems, new Function<WebElement, MainMenuWrapper>() {
            public MainMenuWrapper apply(WebElement webElement) {
                return new MainMenuWrapper(baseFunctions, webElement);
            }
        }));
        return result;
    }

    /**
     * Method returns menu wrapper by name
     *
     * @param name of menu item
     *
     * @return - selected menu item wrapper
     */
    public MainMenuWrapper getMenuItemByName(final String name) {
        Optional<MainMenuWrapper> wrapper = Iterables.tryFind(getAllMenuItems(), new Predicate<MainMenuWrapper>() {
            public boolean apply(MainMenuWrapper mainMenuWrapper) {
                return mainMenuWrapper.getMenuName().contains(name);
            }
        });
        return wrapper.get();
    }

    /**
     * This method selects category from catalog (main menu)
     *
     * @param name - category name
     * @return category page
     */
    public SubCategoryPage openCatalogCategory(String name) {
        MainMenuWrapper menu = getMenuItemByName(name);
        menu.clickMenuItem();
        LOGGER.info("User clicked on the " + name + " Menu Item");
        return new SubCategoryPage(baseFunctions);
    }
}