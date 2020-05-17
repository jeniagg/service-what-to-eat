package what.to.eat.dtos;

public enum CookingMethodEnum {
    BAKING, FRYING, ROASTING, GRILLING, STEAMING, POACHING, SIMMERING, BROILING, BLANCHING, BRAISING, STEWING;

    CookingMethodEnum() {}

    /**
     * Checks if the passed cooking method is a valid one
     * @param name - name of the cooking method to be checked
     * @return true if the cooking method name is a valid one
     */
    public static boolean isValidCookingMethod(String name) {
        if (name != null) {
            try {
                CookingMethodEnum.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return false;
            }
            return true;
        }
        return true;
    }
}
