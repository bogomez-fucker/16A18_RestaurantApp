/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author User
 */
public class User implements Serializable, Cloneable {
    public static final int FIELDS_NUMBER = 5;
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";
    
    private long id;
    private String login;
    private String password;
    private String role;
    private double money;

    public User(long id, String name, String password, String role, double money) {
        this.id = id;
        this.login = name;
        this.password = password;
        this.role = role;
        this.money = money;
    }
    
    public User(String[] fieldsArray) {
        this.id = Long.valueOf(fieldsArray[0]);
        this.login = fieldsArray[1];
        this.password = fieldsArray[2];
        this.role = fieldsArray[3];
        this.money = Double.valueOf(fieldsArray[4]);
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.money) ^ (Double.doubleToLongBits(this.money) >>> 32));
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
        final User other = (User) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + ", money=" + money + '}';
    }
}
