package restaurant.controller;

import restaurant.model.Bill;
import restaurant.util.FileDB;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestPaymentButtonController implements ActionListener {

    private JTree billsTree;
    private Timer billsTimer;

    public RequestPaymentButtonController(JTree billsTree, Timer billsTimer) {
        this.billsTree = billsTree;
        this.billsTimer = billsTimer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Find selected bills
        TreePath[] selectionPaths = billsTree.getSelectionPaths();

        if (selectionPaths == null) {
            JOptionPane.showMessageDialog(null,
                    "No one bill selected!");
            return;
        }

        // Get selected orders
        List<Long> selectedIds = new ArrayList<>();

        for (TreePath path : selectionPaths) { // for every selected order
            long billId = Long.valueOf(String.valueOf(
                    path.getPath()[1]).split(" ")[1]);

            selectedIds.add(billId);
        }

        List<Bill> bills = FileDB.getInstance().getBills();

        bills.stream()
                .filter(x -> selectedIds.contains(x.getId()))
                .forEach(x -> x.setRequestedForPayment(true));

        // Write out bills
        FileDB.getInstance().setBills(bills, false);

        // Redraw bills tree
        billsTimer.stop();
        billsTimer.start();

        String selectedIdsStr = selectedIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        JOptionPane.showMessageDialog(null,
                "Bills " + selectedIdsStr + " requested for payment!");
    }
}
