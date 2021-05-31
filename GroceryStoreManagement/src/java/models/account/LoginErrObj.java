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
public class LoginErrObj implements Serializable {

    private String LoginErr;
    private boolean has_Error;
    private int user_type;

    public LoginErrObj(boolean has_Error, int user_type) {
        this.has_Error = has_Error; 
        this.user_type = user_type;
    }

    public LoginErrObj(String LoginErr) {
        this.LoginErr = LoginErr;
        this.has_Error = true;
       
    }

   
}
