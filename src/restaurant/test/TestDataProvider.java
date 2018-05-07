package restaurant.test;

import restaurant.model.*;

import java.util.Arrays;

public abstract class TestDataProvider {
    public static User user = new User(01, "Petro Petrovych",
            "returnMy2007", "user", 1001.77);
    public static Dish dish1 = new Dish(1, "Some dish 1", "Dish description",
            12.08, 12.99, "first");
    public static Dish dish2 = new Dish(2, "Some dish 2", "Dish description",
            19.08, 11.99, "second");
    public static Dish dish3 = new Dish(3, "Some dish 3", "Dish description",
            19.12, 121.99, "drink");
    public static Order order = new Order(1, Arrays.asList(dish1, dish2, dish3), false);
    public static Bill bill = new Bill(1, 1, 99.0, false, false);
    public static MenuTableModel menuTableModel = new MenuTableModel();

    public static void println(Object o) {
        System.out.println(o);
    }
}
