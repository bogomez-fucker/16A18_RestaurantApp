package restaurant.controller;

import restaurant.util.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller fires when add dish button clicked.
 */
public class AddDishButtonController implements ActionListener {
    private JTable sourceTable;
    private DefaultTableModel targetModel;
    private JFrame frame;

    public AddDishButtonController(JTable sourceTable, DefaultTableModel targetModel, JFrame frame) {
        this.sourceTable = sourceTable;
        this.targetModel = targetModel;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel sourceModel = (DefaultTableModel) sourceTable.getModel();

        int selectedRowIndex = sourceTable.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "No one dish selected!");
            return;
        }

        Object[] rowArray = new Object[Constants.CLIENT_MENU_VISIBLE_FIELDS];

        for (int i = 0; i < Constants.CLIENT_MENU_VISIBLE_FIELDS; i++)
            rowArray[i] = sourceModel.getValueAt(selectedRowIndex, i);

        targetModel.addRow(rowArray);
    }
}
