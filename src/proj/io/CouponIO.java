/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proj.io;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.ibatis.session.SqlSession;
import proj.Main;
import static proj.Main.factory;
import proj.vo.CouponVO;
import proj.vo.UserVO;

/**
 *
 * @author user
 */
public class CouponIO {
    
    public CouponIO(){};
    
    // 쿠폰 사용시 -1
    public static void minusCoupon(int coupon_index, String user_index){
        SqlSession ss = Main.factory.openSession();
        Map<String, String> map = new HashMap<>();
        map.put("coupon_idx",Integer.toString(coupon_index));
        map.put("u_idx",user_index);
        
        int result = ss.update("couponlist.coupon_consume",map);
        
        if(result==1)
            ss.commit();
        
        if(ss!=null)
            ss.close(); 
    }
    
    public static void giveDateCoupon(){
        SqlSession ss = Main.factory.openSession();
        List<UserVO> u_list = ss.selectList("user.select_by_phone", null); // u_idx랑 crt_dtm 가져옴
        List<CouponVO> c_list = ss.selectList("coupon.search_all"); // 쿠폰 테이블 가져옴
        
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < u_list.size(); i++){
            UserVO uvo = u_list.get(i);
            for (int j = 0; j < c_list.size(); j++){
                CouponVO cvo = c_list.get(j);
                if (cvo.getChkymd().equals("0")) // 일시지급쿠폰이라 continue (0 = 일시지급/ 1 = 연 / 2 = 월 / 3 = 일)
                    continue;
                
                Map<String, String> tempMap = new HashMap<>();
                tempMap.put("coupon_idx", Integer.toString(cvo.getCoupon_idx()));
                tempMap.put("u_idx", uvo.getU_idx());
                int result = ss.selectOne("couponlist.search_couponExist", tempMap); // 이미 그 쿠폰을 받았으면 pass
                if (result > 0){
                    continue;
                }
                
                LocalDateTime now = LocalDateTime.now();
                
                CharSequence tempChar = uvo.getCrt_dtm().substring(0, 10) + "T" + uvo.getCrt_dtm().substring(11);
                LocalDateTime uvoDtm = LocalDateTime.parse(tempChar);
                
                Duration dur = Duration.between(uvoDtm, now);
                long days = dur.toDays();
                int insert_coupon = 0;
                switch (cvo.getChkymd()){
                    case "1":
                        
                        if(Integer.parseInt(cvo.getCntymd()) * 365 <= days)
                            insert_coupon = ss.insert("couponlist.imediate_coupon", tempMap);
                        break;
                    case "2":
                        if(Integer.parseInt(cvo.getCntymd()) * 30 <= days)
                            insert_coupon = ss.insert("couponlist.imediate_coupon", tempMap);
                        break;
                    case "3":
                        if(Integer.parseInt(cvo.getCntymd()) <= days)
                            insert_coupon = ss.insert("couponlist.imediate_coupon", tempMap);
                        break;
                }
            }
        }
        ss.commit();
        if (ss != null)
            ss.close();
    }
    
    // 관리자 - 쿠폰관리
    List<CouponVO> coupon_list;
    public List<CouponVO> setCouponTable(){
        SqlSession ss = factory.openSession();
        coupon_list = ss.selectList("coupon.search_all");
        
        if (ss != null)
            ss.close();
        
        return coupon_list;
    }
}
