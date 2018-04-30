/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import java.util.List;

/**
 *
 * @author User
 */
public class Menu {
    private long id;
    private String name;
    private List<Dish> dishes;

    public Menu(long id, String name, List<Dish> dishes) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
    }
}
