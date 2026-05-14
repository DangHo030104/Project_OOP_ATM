package ATMSimulationApplication.atm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ATMSimulationApplication.admin.AdminLogin;
import ATMSimulationApplication.database.Conn;

import java.sql.*;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class ATMLogin extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel l1, l2, l3, adminLabel, forgotPinLabel;
	JTextField tf1;
	JPasswordField pf2;
	JButton b1;

	public ATMLogin() {
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
		b1.setBounds(300, 280, 230, 30);
		add(b1);
		
		forgotPinLabel = new JLabel("Quên mã PIN?");
		forgotPinLabel.setForeground(Color.WHITE);
		forgotPinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		forgotPinLabel.setBounds(440, 235, 120, 25);
		forgotPinLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(forgotPinLabel);

		forgotPinLabel.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        setVisible(false);
		        new ForgotPin();
		    }
		});
		
		adminLabel = new JLabel("Admin Login");
		adminLabel.setBounds(650, 410, 140, 30);
		adminLabel.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		adminLabel.setForeground(Color.WHITE);
		adminLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		adminLabel.setText("<html><u>Admin Login</u></html>");
		add(adminLabel);
		
		adminLabel.addMouseListener(new MouseAdapter() {

		    @Override
		    public void mouseClicked(MouseEvent e) {

		        setVisible(false);

		        new AdminLogin().setVisible(true);
		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {

		        adminLabel.setForeground(new Color(255, 215, 0));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {

		        adminLabel.setForeground(Color.WHITE);
		    }
		});
		
		// Đăng ký listener cho các button.
		b1.addActionListener(this); // this -> ATMLogin

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
			if (event.getSource() == b1) { // Đăng nhập
				String cardno = tf1.getText();
				String pin = new String(pf2.getPassword());

				Conn c1 = new Conn();

				// Query kiểm tra cardno, pin của login có trùng với cardno, pin của Login
				String query = "select * from login where SOTHE = '" + cardno + "' and MA_PIN = '" + pin + "'";

				ResultSet rs = c1.s.executeQuery(query); // Chạy query SELECT -> Lưu kq trong rs

				if (rs.next()) {       // rs.next() trả về true nếu có ít nhất một dòng dữ liệu trùng mã PIN.
					JOptionPane.showMessageDialog(null, "Đăng nhập ATM thành công!");
					setVisible(false);
					new Transactions(pin).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Số thẻ hoặc mã PIN không đúng!");
				}
			}
//			} else if (event.getSource() == b2) { // Xóa
//				tf1.setText("");
//				pf2.setText("");
//				
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ATMLogin();
	}
}
