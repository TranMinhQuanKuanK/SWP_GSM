/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.sessionBill;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tran Minh Quan
 */
public class BillErrObj implements Serializable {
    private ArrayList<String> error_list;
    boolean hasError;

    public BillErrObj() {
        error_list = new ArrayList<>();
        hasError = false;
    }

    public void appendError(String error) {
        this.error_list.add(error);
        this.hasError = true;
    }
    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
    
}
