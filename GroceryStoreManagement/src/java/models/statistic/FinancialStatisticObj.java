/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.statistic;

import java.util.List;

/**
 *
 * @author Huu Quoc
 */
public class FinancialStatisticObj {
    int countBill, countReceipt, sumRevenue, sumProfit, sumCost;

    public FinancialStatisticObj() {
    }

    public int getSumRevenue() {
        return sumRevenue;
    }

    public void setSumRevenue(int sumRevenue) {
        this.sumRevenue = sumRevenue;
    }

    public int getSumProfit() {
        return sumProfit;
    }

    public void setSumProfit(int sumProfit) {
        this.sumProfit = sumProfit;
    }

    public int getSumCost() {
        return sumCost;
    }

    public void setSumCost(int sumCost) {
        this.sumCost = sumCost;
    }
    
    public int getCountBill() {
        return countBill;
    }

    public void setCountBill(int countBill) {
        this.countBill = countBill;
    }

    public int getCountReceipt() {
        return countReceipt;
    }

    public void setCountReceipt(int countReceipt) {
        this.countReceipt = countReceipt;
    }
}
