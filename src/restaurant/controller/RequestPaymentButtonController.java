package restaurant.controller;

import restaurant.model.Bill;
import restaurant.model.FilesDAO;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller fires when request payment button clicked.
 */
public class RequestPaymentButtonController implements ActionListener {

    private JTree billsTree;
    private Timer billsTimer;
    private JFrame frame;

    public RequestPaymentButtonController(JTree billsTree, Timer billsTimer, JFrame frame) {
        this.billsTree = billsTree;
        this.billsTimer = billsTimer;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Find selected bills
        TreePath[] selectionPaths = billsTree.getSelectionPaths();

        if (selectionPaths == null) {
            JOptionPane.showMessageDialog(frame,
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

        List<Bill> bills = FilesDAO.getInstance().getBills();

        bills.stream()
                .filter(x -> selectedIds.contains(x.getId()))
                .forEach(x -> x.setRequestedForPayment(true));

        // Write out bills
        FilesDAO.getInstance().setBills(bills, false);

        // Redraw bills tree
        billsTimer.stop();
        billsTimer.start();

        String selectedIdsStr = selectedIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        JOptionPane.showMessageDialog(frame,
                "Bills " + selectedIdsStr + " requested for payment!");
    }
}
