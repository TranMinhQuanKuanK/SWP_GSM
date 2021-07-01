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
public class FinancialChartDataObj {
    List<String> events;
    List<Integer> revenue, profit, cost;

    public FinancialChartDataObj(List<String> events, List<Integer> revenue, List<Integer> profit, List<Integer> cost) {
        this.events = events;
        this.revenue = revenue;
        this.profit = profit;
        this.cost = cost;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public List<Integer> getRevenue() {
        return revenue;
    }

    public void setRevenue(List<Integer> revenue) {
        this.revenue = revenue;
    }

    public List<Integer> getProfit() {
        return profit;
    }

    public void setProfit(List<Integer> profit) {
        this.profit = profit;
    }
    
    public List<Integer> getCost() {
        return cost;
    }

    public void setCost(List<Integer> cost) {
        this.cost = cost;
    }
}
