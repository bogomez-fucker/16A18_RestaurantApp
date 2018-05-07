package restaurant.util.comparator;

import restaurant.model.Bill;

import java.util.Comparator;

public class BillAmountComparator implements Comparator<Bill> {

    @Override
    public int compare(Bill o1, Bill o2) {
        return Double.valueOf(o1.getAmount() - o2.getAmount()).intValue();
    }
}
