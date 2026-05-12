package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel l1, l2, l3;
	JTextField tf1;
	JPasswordField pf2;
	JButton b1, b2;

	Login() {
		setTitle("ATM Login");
		setLayout(null); // Set components thủ công setBounds(...)

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo_bank.png"));  // ClassLoader.getSystemResource(...): dùng để lấy file tài nguyên trong project
		Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); 		// Lấy ảnh gốc ra dưới dạng Image, sau đó Resize lại 100x100 pixel
		ImageIcon i3 = new ImageIcon(i2);  // Chuyển ảnh đã resize lại thành ImageIcon
		JLabel image = new JLabel(i3);     // Tạo 1 JLabel chứa i3
		image.setBounds(120, 10, 120, 120); // Set i3 ở tọa độ (70, 10) với kích thước 100x100
		add(image); 					   // Add Label vào JFrame.

        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo_card.png"));
        Image ii2 = ii1.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(600,300,100,100);
        add(iimage);
		
		l1 = new JLabel("BLACK BANK ATM");
		l1.setFont(new Font("Arial", Font.BOLD, 38));
		l1.setForeground(Color.WHITE);
		l1.setBounds(240, 50, 450, 40);
		add(l1);

		// Card No
		l2 = new JLabel("Số thẻ:");
		l2.setFont(new Font("Arial", Font.BOLD, 20));
		l2.setForeground(Color.WHITE);
		l2.setBounds(200, 150, 375, 30);
		add(l2);

		tf1 = new JTextField(15);
		tf1.setBounds(300, 150, 230, 30);
		tf1.setFont(new Font("Arial", Font.BOLD, 14));
		tf1.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(tf1);

		// PIN
		l3 = new JLabel("Mã PIN:");
		l3.setFont(new Font("Arial", Font.BOLD, 20));
		l3.setForeground(Color.WHITE);
		l3.setBounds(200, 200, 375, 30);
		add(l3);

		pf2 = new JPasswordField(15);
		pf2.setFont(new Font("Arial", Font.BOLD, 14));
		pf2.setBounds(300, 200, 230, 30);
		pf2.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); 
		add(pf2);

		b1 = new JButton("Đăng nhập");
		b1.setBackground(Color.BLACK); // Nền đen
		b1.setForeground(Color.WHITE); // Chữ trắng
		b1.setFont(new Font("Arial", Font.BOLD, 14));
		b1.setBounds(420, 280, 110, 30);
		add(b1);

		b2 = new JButton("Xóa");
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);
		b2.setFont(new Font("Arial", Font.BOLD, 14));
		b2.setBounds(470, 240, 60, 30);
		add(b2);

		// Đăng ký listener cho các button.
		b1.addActionListener(this); // this -> Login
		b2.addActionListener(this);

        ImageIcon iii1 = new ImageIcon(ClassLoader.getSystemResource("icons/backg.png"));
        Image iii2 = iii1.getImage().getScaledInstance(800,500,Image.SCALE_SMOOTH);  // SCALE_SMOOTH:giữ nguyên chất lượng ảnh khi resize.
        ImageIcon iii3 = new ImageIcon(iii2);
        JLabel iiimage = new JLabel(iii3);
        iiimage.setBounds(0,0,800,500);
        add(iiimage);
		
		getContentPane().setBackground(Color.WHITE); // Set màu nền của JFrame (Login)
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Hàm xử lý sự kiện JButton
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() == b1) { // SIGN IN
				String cardno = tf1.getText();
				String pin = new String(pf2.getPassword());

				Conn c1 = new Conn();

				// Query kiểm tra cardno, pin của login có trùng với cardno, pin của Login
				String query = "select * from login where SOTHE = '" + cardno + "' and MA_PIN = '" + pin + "'";

				ResultSet rs = c1.s.executeQuery(query); // Chạy query SELECT -> Lưu kq trong rs

				if (rs.next()) {       // rs.next() trả về true nếu có ít nhất một dòng dữ liệu trùng mã PIN.
					setVisible(false);
					new Transactions(pin).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Số thẻ hoặc mã PIN không đúng!");
				}

			} else if (event.getSource() == b2) { // CLEAR
				tf1.setText("");
				pf2.setText("");
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}
