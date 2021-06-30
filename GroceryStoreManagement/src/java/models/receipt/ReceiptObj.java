/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.receipt;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author ROG STRIX
 */
public class ReceiptObj implements Serializable{
    private ArrayList<ReceiptItem> receipt_detail;
    private Timestamp import_date;
    private int total_cost;
    
    public ReceiptObj(){
        receipt_detail = new ArrayList<>();
        import_date = null;
        total_cost = 0;
    }

    public ArrayList<ReceiptItem> getReceipt_detail() {
        return receipt_detail;
    }

    public void setReceipt_detail(ArrayList<ReceiptItem> receipt_detail) {
        this.receipt_detail = receipt_detail;
    }

    public Timestamp getImport_date() {
        return import_date;
    }

    public void setImport_date(Timestamp import_date) {
        this.import_date = import_date;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }
    
    
}
