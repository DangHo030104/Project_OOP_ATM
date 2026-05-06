package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

class BalanceEnquiry extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton b1;
    JLabel l1;
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

		// Label hiển thị số dư
        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 14));
        l1.setBounds(150, 250, 300, 30);
        l2.add(l1);
        
        // Btn BACK
        b1 = new JButton("QUAY LẠI");
        b1.setBounds(310, 420, 140, 30);
        l2.add(b1);

        int balance = 0;	// Số dư
        
        try{
            Conn c1 = new Conn();
            
            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");	// Truy vấn tất cả giao dịch bank của tài khoản có pin tương ứng
            
            while (rs.next()) {		// True
                if (rs.getString("type").equals("Deposit") || rs.getString("type").equals("Transfer In")) {		// Type = deposit hoặc Transfer In
                    balance += Integer.parseInt(rs.getString("amount"));	// + vào số dư
                } else {													// Type = Withdrawal hoặc Transfer Out
                    balance -= Integer.parseInt(rs.getString("amount"));	// - vào số dư
                }	
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        // Hiển thị số dư lên label
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));	// Dùng NumberFormat để format số có dấu chấm.
        String formattedBalance = formatter.format(balance);

        l1.setText("Số dư tài khoản: " + formattedBalance + " VND");

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

