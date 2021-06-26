/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.pendingItem;

import java.io.Serializable;

/**
 *
 * @author ROG STRIX
 */
public class SuggestionListDTO implements Serializable{
    
    private int product_ID;
    private String product_name;
    private int product_quantity;
    
    public SuggestionListDTO(){}

    public SuggestionListDTO(int product_ID, String product_name, int product_quantity) {
        this.product_ID = product_ID;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    
    
}
