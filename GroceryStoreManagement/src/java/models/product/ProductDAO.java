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
import utils.StringNormalizer;

/**
 *
 * @author Tran Minh Quan
 */
public class ProductDAO implements Serializable {

    private ArrayList<ProductDTO> listProduct = new ArrayList<>();

    public ArrayList<ProductDTO> GetProductList(Integer category_id,
            String search_value, boolean only_noos_items)
            throws SQLException, NamingException {

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
                StringNormalizer norm = new StringNormalizer();
                while (rs.next()) {
                    boolean isRightRecord = true;
                    if (category_id != null && !(rs.getInt("category_ID") == category_id)) {
                        isRightRecord = false;
                    }
                    if (search_value != null && !norm.normalize(rs.getString("name")).contains(norm.normalize(search_value))) {
                        isRightRecord = false;
                    }
                    if (only_noos_items && rs.getInt("quantity") > rs.getInt("lower_threshold")) {
                        isRightRecord = false;
                    }
                    if (isRightRecord) {
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

    //chưa test
    public ProductDTO GetProductByID(int id)
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT product_ID, name, quantity"
                        + ",cost_price,selling_price,lower_threshold,"
                        + " category_ID,unit_label,is_selling,location "
                        + " FROM product WHERE product_ID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
   
                if (rs.next()) {
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
                    return pDTO;
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
