package restaurant.util;

public class Constants {

    public static final Object[] DISHES_TABLE_HEADER = {
            "Name", "Description", "Cooking time", "Price"
    };
    public static final int UPDATE_DELAY = 5000; // 5 sec
    public static final int CLIENT_MENU_VISIBLE_FIELDS = 4;

    public static final int CLIENT_FRAME_WIDTH = 900;
    public static final int CLIENT_FRAME_HEIGHT = 800;
    public static final int CLIENT_TABLE_WIDTH = 350;
    public static final int CLIENT_TABLE_HEIGHT = 350;
    public static final int ADMINISTRATOR_FRAME_WIDTH = 500;
    public static final int ADMINISTRATOR_FRAME_HEIGHT = 800;
    public static final int ADMINISTRATOR_TREE_WIDTH = 350;
    public static final int ADMINISTRATOR_TREE_HEIGHT = 350;
    public static final int AUTHORIZATION_FRAME_WIDTH = 180;

    public static final String USERS_FILEPATH = "db/users.tsv";
    public static final String DISHES_FILEPATH = "db/dishes.tsv";
    public static final String ORDERS_FILEPATH = "db/orders.tsv";
    public static final String BILLS_FILEPATH = "db/bills.tsv";
}
