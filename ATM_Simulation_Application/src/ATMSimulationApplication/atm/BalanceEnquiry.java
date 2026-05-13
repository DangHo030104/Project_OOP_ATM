package ATMSimulationApplication.atm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import ATMSimulationApplication.database.Conn;

import java.text.NumberFormat;
import java.util.Locale;

class BalanceEnquiry extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton b1;
    JLabel l1, l3, l4;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;
        
		setTitle("ATM Simulation Application");
		setLayout(null);
        
		// Tạo ảnh nền ATM phủ toàn bộ JFrame.
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));  // Lấy file ảnh atm.jpg từ thư mục resource icons -> Tạo 1 ImageIcon từ ảnh đó.
		Image i2 = i1.getImage().getScaledInstance(800, 750, Image.SCALE_DEFAULT);     // Lấy ảnh gốc -> Resize 800 x 750
		ImageIcon i3 = new ImageIcon(i2);											   // Chuyển i2 lại thành ImageIcon
		JLabel l2 = new JLabel(i3);													   // Đặt ảnh vào JLabel
		l2.setBounds(0, 0, 800, 750);    											   // Ảnh nền sẽ phủ lên toàn bộ frame.
		add(l2);	

		// Label title
		l3 = new JLabel("Thông tin tài khoản");
		l3.setForeground(Color.WHITE);
		l3.setFont(new Font("System", Font.BOLD, 16));
		l3.setBounds(230, 240, 300, 30);
		l2.add(l3);
		
		// Label số thẻ
		l4 = new JLabel();
		l4.setForeground(Color.WHITE);
		l4.setFont(new Font("System", Font.BOLD, 16));
		l4.setBounds(150, 280, 300, 30);
		l2.add(l4);
		
        // Truy vấn bảng login để lấy số thẻ
        try{
            Conn c = new Conn();
            
            ResultSet rs = c.s.executeQuery("select * from login where MA_PIN = '"+pin+"'");	// Lấy toàn bộ dòng trong bảng login mà có pin đúng với user hiện tại.
            
            while(rs.next()){	// True
                l4.setText("Số thẻ:    " + rs.getString("SOTHE").substring(0, 4) + " XXXX XXXX " + rs.getString("SOTHE").substring(12));	// Hiển thị cardno gồm 4 số đầu và 4 số cuối (1234XXXXXXXX5678)
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		// Label hiển thị số dư
        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(150, 320, 300, 30);
        l2.add(l1);
        
        // Btn BACK
        b1 = new JButton("QUAY LẠI");
        b1.setBounds(310, 420, 140, 30);
        l2.add(b1);

        int balance = 0;	// Số dư
        
        // Truy vấn bảng bank để tính số dư của tài khoản hiện tại
        try{
        	String cardno = null;   	
        	       	
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery(
                    "SELECT SOTHE FROM login WHERE MA_PIN = '" + pin + "'"
                );

            if (rs.next()) {
                cardno = rs.getString("SOTHE");
            }
            
            if (cardno != null) {
                ResultSet rs1 = c1.s.executeQuery(
                    "SELECT * FROM bank WHERE SOTHE = '" + cardno + "'"
                );

                while(rs1.next()){	// Lặp qua toàn bộ lịch sử giao dịch của user.
                	if(rs1.getString("LOAIGD").equals("Nạp tiền") || rs1.getString("LOAIGD").equals("Nhận chuyển khoản")){	// Type = Nạp tiền hoặc Nhận chuyển khoản
                		balance += rs1.getInt("SOTIEN");	// + vào số dư
                    }else{									// Type = Rút tiền hoặc Chuyển khoản đi
                    	balance -= rs1.getInt("SOTIEN");	// - vào số dư
                    }
                }
            }

        }catch(Exception e){
        	e.printStackTrace();
        }
        
        // Hiển thị số dư lên label
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));	// Dùng NumberFormat để format số có dấu chấm.
        String formattedBalance = formatter.format(balance);

        l1.setText("Số dư hiện tại: " + formattedBalance + " VND");

        // Đăng ký sự kiện cho btn
        b1.addActionListener(this);

		setSize(800, 750);
		setLocationRelativeTo(null); // Cửa sổ giữa màn hình
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("");
    }
}

