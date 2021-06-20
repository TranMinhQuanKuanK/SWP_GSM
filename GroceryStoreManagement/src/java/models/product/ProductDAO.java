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
                while (rs.next()) {
                    boolean isRightRecord = true;
                    if (category_id != null && !(rs.getInt("category_ID") == category_id)) {
                        isRightRecord = false;
                    }
                    if (search_value != null && !StringNormalizer.normalize(rs.getString("name")).contains(StringNormalizer.normalize(search_value))) {
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

    public ArrayList<ProductDTO> GetProductListForCashier(Integer category_id,
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
                    if (isRightRecord && rs.getBoolean("is_selling")) {
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

    //thêm số lượng vào db, trả vè true nếu món đó dưới ngưỡng, false nếu ko
    public boolean AddQuantityToProduct(int ProductID, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                Integer currentQuantity = 0;
                Integer lower_threshold = 0;

                String sql = "SELECT quantity, lower_threshold "
                        + " FROM product WHERE product_ID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, ProductID);
                rs = stm.executeQuery();

                if (rs.next()) {
                    currentQuantity = rs.getInt("quantity");
                    lower_threshold = rs.getInt("lower_threshold");
                }
                sql = "UPDATE product "
                        + "SET quantity = ?"
                        + " WHERE product_ID = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, currentQuantity + quantity);
                stm.setInt(2, ProductID);
                int rowAffect = stm.executeUpdate();
                //return
                if (currentQuantity + quantity <= lower_threshold) {
                    return true;
                } else {
                    return false;
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

    // kiểm tra hàng hóa đã có tên tương tự trong hệ thống chưa
    // nếu trùng ID thì coi như false
    public boolean ConfirmMatchedProduct(String productName, int productID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT product_ID, name FROM product";

                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                productName = StringNormalizer.normalize(productName);
                System.out.println(productName);
                while (rs.next()) {
                    boolean bool = StringNormalizer.normalize(rs.getString("name")).equals(productName);
                    if (bool) {
                        if (productID == 0) return bool;
                        else {
                            System.out.println(StringNormalizer.normalize(rs.getString("name")));
                        System.out.println(rs.getInt("product_ID") != productID);
                        return rs.getInt("product_ID") != productID;
                        }
                    }
                }
                return false;
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

    //thay đổi thông tin hàng hóa vào db, trả vè true nếu thay đổi thành công, false nếu ko
    public boolean UpdateProductInfo(int ProductID, String productName, int categoryID, int threshold, int costPrice, int sellingPrice, String unitLabel, String location, boolean isSelling) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

//                String sql = "SELECT product_ID, name, cost_price, selling_price, lower_threshold, category_ID, unit_label, is_selling, location "
//                        + " FROM product WHERE product_ID = ?";
                String sql = "UPDATE product "
                        + "SET name = ?,"
                        + "cost_price = ?,"
                        + "selling_price = ?,"
                        + "lower_threshold = ?,"
                        + "category_ID = ?,"
                        + "unit_label = ?,"
                        + "location = ?,"
                        + "is_selling = ?"
                        + " WHERE product_ID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, productName);
                stm.setInt(2, costPrice);
                stm.setInt(3, sellingPrice);
                stm.setInt(4, threshold);
                stm.setInt(5, categoryID);
                stm.setString(6, unitLabel);
                stm.setString(7, location);
                stm.setBoolean(8, isSelling);
                stm.setInt(9, ProductID);

                int rowAffect = stm.executeUpdate();
                //return
                System.out.println(rowAffect > 0);
                return rowAffect > 0;

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
    
    public boolean changeQuantity(Integer productID, Integer quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "UPDATE product\n"
                        + "SET quantity = ?\n"
                        + "WHERE product_ID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setInt(2, productID);
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
     public boolean AddNewProduct( String productName, int categoryID, int threshold, int costPrice, int sellingPrice, String unitLabel, String location, boolean isSelling) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {


                String sql = "INSERT INTO product(name, quantity, cost_price, selling_price, lower_threshold, category_ID, unit_label, location, is_selling) "
                        + "VALUES(?, 0, ?, ?, ?, ?, ?, ?, ?)"; 
                
                stm = con.prepareStatement(sql);
                stm.setNString(1, productName);
                stm.setInt(2, costPrice);
                stm.setInt(3, sellingPrice);
                stm.setInt(4, threshold);
                stm.setInt(5, categoryID);
                stm.setNString(6, unitLabel);
                stm.setNString(7, location);
                stm.setBoolean(8, isSelling);

                int rowAffect = stm.executeUpdate();
                
                return rowAffect > 0;
            }

        } finally {
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
