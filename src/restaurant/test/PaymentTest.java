package restaurant.test;

import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.User;
import restaurant.view.Payment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Test of Payment GUI.
 * @author User
 */
public class PaymentTest {

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            List<Dish> dishes = new ArrayList<>();

            dishes.add(new Dish(111, "Fish in dish", "desc", 15.08, 99.99, "second"));
            new Payment(new User(0, "Test User", "abcd", "user", 100.0), new Order(12345, dishes, true)).setVisible(true);
        });
    }
}
