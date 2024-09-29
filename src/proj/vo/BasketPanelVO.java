/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proj.vo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import proj.Main;

/**
 *
 * @author minhopark
 */
public class BasketPanelVO {
    public JPanel p;
    public JLabel lbImg, m_name, m_price, cnt;
    public JButton btDel;
    public JSpinner jsCnt;
    public JTextField tfM_name, tfM_price;
    public MenuVO mvo;
    Main f;
    
    public BasketPanelVO(MenuVO mvo, Main f){
        this.mvo = mvo;
        this.f = f;
        p = new JPanel();
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        Image img = new ImageIcon(getClass().getResource(mvo.getImg_url())).getImage();
        Image updateImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        
        lbImg = new JLabel();
        lbImg.setPreferredSize(new java.awt.Dimension(100, 100));
        lbImg.setIcon(new ImageIcon(updateImg));
        m_name = new JLabel("메뉴명");
        m_price = new JLabel("가격");
        cnt = new JLabel("수량");
        btDel = new JButton("X");
        btDel.setBackground(new java.awt.Color(255, 0, 0));
        SpinnerNumberModel numberModel = new SpinnerNumberModel(1, 1, 99, 1);
        jsCnt = new JSpinner(numberModel);
        ((JSpinner.DefaultEditor) jsCnt.getEditor()).getTextField().setEditable(false);
//        for (Component component : jsCnt.getComponents()) {
//            if (component.getName() != null && component.getName().endsWith("Button")) {
//                jsCnt.remove(component);
//            }
//        }
        tfM_name = new JTextField(mvo.m_name);
        tfM_price = new JTextField(mvo.price);
        p.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(p);
        p.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_name, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(m_price))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfM_price, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfM_name))
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(cnt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jsCnt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btDel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(m_name)
                            .addComponent(tfM_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(m_price)
                            .addComponent(tfM_price, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cnt)
                            .addComponent(jsCnt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btDel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        btDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.b_list.remove(BasketPanelVO.this.mvo);
                f.bp_list.remove(BasketPanelVO.this);
                f.delete_basket();
            }
        });
        
        tfM_name.setEditable(false);
        tfM_price.setEditable(false);
        
        jsCnt.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) jsCnt.getValue() > Integer.parseInt(mvo.stock) ){
                    JOptionPane.showMessageDialog(f, String.format("현재 남은 재고는 %s개 입니다.", mvo.stock));
                    jsCnt.setValue((int)jsCnt.getValue()-1);
                }
                f.setPrice();
            }
        });
    }
}
