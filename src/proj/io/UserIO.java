/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proj.io;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import proj.Main;
import static proj.Main.factory;
import proj.vo.UserVO;

/**
 *
 * @author user
 */
public class UserIO {
    
    Main f;
    public UserIO(){};
    
    public UserIO(Main f){
        this.f = f;
    };
    
    // 관리자 로그인
    public UserVO login(String id, String pw){
        SqlSession ss = Main.factory.openSession();
				
        Map<String, String> map = new HashMap<>();
        map.put("key_id", id);
        map.put("key_pw", pw);

        UserVO vo = ss.selectOne("user.login", map);

        if (ss != null) {
            ss.close();
        }
        
        return vo;
    }
    
    // SQL: u_idx 세팅
    public String getu_idx(String phone) {
        SqlSession ss = Main.factory.openSession();
        UserVO user = ss.selectOne("search_uidx", phone);
        if (ss != null) {
            ss.close();
    	}
        
        if (user == null)
            return null;
        
    	return user.getU_idx();
    }
    
    // 관리자 - 사용자 추가
    public int addUser(String phone){
        SqlSession ss = Main.factory.openSession();
        
        Map<String, String> u_map = new HashMap<>();
        String u_phone = phone;
        
        if(u_phone.length()==0)
            return 1;
        
        UserVO uvo = ss.selectOne("user.search_uidx",u_phone);
        if(uvo == null){
            u_map.put("u_phone", u_phone);
        } else {
            return 2;
        }
        
        int result = ss.insert("user.add_user",u_map);
        
        if(result==1){
            ss.commit();
            UserVO uvo2 = ss.selectOne("user.search_uidx", u_phone);
            int insert_res = ss.insert("couponlist.new_user", uvo2.getU_idx());
            if (insert_res == 1){
                ss.commit();
            }
            f.view_UserTable();
        }
        
        return result;
    }
    
    // 관리자 - 사용자 수정
    public int updateUser(String u_idx, String phone){
        SqlSession ss = Main.factory.openSession();
        
        Map<String, String> u_map = new HashMap<>();
        
        u_map.put("u_idx", u_idx);
        
        if(phone.length()==0){
            return 2;
        }
        
        UserVO uvo = ss.selectOne("user.search_uidx", phone);
        if(uvo == null){
            u_map.put("u_phone", phone);
        } else {
            return 3;
        }
        
        int result = ss.update("user.update_user",u_map);
        
        if(result==1){
            ss.commit();
            f.view_UserTable();
        }
        
        return result;
    }
    
    // 관리자 - 사용자 삭제
    public int deleteUser(String u_idx, String phone){
        SqlSession ss = Main.factory.openSession();
                
        int result = ss.update("user.delete_user",u_idx);
        
        if(result==1){
            ss.commit();
            f.view_UserTable();
        }
        
        return result;
    }
}
