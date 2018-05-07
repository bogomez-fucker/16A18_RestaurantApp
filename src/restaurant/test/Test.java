package restaurant.test;

import java.util.Arrays;
import java.util.List;
import restaurant.model.Dish;
import restaurant.model.User;
import restaurant.model.FilesDAO;

/**
 *
 * @author User
 */
public class Test {
    public static void main(String[] args) {
        // Read users from file test
        for (User u : FilesDAO.getInstance().getUsers())
            System.out.println(u);
        
        // Setting dishes test
        List<Dish> temp = FilesDAO.getInstance().getDishes();
        temp.add(new Dish(1, "adsasd", "random dish", 12.4, 1.1, "type1"));
        FilesDAO.getInstance().setDishes(temp, false);
        temp = FilesDAO.getInstance().getDishes(); // read again
        temp = temp.subList(temp.size() - 6, temp.size() - 1);
        for (Dish d : temp)
            System.out.println(d);
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 5);
        
        Integer sum = numbers.stream()
                .reduce(10, (left, right) -> left + right);
        
        System.out.println(sum); // output 11
    }
}
