package restaurant.controller;

import restaurant.model.Bill;
import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.User;
import restaurant.util.FileDB;
import restaurant.view.Payment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfirmOrderButtonController implements ActionListener {

    private User user;
    private JTable orderTable;

    public ConfirmOrderButtonController(User user, JTable orderTable) {
        this.user = user;
        this.orderTable = orderTable;
    }

    @Override
    public void actionPerformed(ActionEvent e0) {
        if (orderTable.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "Your order is empty. Add a few dishes and try again!");
            return;
        }

        DefaultTableModel ordersTableModel = (DefaultTableModel) orderTable.getModel();
        long orderId = new Date().getTime();
        List<Dish> orderDishes = new ArrayList<>();

        for (int i = 0; i < ordersTableModel.getRowCount(); i++)
            orderDishes.add(FileDB.getInstance()
                    .getDishByName(String.valueOf(ordersTableModel.getValueAt(i, 0))));

        // Check the solvency
        double amount = orderDishes.stream()
                .map(Dish::getPrice)
                .reduce(0.0, (left, right) -> left + right);
        double clientMoney = user.getMoney();

        if (amount > clientMoney) {
            JOptionPane.showMessageDialog(null, "Sorry, you don't have enough money.\n" +
                    "Your money: " + clientMoney + "\n" +
                    "Amount of your order: " + amount);
            return;
        }

        // Create new order and write it
        Order order = new Order(orderId, orderDishes, false);

        FileDB.getInstance().setOrder(order, true);
        JOptionPane.showMessageDialog(null, "Your order " + orderId +
                "(" + amount + " UAH) " +
                "is confirmed.\n" +
                "The administrator will review it as soon as possible.");

        // Clean order table
        for (int i = ordersTableModel.getRowCount() - 1; i >= 0; i--)
            ordersTableModel.removeRow(i);

        // Schedule accept notification
        Timer orderCheckTimer = new Timer(0, (ActionEvent e1) -> {
            boolean notDeclined = FileDB.getInstance().getOrders().stream()
                    .anyMatch(x -> x.getId() == orderId);

            if (notDeclined) {
                boolean orderIsAccepted = FileDB.getInstance()
                        .getOrders()
                        .stream()
                        .filter(x -> x.getId() == orderId)
                        .anyMatch(Order::isAccepted);

                if (orderIsAccepted) {
                    ((Timer)e1.getSource()).stop();
                    JOptionPane.showMessageDialog(null, "Your order " + orderId + " is accepted by administrator!");

                    // Schedule payment
                    Timer billingTimer = new Timer(0, (ActionEvent e2) -> {
                        FileDB.getInstance().getBills()
                                .stream()
                                .filter(Bill::isRequestedForPayment)
                                .filter(x -> x.getId_order() == orderId)
                                .findFirst()
                                .ifPresent(x -> {
                                    Payment payment = new Payment(user, order);

                                    payment.setVisible(true);
                                    ((Timer)e2.getSource()).stop();
                                });
                    });

                    billingTimer.setDelay(5 * 1000); // sec
                    billingTimer.start();
                }
            } else {
                ((Timer)e1.getSource()).stop();
                JOptionPane.showMessageDialog(null, "Sorry, but your order " + orderId + " is declined by administrator!");
            }
        });

        orderCheckTimer.setDelay(5 * 1000); // 10 sec
        orderCheckTimer.start();
    }
}
