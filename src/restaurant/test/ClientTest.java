package restaurant.test;

import restaurant.model.User;
import restaurant.util.Utils;
import restaurant.view.Client;

import java.awt.*;

/**
 * Test of Client GUI.
 * @author User
 */
public class ClientTest {

    public static void main(String[] args) {
        Utils.setNimbusLookAndFeel();

        /* Create and display the frame */
        EventQueue.invokeLater(() ->
                new Client(new User(0, "Test User", "abcd", "user", 100.0))
                        .setVisible(true));
    }
}
