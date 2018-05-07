package restaurant.util.comparator;

import restaurant.model.MenuTableModel;

import java.util.Comparator;

public class MenuHeaderComparator implements Comparator<MenuTableModel> {

    @Override
    public int compare(MenuTableModel o1, MenuTableModel o2) {
        return o1.getHeader().length - o2.getHeader().length;
    }
}
