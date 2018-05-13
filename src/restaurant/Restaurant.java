package restaurant;

import restaurant.util.Utils;
import restaurant.view.Authorization;

/**
 * Application entry point.
 * @author User
 */
public class Restaurant {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Utils.setNimbusLookAndFeel();

        Authorization authorization = new Authorization();
        
        authorization.setVisible(true);
    }
}
