package proj.vo;

public class CouponVO {

	public String coupon_name, chkymd, cntymd;

    public String getChkymd() {
        return chkymd;
    }

    public void setChkymd(String chkymd) {
        this.chkymd = chkymd;
    }

    public String getCntymd() {
        return cntymd;
    }

    public void setCntymd(String cntymd) {
        this.cntymd = cntymd;
    }
	
	private int coupon_idx, discount;

	public String getCoupon_name() {
		return coupon_name;
	}

	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}

	public int getCoupon_idx() {
		return coupon_idx;
	}

	public void setCoupon_idx(int coupon_idx) {
		this.coupon_idx = coupon_idx;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}


}
