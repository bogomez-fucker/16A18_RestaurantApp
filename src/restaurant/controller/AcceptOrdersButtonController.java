package restaurant.controller;

import restaurant.model.Bill;
import restaurant.model.Dish;
import restaurant.model.FilesDAO;
import restaurant.model.Order;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller fires when accept orders button clicked.
 */
public class AcceptOrdersButtonController implements ActionListener {

    private JTree ordersTree;
    private Timer ordersRefreshTimer;
    private Timer billsRefreshTimer;


    public AcceptOrdersButtonController(JTree ordersTree, Timer ordersRefreshTimer, Timer billsRefreshTimer) {
        this.ordersTree = ordersTree;
        this.ordersRefreshTimer = ordersRefreshTimer;
        this.billsRefreshTimer = billsRefreshTimer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Find selected for accepting orders
        TreePath[] selectionPaths = ordersTree.getSelectionPaths();

        if (selectionPaths == null) {
            JOptionPane.showMessageDialog(null, "No one order selected!");

            return;
        }

        List<Long> acceptedIds = new ArrayList<>();

        for (TreePath path : selectionPaths) { // for every selected order
            long orderId = Long.valueOf(String.valueOf(
                    path.getPath()[1]).split(" ")[1]);

            acceptedIds.add(orderId);
        }

        List<Order> orders = FilesDAO.getInstance().getOrders();

        // Change status of accepted orders
        orders.stream()
                .filter(x -> acceptedIds.contains(x.getId()))
                .forEach(x -> x.setAccepted(true));

        // Write out orders
        FilesDAO.getInstance().setOrders(orders, false);

        // Redraw orders tree
        ordersRefreshTimer.stop();
        ordersRefreshTimer.start();

        // Get accepted orders
        List<Order> acceptedOrders = orders.stream()
                .filter(x -> acceptedIds.contains(x.getId()))
                .collect(Collectors.toList());

        // Create Bills
        List<Bill> bills = new ArrayList<>();

        for (Order o : acceptedOrders) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Long billId = new Date().getTime();
            Long orderId = o.getId();
            Double amount = o.getDishes().stream()
                    .map(Dish::getPrice)
                    .reduce((left, right) -> left + right)
                    .orElse(0.0);
            boolean billed = false;
            boolean reqForPayment = false;

            bills.add(new Bill(billId, orderId, amount, billed, reqForPayment));
        }

        // Write out bills
        FilesDAO.getInstance().setBills(bills, true);

        // Redraw bills tree
        billsRefreshTimer.stop();
        billsRefreshTimer.start();

        String acceptedIdsStr = acceptedIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        JOptionPane.showMessageDialog(null, "Orders " + acceptedIdsStr + " accepted!");
    }
}
