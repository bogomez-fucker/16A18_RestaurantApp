package restaurant.test;

import restaurant.model.*;
import restaurant.util.comparator.*;

import java.util.Arrays;
import java.util.TreeSet;

public class ComparatorTest extends TestDataProvider {

    public static void main(String args[]) {
        // Bill test
        BillAmountComparator billAmountComparator = new BillAmountComparator();
        TreeSet<Bill> billsTreeSet = new TreeSet<>(billAmountComparator);

        billsTreeSet.addAll(Arrays.asList(b1, b2, b3));

        println("Bills sorted by amount with comparator:");
        for (Bill b: billsTreeSet)
            println(b);
        println("");

        // Dish test
        DishPriceComparator dishPriceComparator = new DishPriceComparator();
        TreeSet<Dish> dishesTreeSet = new TreeSet<>(dishPriceComparator);

        dishesTreeSet.addAll(Arrays.asList(d1, d2, d3));

        println("Dishes sorted by price with comparator:");
        for (Dish d: dishesTreeSet)
            println(d);
        println("");

        // MenuTableModel test
        MenuHeaderComparator menuHeaderComparator = new MenuHeaderComparator();
        TreeSet<MenuTableModel> menuTableModelTreeSet = new TreeSet<>(menuHeaderComparator);

        menuTableModelTreeSet.addAll(Arrays.asList(x1, x2, x3));

        println("MenuTableModels sorted by header with comparator:");
        for (MenuTableModel x: menuTableModelTreeSet)
            println(x);
        println("");

        // Order test
        OrderDishNumberComparator orderDishNumberComparator = new OrderDishNumberComparator();
        TreeSet<Order> ordersTreeSet = new TreeSet<>(orderDishNumberComparator);

        ordersTreeSet.addAll(Arrays.asList(o1, o2, o3));

        println("Orders sorted by number of its dishes with comparator:");
        for (Order o: ordersTreeSet)
            println(o);
        println("");

        // User test
        UserLoginComparator userLoginComparator = new UserLoginComparator();
        TreeSet<User> usersTreeSet = new TreeSet<>(userLoginComparator);

        usersTreeSet.addAll(Arrays.asList(u1, u2, u3));

        println("Users sorted by login length with comparator:");
        for (User u: usersTreeSet)
            println(u);
        println("");
    }
}
