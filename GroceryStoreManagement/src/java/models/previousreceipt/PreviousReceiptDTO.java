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
public class PreviousReceiptDTO implements Serializable{
    
    private int receipt_ID;
    private String import_date;
    private String store_owner_username;
    private int total;
    
    public PreviousReceiptDTO(){
    }

    public PreviousReceiptDTO(int receipt_ID, String import_date, String store_owner_username, int total) {
        this.receipt_ID = receipt_ID;
        this.import_date = import_date;
        this.store_owner_username = store_owner_username;
        this.total = total;
    }

    public int getReceipt_ID() {
        return receipt_ID;
    }

    public void setReceipt_ID(int receipt_ID) {
        this.receipt_ID = receipt_ID;
    }

    public String getImport_date() {
        return import_date;
    }

    public void setImport_date(String import_date) {
        this.import_date = import_date;
    }

    public String getStore_owner_username() {
        return store_owner_username;
    }

    public void setStore_owner_username(String store_owner_username) {
        this.store_owner_username = store_owner_username;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    
}
