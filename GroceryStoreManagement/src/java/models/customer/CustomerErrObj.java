/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.customer;

import java.io.Serializable;

/**
 *
 * @author quan6
 */
public class CustomerErrObj implements Serializable {

    private boolean has_Error;
    private String phone_noError;

    public CustomerErrObj() {
        this.phone_noError = "";
        this.has_Error = false;
    }

    public boolean isHas_Error() {
        return has_Error;
    }

    public void setHas_Error(boolean has_Error) {
        this.has_Error = has_Error;
    }

    public String getPhone_noError() {
        return phone_noError;
    }

    public void setPhone_noError(String phone_noError) {
        this.phone_noError = phone_noError;
    }
    


 

}
