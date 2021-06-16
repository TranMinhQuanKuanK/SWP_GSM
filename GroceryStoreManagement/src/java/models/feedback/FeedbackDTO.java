/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.feedback;

import java.io.Serializable;
import java.sql.Timestamp;
import models.account.AccountDTO;

/**
 *
 * @author Tran Minh Quan
 */
public class FeedbackDTO  implements Serializable { 
    private int feedback_ID;
    private Timestamp feedback_date;
    private String feedback_content;
    private boolean is_seen;
    private AccountDTO account;

    public FeedbackDTO() {
    }

    public FeedbackDTO(int feedback_ID, Timestamp feedback_date, String feedback_content, boolean is_seen, AccountDTO account) {
        this.feedback_ID = feedback_ID;
        this.feedback_date = feedback_date;
        this.feedback_content = feedback_content;
        this.is_seen = is_seen;
        this.account = account;
    }

    public int getFeedback_ID() {
        return feedback_ID;
    }

    public void setFeedback_ID(int feedback_ID) {
        this.feedback_ID = feedback_ID;
    }

    public Timestamp getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(Timestamp feedback_date) {
        this.feedback_date = feedback_date;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public boolean isIs_seen() {
        return is_seen;
    }

    public void setIs_seen(boolean is_seen) {
        this.is_seen = is_seen;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }
      
}
