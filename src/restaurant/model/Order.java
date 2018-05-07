/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author User
 */
public class Order implements Serializable, Cloneable {
    public static final int FIELDS_NUMBER = 3;
    public static final int VISIBLE_FIELDS_NUMBER = 2;
    
    private long id;
    private List<Dish> dishes;
    private boolean accepted;

    public Order(long id, List<Dish> dishes, boolean accepted) {
        this.id = id;
        this.dishes = dishes;
        this.accepted = accepted;
    }
    
    public Order(String[] fieldsArray) {
        this.id = Long.valueOf(fieldsArray[0]);
        this.dishes = new ArrayList<>();
        
        for (String dishId : fieldsArray[1].split(" "))
            dishes.add(FilesDAO.getInstance()
                    .getDishById(Long.valueOf(dishId)));
        
        this.accepted = Boolean.valueOf(fieldsArray[2]);
    }

    public long getId() {
        return id;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public Order clone() throws CloneNotSupportedException {
        return (Order) super.clone();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.dishes);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                accepted == order.accepted &&
                Objects.equals(dishes, order.dishes);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dishes=" + dishes +
                ", accepted=" + accepted +
                '}';
    }
}
