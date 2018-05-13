package restaurant.test;

import restaurant.model.User;
import restaurant.util.Utils;
import restaurant.view.Administrator;
import restaurant.view.Client;

import java.awt.*;

/**
 * Test of application functionality running Client and Administrator GUIs without authorization.
 * Used temp data.
 * @author User
 */
public class AppTest {

    public static void main(String[] args) {
        Utils.setNimbusLookAndFeel();

        // Create and display Client frame
        EventQueue.invokeLater(() ->
                new Client(new User(0, "Test User", "abcd", "user", 100.0))
                        .setVisible(true));
        // Create and display Administrator frame
        EventQueue.invokeLater(() ->
                new Administrator().setVisible(true));
    }
}
