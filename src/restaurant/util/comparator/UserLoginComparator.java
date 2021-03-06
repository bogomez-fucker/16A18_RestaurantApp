package restaurant.util.comparator;

import restaurant.model.User;

import java.util.Comparator;

/**
 * Providing comparison of Users by login length.
 * @author User
 */
public class UserLoginComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return o1.getLogin().length() - o2.getLogin().length();
    }
}
