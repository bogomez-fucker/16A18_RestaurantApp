package restaurant.test;

import restaurant.model.User;
import restaurant.view.Administrator;
import restaurant.view.Client;

import javax.swing.*;
import java.awt.*;

/**
 * Test of application functionality running Client and Administrator GUIs without authorization.
 * Used temp data.
 * @author User
 */
public class AppTest {

    public static void main(String[] args) {
        // Set the Nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and display Client frame
        EventQueue.invokeLater(() ->
                new Client(new User(0, "Test User", "abcd", "user", 100.0))
                        .setVisible(true));
        // Create and display Administrator frame
        EventQueue.invokeLater(() ->
                new Administrator().setVisible(true));
    }
}
