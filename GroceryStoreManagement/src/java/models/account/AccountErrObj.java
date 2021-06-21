/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.account;

import java.io.Serializable;

/**
 *
 * @author Tran Minh Quan + Huu Quoc
 */
public class AccountErrObj implements Serializable {
   private boolean hasError;
   private String currentPasswordError;
   private String newPasswordError;
   private String resetPasswordError;
   private String deleteAccountError;

    public AccountErrObj() {
        this.hasError = false;
        this.currentPasswordError = "";
        this.newPasswordError = "";
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getCurrentPasswordError() {
        return currentPasswordError;
    }

    public void setCurrentPasswordError(String currentPasswordError) {
        this.currentPasswordError = currentPasswordError;
    }

    public String getNewPasswordError() {
        return newPasswordError;
    }

    public void setNewPasswordError(String newPasswordError) {
        this.newPasswordError = newPasswordError;
    }

    public String getResetPasswordError() {
        return resetPasswordError;
    }

    public void setResetPasswordError(String resetPasswordError) {
        this.resetPasswordError = resetPasswordError;
    }

    public String getDeleteAccountError() {
        return deleteAccountError;
    }

    public void setDeleteAccountError(String deleteAccountError) {
        this.deleteAccountError = deleteAccountError;
    }
}
