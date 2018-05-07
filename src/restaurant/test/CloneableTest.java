package restaurant.test;

import restaurant.model.*;

public class CloneableTest extends TestDataProvider {

    public static void main(String args[]) {
        User userCloned = null;
        Dish dish1Cloned = null;
        Dish dish2Cloned = null;
        Dish dish3Cloned = null;
        Order orderCloned = null;
        Bill billCloned = null;
        MenuTableModel menuTableModelCloned = null;

        try {
            userCloned = user.clone();
            dish1Cloned = dish1.clone();
            dish2Cloned = dish2.clone();
            dish3Cloned = dish3.clone();
            orderCloned = order.clone();
            billCloned = bill.clone();
            menuTableModelCloned = menuTableModel.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        println("Cloned object:");
        println("\t==\t.equals()");
        println("\t" + String.valueOf(user == userCloned) + "\t" + String.valueOf(user.equals(userCloned)));
        println("\t" + String.valueOf(dish1 == dish1Cloned) + "\t" + String.valueOf(dish1.equals(dish1Cloned)));
        println("\t" + String.valueOf(dish2 == dish2Cloned) + "\t" + String.valueOf(dish2.equals(dish2Cloned)));
        println("\t" + String.valueOf(dish3 == dish3Cloned) + "\t" + String.valueOf(dish3.equals(dish3Cloned)));
        println("\t" + String.valueOf(order == orderCloned) + "\t" + String.valueOf(order.equals(orderCloned)));
        println("\t" + String.valueOf(bill == billCloned) + "\t" + String.valueOf(bill.equals(billCloned)));
        println("\t" + String.valueOf(menuTableModel == menuTableModelCloned) + "\t" + String.valueOf(menuTableModel.equals(menuTableModelCloned)));
    }
}
