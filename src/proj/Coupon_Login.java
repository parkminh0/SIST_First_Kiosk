/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proj;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import proj.io.UserIO;
import proj.vo.CouponListVO;
import proj.vo.CouponVO;
import proj.vo.UserVO;

/**
 *
 * @author 2015k
 */
public class Coupon_Login extends JFrame {
    Main f;
    List<CouponListVO> coupon_list;
    public String u_idx;
    boolean have_Coupon;
    
    /**
     * Creates new form NewJFrame
     */
    public Coupon_Login(Main f) {
        this.f = f;
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        tfPhone.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    c_login();
                }
            }
            
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        tfPhone = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel3.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("전화번호");

        tfPhone.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
        tfPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPhoneActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(51, 153, 255));
        btnSearch.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("조회");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(51, 153, 255));
        btnClose.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("닫기");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(tfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(btnSearch)
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnClose))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        c_login();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tfPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPhoneActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField tfPhone;
    // End of variables declaration//GEN-END:variables
    
    
    public boolean searchCoupon() {
        SqlSession ss = Main.factory.openSession();
        coupon_list = ss.selectList("couponlist.search_couponList", u_idx);
        
        if(ss!= null)
            ss.close();
        
        if (coupon_list.size() == 0) {
            JOptionPane.showMessageDialog(this, "쿠폰 없음");
            return false;
    	}
    	return true;
    }
    
    private void c_login(){
    // TODO add your handling code here:
        u_idx = null;
        String phone = tfPhone.getText().trim();
        UserIO uio = new UserIO();
        u_idx = uio.getu_idx(phone);
        if (u_idx == null){
            int result = JOptionPane.showConfirmDialog(this, "존재하지 않는 회원입니다.\r\n회원가입 하시겠습니까?");
            if (result == JOptionPane.YES_OPTION){
                dispose();
                UserDialog ud = new UserDialog(f, "추가", f, phone);
            }
            return;
        }
        have_Coupon = searchCoupon();
        if (have_Coupon == false)
                return;
        new CouponList(Coupon_Login.this, f);
        dispose();
    }
}
