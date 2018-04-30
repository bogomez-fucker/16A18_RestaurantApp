/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import restaurant.model.Bill;
import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.User;

/**
 * Singleton implementation of simple file DB that can read/write from files
 * @author User
 */
public final class FileDB {
    private static final String USERS_FILEPATH = "db/users.tsv";
    private static final String DISHES_FILEPATH = "db/dishes.tsv";
    private static final String ORDERS_FILEPATH = "db/orders.tsv";
    private static final String BILLS_FILEPATH = "db/bills.tsv";
    
    private static final FileDB _instance = new FileDB();
    
    private ReadWriteLock usersLock;
    private ReadWriteLock dishesLock;
    private ReadWriteLock ordersLock;
    private ReadWriteLock billsLock;
    
    private FileDB() {
        usersLock = new ReentrantReadWriteLock();
        dishesLock = new ReentrantReadWriteLock();
        ordersLock = new ReentrantReadWriteLock();
        billsLock = new ReentrantReadWriteLock();
    }
        
    public static synchronized FileDB getInstance() {
        return _instance;
    }
    
    public List<Bill> getBills() {
        List<String[]> splittedFile = readFileSplittedByTab(BILLS_FILEPATH,
                Bill.FIELDS_NUMBER, billsLock);
        List<Bill> bills = new ArrayList<>();
        
        for (String[] line : splittedFile)
            bills.add(new Bill(line));
        
        return bills;
    }
    
    public void setBills(List<Bill> bills, boolean appendData) {
        billsLock.writeLock().lock();
        
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(BILLS_FILEPATH, appendData)));
            
            for (Bill b : bills) {
                printWriter.print(b.getId() + "\t");
                printWriter.print(b.getId_order() + "\t");
                printWriter.print(b.getAmount() + "\t");
                printWriter.print(b.isBilled() + "\t");
                printWriter.println(b.isRequestedForPayment());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
            billsLock.writeLock().unlock();
        }
    }
    
    public List<Order> getOrders() {
        List<String[]> splittedFile = readFileSplittedByTab(ORDERS_FILEPATH,
                Order.FIELDS_NUMBER, ordersLock);
        List<Order> orders = new ArrayList<>();
        
        for (String[] line : splittedFile)
            orders.add(new Order(line));
        
        return orders;
    }
    
    public void setOrders(List<Order> orders, boolean appendData) {
        if (appendData) {
            for (Order o : orders)
                setOrder(o, appendData);
        } else {
            if (orders.isEmpty()) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    wipeFile(ORDERS_FILEPATH, ordersLock);
                });
            }
            
            // rewrite for the first order, append other
            for (int i = 0; i < orders.size(); i++) {
                if (i == 0)
                    setOrder(orders.get(i), false);
                else
                    setOrder(orders.get(i), true);
            }
        }
    }
    
    public void setOrder(Order order, boolean appendData) {
        ordersLock.writeLock().lock();
        
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(ORDERS_FILEPATH, appendData)));
            
            printWriter.print(order.getId() + "\t");
            
            String dishesId = "";
            
            for (Dish d : order.getDishes())
                dishesId += d.getId() + " ";
            
            printWriter.print(dishesId.trim() + "\t");
            printWriter.println(order.isAccepted());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
            ordersLock.writeLock().unlock();
        }
    }
    
    public Dish getDishById(long id) {
        Dish xx = getDishes().stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .get();
        
        return xx;
    }
    
    public Dish getDishByName(String name) {
        return getDishes().stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }
    
    public List<Dish> getDishes() {
        List<String[]> splittedFile = readFileSplittedByTab(DISHES_FILEPATH,
                Dish.FIELDS_NUMBER, dishesLock);
        List<Dish> dishes = new ArrayList<>();
        
        for (String[] line : splittedFile)
            dishes.add(new Dish(line));
        
        return dishes;
    }
    
    public void setDishes(List<Dish> dishes, boolean appendData) {
        dishesLock.writeLock().lock();
        
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(DISHES_FILEPATH, appendData)));
            
            for (Dish d : dishes) {
                printWriter.println(d.getId() + "\t" +
                        d.getName() + "\t" +
                        d.getDescription() + "\t" +
                        d.getCookingTime()+ "\t" +
                        d.getPrice() + "\t" +
                        d.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
            dishesLock.writeLock().unlock();
        }
    }
    
    public List<User> getUsers() {
        List<String[]> splittedFile = readFileSplittedByTab(USERS_FILEPATH,
                User.FIELDS_NUMBER, usersLock);
        List<User> users = new ArrayList<>();
        
        for (String[] line : splittedFile)
            users.add(new User(line));
        
        return users;
    }
    
    public void setUsers(List<User> users, boolean appendData) {
        usersLock.writeLock().lock();
        
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(USERS_FILEPATH, appendData)));
            
            for (User u : users) {
                printWriter.println(u.getId() + "\t" +
                        u.getLogin() + "\t" +
                        u.getPassword() + "\t" +
                        u.getRole() + "\t" +
                        u.getMoney());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
            usersLock.writeLock().unlock();
        }
    }
    
    private void wipeFile(String filePath, ReadWriteLock lock) {  
        PrintWriter printWriter = null;
        
        try {
            lock.writeLock().lock();
            Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.TRUNCATE_EXISTING).close();
//            printWriter = new PrintWriter(
//                    new BufferedWriter(
//                            new FileWriter(filePath, false)));
//            printWriter.print("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            printWriter.flush();
//            printWriter.close();
            lock.writeLock().unlock();
        }
        
        System.out.println("2");
    }
    
    private List<String[]> readFileSplittedByTab(String filePath,
            int columns, ReadWriteLock lock) {
        lock.readLock().lock();
        
        File file = new File(filePath);
        
        // if file is absent or empty return empty list
        if (!file.exists() || file.length() == 0) {
            lock.readLock().unlock();
            return new ArrayList<String[]>();
        }
        
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }

        List<String[]> splittedFile = new ArrayList<>();
        String[] parts = null;
        
        for (String line : lines) {
            parts = line.split("\t");
        
            if (parts.length != columns) // skip bad string
                continue;
        
            splittedFile.add(parts);
        }
        
        return splittedFile;
    }
}
