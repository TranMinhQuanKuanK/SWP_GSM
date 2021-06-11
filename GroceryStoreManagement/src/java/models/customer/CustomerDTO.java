/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.customer;

import java.io.Serializable;

/**
 *
 * @author Tran Minh Quan
 */
public class CustomerDTO implements Serializable {
    private String phone_no;
    private String name;
    private int point;

    public CustomerDTO(String phone_no, String name, int point) {
        this.phone_no = phone_no;
        this.name = name;
        this.point = point;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
}
