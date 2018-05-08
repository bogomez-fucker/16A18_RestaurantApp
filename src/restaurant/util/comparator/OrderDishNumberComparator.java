package restaurant.util.comparator;

import restaurant.model.Order;

import java.util.Comparator;

/**
 * Providing comparison of orders by number of its dishes.
 * @author User
 */
public class OrderDishNumberComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        return o1.getDishes().size() - o2.getDishes().size();
    }
}
