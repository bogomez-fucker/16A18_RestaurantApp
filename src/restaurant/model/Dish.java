/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import java.util.Objects;

/**
 *
 * @author User
 */
public class Dish {
    public static final int FIELDS_NUMBER = 6;
    public static final int VISIBLE_FIELDS_NUMBER = 4;
    public static final String TYPE_FIRST = "first";
    public static final String TYPE_SECOND = "second";
    public static final String TYPE_DRINK = "drink";
    
    private long id;
    private String name;
    private String description;
    private double cookingTime;
    private double price;
    private String type;

    public Dish(long id, String name, String description, double cookingTime, double price, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cookingTime = cookingTime;
        this.price = price;
        this.type = type;
    }
    
    public Dish(String[] fieldsArray) {
        this.id = Long.valueOf(fieldsArray[0]);
        this.name = fieldsArray[1];
        this.description = fieldsArray[2];
        this.cookingTime = Double.valueOf(fieldsArray[3]);
        this.price = Double.valueOf(fieldsArray[4]);
        this.type = fieldsArray[5];
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCookingTime() {
        return cookingTime;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.cookingTime) ^ (Double.doubleToLongBits(this.cookingTime) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dish other = (Dish) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dish{" + "id=" + id + ", name=" + name + ", description=" + description + ", cookingTime=" + cookingTime + ", price=" + price + ", type=" + type + '}';
    }
}
