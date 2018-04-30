package restaurant.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteDishButtonController implements ActionListener {

    private JTable targetTable;

    public DeleteDishButtonController(JTable targetTable) {
        this.targetTable = targetTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel targetModel = (DefaultTableModel) targetTable.getModel();

        int selectedRowIndex = targetTable.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(null, "No one dish selected!");
            return;
        }

        targetModel.removeRow(selectedRowIndex);
    }
}
