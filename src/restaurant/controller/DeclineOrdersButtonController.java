package restaurant.controller;

import restaurant.model.Order;
import restaurant.model.FilesDAO;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeclineOrdersButtonController implements ActionListener {

    private JTree ordersTree;
    private Timer ordersTimer;

    public DeclineOrdersButtonController(JTree ordersTree, Timer ordersTimer) {
        this.ordersTree = ordersTree;
        this.ordersTimer = ordersTimer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Find selected orders
        TreePath[] selectionPaths = ordersTree.getSelectionPaths();

        if (selectionPaths == null) {
            JOptionPane.showMessageDialog(null, "No one order selected!");

            return;
        }

        // Get id of selected orders
        List<Long> selectedIds = new ArrayList<>();

        for (TreePath path : selectionPaths) { // for every selected order
            long orderId = Long.valueOf(String.valueOf(
                    path.getPath()[1]).split(" ")[1]);

            selectedIds.add(orderId);
        }

        // Filter out declined
        List<Order> ordersWithoutDeclined = FilesDAO.getInstance().getOrders()
                .stream()
                .filter(x -> !selectedIds.contains(x.getId()))
                .collect(Collectors.toList());

        // Write result
        FilesDAO.getInstance().setOrders(ordersWithoutDeclined, false);

        // Redraw orders tree
        ordersTimer.stop();
        ordersTimer.start();

        // Notify user
        String declinedIdsStr = selectedIds.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(", "));

        JOptionPane.showMessageDialog(null, "Orders " + declinedIdsStr + " declined!");
    }
}
