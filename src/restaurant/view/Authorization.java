package restaurant.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

import restaurant.controller.LoginButtonController;
import restaurant.model.User;
import restaurant.util.Constants;
import restaurant.util.FileDB;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

/**
 * @author User
 */
public class Authorization extends JFrame {

    private JButton loginButton;
    private JLabel loginHintLabel;
    private JLabel passwordHintLabel;
    private JLabel headerLabel;
    private JPasswordField passwordField;
    private JTextField loginField;

    /**
     * Creates new Authorization GUI
     */
    public Authorization() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the Authorization GUI.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        // init components
        loginHintLabel = new JLabel();
        passwordHintLabel = new JLabel();
        passwordField = new JPasswordField();
        headerLabel = new JLabel();
        loginButton = new JButton();
        loginField = new JTextField();

        /* Setup components */

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Restaurant App | Authorization");
        setResizable(false);

        loginHintLabel.setText("Enter login:");

        passwordHintLabel.setText("Enter password:");

        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setText("Authorization");

        loginButton.setText("Login");
        loginButton.addActionListener(new LoginButtonController(loginField, passwordField, this));

        /* Setup main layout */

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // setup horizontal positioning
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(loginButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordHintLabel)
                            .addComponent(passwordField)
                            .addComponent(headerLabel, GroupLayout.DEFAULT_SIZE,
                                    Constants.AUTHORIZATION_FRAME_WIDTH, Short.MAX_VALUE)
                            .addComponent(loginHintLabel)
                            .addComponent(loginField))))
                .addContainerGap())
        );
        // setup vertical positioning
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginHintLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordHintLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginButton)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null); // centering when appears
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Authorization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Authorization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Authorization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Authorization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the GUI */
        EventQueue.invokeLater(() -> new Authorization().setVisible(true));
    }
}
