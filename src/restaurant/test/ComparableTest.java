package restaurant.test;

import restaurant.model.*;

import java.util.TreeSet;

/**
 * Test of comparable implementation of models.
 * @author User
 */
public class ComparableTest extends TestDataProvider {

    public static void main(String args[]) {
        // Bill test
        TreeSet<Bill> billsTreeSet = new TreeSet<>();

        billsTreeSet.add(b1);
        billsTreeSet.add(b2);
        billsTreeSet.add(b3);

        println("Bills sorted by id:");
        for (Bill b: billsTreeSet)
            println(b);
        println("");

        // Dish test
        TreeSet<Dish> dishesTreeSet = new TreeSet<>();

        dishesTreeSet.add(d1);
        dishesTreeSet.add(d2);
        dishesTreeSet.add(d3);

        println("Dishes sorted by id:");
        for (Dish d: dishesTreeSet)
            println(d);
        println("");

        // MenuTableModel test
        TreeSet<MenuTableModel> mtmsTreeSet = new TreeSet<>();

        mtmsTreeSet.add(x1);
        mtmsTreeSet.add(x2);
        mtmsTreeSet.add(x3);

        println("MenuTableModels sorted by header:");
        for (MenuTableModel x: mtmsTreeSet)
            println(x);
        println("");

        // Order test
        TreeSet<Order> ordersTreeSet = new TreeSet<>();

        ordersTreeSet.add(o1);
        ordersTreeSet.add(o2);
        ordersTreeSet.add(o3);

        println("Orders sorted by id:");
        for (Order o: ordersTreeSet)
            println(o);
        println("");

        // User test
        TreeSet<User> usersTreeSet = new TreeSet<>();

        usersTreeSet.add(u1);
        usersTreeSet.add(u2);
        usersTreeSet.add(u3);

        println("Users sorted by id:");
        for (User u: usersTreeSet)
            println(u);
        println("");
    }
}
