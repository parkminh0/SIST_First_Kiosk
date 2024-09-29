/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proj;

import proj.io.CouponIO;
import proj.vo.MenuPanelVO;
import proj.vo.MenuVO;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt​.Graphics;
import java.util.ArrayList;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import jdk.jfr.Event;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import proj.vo.AnalyzeVO;
import proj.vo.BasketPanelVO;
import proj.vo.CouponVO;
import proj.vo.OrderListVO;
import proj.vo.OrderVO;
import proj.vo.StockVO;
import proj.vo.UserVO;
/**
 *
 * @author a
 */
public class Main extends javax.swing.JFrame {
    List<MenuVO> m_list;
    public ArrayList<MenuVO> b_list; // 장바구니에 담겨있는 메뉴 리스트
    public ArrayList<BasketPanelVO> bp_list; // 장바구니 패널 리스트
    public int basket_y = 10; // 장바구니 패널용 초기 y값
    
    int orderPrice = 0; // 주문금액
    int orderQty = 0; // 주문수량
    public int totalPrice = 0; // 총 결제금액
    public int discountPrice = 0; // 할인금액
    public int isCoupon = 0; // 0: 쿠폰안씀, 1: 쿠폰씀
    int coupon_index;
    public int total_time; // 총 조리시간
    public String user_index; // 쿠폰 적용한 사용자 키, u_idx
    
    ImageIcon icon = new ImageIcon("src/images/menu/main.png");
    CardLayout card; // Main Card
    CardLayout clUser; // User 화면 Menu CardLayout
    CardLayout clAdmin; // Admin 화면 Menu CardLayout
    CardLayout clDrinks;
    static public SqlSessionFactory factory;
    
public void startProgram(){
        SqlSession ss = factory.openSession();
        Map<String, String> s_map = new HashMap<>();
        LocalDate tmp = LocalDate.now();
        String now = String.valueOf(tmp).substring(0, 10);
        List<MenuVO> m_list = ss.selectList("menu.list");
        int result = 1;
        
        for(int i=0; i<m_list.size();i++){
            MenuVO mvo = m_list.get(i);
            String m_idx = mvo.getM_idx();
            String menu_stock_date = (String)ss.selectOne("stock.last_crtDtm",m_idx);
            // stock 테이블에 존재하지 않을 경우 insert
            if (menu_stock_date.equals("test") || !now.equals(menu_stock_date.substring(0, 10))){ 
                s_map.put("m_idx",m_idx);
                s_map.put("pre_stock",mvo.getStock());
                s_map.put("add_stock", "0");
                s_map.put("start_stock",mvo.getStock());
                s_map.put("end_stock", mvo.getStock());
                result = ss.insert("stock.insert",s_map);
            }
            if(result == 0 ){ break ;}
        }
        
        if(result == 1){
            ss.commit();
        } else {
            JOptionPane.showMessageDialog(this, "stock 테이블 추가 과정에서 오류가 발생했습니다.\n다시 시도해주세요.");
            if (ss != null)
                ss.close();
            System.exit(0);
        }
        
        if (ss != null)
            ss.close();
        
        CouponIO.giveDateCoupon();
    }
    
    public void exitProgram(){
        SqlSession ss = factory.openSession();
        Map<String, String> s_map = new HashMap<>();
        LocalDate tmp = LocalDate.now();
        String now = String.valueOf(tmp).substring(0, 10);
        List<MenuVO> m_list = ss.selectList("menu.list");
        int result = 1;
        
        for(int i=0; i<m_list.size();i++){
            MenuVO mvo = m_list.get(i);
            String m_idx = mvo.getM_idx();
            String menu_stock_date = (String)ss.selectOne("stock.last_crtDtm",m_idx);
            if (menu_stock_date.equals("test"))
                continue;
            // stock 테이블에 존재할 경우 update
            if (menu_stock_date.equals("test") || now.equals(menu_stock_date.substring(0, 10))){ 
                s_map.put("m_idx",m_idx);
                s_map.put("crt_dtm", ss.selectOne("stock.last_crtDtm",m_idx));
                s_map.put("end_stock",mvo.getStock());
                result = ss.update("stock.end_stock",s_map);
            }
            if(result == 0 ){ break ;}
        }
        
        if(result == 1){
            ss.commit();
            JOptionPane.showMessageDialog(this, "프로그램을 종료합니다.");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(this, "종료하는 과정에서 오류가 발생했습니다.\n잠시 후 다시 시도해주세요.");
        }
    }
    
