package restaurant.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller fires when delete dish button clicked.
 */
public class DeleteDishButtonController implements ActionListener {

    private JTable targetTable;
    private JFrame frame;

    public DeleteDishButtonController(JTable targetTable, JFrame frame) {
        this.targetTable = targetTable;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel targetModel = (DefaultTableModel) targetTable.getModel();

        int selectedRowIndex = targetTable.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "No one dish selected!");
            return;
        }

        targetModel.removeRow(selectedRowIndex);
    }
}
