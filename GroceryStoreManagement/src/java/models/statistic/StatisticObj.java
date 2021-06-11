/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.statistic;

/**
 *
 * @author Huu Quoc
 */
public class StatisticObj {

    int productID, quantity, total;
    String phoneNum;

    public StatisticObj(int productID, int quantity, int total) {
        this.productID = productID;
        this.quantity = quantity;
        this.total = total;
    }

    public StatisticObj(String phoneNum, int quantity, int total) {
        this.phoneNum = phoneNum;
        this.quantity = quantity;
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
