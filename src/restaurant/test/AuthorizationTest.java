package restaurant.test;

import restaurant.util.Utils;
import restaurant.view.Authorization;

import java.awt.*;

/**
 * Test of Authorization GUI.
 * @author User
 */
public class AuthorizationTest {

    public static void main(String args[]) {
        Utils.setNimbusLookAndFeel();

        /* Create and display the GUI */
        EventQueue.invokeLater(() -> new Authorization().setVisible(true));
    }
}
