package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	JLabel l1, l2, l3;
	JTextField tf1;
	JPasswordField pf2;
	JButton b1, b2, b3;

	Login() {
		setTitle("ATM Login");
		setLayout(null); // Set components thủ công setBounds(...)

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));  // ClassLoader.getSystemResource(...): dùng để lấy file tài nguyên trong project
		Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT); 		// Lấy ảnh gốc ra dưới dạng Image, sau đó Resize lại 100x100 pixel
		ImageIcon i3 = new ImageIcon(i2);  // Chuyển ảnh đã resize lại thành ImageIcon
		JLabel label = new JLabel(i3);     // Tạo 1 JLabel chứa i3
		label.setBounds(70, 10, 100, 100); // Set i3 ở tọa độ (70, 10) với kích thước 100x100
		add(label); 					   // Add Label vào JFrame.

		l1 = new JLabel("WELCOME TO ATM");
		l1.setFont(new Font("Arial", Font.BOLD, 38));
		l1.setBounds(200, 50, 450, 40);
		add(l1);

		// Card No
		l2 = new JLabel("Card No:");
		l2.setFont(new Font("Arial", Font.BOLD, 28));
		l2.setBounds(125, 150, 375, 30);
		add(l2);

		tf1 = new JTextField(15);
		tf1.setBounds(300, 150, 230, 30);
		tf1.setFont(new Font("Arial", Font.BOLD, 14));
		tf1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // tạo viền màu xám, dày 1 pixel
		add(tf1);

		// PIN
		l3 = new JLabel("PIN:");
		l3.setFont(new Font("Arial", Font.BOLD, 28));
		l3.setBounds(125, 220, 375, 30);
		add(l3);

		pf2 = new JPasswordField(15);
		pf2.setFont(new Font("Arial", Font.BOLD, 14));
		pf2.setBounds(300, 220, 230, 30);
		pf2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		add(pf2);

		b1 = new JButton("SIGN IN");
		b1.setBackground(Color.BLACK); // Nền đen
		b1.setForeground(Color.WHITE); // Chữ trắng
		b1.setFont(new Font("Arial", Font.BOLD, 14));
		b1.setBounds(300, 300, 100, 30);
		add(b1);

		b2 = new JButton("CLEAR");
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);
		b2.setFont(new Font("Arial", Font.BOLD, 14));
		b2.setBounds(430, 300, 100, 30);
		add(b2);

		b3 = new JButton("SIGN UP");
		b3.setBackground(Color.BLACK);
		b3.setForeground(Color.WHITE);
		b3.setFont(new Font("Arial", Font.BOLD, 14));
		b3.setBounds(300, 350, 230, 30);
		add(b3);

		// Đăng ký listener cho các button.
		b1.addActionListener(this); // this -> Login
		b2.addActionListener(this);
		b3.addActionListener(this);

		getContentPane().setBackground(Color.WHITE); // Set màu nền của JFrame (Login)
		setSize(750, 450);
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
				String query = "select * from login where cardno = '" + cardno + "' and pin = '" + pin + "'";

				ResultSet rs = c1.s.executeQuery(query); // Chạy query SELECT -> Lưu kq trong rs

				if (rs.next()) {       // rs.next() trả về true nếu có ít nhất một dòng dữ liệu trùng mã PIN.
					setVisible(false);
					new Transactions(pin).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect Card No or PIN");
				}

			} else if (event.getSource() == b2) { // CLEAR
				tf1.setText("");
				pf2.setText("");
			} else if (event.getSource() == b3) { // SIGN UP
				setVisible(false);
				new Signup1().setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}
