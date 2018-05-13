package restaurant.test;

import restaurant.util.Utils;
import restaurant.view.Administrator;

import java.awt.*;

/**
 * Test of Administrator GUI.
 * @author User
 */
public class AdministratorTest {

    public static void main(String args[]) {
        Utils.setNimbusLookAndFeel();

        /* Create and display the form */
        EventQueue.invokeLater(() ->
                new Administrator().setVisible(true));
    }
}
