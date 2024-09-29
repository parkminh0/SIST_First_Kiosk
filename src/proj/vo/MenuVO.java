/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proj.vo;

/**
 *
 * @author a
 */
public class MenuVO {
    public String m_idx, m_name, img_url, price, unit_price, desc, category_idx, brew_time, stock;


    public void setM_idx(String m_idx) {
        this.m_idx = m_idx;
    }
    
    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }
    
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCategory_idx(String category_idx) {
        this.category_idx = category_idx;
    }

    public void setBrew_time(String brew_time) {
        this.brew_time = brew_time;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
    
    public String getM_idx() {
        return m_idx;
    }
    
    public String getM_name() {
        return m_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getPrice() {
        return price;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public String getDesc() {
        return desc;
    }

    public String getCategory_idx() {
        return category_idx;
    }

    public String getBrew_time() {
        return brew_time;
    }

    public String getStock() {
        return stock;
    }
}