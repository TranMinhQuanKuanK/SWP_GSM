/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.previousreceipt;

import java.io.Serializable;

/**
 *
 * @author ROG STRIX
 */
public class PreviousReceiptDetailDTO implements Serializable{
    int quantity, cost, total;
    String productName;

    public PreviousReceiptDetailDTO(int quantity, int cost, int total, String productName) {
        this.quantity = quantity;
        this.cost = cost;
        this.total = total;
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
}
