/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.sessionBill;

import java.io.Serializable;
import models.product.ProductDTO;

/**
 *
 * @author Tran Minh Quan
 */
public class BillItemObject implements Serializable {
    private ProductDTO product;
    private int quantity;

    public BillItemObject(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
