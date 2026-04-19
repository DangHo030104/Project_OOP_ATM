package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener{
 
    JButton b1;
    JLabel l1, l2, l3, l4;
    
    MiniStatement(String pin){
        setTitle("Mini Statement");
        setLayout(null);
        
        // Lịch sử giao dịch
        l1 = new JLabel();
        l1.setBounds(20, 100, 400, 200);
        add(l1);
        
        // Tên Bank
        l2 = new JLabel("Black Bank");
        l2.setFont(new Font("Raleway", Font.BOLD, 20));
        l2.setBounds(145, 20, 150, 20);
        add(l2);
        
        // Cardno
        l3 = new JLabel();
        l3.setBounds(20, 70, 300, 20);
        add(l3);
        
        // Số dư
        l4 = new JLabel();
        l4.setBounds(20, 450, 300, 20);
        add(l4);
        
        // Btn Exit
        b1 = new JButton("Exit");
        b1.setBounds(250, 500, 100, 25);
        add(b1);
        
        // Truy vấn bảng login để lấy số thẻ
        try{
            Conn c = new Conn();
            
            ResultSet rs = c.s.executeQuery("select * from login where pin = '"+pin+"'");	// Lấy toàn bộ dòng trong bảng login mà có pin đúng với user hiện tại.
            
            while(rs.next()){	// True
                l3.setText("Card Number:    " + rs.getString("cardno").substring(0, 4) + "XXXXXXXX" + rs.getString("cardno").substring(12));	// Hiển thị cardno gồm 4 số đầu và 4 số cuối (1234XXXXXXXX5678)
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        	
        // Truy vấn bảng bank để hiển thị giao dịch và tính số dư
        try{
            int balance = 0;	// Số dư
            
            Conn c1  = new Conn();	// Tạo kết nối DB mới để truy vấn bảng bank.
            
            ResultSet rs = c1.s.executeQuery("SELECT * FROM bank where pin = '"+pin+"'");
            
            while(rs.next()){	// True
            	// Hiển thị từng giao dịch lên l1
                l1.setText(l1.getText() + "<html>"+rs.getString("date")+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount") + "<br><br><html>");
                
                if(rs.getString("type").equals("Deposit")){					// Type = Deposit
                    balance += Integer.parseInt(rs.getString("amount"));	// + vào số dư
                }else{														// Type = Withdrawal
                    balance -= Integer.parseInt(rs.getString("amount"));	// - vào số dư
                }
            }
            // Hiển thị tổng số dư
            l4.setText("Your total Balance is Rs " + balance);
        }catch(Exception e){
            e.printStackTrace();
        }
          
        // Đăng ký sự kiện cho Btn
        b1.addActionListener(this);
                
        setSize(400,600);
        //setLocation(20,20);
        setLocationRelativeTo(null); // Cửa sổ giữa màn hình
        getContentPane().setBackground(Color.WHITE);	// Màu nền JFrame
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        this.setVisible(false);
    }
    
    public static void main(String[] args){
        new MiniStatement("");
    }
    
}

