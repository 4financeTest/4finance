package core;

/**
 * A list of main catalog categories
 */
public enum MainCategories {

    MENS("Mens"), LADIES("Ladies"), KIDS("Kids"), FOOTBAL_SHIRTS("Footbal shirts"), ACCESSORIES("Accessories"), SPORTS("Sports"), BRANDS("Brands"), CLEARANCE("Clearance");

    private String name;

    MainCategories(String name) {
        this.name= name;
    }

    public String getCategory() {
        return this.name;
    }
}
