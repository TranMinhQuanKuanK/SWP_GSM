/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.previousBill;

/**
 *
 * @author Huu Quoc
 */
public class PreBillDTO {
    int billID;
    String name, phoneNo, buyDate, totalCost;

    public PreBillDTO(int billID, String name, String phoneNo, String buyDate, String totalCost) {
        this.billID = billID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.buyDate = buyDate;
        this.totalCost = totalCost;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
}
