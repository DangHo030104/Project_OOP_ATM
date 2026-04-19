package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener{
    
    JPasswordField t1,t2;
    JButton b1,b2;                               
    JLabel l1,l2,l3;
    String pin;
    
    PinChange(String pin){
        this.pin = pin;
        
		setTitle("ATM Simulation Application");
		setLayout(null);
        
		// Tạo ảnh nền ATM phủ toàn bộ JFrame.
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));  // Lấy file ảnh atm.jpg từ thư mục resource icons -> Tạo 1 ImageIcon từ ảnh đó.
		Image i2 = i1.getImage().getScaledInstance(800, 750, Image.SCALE_DEFAULT);     // Lấy ảnh gốc -> Resize 800 x 750
		ImageIcon i3 = new ImageIcon(i2);											   // Chuyển i2 lại thành ImageIcon
		JLabel l4 = new JLabel(i3);													   // Đặt ảnh vào JLabel
		l4.setBounds(0, 0, 800, 750);    											   // Ảnh nền sẽ phủ lên toàn bộ frame.
		add(l4);	
        
        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        l1.setBounds(230, 240, 300, 30);
        l4.add(l1);
        
        // New PIN
        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 14));
        l2.setForeground(Color.WHITE);
        l2.setBounds(150, 280, 150, 30);
        l4.add(l2);
        
        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        t1.setBounds(280, 280, 170, 25);
        l4.add(t1);
        
        // Re-Enter New PIN
        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 14));
        l3.setForeground(Color.WHITE);
        l3.setBounds(150, 310, 200, 30);
        l4.add(l3);
               
        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));
        t2.setBounds(280, 310, 170, 25);
        l4.add(t2);
        
        // Btn CHANGE
        b1 = new JButton("CHANGE");
        b1.setBounds(310, 380, 140, 30);
        l4.add(b1);
        
        // Btn BACK
        b2 = new JButton("BACK");
        b2.setBounds(310, 420, 140, 30);
        l4.add(b2);
        
        // Đăng ký listener cho btn.
        b1.addActionListener(this);
        b2.addActionListener(this);
        
		setSize(800, 750);
		setLocationRelativeTo(null); // Cửa sổ giữa màn hình
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }
    
    // Xử lý sự kiện Btn
    public void actionPerformed(ActionEvent ae){
        try{        
            String npin = new String(t1.getPassword());		// Lấy data từ ô nhập PIN mới.
            String rpin = new String(t2.getPassword());		// Lấy data từ ô nhập lại PIN mới.
            
            // Kiểm tra 2 PIN có trùng nhau không?
            if(!npin.equals(rpin)){		// False
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                return;		// Thoát hàm
            }
            
            if(ae.getSource() == b1){		// Btn CHANGE
                if (t1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter New PIN");
                }
                if (t2.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Re-Enter new PIN");
                }
                
                Conn c1 = new Conn();	// Tạo đối tượng kết nối tới DB.
                
                // Cập nhật PIN cho các table đang lưu PIN cũ
                String q1 = "update bank set pin = '"+rpin+"' where pin = '"+pin+"' ";
                String q2 = "update login set pin = '"+rpin+"' where pin = '"+pin+"' ";
                String q3 = "update signup3 set pin = '"+rpin+"' where pin = '"+pin+"' ";

                // Thực thi cmd UPDATE
                c1.s.executeUpdate(q1);
                c1.s.executeUpdate(q2);
                c1.s.executeUpdate(q3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new Transactions(rpin).setVisible(true);
            
            }else if(ae.getSource() == b2){		// Btn BACK
            	setVisible(false);
                new Transactions(pin).setVisible(true);         
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new PinChange("");
    }
}
