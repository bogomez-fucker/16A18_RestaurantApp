package restaurant.test;

import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.User;
import restaurant.util.Utils;
import restaurant.view.Payment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Test of Payment GUI.
 * @author User
 */
public class PaymentTest {

    public static void main(String[] args) {
        Utils.setNimbusLookAndFeel();

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            List<Dish> dishes = new ArrayList<>();

            dishes.add(new Dish(111, "Fish in dish", "desc", 15.08, 99.99, "second"));
            new Payment(new User(0, "Test User", "abcd", "user", 100.0), new Order(12345, dishes, true)).setVisible(true);
        });
    }
}
