/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.product;

import java.io.Serializable;
import models.category.CategoryDTO;

/**
 *
 * @author Tran Minh Quan
 */
public class ProductDTO implements Serializable {

    private int product_ID;
    private String name;
    private int quantity;
    private int cost_price;
    private int selling_price;
    private int lower_threshold;
    private CategoryDTO category;
    private String unit_label;
    private boolean is_selling;
    private String location;

    public ProductDTO(int product_ID, String name, 
            int quantity, int cost_price, 
            int selling_price, int lower_threshold, 
            CategoryDTO category, String unit_label, 
            boolean is_selling, String location) {
        this.product_ID = product_ID;
        this.name = name;
        this.quantity = quantity;
        this.cost_price = cost_price;
        this.selling_price = selling_price;
        this.lower_threshold = lower_threshold;
        this.category = category;
        this.unit_label = unit_label;
        this.is_selling = is_selling;
        this.location = location;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCost_price() {
        return cost_price;
    }

    public void setCost_price(int cost_price) {
        this.cost_price = cost_price;
    }

    public int getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(int selling_price) {
        this.selling_price = selling_price;
    }

    public int getLower_threshold() {
        return lower_threshold;
    }

    public void setLower_threshold(int lower_threshold) {
        this.lower_threshold = lower_threshold;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public String getUnit_label() {
        return unit_label;
    }

    public void setUnit_label(String unit_label) {
        this.unit_label = unit_label;
    }

    public boolean isIs_selling() {
        return is_selling;
    }

    public void setIs_selling(boolean is_selling) {
        this.is_selling = is_selling;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
}
