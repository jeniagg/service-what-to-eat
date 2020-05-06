package what.to.eat.entities;

public enum CategoryEnum {
    APPETIZER, SALAD, ENTREE, MAIN, DESSERT, BEVERAGE;

    CategoryEnum() {}

    /**
     * Checks if the passed category is a valid one
     * @param name - name of the category to be checked
     * @return true if the categoryName is a valid one
     */
    public static boolean isValidCategory(String name) {
        if (CategoryEnum.valueOf(name.toUpperCase()) != null) {
            return true;
        }
        return false;
    }
}
