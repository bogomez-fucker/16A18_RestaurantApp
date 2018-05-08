package restaurant.test;

import restaurant.model.*;

import java.util.Arrays;
import java.util.List;

/**
 * Holds reusable test data and methods. In glory to DRY.
 * @author User
 */
public abstract class TestDataProvider {

    /* Serialization + cloneable tests data */
    public static User user = new User(01, "Petro Petrovych",
            "returnMy2007", "user", 1001.77);
    public static Dish dish1 = new Dish(1, "Some dish 1", "Dish description",
            12.08, 12.99, "first");
    public static Dish dish2 = new Dish(2, "Some dish 2", "Dish description",
            19.08, 11.99, "second");
    public static Dish dish3 = new Dish(3, "Some dish 3", "Dish description",
            19.12, 121.99, "drink");
    public static List<Dish> listDishes = Arrays.asList(dish1, dish2, dish3);
    public static Order order = new Order(1, listDishes, false);
    public static Bill bill = new Bill(1, 1, 99.0, false, false);
    public static MenuTableModel menuTableModel = new MenuTableModel();

    /* Comparable + comparator tests data */
    public static Bill b1 = new Bill(13, 1, 123.56, false, false);
    public static Bill b2 = new Bill(11, 2, 1123.16, false, false);
    public static Bill b3 = new Bill(12, 3, 1.36, false, false);
    public static Dish d1 = new Dish(23, "Tempo Dish", "No desc", 13.08, 99.13, "first");
    public static Dish d2 = new Dish(21, "Tempo Dish", "No desc", 15.48, 100.23, "second");
    public static Dish d3 = new Dish(22, "Tempo Dish", "No desc", 9.00, 87.55, "first");
    public static Object[] header1 = {"Id"};
    public static Object[] header2 = {"Id", "Name", "Description"};
    public static Object[] header3 = {"Name", "Surname"};
    public static MenuTableModel x1 = new MenuTableModel(header1);
    public static MenuTableModel x2 = new MenuTableModel(header2);
    public static MenuTableModel x3 = new MenuTableModel(header3);
    public static List<Dish> listDishes1 = Arrays.asList(d1);
    public static List<Dish> listDishes2 = Arrays.asList(d1, d2);
    public static List<Dish> listDishes3 = Arrays.asList(d1, d2, d3);
    public static Order o1 = new Order(33, listDishes1, true);
    public static Order o2 = new Order(31, listDishes2, true);
    public static Order o3 = new Order(32, listDishes3, true);
    public static User u1 = new User(43, "Yrko", "asdf123", "admen", 12.90);
    public static User u2 = new User(41, "Gmyrko", "asdf1asdasd23", "usr", 1.00);
    public static User u3 = new User(42, "Taras", "panasfs3", "usr", 101.05);

    public static void println(Object o) {
        System.out.println(o);
    }
}
