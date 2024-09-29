/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package proj;

import proj.io.CouponIO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import org.apache.ibatis.session.SqlSession;
import proj.vo.CouponVO;
import proj.vo.UserVO;

/**
 *
 * @author 2015k
 */
public class Coupon_Table_Dialog extends javax.swing.JDialog {

    /**
     * Creates new form Coupon_Table_Dialog
     */
    public String coupon_idx;
    public String coupon_name;
    public String discount;  
    public String chkymd;
    public String cntymd;
    private int editMode;
    Coupon_table f;
    Main mainform;
    // 추가
    public Coupon_Table_Dialog(java.awt.Frame parent, String editMode) {
        initComponents();
        this.f = (Coupon_table)parent;
        this.editMode = 1;
        spYMD.setValue(1);
        lbTitle.setText("쿠폰추가");
        btnApply.setText("추가");
        setSpinner();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // 수정, 삭제
    public Coupon_Table_Dialog(java.awt.Frame parent, String editMode, String coupon_idx, String coupon_name, String coupon_price, String chkymd, String cntymd) {
        initComponents();
        this.f = (Coupon_table)parent;
        this.coupon_idx = coupon_idx;
        this.coupon_name = coupon_name;
        this.discount = coupon_price;
        this.chkymd = chkymd;
        this.cntymd = cntymd;
        spYMD.setValue(1);
        
        switch (editMode){
            case "수정":
                tf_Coupon_name.setText(coupon_name);
                tf_Coupon_price.setText(coupon_price);
                lbTitle.setText("쿠폰수정");
                btnApply.setText("수정");
                cboCoupon.setEnabled(false);
                cboCouponYMD.setEnabled(false);
                spYMD.setEnabled(false);
                switch(chkymd){
                    case "가입쿠폰(년)":
                        cboCouponYMD.setSelectedIndex(0);
                        cboCoupon.setSelectedIndex(0);
                        break;
                    case "가입쿠폰(월)":
                        cboCouponYMD.setSelectedIndex(1);
                        cboCoupon.setSelectedIndex(0);
                        break;
                    case "가입쿠폰(일)":
                        cboCouponYMD.setSelectedIndex(2);
                        cboCoupon.setSelectedIndex(0);
                        break;
                    default:
                        cboCoupon.setSelectedIndex(1);
                        break;
                }
                if (cntymd != null)
                    spYMD.setValue(Integer.parseInt(cntymd));
                this.editMode = 2;
                break;
            case "삭제":
                tf_Coupon_name.setText(coupon_name);
                tf_Coupon_price.setText(coupon_price);
                tf_Coupon_name.setEnabled(false);
                tf_Coupon_price.setEnabled(false);
                cboCoupon.setEnabled(false);
                cboCouponYMD.setEnabled(false);
                spYMD.setEnabled(false);
                lbTitle.setText("쿠폰삭제");
                btnApply.setText("삭제");                
                switch(chkymd){
                    case "가입쿠폰(년)":
                        cboCouponYMD.setSelectedIndex(0);
                        cboCoupon.setSelectedIndex(0);
                        break;
                    case "가입쿠폰(월)":
                        cboCouponYMD.setSelectedIndex(1);
                        cboCoupon.setSelectedIndex(0);
                        break;
                    case "가입쿠폰(일)":
                        cboCouponYMD.setSelectedIndex(2);
                        cboCoupon.setSelectedIndex(0);
                        break;
                    default:
                        cboCoupon.setSelectedIndex(1);
                        break;
                }
                if (cntymd != null)
                    spYMD.setValue(Integer.parseInt(cntymd));
                this.editMode = 3;
                break;
        }
        setSpinner();
        this.setVisible(true);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tf_Coupon_name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tf_Coupon_price = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboCoupon = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboCouponYMD = new javax.swing.JComboBox<>();
        spYMD = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lbTitle.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        lbTitle.setText("쿠폰추가");

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tf_Coupon_name.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel3.setText("쿠폰명");

        jLabel4.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel4.setText("할인금액");

        tf_Coupon_price.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel5.setText("쿠폰종류");

        cboCoupon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "가입쿠폰", "일시지급쿠폰" }));
        cboCoupon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCouponItemStateChanged(evt);
            }
        });
        cboCoupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCouponActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel6.setText("연/월/일");

        cboCouponYMD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "년", "월", "일" }));
        cboCouponYMD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCouponYMDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spYMD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCouponYMD, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_Coupon_price, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_Coupon_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCoupon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_Coupon_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tf_Coupon_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboCoupon)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboCouponYMD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spYMD)))
                .addContainerGap())
        );

        jButton1.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jButton1.setText("닫기");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnApply.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        btnApply.setText("저장");
        btnApply.setToolTipText("");
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lbTitle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnApply)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApply)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        // TODO add your handling code here:
        crud_coupon();
    }//GEN-LAST:event_btnApplyActionPerformed

    private void cboCouponItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCouponItemStateChanged
        // TODO add your handling code here:
        if (cboCoupon.getSelectedIndex() == 1) // 일시지급
        {
            spYMD.setEnabled(false);
            cboCouponYMD.setEnabled(false);
        } else{
            spYMD.setEnabled(true);
            cboCouponYMD.setEnabled(true);
        }
    }//GEN-LAST:event_cboCouponItemStateChanged

    private void cboCouponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCouponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCouponActionPerformed

    private void cboCouponYMDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCouponYMDActionPerformed
        // TODO add your handling code here:
        System.out.println(cboCouponYMD.getSelectedIndex());
    }//GEN-LAST:event_cboCouponYMDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Coupon_Table_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Coupon_Table_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Coupon_Table_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Coupon_Table_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JComboBox<String> cboCoupon;
    private javax.swing.JComboBox<String> cboCouponYMD;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JSpinner spYMD;
    private javax.swing.JTextField tf_Coupon_name;
    private javax.swing.JTextField tf_Coupon_price;
    // End of variables declaration//GEN-END:variables
    
    private void crud_coupon(){
        coupon_name = tf_Coupon_name.getText().trim();
        discount = tf_Coupon_price.getText().trim();
        
        SqlSession ss = Main.factory.openSession();
        int res = 0;
        Map<String, String> map = new HashMap<>();
        
        switch (editMode){
            case 1: // 추가
                map.put("coupon_name", coupon_name);
                map.put("discount", discount);
                if (cboCoupon.getSelectedIndex() == 0){
                    map.put("chkymd", Integer.toString(cboCouponYMD.getSelectedIndex()+1));
                    String tmp = String.valueOf(spYMD.getValue());
                    map.put("cntymd", tmp);
                } else {
                    map.put("chkymd", "0");
                    map.put("cntymd", null);
                }
                res = ss.insert("coupon.coupon_insert", map);
                break;
            case 2: // 수정
                map.put("coupon_name", coupon_name);
                map.put("discount", discount);
                map.put("coupon_idx", coupon_idx);
                if (cboCoupon.getSelectedIndex() == 0){
                    map.put("chkymd", Integer.toString(cboCouponYMD.getSelectedIndex()+1));
                    String tmp = String.valueOf(spYMD.getValue());
                    map.put("cntymd", tmp);
                } else {
                    map.put("chkymd", "0");
                    map.put("cntymd", null);
                }
                res = ss.update("coupon.coupon_update", map);
                break;
            case 3: // 삭제
                res = ss.delete("coupon.coupon_delete", coupon_idx);
                break;
        }
        if (res != 1){
            if (ss != null)
                ss.close();
            JOptionPane.showMessageDialog(this, "쿠폰 CRUD 오류");
        }
        
        ss.commit();
        if (ss != null)
            ss.close();
        
        if (editMode == 1 && cboCoupon.getSelectedIndex() == 1) // 일시지급쿠폰
            addImediateCoupon();
        else {
            CouponIO.giveDateCoupon();
        }
        f.setTable();
        dispose();
    }
    
    private void setSpinner(){
        spYMD.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spYMD.getValue() < 1 ){
                    spYMD.setValue(1);
                }
            }
        });
    }
    
    List<UserVO> u_list;
    private void addImediateCoupon(){
        SqlSession ss = Main.factory.openSession();
        String coupon_idxx = ss.selectOne("coupon.search_max");
        u_list = ss.selectList("user.select_by_phone", null);
        
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < u_list.size(); i++){
            UserVO uvo = u_list.get(i);
            map.put("u_idx", uvo.getU_idx());
            map.put("coupon_idx", coupon_idxx);
            int result = ss.insert("couponlist.imediate_coupon", map);
            if (result != 1){
                JOptionPane.showMessageDialog(this, "일시지급쿠폰 지급 오류");
                return;
            }
        }
        
        ss.commit();
        if (ss != null)
            ss.close();
    }
    
    
}