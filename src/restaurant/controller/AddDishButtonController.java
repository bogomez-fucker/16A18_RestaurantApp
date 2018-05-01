package restaurant.controller;

import restaurant.util.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDishButtonController implements ActionListener {
    private JTable sourceTable;
    private DefaultTableModel targetModel;

    public AddDishButtonController(JTable sourceTable, DefaultTableModel targetModel) {
        this.sourceTable = sourceTable;
        this.targetModel = targetModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel sourceModel = (DefaultTableModel) sourceTable.getModel();

        int selectedRowIndex = sourceTable.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(null, "No one dish selected!");
            return;
        }

        Object[] rowArray = new Object[Constants.CLIENT_MENU_VISIBLE_FIELDS];

        for (int i = 0; i < Constants.CLIENT_MENU_VISIBLE_FIELDS; i++)
            rowArray[i] = sourceModel.getValueAt(selectedRowIndex, i);

        targetModel.addRow(rowArray);
    }
}
