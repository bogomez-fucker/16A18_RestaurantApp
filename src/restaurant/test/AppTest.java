package restaurant.test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import restaurant.model.Dish;
import restaurant.model.User;
import restaurant.model.FilesDAO;
import restaurant.view.Administrator;
import restaurant.view.Client;

import javax.swing.*;

/**
 *
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
