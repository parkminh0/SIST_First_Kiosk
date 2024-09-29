/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proj.vo;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import proj.vo.MenuVO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import proj.Main;
import proj.MenuDialog;

public class MenuPanelVO {
    public MenuVO m_vo;
    public JButton bt;
    public JLabel label;
    public JLabel label2;
    public JPanel p;
    Main f;

    public MenuPanelVO(Main f) {
        this.f = f;
        
        Image img = new ImageIcon(getClass().getResource("/Images/menu/menu_plus.png")).getImage();
        Image updateImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        bt = new JButton();
        bt.setPreferredSize(new java.awt.Dimension(100, 100));
        bt.setIcon(new ImageIcon(updateImg));
        //bt.setIcon(new javax.swing.ImageIcon(getClass().getResource(m_vo.getImg_url()))); // m_vo.icon
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDialog md = new MenuDialog("추가",f);
            }
        });
        bt.setBackground(new Color(255, 255, 255));
        
        label = new JLabel();
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("추가");
        
        p = new JPanel();
        p.setLayout(new java.awt.BorderLayout());
        p.add(bt, java.awt.BorderLayout.PAGE_START);
        p.add(label, java.awt.BorderLayout.CENTER);
        p.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                MenuDialog md = new MenuDialog("추가",f);
                
            }
        });
    }
    
    
    
    public MenuPanelVO (MenuVO m_vo, Main f, int is_admin) {
        this.m_vo = m_vo;
        this.f = f;
        
        Image img = new ImageIcon(getClass().getResource(m_vo.getImg_url())).getImage();
        Image updateImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        bt = new JButton();
        bt.setPreferredSize(new java.awt.Dimension(100, 100));
        bt.setIcon(new ImageIcon(updateImg));
        //bt.setIcon(new javax.swing.ImageIcon(getClass().getResource(m_vo.getImg_url()))); // m_vo.icon
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_admin==0){
                    boolean chk = f.chk_list(m_vo);
                    if (chk == true){
                        f.b_list.add(m_vo);
                        f.basket_list();
                    }
                } else {
                    MenuDialog md = new MenuDialog("수정",m_vo,f);
                }
            }
        });
        
        label = new JLabel();
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText(m_vo.m_name);
        label2 = new JLabel();
        label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label2.setText(m_vo.price);
        
        p = new JPanel();
        p.setLayout(new java.awt.BorderLayout());
        p.add(bt, java.awt.BorderLayout.PAGE_START);
        p.add(label, java.awt.BorderLayout.CENTER);
        p.add(label2, java.awt.BorderLayout.PAGE_END);
        p.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                
                if(is_admin==0){
                    boolean chk = f.chk_list(m_vo);
                    if (chk == true){
                        f.b_list.add(m_vo);
                        f.basket_list();
                    }
                } else {
                    MenuDialog md = new MenuDialog("수정",m_vo,f);
                }
                
            }
        });
        
        if (is_admin == 0 && Integer.parseInt(m_vo.stock) == 0){
            System.out.println("재고 0임");
            MouseListener ml = p.getMouseListeners()[0];
            p.removeMouseListener(ml);
            bt.setEnabled(false);
            label2.setText("재고 없음");          
        }
    }
}
