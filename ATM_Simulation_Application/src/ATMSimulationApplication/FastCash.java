package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
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

        l1 = new JLabel("Vui lòng chọn số tiền rút");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(200, 250, 300, 30);
        l3.add(l1);
        
        b1 = new JButton("500.000");
        b1.setBounds(150, 300, 120, 30);
        l3.add(b1);
        
        b2 = new JButton("1.000.000");
        b2.setBounds(330, 300, 120, 30);
        l3.add(b2);
        
        b3 = new JButton("2.000.000");
        b3.setBounds(150, 340, 120, 30);
        l3.add(b3);
        
        b4 = new JButton("3.000.000");
        b4.setBounds(330, 340, 120, 30);
        l3.add(b4);
        
        b5 = new JButton("5.000.000");
        b5.setBounds(150, 380, 120, 30);
        l3.add(b5);
        
        b6 = new JButton("SỐ KHÁC");
        b6.setBounds(330, 380, 120, 30);
        l3.add(b6);
        
        b7 = new JButton("QUAY LẠI");
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
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b7) {		// Btn "QUAY LẠI"
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
                return;
            }
        	
            if (ae.getSource() == b6) {		// Btn SỐ KHÁC
            	this.setVisible(false);
               	new Withdrawal(pin).setVisible(true);
            	return;		
            }
        	
            String amount = ((JButton)ae.getSource()).getText().replace(".", "");	// Vd: "5.000.000" → "5000000"
            int iAmount = Integer.parseInt(amount);
            
            String cardno = null;
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT SOTHE FROM login WHERE MA_PIN = '" + pin + "'");
            	
            if (rs.next()) {
				cardno = rs.getString("SOTHE");
			}

            if (cardno == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy số thẻ!");
                return;
            }
            
            Conn c1 = new Conn();
            
            ResultSet rs1 = c1.s.executeQuery(
                    "SELECT * FROM bank WHERE SOTHE = '" + cardno + "'"
                    );	// Lấy toàn bộ giao dịch của user theo số thẻ.
            
            int balance = 0;	// Số dư
            
            while(rs1.next()){	// Lặp qua toàn bộ lịch sử giao dịch của user.
            	if(rs1.getString("LOAIGD").equals("Nạp tiền") || rs1.getString("LOAIGD").equals("Nhận chuyển khoản")){	// Type = Nạp tiền hoặc Nhận chuyển khoản
            		balance += rs1.getInt("SOTIEN");	// -> + vào số dư
                }else{									// Type = Rút tiền hoặc Chuyển khoản đi
                	balance -= rs1.getInt("SOTIEN");	// - vào số dư
                }
            }
            
            // Kiểm tra số dư còn đủ để rút tiền?
            if(balance < iAmount){		
            	JOptionPane.showMessageDialog(null, "Số dư không đủ để rút tiền!");
                return;   
            }
            
            // Nếu đủ tiền -> Lưu giao dịch rút tiền vào DB.
            c1.s.executeUpdate(
                    "INSERT INTO bank(SOTHE, NGAYGD, LOAIGD, SOTIEN) " +
                    "VALUES('" + cardno + "', NOW(), 'Rút tiền', '" + amount + "')"
                );
            
            JOptionPane.showMessageDialog(null, "Rút tiền thành công!");		// Đã trừ thành công ? đồng.
                
            setVisible(false);
            new Transactions(pin).setVisible(true);	                  
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new FastCash("");
    }
}
