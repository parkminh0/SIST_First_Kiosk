package proj.vo;

public class CouponListVO {
	
    private String cl_idx, u_idx;
    public CouponVO cvo;
    private int coupon_idx, coupon_qty;

    public CouponVO getCvo() {
        return cvo;
    }

    public void setCvo(CouponVO cvo) {
        this.cvo = cvo;
    }

	public String getCl_idx() {
		return cl_idx;
	}

	public void setCl_idx(String cl_idx) {
		this.cl_idx = cl_idx;
	}

	public String getU_idx() {
		return u_idx;
	}

	public void setU_idx(String u_idx) {
		this.u_idx = u_idx;
	}

	public int getCoupon_idx() {
		return coupon_idx;
	}

	public void setCoupon_idx(int coupon_idx) {
		this.coupon_idx = coupon_idx;
	}

	public int getCoupon_qty() {
		return coupon_qty;
	}

	public void setCoupon_qty(int coupon_qty) {
		this.coupon_qty = coupon_qty;
	}


	

}
