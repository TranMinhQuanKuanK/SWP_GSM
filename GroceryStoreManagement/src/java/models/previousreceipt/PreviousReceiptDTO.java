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
    private String owner_name;
    private int total;
    
    public PreviousReceiptDTO(){
    }

    public PreviousReceiptDTO(int receipt_ID, String import_date, String owner_name, int total) {
        this.receipt_ID = receipt_ID;
        this.import_date = import_date;
        this.owner_name = owner_name;
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

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    

    
}
