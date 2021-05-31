/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.category;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import utils.DBHelpers;

/**
 *
 * @author Tran Minh Quan
 */
public class CategoryDAO implements Serializable{

    private ArrayList<CategoryDTO> listCategory = new ArrayList<>();

    public ArrayList<CategoryDTO> GetAllCategory() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT category_ID, name, info "
                        + "FROM category ";

                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int category_ID = rs.getInt("category_ID");
                    String name = rs.getString("name");
                    String info = rs.getString("info");
                    CategoryDTO cDTO = new CategoryDTO(category_ID, name, info);
                    listCategory.add(cDTO);
                } 
                return listCategory;
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

    public CategoryDTO GetCategoryByID(int ID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT category_ID, name, info "
                        + "FROM category "
                        + "WHERE category_ID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, ID);
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    int category_ID = rs.getInt("category_ID");
                    String name = rs.getString("name");
                    String info = rs.getString("info");
                    CategoryDTO cDTO = new CategoryDTO(category_ID, name, info);
                    return cDTO;
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

        return null;
    }
}
