package what.to.eat.dtos;

public enum CategoryEnum {
    APPETIZER, SALAD, ENTREE, MAIN, DESSERT, BEVERAGE;

    CategoryEnum() {}

    /**
     * Checks if the passed category is a valid one
     * @param name - name of the category to be checked
     * @return true if the categoryName is a valid one
     */
    public static boolean isValidCategory(String name) {
        if (name != null) {
            try {
                CategoryEnum.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return false;
            }
            return  true;
        }
        return true;
    }
}
