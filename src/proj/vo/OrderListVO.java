package proj.vo;

import java.util.ArrayList;
import java.util.List;

import proj.Main;
import proj.OrderDialog;

public class OrderListVO extends Thread{
	private String ol_idx, u_idx, odr_date, total_cnt, odr_price, is_coupon, dis_price, total_price, u_phone;
	private List<OrderVO> o_list;

	private OrderDialog f;

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }
        
	public OrderListVO(){};
        
	public OrderListVO(String u_idx, String odr_date, String total_cnt, String odr_price, String is_coupon,
			String dis_price, String total_price, ArrayList<OrderVO> o_list) {
		this.u_idx = u_idx;
		this.odr_date = odr_date;
		this.total_cnt = total_cnt;
		this.odr_price = odr_price;
		this.is_coupon = is_coupon;
		this.dis_price = dis_price;
		this.total_price = total_price;
		this.o_list = o_list;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public void setOl_idx(String ol_idx) {
		this.ol_idx = ol_idx;
	}

	public void setU_idx(String u_idx) {
		this.u_idx = u_idx;
	}

	public void setOdr_date(String odr_date) {
		this.odr_date = odr_date;
	}

	public void setTotal_cnt(String total_cnt) {
		this.total_cnt = total_cnt;
	}

	public void setOdr_price(String odr_price) {
		this.odr_price = odr_price;
	}

	public void setIs_coupon(String is_coupon) {
		this.is_coupon = is_coupon;
	}

	public void setDis_price(String dis_price) {
		this.dis_price = dis_price;
	}

	public void setO_list(List<OrderVO> o_list) {
		this.o_list = o_list;
	}
	public void setF(OrderDialog f) {
            this.f = f;
	}

	public String getOl_idx() {
		return ol_idx;
	}

	public String getU_idx() {
		return u_idx;
	}

	public String getOdr_date() {
		return odr_date;
	}

	public String getTotal_cnt() {
		return total_cnt;
	}

	public String getOdr_price() {
		return odr_price;
	}

	public String getIs_coupon() {
		return is_coupon;
	}

	public String getDis_price() {
		return dis_price;
	}

	public String getTotal_price() {
		return total_price;
	}

	public List<OrderVO> getO_list() {
		return o_list;
	}

	
	@Override
	public void run() {
            while(f.totalTime>0) {
                    try {
                            Thread.sleep(1000);
                            f.threadTest();
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                    }

            }
	}
}