    public Main() {
        initComponents();
        buildFactory();
        menu_list();
        menu_list_admin();
        clUser = (CardLayout)Content_Menu.getLayout();
        clAdmin = (CardLayout)Content_Admin.getLayout();
        clDrinks = (CardLayout) Content_Drinks.getLayout();
        card = (CardLayout) this.getContentPane().getLayout();
        
        b_list = new ArrayList<>();
        bp_list = new ArrayList<>();
        
        bgAnalyze.add(jRadioButton1);
        bgAnalyze.add(jRadioButton2);
        bgAnalyze.add(jRadioButton3);
        jRadioButton2.setSelected(true);
        startProgram();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgAnalyze = new javax.swing.ButtonGroup();
        Init = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        main_p = new JPanel() {
             public void paintComponent(Graphics g) {
                 Image img = icon.getImage();
                 g.drawImage(img, this.getWidth()/2 - img.getWidth(this)/2, 0, null);
                 setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                 super.paintComponent(g);
             }
         };
        btnInit = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        Menu_User = new javax.swing.JPanel();
        Top = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        Content = new javax.swing.JPanel();
        btnCoffee = new javax.swing.JButton();
        btnNonCoffee = new javax.swing.JButton();
        btnAde = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Content_Menu = new javax.swing.JPanel();
        p_Coffee = new javax.swing.JPanel();
        p_NonCoffee = new javax.swing.JPanel();
        p_Ade = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        BasketPanel = new javax.swing.JPanel();
        PricePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfOrderPrice = new javax.swing.JTextField();
        tfOrderQty = new javax.swing.JTextField();
        tfDiscount = new javax.swing.JTextField();
        tfTotalPrice = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnUserOrder = new javax.swing.JButton();
        btnUserCoupon = new javax.swing.JButton();
        btnUserDeleteAll = new javax.swing.JButton();
        Menu_Admin = new javax.swing.JPanel();
        Top1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        Content1 = new javax.swing.JPanel();
        btnUser = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnOrder = new javax.swing.JButton();
        btnStock = new javax.swing.JButton();
        btnCoupon = new javax.swing.JButton();
        btnAnalyze = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Content_Admin = new javax.swing.JPanel();
        p_User = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        u_table = new javax.swing.JTable();
        p_User_Phone_tf = new javax.swing.JTextField();
        search_btn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        update_btn = new javax.swing.JButton();
        add_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        p_Menu = new javax.swing.JPanel();
        p_Menu_Admin = new javax.swing.JPanel();
        Content3 = new javax.swing.JPanel();
        btnCoffee2 = new javax.swing.JButton();
        btnNonCoffee2 = new javax.swing.JButton();
        btnAde2 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        Content_Drinks = new javax.swing.JPanel();
        p_Coffee_Admin = new javax.swing.JPanel();
        p_NonCoffee_Admin = new javax.swing.JPanel();
        p_Ade_Admin = new javax.swing.JPanel();
        p_Order = new javax.swing.JPanel();
        p_Order_start_tf = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        p_Order_Table = new javax.swing.JTable();
        p_Order_end_tf = new javax.swing.JTextField();
        p_Stock = new javax.swing.JPanel();
        p_Stock_end_date = new javax.swing.JTextField();
        p_Stock_start_date = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        p_keyword_tf = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        p_Stock_Table = new javax.swing.JTable();
        cate_cb = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        p_Analyze = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        p_Analyze_end_date = new javax.swing.JTextField();
        p_Analyze_start_date = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jButton13 = new javax.swing.JButton();
        tf_total_cnt = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tf_total_price = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImages(null);
        setLocation(new java.awt.Point(500, 150));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        jLabel2.setFont(new java.awt.Font("휴먼둥근헤드라인", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("쌍용다방");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        main_p.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnInit.setFont(new java.awt.Font("맑은 고딕", 0, 36)); // NOI18N
        btnInit.setText("주문시작");
        btnInit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInitActionPerformed(evt);
            }
        });
        main_p.add(btnInit, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 480, 280, 79));

        btnLogin.setText("관리자로그인");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        main_p.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 600, 130, 30));

        javax.swing.GroupLayout InitLayout = new javax.swing.GroupLayout(Init);
        Init.setLayout(InitLayout);
        InitLayout.setHorizontalGroup(
            InitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(main_p, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        InitLayout.setVerticalGroup(
            InitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InitLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(main_p, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(Init, "card_main");

        Menu_User.setMaximumSize(new java.awt.Dimension(1080, 720));
        Menu_User.setMinimumSize(new java.awt.Dimension(1080, 720));
        Menu_User.setPreferredSize(new java.awt.Dimension(1080, 720));

        btnHome.setText("Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("휴먼둥근헤드라인", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("쌍용다방");

        javax.swing.GroupLayout TopLayout = new javax.swing.GroupLayout(Top);
        Top.setLayout(TopLayout);
        TopLayout.setHorizontalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnHome)
                .addContainerGap())
        );
        TopLayout.setVerticalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnHome))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Content.setLayout(new java.awt.GridLayout(1, 3));

        btnCoffee.setText("커피");
        btnCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffeeActionPerformed(evt);
            }
        });
        Content.add(btnCoffee);

        btnNonCoffee.setText("논커피");
        btnNonCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNonCoffeeActionPerformed(evt);
            }
        });
        Content.add(btnNonCoffee);

        btnAde.setText("에이드");
        btnAde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdeActionPerformed(evt);
            }
        });
        Content.add(btnAde);

        Content_Menu.setLayout(new java.awt.CardLayout());

        p_Coffee.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Content_Menu.add(p_Coffee, "cardCoffee");

        p_NonCoffee.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Content_Menu.add(p_NonCoffee, "cardNonCoffee");

        p_Ade.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Content_Menu.add(p_Ade, "cardAde");

        jScrollPane1.setViewportView(Content_Menu);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        BasketPanel.setMaximumSize(new java.awt.Dimension(500, 500));
        BasketPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        BasketPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane2.setViewportView(BasketPanel);

        jLabel1.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
        jLabel1.setText("총 주문수량");

        jLabel4.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
        jLabel4.setText("주문금액");

        jLabel6.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
        jLabel6.setText("할인 금액");

        jLabel9.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
        jLabel9.setText("총 결제 금액");

        tfOrderPrice.setEditable(false);
        tfOrderPrice.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N

        tfOrderQty.setEditable(false);
        tfOrderQty.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N

        tfDiscount.setEditable(false);
        tfDiscount.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N

        tfTotalPrice.setEditable(false);
        tfTotalPrice.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N

        javax.swing.GroupLayout PricePanelLayout = new javax.swing.GroupLayout(PricePanel);
        PricePanel.setLayout(PricePanelLayout);
        PricePanelLayout.setHorizontalGroup(
            PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PricePanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfOrderQty, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfOrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        PricePanelLayout.setVerticalGroup(
            PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PricePanelLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfOrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfOrderQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(PricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        jPanel2.setLayout(new java.awt.GridLayout(3, 0, 1, 0));

        btnUserOrder.setText("결제하기");
        btnUserOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserOrderActionPerformed(evt);
            }
        });
        jPanel2.add(btnUserOrder);

        btnUserCoupon.setText("쿠폰");
        btnUserCoupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserCouponActionPerformed(evt);
            }
        });
        jPanel2.add(btnUserCoupon);

        btnUserDeleteAll.setText("주문 전체삭제");
        btnUserDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserDeleteAllActionPerformed(evt);
            }
        });
        jPanel2.add(btnUserDeleteAll);

        javax.swing.GroupLayout Menu_UserLayout = new javax.swing.GroupLayout(Menu_User);
        Menu_User.setLayout(Menu_UserLayout);
        Menu_UserLayout.setHorizontalGroup(
            Menu_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_UserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Menu_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Menu_UserLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PricePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Menu_UserLayout.setVerticalGroup(
            Menu_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_UserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Content, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Menu_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(PricePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(Menu_User, "cardUser");

        Menu_Admin.setPreferredSize(new java.awt.Dimension(1080, 720));

        jLabel5.setFont(new java.awt.Font("휴먼둥근헤드라인", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 0));
        jLabel5.setText("쌍용다방");

        jButton10.setText("Home");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Top1Layout = new javax.swing.GroupLayout(Top1);
        Top1.setLayout(Top1Layout);
        Top1Layout.setHorizontalGroup(
            Top1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Top1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(423, 423, 423)
                .addComponent(jButton10)
                .addContainerGap())
        );
        Top1Layout.setVerticalGroup(
            Top1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Top1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Top1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jButton10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Content1.setLayout(new java.awt.GridLayout(1, 3));

        btnUser.setText("사용자관리");
        btnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserActionPerformed(evt);
            }
        });
        Content1.add(btnUser);

        btnMenu.setText("메뉴관리");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        Content1.add(btnMenu);

        btnOrder.setText("주문내역관리");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });
        Content1.add(btnOrder);

        btnStock.setText("재고관리");
        btnStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStockActionPerformed(evt);
            }
        });
        Content1.add(btnStock);

        btnCoupon.setText("쿠폰관리");
        btnCoupon.setToolTipText("");
        btnCoupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCouponActionPerformed(evt);
            }
        });
        Content1.add(btnCoupon);

        btnAnalyze.setText("분석 및 정산");
        btnAnalyze.setToolTipText("");
        btnAnalyze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalyzeActionPerformed(evt);
            }
        });
        Content1.add(btnAnalyze);

        jScrollPane3.setHorizontalScrollBar(null);

        Content_Admin.setMinimumSize(new java.awt.Dimension(1068, 620));
        Content_Admin.setPreferredSize(new java.awt.Dimension(1068, 620));
        Content_Admin.setLayout(new java.awt.CardLayout());

        u_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NO", "전화번호", "아이디", "비밀번호", "생성일자", "수정일자", "삭제여부", "관리자여부"
            }
        ));
        u_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(u_table);

        p_User_Phone_tf.setFont(new java.awt.Font("맑은 고딕", 1, 16)); // NOI18N
        p_User_Phone_tf.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        p_User_Phone_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_User_Phone_tfActionPerformed(evt);
            }
        });

        search_btn.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        search_btn.setText("검색");
        search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_btnActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel7.setText("전화번호");

        update_btn.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        update_btn.setText("수정");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        add_btn.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        add_btn.setText("추가");
        add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btnActionPerformed(evt);
            }
        });

        delete_btn.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        delete_btn.setText("삭제");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p_UserLayout = new javax.swing.GroupLayout(p_User);
        p_User.setLayout(p_UserLayout);
        p_UserLayout.setHorizontalGroup(
            p_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_UserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_UserLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(p_User_Phone_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(update_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE))
                .addContainerGap())
        );
        p_UserLayout.setVerticalGroup(
            p_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_UserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(search_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(update_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delete_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(p_UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(p_User_Phone_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );

        Content_Admin.add(p_User, "cardUser");

        p_Menu.setLayout(new java.awt.CardLayout());

        p_Menu_Admin.setMaximumSize(new java.awt.Dimension(900, 600));
        p_Menu_Admin.setMinimumSize(new java.awt.Dimension(900, 600));

        Content3.setLayout(new java.awt.GridLayout(1, 3));

        btnCoffee2.setText("커피");
        btnCoffee2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffee2ActionPerformed(evt);
            }
        });
        Content3.add(btnCoffee2);

        btnNonCoffee2.setText("논커피");
        btnNonCoffee2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNonCoffee2ActionPerformed(evt);
            }
        });
        Content3.add(btnNonCoffee2);

        btnAde2.setText("에이드");
        btnAde2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAde2ActionPerformed(evt);
            }
        });
        Content3.add(btnAde2);

        Content_Drinks.setLayout(new java.awt.CardLayout());

        p_Coffee_Admin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Content_Drinks.add(p_Coffee_Admin, "cardCoffeeAdmin");

        p_NonCoffee_Admin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Content_Drinks.add(p_NonCoffee_Admin, "cardNonCoffeeAdmin");

        p_Ade_Admin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Content_Drinks.add(p_Ade_Admin, "cardAdeAdmin");

        jScrollPane9.setViewportView(Content_Drinks);

        javax.swing.GroupLayout p_Menu_AdminLayout = new javax.swing.GroupLayout(p_Menu_Admin);
        p_Menu_Admin.setLayout(p_Menu_AdminLayout);
        p_Menu_AdminLayout.setHorizontalGroup(
            p_Menu_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_Menu_AdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_Menu_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Content3, javax.swing.GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE)
                    .addComponent(jScrollPane9))
                .addContainerGap())
        );
        p_Menu_AdminLayout.setVerticalGroup(
            p_Menu_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_Menu_AdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Content3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        p_Menu.add(p_Menu_Admin, "cardMenuAdmin");

        Content_Admin.add(p_Menu, "cardMenu");

        p_Order_start_tf.setFont(new java.awt.Font("맑은 고딕", 1, 16)); // NOI18N
        p_Order_start_tf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p_Order_start_tfMousePressed(evt);
            }
        });
        p_Order_start_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_Order_start_tfActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jButton11.setText("검색");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jLabel12.setText("~");

        jLabel13.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel13.setText("주문일자");

        p_Order_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NO", "사용자", "수량", "쿠폰여부", "주문금액", "할인금액", "전체금액", "주문일자"
            }
        ));
        jScrollPane6.setViewportView(p_Order_Table);

        p_Order_end_tf.setFont(new java.awt.Font("맑은 고딕", 1, 16)); // NOI18N
        p_Order_end_tf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p_Order_end_tfMousePressed(evt);
            }
        });
        p_Order_end_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_Order_end_tfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p_OrderLayout = new javax.swing.GroupLayout(p_Order);
        p_Order.setLayout(p_OrderLayout);
        p_OrderLayout.setHorizontalGroup(
            p_OrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_OrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_OrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_OrderLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(p_Order_start_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p_Order_end_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 574, Short.MAX_VALUE)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );
        p_OrderLayout.setVerticalGroup(
            p_OrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_OrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_OrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_Order_end_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p_Order_start_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        Content_Admin.add(p_Order, "cardOrder");

        p_Stock_end_date.setFont(new java.awt.Font("맑은 고딕", 1, 16)); // NOI18N
        p_Stock_end_date.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p_Stock_end_dateMousePressed(evt);
            }
        });
        p_Stock_end_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_Stock_end_dateActionPerformed(evt);
            }
        });

        p_Stock_start_date.setFont(new java.awt.Font("맑은 고딕", 1, 16)); // NOI18N
        p_Stock_start_date.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p_Stock_start_dateMousePressed(evt);
            }
        });
        p_Stock_start_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_Stock_start_dateActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jButton5.setText("검색");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jLabel10.setText("~");

        jLabel11.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel11.setText("생성일자");

        p_keyword_tf.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N

        p_Stock_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NO", "메뉴명", "생성일자", "분류", "전날재고", "추가재고", "시작재고", "마감재고"
            }
        ));
        jScrollPane5.setViewportView(p_Stock_Table);

        cate_cb.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        cate_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "카테고리", "커피", "논커피", "에이드" }));
        cate_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cate_cbActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("맑은 고딕", 1, 20)); // NOI18N
        jLabel16.setText("메뉴명");

        javax.swing.GroupLayout p_StockLayout = new javax.swing.GroupLayout(p_Stock);
        p_Stock.setLayout(p_StockLayout);
        p_StockLayout.setHorizontalGroup(
            p_StockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_StockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_StockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(p_StockLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(p_Stock_start_date, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p_Stock_end_date, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p_keyword_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cate_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        p_StockLayout.setVerticalGroup(
            p_StockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_StockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_StockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_Stock_end_date, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p_Stock_start_date, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(p_keyword_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(cate_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        Content_Admin.add(p_Stock, "cardStock");

        p_Analyze.setPreferredSize(new java.awt.Dimension(1068, 622));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(jTable4);

        p_Analyze_end_date.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        p_Analyze_end_date.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p_Analyze_end_dateMousePressed(evt);
            }
        });
        p_Analyze_end_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_Analyze_end_dateActionPerformed(evt);
            }
        });

        p_Analyze_start_date.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        p_Analyze_start_date.setToolTipText("");
        p_Analyze_start_date.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p_Analyze_start_dateMousePressed(evt);
            }
        });
        p_Analyze_start_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_Analyze_start_dateActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jLabel14.setText("~");

        jLabel15.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        jLabel15.setText("주문일자");

        jRadioButton1.setText("월별");

        jRadioButton2.setText("일별");

        jRadioButton3.setText("시간별");

        jButton13.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jButton13.setText("검색");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        tf_total_cnt.setEditable(false);
        tf_total_cnt.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        tf_total_cnt.setToolTipText("");
        tf_total_cnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_total_cntActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        jLabel17.setText("총 주문금액");

        tf_total_price.setEditable(false);
        tf_total_price.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        tf_total_price.setToolTipText("");
        tf_total_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_total_priceActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        jLabel18.setText("총 수량");

        javax.swing.GroupLayout p_AnalyzeLayout = new javax.swing.GroupLayout(p_Analyze);
        p_Analyze.setLayout(p_AnalyzeLayout);
        p_AnalyzeLayout.setHorizontalGroup(
            p_AnalyzeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_AnalyzeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_AnalyzeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_AnalyzeLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1035, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(p_AnalyzeLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p_Analyze_start_date, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p_Analyze_end_date, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_total_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_total_price, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );
        p_AnalyzeLayout.setVerticalGroup(
            p_AnalyzeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_AnalyzeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_AnalyzeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_Analyze_end_date, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p_Analyze_start_date, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tf_total_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tf_total_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Content_Admin.add(p_Analyze, "cardAnalyze");

        jScrollPane3.setViewportView(Content_Admin);

        javax.swing.GroupLayout Menu_AdminLayout = new javax.swing.GroupLayout(Menu_Admin);
        Menu_Admin.setLayout(Menu_AdminLayout);
        Menu_AdminLayout.setHorizontalGroup(
            Menu_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_AdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Menu_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Top1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Content1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE))
                .addContainerGap())
        );
        Menu_AdminLayout.setVerticalGroup(
            Menu_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_AdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Top1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Content1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(Menu_Admin, "cardAdmin");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInitActionPerformed
        menu_list();
        card.show(this.getContentPane(), "cardUser");
        resetAll();
    }//GEN-LAST:event_btnInitActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        card.show(this.getContentPane(), "card_main");
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnNonCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNonCoffeeActionPerformed
        clUser.show(Content_Menu, "cardNonCoffee");
    }//GEN-LAST:event_btnNonCoffeeActionPerformed

    private void btnCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffeeActionPerformed
        // TODO add your handling code here:
        clUser.show(Content_Menu, "cardCoffee");
    }//GEN-LAST:event_btnCoffeeActionPerformed

    private void btnUserOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserOrderActionPerformed
        makeOrder();
        menu_list();
        resetAll();
    }//GEN-LAST:event_btnUserOrderActionPerformed

    // 주문 전체삭제
    private void btnUserDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserDeleteAllActionPerformed
        // TODO add your handling code here:
        b_list.clear();
        bp_list.clear();
        BasketPanel.removeAll();
        basket_y = 10;
        jScrollPane2.setViewportView(BasketPanel);
        setPrice();
        
        Main.this.BasketPanel.revalidate();
    }//GEN-LAST:event_btnUserDeleteAllActionPerformed

    private void btnUserCouponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserCouponActionPerformed
        // TODO add your handling code here:
        Coupon_Login coupon_login = new Coupon_Login(Main.this);
    }//GEN-LAST:event_btnUserCouponActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        Admin_Login admin_login = new Admin_Login(Main.this);
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnAdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdeActionPerformed
        // TODO add your handling code here:
        clUser.show(Content_Menu, "cardAde");
    }//GEN-LAST:event_btnAdeActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
         card.show(this.getContentPane(), "card_main");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        // TODO add your handling code here:
        clAdmin.show(Content_Admin, "cardUser");
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        
        clAdmin.show(Content_Admin, "cardMenu");
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        clAdmin.show(Content_Admin, "cardOrder");
    }//GEN-LAST:event_btnOrderActionPerformed

    private void p_User_Phone_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_User_Phone_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_User_Phone_tfActionPerformed

    private void p_Stock_end_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_Stock_end_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_Stock_end_dateActionPerformed

    private void p_Stock_start_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_Stock_start_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_Stock_start_dateActionPerformed

    private void btnStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStockActionPerformed
        // TODO add your handling code here:
        clAdmin.show(Content_Admin, "cardStock");
    }//GEN-LAST:event_btnStockActionPerformed

    private void p_Order_start_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_Order_start_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_Order_start_tfActionPerformed

    private void p_Order_end_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_Order_end_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_Order_end_tfActionPerformed

    private void btnCouponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCouponActionPerformed
        // TODO add your handling code here:
        Coupon_table ct = new Coupon_table(Main.this);
    }//GEN-LAST:event_btnCouponActionPerformed

    private void btnAnalyzeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalyzeActionPerformed
        // TODO add your handling code here:
        clAdmin.show(Content_Admin, "cardAnalyze");
    }//GEN-LAST:event_btnAnalyzeActionPerformed

    private void add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btnActionPerformed
        // TODO add your handling code here:
        UserDialog ud = new UserDialog(this, "추가", this, null);
    }//GEN-LAST:event_add_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
        // TODO add your handling code here:
        int u_t_idx = u_table.getSelectedRow();
        if(u_t_idx < 0){
            return;
        }
        
        UserDialog ud = new UserDialog(this, "수정", String.valueOf(u_table.getValueAt(u_t_idx, 0)), String.valueOf(u_table.getValueAt(u_t_idx, 1)), this);
    }//GEN-LAST:event_update_btnActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        view_OrderTable();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        view_StockTable();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_btnActionPerformed
        // TODO add your handling code here:
        view_UserTable();
    }//GEN-LAST:event_search_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        // TODO add your handling code here:
        int u_t_idx = u_table.getSelectedRow();
        if(u_t_idx < 0){
            return;
        }
        
        UserDialog ud = new UserDialog(this, "삭제", String.valueOf(u_table.getValueAt(u_t_idx, 0)), String.valueOf(u_table.getValueAt(u_t_idx, 1)), this);
    }//GEN-LAST:event_delete_btnActionPerformed

    String clDrinksCard = "cardCoffeeAdmin";
    
    private void btnCoffee2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffee2ActionPerformed
        // TODO add your handling code here:
        clDrinksCard = "cardCoffeeAdmin";
        clDrinks.show(Content_Drinks, "cardCoffeeAdmin");
    }//GEN-LAST:event_btnCoffee2ActionPerformed

    private void btnNonCoffee2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNonCoffee2ActionPerformed
        clDrinksCard = "cardNonCoffeeAdmin";
        clDrinks.show(Content_Drinks, "cardNonCoffeeAdmin");
    }//GEN-LAST:event_btnNonCoffee2ActionPerformed

    private void btnAde2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAde2ActionPerformed
        clDrinksCard = "cardAdeAdmin";
        clDrinks.show(Content_Drinks, "cardAdeAdmin");
    }//GEN-LAST:event_btnAde2ActionPerformed

    private void p_Analyze_end_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_Analyze_end_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_Analyze_end_dateActionPerformed

    private void p_Analyze_start_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_Analyze_start_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_Analyze_start_dateActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        getAnalyzeTable();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void tf_total_cntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_total_cntActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_total_cntActionPerformed

    private void tf_total_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_total_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_total_priceActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
        exitProgram();
    }//GEN-LAST:event_formWindowClosing

    private void cate_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cate_cbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cate_cbActionPerformed

    private void p_Order_start_tfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_Order_start_tfMousePressed
        openCalendar(p_Order_start_tf);
    }//GEN-LAST:event_p_Order_start_tfMousePressed

    private void p_Order_end_tfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_Order_end_tfMousePressed
        openCalendar(p_Order_end_tf);
    }//GEN-LAST:event_p_Order_end_tfMousePressed

    private void p_Stock_start_dateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_Stock_start_dateMousePressed
        openCalendar(p_Stock_start_date);
    }//GEN-LAST:event_p_Stock_start_dateMousePressed

    private void p_Stock_end_dateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_Stock_end_dateMousePressed
        openCalendar(p_Stock_end_date);
    }//GEN-LAST:event_p_Stock_end_dateMousePressed

    private void p_Analyze_start_dateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_Analyze_start_dateMousePressed
        openCalendar(p_Analyze_start_date);
    }//GEN-LAST:event_p_Analyze_start_dateMousePressed

    private void p_Analyze_end_dateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_Analyze_end_dateMousePressed
        openCalendar(p_Analyze_end_date);
    }//GEN-LAST:event_p_Analyze_end_dateMousePressed

    private void openCalendar(JTextField jf) {
        CalendarDialog calendar = new CalendarDialog(jf);
        jf.setEditable(false);
        calendar.setLocation(jf.getLocationOnScreen().x - 7, jf.getLocationOnScreen().y + 39);
    }
    
    public static void main(String args[]) {
        new Main();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BasketPanel;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Content1;
    private javax.swing.JPanel Content3;
    private javax.swing.JPanel Content_Admin;
    private javax.swing.JPanel Content_Drinks;
    private javax.swing.JPanel Content_Menu;
    private javax.swing.JPanel Init;
    private javax.swing.JPanel Menu_Admin;
    private javax.swing.JPanel Menu_User;
    private javax.swing.JPanel PricePanel;
    private javax.swing.JPanel Top;
    private javax.swing.JPanel Top1;
    private javax.swing.JButton add_btn;
    private javax.swing.ButtonGroup bgAnalyze;
    private javax.swing.JButton btnAde;
    private javax.swing.JButton btnAde2;
    private javax.swing.JButton btnAnalyze;
    private javax.swing.JButton btnCoffee;
    private javax.swing.JButton btnCoffee2;
    private javax.swing.JButton btnCoupon;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnInit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNonCoffee;
    private javax.swing.JButton btnNonCoffee2;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnStock;
    private javax.swing.JButton btnUser;
    private javax.swing.JButton btnUserCoupon;
    private javax.swing.JButton btnUserDeleteAll;
    private javax.swing.JButton btnUserOrder;
    private javax.swing.JComboBox<String> cate_cb;
    private javax.swing.JButton delete_btn;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable4;
    private javax.swing.JPanel main_p;
    private javax.swing.JPanel p_Ade;
    private javax.swing.JPanel p_Ade_Admin;
    private javax.swing.JPanel p_Analyze;
    private javax.swing.JTextField p_Analyze_end_date;
    private javax.swing.JTextField p_Analyze_start_date;
    private javax.swing.JPanel p_Coffee;
    private javax.swing.JPanel p_Coffee_Admin;
    private javax.swing.JPanel p_Menu;
    private javax.swing.JPanel p_Menu_Admin;
    private javax.swing.JPanel p_NonCoffee;
    private javax.swing.JPanel p_NonCoffee_Admin;
    private javax.swing.JPanel p_Order;
    private javax.swing.JTable p_Order_Table;
    private javax.swing.JTextField p_Order_end_tf;
    private javax.swing.JTextField p_Order_start_tf;
    private javax.swing.JPanel p_Stock;
    private javax.swing.JTable p_Stock_Table;
    private javax.swing.JTextField p_Stock_end_date;
    private javax.swing.JTextField p_Stock_start_date;
    private javax.swing.JPanel p_User;
    private javax.swing.JTextField p_User_Phone_tf;
    private javax.swing.JTextField p_keyword_tf;
    private javax.swing.JButton search_btn;
    private javax.swing.JTextField tfDiscount;
    private javax.swing.JTextField tfOrderPrice;
    private javax.swing.JTextField tfOrderQty;
    private javax.swing.JTextField tfTotalPrice;
    private javax.swing.JTextField tf_total_cnt;
    private javax.swing.JTextField tf_total_price;
    private javax.swing.JTable u_table;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
    
    // Factory 생성
    private void buildFactory() {
        Reader r = null;
        try {
            r = Resources.getResourceAsReader("proj/config.xml");
            factory = new SqlSessionFactoryBuilder().build(r);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            }
        }
    } 
    
    public void menu_list(){
    	SqlSession ss = factory.openSession();
        m_list = ss.selectList("menu.list");
        p_Coffee.removeAll();
        p_NonCoffee.removeAll();
        p_Ade.removeAll();
        int x_c = 30;
        int y_c = 17;

        int x_n = 30;
        int y_n = 17;

        int x_a = 30;
        int y_a = 17;
		
        for(int i=0; i < m_list.size(); i++) {
            MenuVO mvo = m_list.get(i);
            MenuPanelVO mpvo = new MenuPanelVO(mvo, this, 0);
            
            switch(Integer.parseInt(mvo.getCategory_idx())) {
        	case 1:
                    if (x_c > Content_Menu.getWidth()-47){
                        x_c = 30;
                        y_c += 150;
                    }
                    p_Coffee.add(mpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_c, y_c, -1, -1));	
                    x_c += 148;
                    break;
        	case 2:
                    if (x_n > Content_Menu.getWidth()-47){
	                x_n = 30;
	                y_n += 150;
	            }
                    p_NonCoffee.add(mpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_n, y_n, -1, -1));	
                    x_n += 148;
                    break;
        	case 3:
                    if (x_a > Content_Menu.getWidth()-47){
	                x_a = 30;
	                y_a += 150;
                    }
                    p_Ade.add(mpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_a, y_a, -1, -1));	
                    x_a += 148;
                    break;
        	}
        }
        
        Content_Menu.add(p_Coffee, "cardCoffee");
        Content_Menu.add(p_NonCoffee, "cardNonCoffee");
        Content_Menu.add(p_Ade, "cardAde");

        if(ss != null) {
            ss.close();
        }
    	Main.this.p_Coffee.revalidate();
        Main.this.p_NonCoffee.revalidate();
        Main.this.p_Ade.revalidate();
    }
    
    // 커피, 논커피, 에이드 메뉴 구성
    public void menu_list_admin(){
    	SqlSession ss = factory.openSession();
        m_list = ss.selectList("menu.list");
        p_Coffee_Admin.removeAll();
        p_NonCoffee_Admin.removeAll();
        p_Ade_Admin.removeAll();
        int x_c = 30;
        int y_c = 17;

        int x_n = 30;
        int y_n = 17;

        int x_a = 30;
        int y_a = 17;
		
        
        p_Coffee_Admin.add(new MenuPanelVO(this).p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_c, y_c, -1, -1));	
        x_c += 148;
        p_NonCoffee_Admin.add(new MenuPanelVO(this).p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_n, y_n, -1, -1));	
        x_n += 148;
        p_Ade_Admin.add(new MenuPanelVO(this).p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_a, y_a, -1, -1));	
        x_a += 148;
        
        
        for(int i=0; i < m_list.size(); i++) {
            MenuVO mvo = m_list.get(i);
            MenuPanelVO mpvo = new MenuPanelVO(mvo, this, 1);
            
            switch(Integer.parseInt(mvo.getCategory_idx())) {
        	case 1:
                    if (x_c > Content_Drinks.getWidth()-47){
                        x_c = 30;
                        y_c += 150;
                    }
                    p_Coffee_Admin.add(mpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_c, y_c, -1, -1));	
                    x_c += 148;
                    break;
        	case 2:
                    if (x_n > Content_Drinks.getWidth()-47){
	                x_n = 30;
	                y_n += 150;
	            }
                    p_NonCoffee_Admin.add(mpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_n, y_n, -1, -1));	
                    x_n += 148;
                    break;
        	case 3:
                    if (x_a > Content_Drinks.getWidth()-47){
	                x_a = 30;
	                y_a += 150;
                    }
                    p_Ade_Admin.add(mpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(x_a, y_a, -1, -1));	
                    x_a += 148;
                    break;
        	}
        }
        
        Content_Drinks.add(p_Coffee_Admin, "cardCoffeeAdmin");
        Content_Drinks.add(p_NonCoffee_Admin, "cardNonCoffeeAdmin");
        Content_Drinks.add(p_Ade_Admin, "cardAdeAdmin");

        if(ss != null) {
            ss.close();
        }
    	
    	Content_Drinks.revalidate();
    }
    
    // 장바구니 등록 ==> MenuPanelVO가 호출
    public void basket_list(){
        MenuVO mvo = b_list.get(b_list.size()-1);
        BasketPanelVO bpvo = new BasketPanelVO(mvo, this);
        bp_list.add(bpvo);
        BasketPanel.add(bpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, basket_y, 540, 120));
        basket_y += 130;
        
        this.BasketPanel.revalidate();
        setPrice();
    }
        
    // 장바구니 중복 여부 확인
    public boolean chk_list(MenuVO mvo){
        for (int i = 0; i < b_list.size(); i++){
            MenuVO b_mvo = b_list.get(i);
            if (b_mvo.m_idx == mvo.m_idx){
                for (int j = 0; j < bp_list.size(); j++){
                    BasketPanelVO bpvo = bp_list.get(j);
                    if (bpvo.mvo.m_idx == mvo.m_idx){
                        bpvo.jsCnt.setValue((int)bpvo.jsCnt.getValue()+1);
                        setPrice();
                    }
                }
                return false;
            }
        }
        return true;
    }
    
    // 장바구니에서 선택한 메뉴 삭제
    public void delete_basket(){
        BasketPanel.removeAll(); // 먼저 전체 삭제
        jScrollPane2.setViewportView(BasketPanel);
        
        basket_y = 10;
        for (int i = 0; i < bp_list.size(); i++){ 
            BasketPanelVO bpvo = bp_list.get(i);
            BasketPanel.add(bpvo.p, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, basket_y, 540, 120));
            basket_y += 130;
        }
        
        this.BasketPanel.revalidate();
        setPrice();
    }
    
    // PricePanel 세팅
    public void setPrice(){
        orderPrice = 0;
        orderQty = 0;
        totalPrice = 0;
        
        for (int i = 0; i < bp_list.size(); i++){
            BasketPanelVO bpvo = bp_list.get(i);
            orderPrice += Integer.parseInt(bpvo.mvo.price) * (int)bpvo.jsCnt.getValue();
            orderQty += (int)bpvo.jsCnt.getValue();
        }
        
        tfOrderPrice.setText(Integer.toString(orderPrice));
        tfOrderQty.setText(Integer.toString(orderQty));
        tfDiscount.setText(Integer.toString(discountPrice));
        totalPrice = orderPrice - discountPrice;
        tfTotalPrice.setText(Integer.toString(totalPrice));
        
        if(orderPrice == 0){
            btnUserOrder.setEnabled(false);
        } else if (!btnUserOrder.isEnabled()){
            btnUserOrder.setEnabled(true);
        }
        
        this.PricePanel.revalidate();
    }
    
    // 결제하기 
    public void makeOrder(){
        SqlSession ss = factory.openSession();
        int res = (int)ss.selectOne("orderlist.get_idx")+1;
        total_time = 0;
        // Order
        for (int i = 0; i < b_list.size(); i++){
            MenuVO mvo = b_list.get(i);
            BasketPanelVO bpvo = bp_list.get(i);
            
            OrderVO ovo = new OrderVO();
            ovo.setM_idx(mvo.m_idx);
            ovo.setOl_idx(Integer.toString(res));
            ovo.setOrder_qty(String.valueOf(bpvo.jsCnt.getValue()));
            total_time += Integer.parseInt(ovo.getOrder_qty()) * Integer.parseInt(mvo.brew_time);
            int price = (int)bpvo.jsCnt.getValue()*Integer.parseInt(mvo.getPrice());
            ovo.setPrice(Integer.toString(price));
            
            int chk = ss.insert("order.save", ovo);
            int stk = ss.update("menu.stockchange", ovo);
            if (chk == 0 || stk == 0){
                JOptionPane.showMessageDialog(this, "order 또는 menu 테이블 insert/update 오류");
                if (ss != null)
                    ss.close();
                return;
            }
        }
        
        // OrderList
        OrderListVO olvo = new OrderListVO(
            //Integer.toString(res),
            user_index, // u_idx
            null, // orderate => default가 now라서 null 해도 됨
            Integer.toString(orderQty),
            Integer.toString(orderPrice),
            Integer.toString(isCoupon),
            Integer.toString(discountPrice),
            Integer.toString(totalPrice),
            null // List<OrderVO>
        );
        
        int chk2 = ss.insert("orderlist.make_orderlist", olvo);
        if (chk2 == 0){
            JOptionPane.showMessageDialog(this, "order 테이블 insert 오류");
            return;
        }
        
        ss.commit();
        
        if (isCoupon != 0)
            CouponIO.minusCoupon(coupon_index, user_index);
        
        OrderListVO olvo2 = ss.selectOne("orderlist.list", olvo.getOl_idx());
        if (ss != null)
            ss.close();
        
        OrderDialog completeorder = new OrderDialog(Main.this, olvo2);
    }
    
    // 전체 초기화
    public void resetAll(){
        // TODO add your handling code here:
        b_list.clear();
        bp_list.clear();
        BasketPanel.removeAll();
        basket_y = 10;
        discountPrice = 0;
        isCoupon = 0;
        total_time = 0;
        user_index = null;
        setPrice();
        
        jScrollPane2.setViewportView(BasketPanel);
        this.getContentPane().revalidate();
    }
    
    // 관리자 - 사용자 관리
    public void view_UserTable(){
        SqlSession ss= factory.openSession();
        
        String input_u_phone = p_User_Phone_tf.getText().trim();
        
        if(input_u_phone.length()==0){
            input_u_phone = null;
        }
        
        List<UserVO> u_list = ss.selectList("user.select_by_phone",input_u_phone);
        
        String [] u_column =
        { "NO", "전화번호", "아이디", "비밀번호", "생성일자", "수정일자", "삭제여부", "관리자여부"};
        String[][] u_array = new String[u_list.size()][u_column.length];
        
        for(int i=0;i<u_list.size();i++){
            UserVO uvo = u_list.get(i);
            u_array[i][0] = uvo.getU_idx();
            u_array[i][1] = uvo.getU_phone();
            u_array[i][2] = uvo.getU_id();
            u_array[i][3] = uvo.getU_pw();
            u_array[i][4] = uvo.getCrt_dtm();
            u_array[i][5] = uvo.getUpt_dtm();
            u_array[i][6] = uvo.getIs_del();
            u_array[i][7] = uvo.getIs_admin();
        
        }
        
        u_table.setModel(new javax.swing.table.DefaultTableModel(
            u_array, u_column
        ));
    }
    
    //관리자 - 메뉴 관리
    public int add_menu(Map m_idx){
        SqlSession ss = factory.openSession();
        
        int res = ss.update("menu.add_menu",m_idx);
        System.out.println(res);
        if(res==1){
            ss.commit();
            p_Coffee_Admin.removeAll();
            p_NonCoffee_Admin.removeAll();
            p_Ade_Admin.removeAll();
            menu_list_admin();
            clDrinks.show(Content_Drinks, clDrinksCard);
            
        }
        return res;
    }
    
    
    public int update_menu(Map menu_map){
        SqlSession ss = factory.openSession();
        
        int res = ss.update("menu.update_menu",menu_map);
        if(res==1){
            ss.commit();
            p_Coffee_Admin.removeAll();
            p_NonCoffee_Admin.removeAll();
            p_Ade_Admin.removeAll();
            menu_list_admin();
            clDrinks.show(Content_Drinks, clDrinksCard);
            
        }
        return res;
    }
    
    public int delete_menu(String m_idx){
        SqlSession ss = factory.openSession();
        
        int res = ss.update("menu.delete_menu",m_idx);
        
        if(res==1){
            ss.commit();
            p_Coffee_Admin.removeAll();
            p_NonCoffee_Admin.removeAll();
            p_Ade_Admin.removeAll();
            
            menu_list_admin();
            clDrinks.show(Content_Drinks, clDrinksCard);
        }
        
        if (ss != null)
            ss.close();
        
        return res;
    }
    
    // 관리자 - 주문내역관리
    List<OrderListVO> p_Order_list;
    private void view_OrderTable() {
    	SqlSession ss = factory.openSession();
    	Map<String,String> map = new HashMap();
    	map.put("start_odr_date", p_Order_start_tf.getText().trim());
    	map.put("end_odr_date", p_Order_end_tf.getText().trim());
    	p_Order_list = ss.selectList("orderlist.admin_search_orderlist", map);
        
        String [] c_name = {"NO","사용자","수량","쿠폰여부","주문금액","할인금액","전체금액","주문일자"};
    	String [][] data = new String [p_Order_list.size()][c_name.length];
    	
    	for(int i = 0; i<p_Order_list.size(); i++) {
    		OrderListVO vo = p_Order_list.get(i);
    		data[i][0]= vo.getOl_idx();
                if (vo.getU_phone() == null)
                    data[i][1] = "비회원";
                else
                    data[i][1]= vo.getU_phone();
    		data[i][2]= vo.getTotal_cnt();
    		data[i][3]= vo.getIs_coupon();
    		data[i][4]= vo.getOdr_price();
    		data[i][5]= vo.getDis_price();
    		data[i][6]= vo.getTotal_price();
    		data[i][7]= vo.getOdr_date().substring(0, 10);
    	}
    	p_Order_Table.setModel(new DefaultTableModel(data,c_name));
    }
    
    // 관리자 - 재고관리
    List<StockVO> p_Stock_list;
    private void view_StockTable() {
        SqlSession ss = factory.openSession();
        Map<String, String> map = new HashMap<String, String>();
        
        String category_idx = String.valueOf(cate_cb.getSelectedIndex());
                
        map.put("m_name", p_keyword_tf.getText().trim());
        map.put("category_idx", category_idx);
        
        map.put("start_crt_dtm", p_Stock_start_date.getText().trim());
        map.put("end_crt_dtm", p_Stock_end_date.getText().trim());

        p_Stock_list = ss.selectList("stock.search", map);
        
        if (ss != null) {
            ss.close();
        }
        
        String[] s_name = {"NO", "메뉴명", "생성일자", "분류", "전날재고", "추가재고", "시작재고", "마감재고"};
        String[][] data = new String[p_Stock_list.size()][s_name.length];

        for (int i = 0; i < data.length; i++) {
            StockVO vo = p_Stock_list.get(i);

            data[i][0] = vo.getS_idx();
            data[i][1] = vo.getM_name();
            data[i][2] = vo.getCrt_dtm().substring(0, 10);
            data[i][3] = vo.getCate_name();
            data[i][4] = vo.getPre_stock();
            data[i][5] = vo.getAdd_stock();
            data[i][6] = vo.getStart_stock();
            data[i][7] = vo.getEnd_stock();
        }
        
        p_Stock_Table.setModel(new DefaultTableModel(data, s_name));
    }
    
    // 관리자 - 정산 및 분석
    List<AnalyzeVO> p_Analyze_list;
    private void getAnalyzeTable(){
        SqlSession ss = factory.openSession();
        Map<String, String> map = new HashMap<>();
        map.put("start_odr_date", p_Analyze_start_date.getText().trim());
        map.put("end_odr_date", p_Analyze_end_date.getText().trim());
        
        // 라디오버튼 텍스트 가져오기
        Enumeration<AbstractButton> tmp = bgAnalyze.getElements();
        String s = null;
        while (tmp.hasMoreElements()){
            AbstractButton ab = tmp.nextElement();
            JRadioButton jb = (JRadioButton)ab;
            if (jb.isSelected())
                s = jb.getText();
        }
        
        switch (s){
            case "월별":
                p_Analyze_list = ss.selectList("analyze.search_month", map);
                break;
            case "일별":
                p_Analyze_list = ss.selectList("analyze.search_day", map);
                break;
            case "시간별":
                p_Analyze_list = ss.selectList("analyze.search_time", map);
                break;
        }
        
        if (ss != null) {
            ss.close();
        }
        
        int total_cnt = 0;
        int total_price = 0;
        for (int i = 0 ; i < p_Analyze_list.size(); i++){
            AnalyzeVO avo = p_Analyze_list.get(i);
            total_cnt += Integer.parseInt(avo.order_qty);
            total_price += Integer.parseInt(avo.price);
        }
        tf_total_cnt.setText(Integer.toString(total_cnt));
        tf_total_price.setText(Integer.toString(total_price));
        
        String[] a_name = {"주문기간", "메뉴명", "주문수량", "총주문금액", "원가", "순수익"};
        String[][] data = new String[p_Analyze_list.size()][a_name.length];
        
        for (int i = 0; i < data.length; i++) {
            AnalyzeVO vo = p_Analyze_list.get(i);

            data[i][0] = vo.getOdr_date();
            data[i][1] = vo.getM_name();
            data[i][2] = vo.getOrder_qty();
            data[i][3] = vo.getPrice();
            // 원가 ( 단가 * 수량 )
            int tmpCst = Integer.parseInt(vo.getUnit_price())*Integer.parseInt(vo.getOrder_qty());
            String cost = String.valueOf(tmpCst);
            data[i][4] = cost;
            String profit = String.valueOf(
                    Integer.parseInt(vo.getPrice()) - tmpCst
            );
            data[i][5] = profit;
        }
        
        jTable4.setModel(new DefaultTableModel(data, a_name));
        
        p_Analyze.revalidate();
    }
}