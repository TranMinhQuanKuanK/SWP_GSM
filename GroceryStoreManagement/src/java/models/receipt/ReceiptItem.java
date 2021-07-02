/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.receipt;

import java.io.Serializable;
import models.product.ProductDTO;

/**
 *
 * @author ROG STRIX
 */
public class ReceiptItem implements Serializable{
    private ProductDTO product;
    private int quantity;
    
    public ReceiptItem(){}

    public ReceiptItem(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
