package restaurant.controller;

import restaurant.model.*;
import restaurant.util.Utils;
import restaurant.view.Payment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller fires when confirm order button clicked.
 */
public class ConfirmOrderButtonController implements ActionListener {

    private User user;
    private JTable orderTable;
    private JFrame frame;

    public ConfirmOrderButtonController(User user, JTable orderTable, JFrame frame) {
        this.user = user;
        this.orderTable = orderTable;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e0) {
        if (orderTable.getRowCount() < 1) {
            JOptionPane.showMessageDialog(frame, "Your order is empty. Add a few dishes and try again!");
            return;
        }

        DefaultTableModel ordersTableModel = (DefaultTableModel) orderTable.getModel();
        long orderId = new Date().getTime();
        List<Dish> orderDishes = new ArrayList<>();

        for (int i = 0; i < ordersTableModel.getRowCount(); i++)
            orderDishes.add(FilesDAO.getInstance()
                    .getDishByName(String.valueOf(ordersTableModel.getValueAt(i, 0))));

        // Check the solvency
        double amount = orderDishes.stream()
                .map(Dish::getPrice)
                .reduce(0.0, (left, right) -> left + right);
        double clientMoney = user.getMoney();

        if (amount > clientMoney) {
            JOptionPane.showMessageDialog(frame, "Sorry, you don't have enough money.\n" +
                    "Your money: " + Utils.digitsAfterPoint(String.valueOf(clientMoney), 2) + "\n" +
                    "Amount of your order: " + Utils.digitsAfterPoint(String.valueOf(amount), 2));
            return;
        }

        // Create new order and write it
        Order order = new Order(orderId, orderDishes, false);

        FilesDAO.getInstance().setOrder(order, true);
        JOptionPane.showMessageDialog(frame, "Your order " + orderId +
                "(" + Utils.digitsAfterPoint(String.valueOf(amount), 2) + " UAH) " +
                "is confirmed.\n" +
                "The administrator will review it as soon as possible.");

        // Clean order table
        for (int i = ordersTableModel.getRowCount() - 1; i >= 0; i--)
            ordersTableModel.removeRow(i);

        // Schedule accept notification
        Timer orderCheckTimer = new Timer(0, (ActionEvent e1) -> {
            boolean notDeclined = FilesDAO.getInstance().getOrders().stream()
                    .anyMatch(x -> x.getId() == orderId);

            if (notDeclined) {
                boolean orderIsAccepted = FilesDAO.getInstance()
                        .getOrders()
                        .stream()
                        .filter(x -> x.getId() == orderId)
                        .anyMatch(Order::isAccepted);

                if (orderIsAccepted) {
                    ((Timer)e1.getSource()).stop();
                    JOptionPane.showMessageDialog(frame, "Your order " + orderId + " is accepted by administrator!");

                    // Schedule payment
                    Timer billingTimer = new Timer(0, (ActionEvent e2) -> {
                        FilesDAO.getInstance().getBills()
                                .stream()
                                .filter(Bill::isRequestedForPayment)
                                .filter(x -> x.getId_order() == orderId)
                                .findFirst()
                                .ifPresent(x -> {
                                    Payment payment = new Payment(user, order);

                                    payment.setLocationRelativeTo(frame);
                                    payment.setVisible(true);
                                    ((Timer)e2.getSource()).stop();
                                });
                    });

                    billingTimer.setDelay(5 * 1000); // sec
                    billingTimer.start();
                }
            } else {
                ((Timer)e1.getSource()).stop();
                JOptionPane.showMessageDialog(frame, "Sorry, but your order " + orderId + " is declined by administrator!");
            }
        });

        orderCheckTimer.setDelay(5 * 1000); // 10 sec
        orderCheckTimer.start();
    }
}
