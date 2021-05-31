/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.category;

import java.io.Serializable;

/**
 *
 * @author Tran Minh Quan
 */
public class CategoryDTO implements Serializable {
    private int category_ID;
    private String name;
    private String info;

    public CategoryDTO(int id, String name, String info) {
        this.category_ID = id;
        this.name = name;
        this.info = info;
    }

    public int getCategoryID() {
        return category_ID;
    }

    public void setCategoryID(int id) {
        this.category_ID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
}
