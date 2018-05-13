package restaurant.util;

import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.view.Administrator;

import javax.swing.*;

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

    /**
     * Calculates amount of specified order.
     * @param order order which amount we will calculate.
     * @return order amount
     */
    public static double getAmount(Order order) {
        return order.getDishes().stream()
                .map(Dish::getPrice)
                .reduce(0.0, (left, right) -> left + right);
    }

    /**
     * Sets the Nimbus look and feel
     */
    public static void setNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Administrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
