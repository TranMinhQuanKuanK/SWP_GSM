/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.account;

import java.io.Serializable;

/**
 *
 * @author Tran Minh Quan
 */
public class AccountDTO implements Serializable{
     private String username;
     private String password;
     private String name;
     private boolean is_owner;

    public AccountDTO(String username, String password, String name, boolean is_owner) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.is_owner = is_owner;
    }

    public AccountDTO(String username, String name, boolean is_owner) {
        this.username = username;
        this.name = name;
        this.is_owner = is_owner;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_owner() {
        return is_owner;
    }

    public void setIs_owner(boolean is_owner) {
        this.is_owner = is_owner;
    }
    
     
}
