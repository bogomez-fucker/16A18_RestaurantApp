package restaurant.test;

import restaurant.model.*;

import java.io.*;

/**
 * Test of serialization implementation of models.
 * @author User
 */
public class SerializationTest extends TestDataProvider {

    public static void main(String args[]) {
        // init output stream
        File tempFile = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            tempFile = File.createTempFile("serialization-test-", ".dat", new File("."));
            tempFile.deleteOnExit();
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        println("");
        println("Prepared for serialization objects:");
        println(user);
        println(dish1);
        println(dish2);
        println(dish3);
        println(order);
        println(bill);
        println(menuTableModel);
        println("");

        // serializing to file
        try {
            objectOutputStream.writeObject(user);
            objectOutputStream.writeObject(dish1);
            objectOutputStream.writeObject(dish2);
            objectOutputStream.writeObject(dish3);
            objectOutputStream.writeObject(order);
            objectOutputStream.writeObject(bill);
            objectOutputStream.writeObject(menuTableModel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        println("Done serialization to file");
        println("");

        // recovering serialized objects from file
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(tempFile));

            user = (User) objectInputStream.readObject();
            dish1 = (Dish) objectInputStream.readObject();
            dish2 = (Dish) objectInputStream.readObject();
            dish3 = (Dish) objectInputStream.readObject();
            order = (Order) objectInputStream.readObject();
            bill = (Bill) objectInputStream.readObject();
            menuTableModel = (MenuTableModel) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        println("Done deserialization from file");
        println("");

        println("Deserialized objects:");
        println(user);
        println(dish1);
        println(dish2);
        println(dish3);
        println(order);
        println(bill);
        println(menuTableModel);
        println("");
    }
}
