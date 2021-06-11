/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.sessionBill;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import models.customer.CustomerDTO;

/**
 *
 * @author Tran Minh Quan
 */
public class BillObj implements Serializable {

    private ArrayList<BillItemObject> Bill_Detail;
    private String phone_no;
    private Timestamp buy_date;
    private String cashier_username;
    private int total_cost;
   // private int point_used;
    private int cash;
    boolean use_point; //thêm
    CustomerDTO customer_dto; //thêm
    BillErrObj err_obj;//thêm 10/6

    public BillObj() {
        this.Bill_Detail = new ArrayList<>();
        this.phone_no = null;
        this.buy_date = null;
        this.cashier_username = null;
        this.total_cost = 0;
        //this.point_used = 0;
        this.cash = 0;
        this.use_point = false;
        this.customer_dto = null;
        this.err_obj = new BillErrObj();
    }

    public BillErrObj getErr_obj() {
        return err_obj;
    }

    public void setErr_obj(BillErrObj err_obj) {
        this.err_obj = err_obj;
    }
    

    public CustomerDTO getCustomer_dto() {
        return customer_dto;
    }

    public void setCustomer_dto(CustomerDTO customer_dto) {
        this.customer_dto = customer_dto;
    }

    public boolean isUse_point() {
        return use_point;
    }

    public void setUse_point(boolean use_point) {
        this.use_point = use_point;
    }

    public ArrayList<BillItemObject> getBill_Detail() {
        return Bill_Detail;
    }

    public void setBill_Detail(ArrayList<BillItemObject> Bill_Detail) {
        this.Bill_Detail = Bill_Detail;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public Timestamp getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(Timestamp buy_date) {
        this.buy_date = buy_date;
    }

    public String getCashier_username() {
        return cashier_username;
    }

    public void setCashier_username(String cashier_username) {
        this.cashier_username = cashier_username;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }
//
//    public int getPoint_used() {
//        return point_used;
//    }
//
//    public void setPoint_used(int point_used) {
//        this.point_used = point_used;
//    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

}
