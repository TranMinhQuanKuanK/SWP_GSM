/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.previousBill;

import java.util.List;

/**
 *
 * @author Huu Quoc
 */
public class PreBillDTO {
    int billID, totalCost, pointUsed, cash;
    String name, phoneNo, buyDate, cashier;
    List<PreBillDetailDTO> details;

    public PreBillDTO(int billID, int totalCost, int pointUsed, int cash, String name, String phoneNo, String buyDate, String cashier, List<PreBillDetailDTO> details) {
        this.billID = billID;
        this.totalCost = totalCost;
        this.pointUsed = pointUsed;
        this.cash = cash;
        this.name = name;
        this.phoneNo = phoneNo;
        this.buyDate = buyDate;
        this.cashier = cashier;
        this.details = details;
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

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public int getPointUsed() {
        return pointUsed;
    }

    public void setPointUsed(int pointUsed) {
        this.pointUsed = pointUsed;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
    
    public List<PreBillDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<PreBillDetailDTO> details) {
        this.details = details;
    }
}
