package feature.itemFilter;

import core.BaseFunctions;
import core.CatalogPage;
import core.HomePage;
import core.SubCategoryPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ItemFilterSteps {

    BaseFunctions baseFunctions = new BaseFunctions();
    HomePage homePage;
    SubCategoryPage subCategoryPage;
    CatalogPage catalogPage;

    @Given("I am on the '(.+)' page")
    public void openHomePage(final String url) {
        baseFunctions.goToUrl(url);
        homePage = new HomePage(baseFunctions);
    }

    @When("I select '(.+)' category from menu")
    public void selectCategory(final String menu) {
        subCategoryPage = homePage.openCatalogCategory(menu);
    }

    @When("I select '(.+)' subcategory")
    public void selectSubcategory(final String subcategory) {
        catalogPage = subCategoryPage.openCatalogByCategory(subcategory);
    }

    @When("I select '(.+)' as a currency")
    public void selectCurrency(final String currency) {
        catalogPage.selectCurrency(currency);
    }

    @When("I select '(.+)' brand")
    public void selectBrand(final String brand) {
        catalogPage.selectFilter(brand);
    }

    @When("I select price range from '(.+)' to '(.+)'")
    public void selectPriceRange(final int from, final int to) {
        catalogPage.selectPriceRange(from, to);
    }

    @Then("Sales items appears in a price range from '(.+)' to '(.+)'")
    public void checkPriceRange(final double from, final double to) {
        catalogPage.checkPriceRange(from, to);
    }

    @Then("I close the browser")
    public void closeBrowser() {
        baseFunctions.stopDriver();
    }
}
