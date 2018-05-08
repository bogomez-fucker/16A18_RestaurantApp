package restaurant.util.comparator;

import restaurant.model.Dish;

import java.util.Comparator;

/**
 * Providing comparison of Dishes by price
 * @author User
 */
public class DishPriceComparator implements Comparator<Dish> {

    @Override
    public int compare(Dish o1, Dish o2) {
        return Double.valueOf(o1.getPrice() - o2.getPrice()).intValue();
    }
}
