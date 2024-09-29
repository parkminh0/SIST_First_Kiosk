package proj.vo;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class CouponListPanelVO  {
    public CouponVO cp_vo;
    public JRadioButton jrb;
    public JPanel p;

    public CouponListPanelVO(CouponVO cp_vo) {
        this.cp_vo = cp_vo;
        jrb = new JRadioButton(cp_vo.getCoupon_name());
    }
}