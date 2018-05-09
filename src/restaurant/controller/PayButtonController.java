package restaurant.controller;

import restaurant.model.*;
import restaurant.util.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Controller fires when pay button clicked.
 */
public class PayButtonController implements ActionListener {

    private User user;
    private Order order;
    private JLabel balanceLabel;
    private JFrame frame;

    public PayButtonController(User user, Order order, JLabel balanceLabel, JFrame frame) {
        this.user = user;
        this.order = order;
        this.balanceLabel = balanceLabel;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Rewrite user balance
        double balance = user.getMoney() - Utils.getAmount(order);

        if (balance < 0.0) {
            JOptionPane.showMessageDialog(frame, "You have not enough money");
            return;
        }

        List<User> users = FilesDAO.getInstance().getUsers();

        users.stream()
                .filter(x -> x.equals(user))
                .forEach(x -> x.setMoney(balance));

        FilesDAO.getInstance().setUsers(users, false);

        // Rewrite bill status
        List<Bill> bills = FilesDAO.getInstance().getBills();

        bills.stream()
                .filter(x -> x.getId_order() == order.getId())
                .forEach(x -> {
                    x.setBilled(true);
                    x.setRequestedForPayment(false);
                });

        FilesDAO.getInstance().setBills(bills, false);

        // User feedback
        balanceLabel.setText(Utils.digitsAfterPoint(String.valueOf(balance), 2));
        JOptionPane.showMessageDialog(frame, "Paid successfully");
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
