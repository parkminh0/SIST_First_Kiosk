package proj.vo;

public class OrderVO {
private String o_idx, m_idx, ol_idx, order_qty, price;
private MenuVO mvo;

    public String getOrder_qty() {
        return order_qty;
    }

    public void setOrder_qty(String order_qty) {
        this.order_qty = order_qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public void setO_idx(String o_idx) {
    this.o_idx = o_idx;
    }
    public void setM_idx(String m_idx) {
    this.m_idx = m_idx;
    }
    public void setOl_idx(String ol_idx) {
    this.ol_idx = ol_idx;
    }
    public void setMvo(MenuVO mvo) {
    this.mvo = mvo;
    }

    public String getO_idx() {
    return o_idx;
    }
    public String getM_idx() {
    return m_idx;
    }
    public String getOl_idx() {
    return ol_idx;
    }
    public MenuVO getMvo() {
    return mvo;
    }
}