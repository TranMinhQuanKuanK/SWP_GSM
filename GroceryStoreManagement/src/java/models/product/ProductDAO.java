/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import models.category.CategoryDAO;
import models.category.CategoryDTO;
import utils.DBHelpers;

/**
 *
 * @author Tran Minh Quan
 */
public class ProductDAO implements Serializable {
    private ArrayList<ProductDTO> listProduct = new ArrayList<>();
    public ArrayList<ProductDTO> GetAllProduct() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT product_ID, name, quantity"
                        + ",cost_price,selling_price,lower_threshold,"
                        + " category_ID,unit_label,is_selling,location "
                        + " FROM product ";

                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int product_ID = rs.getInt("product_ID");
                    String name = rs.getString("name");
                    int quantity = rs.getInt("quantity");
                    int cost_price = rs.getInt("cost_price");
                    int selling_price = rs.getInt("selling_price");
                    int lower_threshold = rs.getInt("lower_threshold");
                    int category_ID = rs.getInt("category_ID");
                    String unit_label = rs.getString("unit_label");
                    boolean is_selling = rs.getBoolean("is_selling");
                    String location = rs.getString("location");
                    
                    CategoryDAO cDAO = new CategoryDAO();
                    CategoryDTO cDTO = cDAO.GetCategoryByID(category_ID);
                    
                    ProductDTO pDTO = new ProductDTO(product_ID, name, quantity, 
                            cost_price, selling_price, lower_threshold, cDTO,
                            unit_label, is_selling, location);
                    listProduct.add(pDTO);
                }
                return listProduct;
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
