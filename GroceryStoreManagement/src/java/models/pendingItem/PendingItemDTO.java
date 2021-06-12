/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.pendingItem;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author quan6
 */
public class PendingItemDTO implements Serializable {
    private Integer pending_ID;
    private Integer product_ID;
    private Timestamp pending_date;
    private boolean is_resolved;
    private String note;

    public PendingItemDTO(Integer pending_ID, Integer product_ID, Timestamp pending_date, boolean is_resolved, String note) {
        this.pending_ID = pending_ID;
        this.product_ID = product_ID;
        this.pending_date = pending_date;
        this.is_resolved = is_resolved;
        this.note = note;
    }

    public Integer getPending_ID() {
        return pending_ID;
    }

    public void setPending_ID(Integer pending_ID) {
        this.pending_ID = pending_ID;
    }

    public Integer getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(Integer product_ID) {
        this.product_ID = product_ID;
    }

    public Timestamp getPending_date() {
        return pending_date;
    }

    public void setPending_date(Timestamp pending_date) {
        this.pending_date = pending_date;
    }

    public boolean isIs_resolved() {
        return is_resolved;
    }

    public void setIs_resolved(boolean is_resolved) {
        this.is_resolved = is_resolved;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
