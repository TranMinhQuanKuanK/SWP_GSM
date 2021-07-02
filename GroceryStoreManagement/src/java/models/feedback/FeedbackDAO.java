/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.feedback;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.NamingException;
import models.account.AccountDTO;
import utils.DBHelpers;

/**
 *
 * @author Tran Minh Quan
 */
public class FeedbackDAO implements Serializable {
        public boolean createFeedback(Timestamp feedback_date,
                String feedback_content, String cashier_username)
            throws SQLException,NamingException  {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                //B2. create SQL string 

                String sql = "INSERT INTO cashier_feedback "
                        + " (feedback_date, feedback_content,"
                        + " is_seen, cashier_username) "
                        + " VALUES (?,?,?,?)";

                stm = con.prepareStatement(sql);
                stm.setTimestamp(1, feedback_date);
                stm.setString(2, feedback_content);
                stm.setBoolean(3, false);
                stm.setString(4, cashier_username);
   
                int rowAffect = stm.executeUpdate();

                if (rowAffect == 1) {
                    return true;
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false;
    }
        
    ArrayList<FeedbackDTO> listFeedback = new ArrayList<FeedbackDTO>();
        
    public ArrayList<FeedbackDTO> getAllFeedbackList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try{
            con = DBHelpers.makeConnection();
            if (con != null) {
                // create sql string
                String sql = "SELECT feedback_ID, feedback_date, feedback_content, is_seen, username, password_acc, name, is_owner\n"
                            + "FROM cashier_feedback\n"
                            + "JOIN account\n" 
                            + "ON account.username = cashier_feedback.cashier_username\n"
                            + "ORDER BY feedback_date DESC";
                
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                
                while (rs.next()){
                    int feedback_ID = rs.getInt("feedback_ID");
                    Timestamp feedback_date = rs.getTimestamp("feedback_date");
                    String feedback_content = rs.getString("feedback_content");
                    boolean is_seen = rs.getBoolean("is_seen");
                    // Create AccountDTO
                    String username = rs.getString("username");
                    String password = rs.getString("password_acc");
                    String name = rs.getString("name");
                    boolean is_owner = rs.getBoolean("is_owner");
                    AccountDTO tempAccountDTO = new AccountDTO(username, password, name, is_owner);
                    
                    FeedbackDTO tempFeedbackDTO = new FeedbackDTO(feedback_ID, feedback_date, feedback_content, is_seen, tempAccountDTO);
                    listFeedback.add(tempFeedbackDTO);
                }
                
                return listFeedback;
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return null;
    }
    
    public ArrayList<FeedbackDTO> getUnSeenFeedbackList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try{
            con = DBHelpers.makeConnection();
            if (con != null) {
                // create sql string
                String sql = "SELECT feedback_ID, feedback_date, feedback_content, is_seen, username, password_acc, name, is_owner\n"
                            + "FROM cashier_feedback\n"
                            + "JOIN account\n" 
                            + "ON account.username = cashier_feedback.cashier_username\n"
                            + "WHERE is_seen = 0\n"
                            + "ORDER BY feedback_date DESC";
                
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                
                while (rs.next()){
                    int feedback_ID = rs.getInt("feedback_ID");
                    Timestamp feedback_date = rs.getTimestamp("feedback_date");
                    String feedback_content = rs.getString("feedback_content");
                    boolean is_seen = rs.getBoolean("is_seen");
                    // Create AccountDTO
                    String username = rs.getString("username");
                    String password = rs.getString("password_acc");
                    String name = rs.getString("name");
                    boolean is_owner = rs.getBoolean("is_owner");
                    AccountDTO tempAccountDTO = new AccountDTO(username, password, name, is_owner);
                    
                    FeedbackDTO tempFeedbackDTO = new FeedbackDTO(feedback_ID, feedback_date, feedback_content, is_seen, tempAccountDTO);
                    listFeedback.add(tempFeedbackDTO);
                }
                
                return listFeedback;
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return null;
    }

    public boolean ChangeFeedbackToIsSeen(Integer feedback_ID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "UPDATE cashier_feedback\n"
                        + "SET is_seen = 1\n"
                        + "WHERE feedback_ID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, feedback_ID);

                int rowAffect = stm.executeUpdate();
                if (rowAffect > 0) {
                    return true;
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false;

    }
    
}
