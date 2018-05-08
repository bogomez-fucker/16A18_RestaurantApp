package restaurant.model;

import org.jetbrains.annotations.NotNull;
import restaurant.util.Constants;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;

public class MenuTableModel extends DefaultTableModel implements Serializable, Cloneable, Comparable<MenuTableModel> {

    private Object[] header;
    private Object[][] content;

    private Class[] types = new Class[]{
            String.class, String.class, Double.class, Double.class
    };

    private boolean[] canEdit = new boolean[]{
            false, false, false, false
    };

    public MenuTableModel() {
        header = Constants.DISHES_TABLE_HEADER;
        content = new Object[][]{};

        setDataVector(content, header);
    }

    public MenuTableModel(Object[] customHeader) {
        header = customHeader;
        content = new Object[][]{};

        setDataVector(content, header);
    }

    public Object[] getHeader() {
        return header;
    }

    public Object[][] getContent() {
        return content;
    }

    public Class[] getTypes() {
        return types;
    }

    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

    @Override
    public MenuTableModel clone() throws CloneNotSupportedException {
        return (MenuTableModel) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTableModel that = (MenuTableModel) o;
        return Arrays.equals(header, that.header) &&
                Arrays.equals(content, that.content) &&
                Arrays.equals(types, that.types) &&
                Arrays.equals(canEdit, that.canEdit);
    }

    @Override
    public int hashCode() {

        int result = Arrays.hashCode(header);
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + Arrays.hashCode(types);
        result = 31 * result + Arrays.hashCode(canEdit);
        return result;
    }

    @Override
    public String toString() {
        return "MenuTableModel{" +
                "header=" + (header == null ? null : Arrays.asList(header)) +
                ", content=" + (content == null ? null : Arrays.asList(content)) +
                '}';
    }

    @Override
    public int compareTo(@NotNull MenuTableModel o) {
        return Long.valueOf(this.header.length - o.header.length).intValue();
    }
}
