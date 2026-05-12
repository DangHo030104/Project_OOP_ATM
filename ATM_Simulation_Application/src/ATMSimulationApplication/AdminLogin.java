package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class AdminLogin extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    JLabel titleLabel, usernameLabel, passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, atmButton;

    AdminLogin() {
        setTitle("Admin Login");
        setLayout(null);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo_admin.png"));  // ClassLoader.getSystemResource(...): dùng để lấy file tài nguyên trong project
		Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); 		// Lấy ảnh gốc ra dưới dạng Image, sau đó Resize lại 100x100 pixel
		ImageIcon i3 = new ImageIcon(i2);  // Chuyển ảnh đã resize lại thành ImageIcon
		JLabel image = new JLabel(i3);     // Tạo 1 JLabel chứa i3
		image.setBounds(150, 0, 120, 120); // Set i3 ở tọa độ (70, 10) với kích thước 100x100
		add(image); 					   // Add Label vào JFrame.
		
        titleLabel = new JLabel("ADMIN LOGIN");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBounds(270, 40, 300, 40);
        add(titleLabel);

        usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setBounds(140, 130, 150, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField.setBounds(300, 130, 250, 30);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(usernameField);

        passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setBounds(140, 180, 150, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField.setBounds(300, 180, 250, 30);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(passwordField);

        loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(300, 230, 250, 30);
        add(loginButton);

        atmButton = new JButton("ATM Login");
        atmButton.setBackground(new Color(31, 41, 55));
        atmButton.setForeground(Color.WHITE);
        atmButton.setFont(new Font("Arial", Font.BOLD, 14));
        atmButton.setBounds(550, 310, 120, 35);
        add(atmButton);

        loginButton.addActionListener(this);
        atmButton.addActionListener(this);

        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/backg.png"));
        Image ii2 = ii1.getImage().getScaledInstance(700,400,Image.SCALE_SMOOTH);  // SCALE_SMOOTH:giữ nguyên chất lượng ảnh khi resize.
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(0,0,700,400);
        add(iimage);
		
		getContentPane().setBackground(Color.WHITE); // Set màu nền của JFrame (Login)
		setSize(700, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == atmButton) {
                setVisible(false);
                new ATMLogin().setVisible(true);
                return;
            }

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            Conn c = new Conn();

            String query = "select * from admin where username = '" 
                    + username + "' and password = '" + password + "'";

            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) { // True
                //JOptionPane.showMessageDialog(null, "Đăng nhập admin thành công!");
                setVisible(false);
                new AdminDashboard().setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}