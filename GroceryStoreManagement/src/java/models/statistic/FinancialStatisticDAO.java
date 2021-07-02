/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.statistic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utils.DBHelpers;

/**
 *
 * @author Huu Quoc
 */
public class FinancialStatisticDAO {
    
    public int getBillCount(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int countBill = 0;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT COUNT(*) count_bill FROM customer_bill "
                        + "WHERE ? <= buy_date AND buy_date <= ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    countBill = rs.getInt("count_bill");
                }
            }

            return countBill;
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
    }
    
    public int getReceiptCount(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int countReceipt = 0;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT COUNT(*) count_receipt FROM receipt "
                        + "WHERE ? <= import_date AND import_date <= ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    countReceipt = rs.getInt("count_receipt");
                }
            }

            return countReceipt;
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
    }
    
    public int getSumRevenue(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int sumRevenue = 0;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT SUM(total_cost) sum_revenue FROM customer_bill "
                        + "WHERE ? <= buy_date AND buy_date < ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                if (dateFrom.length() == 7) {
                    dateFrom += "-01";
                }
                if (dateTo.length() == 7) {
                    dateTo += "-01";
                }
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    sumRevenue = rs.getInt("sum_revenue");
                }
            }

            return sumRevenue;
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
    }
    
    public int getSumProfit(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int sumProfit = 0;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT SUM(profit) sum_profit FROM customer_bill "
                        + "WHERE ? <= buy_date AND buy_date < ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                if (dateFrom.length() == 7) {
                    dateFrom += "-01";
                }
                if (dateTo.length() == 7) {
                    dateTo += "-01";
                }
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    sumProfit = rs.getInt("sum_profit");
                }
            }

            return sumProfit;
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
    }
    
    public int getSumCost(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int sumCost = 0;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT SUM(total) sum_cost FROM receipt "
                        + "WHERE ? <= import_date AND import_date <= ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                if (dateFrom.length() == 7) {
                    dateFrom += "-01";
                }
                if (dateTo.length() == 7) {
                    dateTo += "-01";
                }
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    sumCost = rs.getInt("sum_cost");
                }
            }

            return sumCost;
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
    }
    
    public String nextMonth(String original) {
        String year = original.substring(0, 4);
        String month = original.substring(5);
        
        final String next[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "01"};
                
        month = next[Integer.parseInt(month)];
        
        if (month.equals("01")) {
            year = Integer.toString(Integer.parseInt(year) + 1);
        } 
        
        return year + '-' + month;
    }
}
