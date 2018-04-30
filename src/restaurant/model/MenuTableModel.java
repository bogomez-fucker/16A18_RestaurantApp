package restaurant.model;

import restaurant.util.Constants;

import javax.swing.table.DefaultTableModel;

public class MenuTableModel extends DefaultTableModel {

    private Class[] types = new Class[]{
            String.class, String.class, Double.class, Double.class
    };

    private boolean[] canEdit = new boolean[]{
            false, false, false, false
    };

    public MenuTableModel() {
        Object[] header = Constants.DISHES_TABLE_HEADER;
        Object[][] content = new Object[][]{};

        setDataVector(content, header);
    }

    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }
}
