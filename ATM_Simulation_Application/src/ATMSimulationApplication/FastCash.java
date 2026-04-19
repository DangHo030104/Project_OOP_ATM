package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
    JTextField t1;
    String pin;

    FastCash(String pin) {  	
        this.pin = pin;
        
		setTitle("ATM Simulation Application");
		setLayout(null);
        
		// Tạo ảnh nền ATM phủ toàn bộ JFrame.
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));  // Lấy file ảnh atm.jpg từ thư mục resource icons -> Tạo 1 ImageIcon từ ảnh đó.
		Image i2 = i1.getImage().getScaledInstance(800, 750, Image.SCALE_DEFAULT);     // Lấy ảnh gốc -> Resize 800 x 750
		ImageIcon i3 = new ImageIcon(i2);											   // Chuyển i2 lại thành ImageIcon
		JLabel l3 = new JLabel(i3);													   // Đặt ảnh vào JLabel
		l3.setBounds(0, 0, 800, 750);    											   // Ảnh nền sẽ phủ lên toàn bộ frame.
		add(l3);						  											   // Add l3 vào Frame.

        l1 = new JLabel("SELECT WITHDRAWL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(180, 250, 300, 30);
        l3.add(l1);
        
        b1 = new JButton("Rs 100");
        b1.setBounds(150, 300, 120, 30);
        l3.add(b1);
        
        b2 = new JButton("Rs 500");
        b2.setBounds(330, 300, 120, 30);
        l3.add(b2);
        
        b3 = new JButton("Rs 1000");
        b3.setBounds(150, 340, 120, 30);
        l3.add(b3);
        
        b4 = new JButton("Rs 2000");
        b4.setBounds(330, 340, 120, 30);
        l3.add(b4);
        
        b5 = new JButton("Rs 5000");
        b5.setBounds(150, 380, 120, 30);
        l3.add(b5);
        
        b6 = new JButton("Rs 10000");
        b6.setBounds(330, 380, 120, 30);
        l3.add(b6);
        
        b7 = new JButton("BACK");
        b7.setBounds(330, 420, 120, 30);
        l3.add(b7);

        // Đăng ký listener cho btn.  
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

		setSize(800, 750);
		setLocationRelativeTo(null); // Cửa sổ giữa màn hình
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }

    // Xử lý sự kiện Btn
    public void actionPerformed(ActionEvent event) {
        try {
            String amount = ((JButton)event.getSource()).getText().substring(3); // Lấy Text trong btn sau khi substring(3): lấy chuỗi từ index 3 -> Cắt chuỗi.
            
            Conn c = new Conn();
            
            ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"'");	// Lấy toàn bộ giao dịch của user theo PIN
            
            int balance = 0;	// Số dư
            while (rs.next()) {	// Duyệt qua từng giao dịch để tính số dư (True).
                if (rs.getString("type").equals("Deposit")) {		// Type = Deposit
                    balance += Integer.parseInt(rs.getString("amount"));	// + vào số dư
                } else {											// Type = Withdrawal
                    balance -= Integer.parseInt(rs.getString("amount"));	// - vào số dư
                }
            } 
                  
            if (event.getSource() != b7 && balance < Integer.parseInt(amount)) {	// Nếu nút bấm khác BACK và số dư < số tiền cần rút
                JOptionPane.showMessageDialog(null, "Insuffient Balance");
                return;		// Thoát hàm
            }

            if (event.getSource() == b7) {		// Btn BACK
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
            }else{
                Date date = new Date();		// Lưu thời gian giao dịch rút tiền
                c.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Withdrawl', '"+amount+"')");
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");		// "Rút tiền thành công"
                    
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new FastCash("");
    }
}
