package restaurant.controller;

import restaurant.model.User;
import restaurant.util.FileDB;
import restaurant.view.Administrator;
import restaurant.view.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

public class LoginButtonController implements ActionListener {
    
    private JTextField loginField;
    private JPasswordField passwordField;
    private JFrame frame;

    public LoginButtonController(JTextField loginField, JPasswordField passwordField, JFrame frame) {
        this.loginField = loginField;
        this.passwordField = passwordField;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enteredLogin = loginField.getText();
        String enteredPass = String.valueOf(passwordField.getPassword());

        if (enteredLogin.isEmpty() || enteredPass.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Enter login, password and THEN press \"Login\" button");
            return;
        }

        User foundUser = FileDB.getInstance().getUsers()
                .stream()
                .filter(x -> x.getLogin().equals(enteredLogin))
                .findFirst()
                .orElse(null);

        if (foundUser == null)
            JOptionPane.showMessageDialog(null, "No user \"" + enteredLogin + "\" found");
        else if (!foundUser.getPassword().equals(enteredPass))
            JOptionPane.showMessageDialog(null, "Password is not corrrect");
        else
        if (foundUser.getRole().equals(User.ROLE_USER)) {
            Client userGUI = new Client(foundUser);

            frame.setVisible(false);
            userGUI.setVisible(true);
            frame.dispose();
        } else if (foundUser.getRole().equals(User.ROLE_ADMIN)) {
            Administrator administrator = new Administrator();

            frame.setVisible(false);
            administrator.setVisible(true);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Not recognized user role \"" + foundUser.getRole() + "\". Exiting...");
            frame.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING));
        }
    }
}
