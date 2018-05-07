package restaurant.util.comparator;

import restaurant.model.Dish;

import java.util.Comparator;

public class DishPriceComparator implements Comparator<Dish> {

    @Override
    public int compare(Dish o1, Dish o2) {
        return Double.valueOf(o1.getPrice() - o2.getPrice()).intValue();
    }
}
