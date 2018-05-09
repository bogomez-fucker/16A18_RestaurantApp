package restaurant.util;

/**
 * Class with useful methods.
 * @author User
 */
public class Utils {

    /**
     * Cut excess digits after decimal point.
     * @param decimal decimal in string representation
     * @param leaveAfterPoint number of digits after comma that will stay
     * @return fixed decimal in string representation
     */
    public static String digitsAfterPoint(String decimal, int leaveAfterPoint) {
        if (!decimal.contains("."))
            return decimal;

        int pointIndex = decimal.indexOf(".");
        int digitsAfterPoint = decimal.length() - 1 - pointIndex;

        if (leaveAfterPoint >= digitsAfterPoint)
            return decimal;

        return decimal.substring(0, pointIndex + 1 + leaveAfterPoint);
    }
}
