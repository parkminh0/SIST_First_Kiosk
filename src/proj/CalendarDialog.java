package proj;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.*;

public class CalendarDialog extends JDialog{
    Calendar cal; //캘린더
    int year, month, day;
    String selectDate = null;
    JPanel pane = new JPanel();

    //위에 버튼 추가
    JButton btn1 = new JButton("◀");//이전버튼
    JButton btn2 = new JButton("▶"); //다음버튼

    //위에 라벨추가
    JLabel lb_year = new JLabel("년");
    JLabel lb_month = new JLabel("월");

    //년월 추가
    JComboBox<Integer> yearCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
    JComboBox<Integer> monthCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();

    //패널추가
    JPanel pane2 = new JPanel(new BorderLayout());
    JPanel title = new JPanel(new GridLayout(1, 7));
    String titleStr[] = {"일", "월", "화", "수", "목", "금", "토"};
    JPanel datePane = new JPanel(new GridLayout(0, 7));
    JLabel lbl = new JLabel();
        //화면디자인
    
    JTextField jf;
    
    public CalendarDialog(JTextField jf) {
       this.jf = jf;
       
        //------년도 월 구하기------------
        cal = Calendar.getInstance(); //현재날짜
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1; //Jframe의 month는 0부터 시작이기에 + 1 해야됨
        day = cal.get(Calendar.DATE);

        //년
        for(int i= 2020;i <= 2050; i++){
            yearModel.addElement(i);
        }

        yearCombo.setModel(yearModel);
        yearCombo.setSelectedItem(year);

        //월
        for(int i=1; i<=12; i++) {
            monthModel.addElement(i);
        }
        monthCombo.setModel(monthModel);
        monthCombo.setSelectedItem(month);

        //월화수목금토일
        for(int i=0; i<titleStr.length; i++){
            JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
            if(i == 0){
                lbl.setForeground(Color.red);
            }else if(i == 6){
                lbl.setForeground(Color.blue);
            }
            title.add(lbl);
        }
        //날짜 출력
        day(year, month);

        //----------------------------
        setTitle("달력");
        pane.add(btn1);
        pane.add(yearCombo);
        pane.add(lb_year);
        pane.add(monthCombo);
        pane.add(lb_month);
        pane.add(btn2);
        pane.setPreferredSize(new Dimension(180,35));

        add(BorderLayout.NORTH, pane);
        pane2.add(title,"North");
        pane2.add(datePane);
        add(BorderLayout.CENTER, pane2);

        //각종 명령어
        setVisible(true);
        setSize(380,220);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        //----------기능구현----------
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(month == 1){
                    year--;
                    month=12;
                }else{
                    month--;
                }
                day(year, month);
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(month == 12){
                    year++;
                    month = 1;
                }else{
                    month++;
                }
                day(year, month);
            }
        });

        yearCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                day((Integer)yearCombo.getSelectedItem(), (Integer)monthCombo.getSelectedItem());
            }
        });

        monthCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                day((Integer)yearCombo.getSelectedItem(), (Integer)monthCombo.getSelectedItem());
            }
        });
    }

    //날짜출력
    public void day(int year, int month) {
        datePane.removeAll();

        Calendar date = Calendar.getInstance();//오늘날짜 + 시간 객체 생성
        date.set(year, month - 1, 1); // 올해 이번 달 1일로 데이트 값을 셋팅
        int firstDay = date.get(Calendar.DAY_OF_WEEK); // 이번달 1일의 요일을 얻어낸다
        int lastDay = date.getActualMaximum(Calendar.DAY_OF_MONTH);

        //공백출력
        for (int i = 1; i < firstDay; i++) {
            datePane.add(new JLabel(""));
        }

        //날짜 출력
        for (int day = 1; day <= lastDay; day++) {
            JButton btn = new JButton(String.valueOf(day));
            cal.set(year, month - 1, day);
            int week = cal.get(Calendar.DAY_OF_WEEK);
            if (week == 1) {
                btn.setForeground(Color.red);
            } else if (week == 7) {
                btn.setForeground(Color.BLUE);
            }
            datePane.add(btn);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + btn.getText();
                    jf.setText(selectDate);
                    dispose();
                }
            });
            datePane.revalidate();
            datePane.repaint();
        }
        
        yearCombo.setSelectedItem(year);
        monthCombo.setSelectedItem(month);
    }
    public String GetSelectDate() {
        return selectDate;
    }
}