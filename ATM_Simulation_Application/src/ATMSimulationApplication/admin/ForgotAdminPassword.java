package ATMSimulationApplication.admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import ATMSimulationApplication.database.Conn;

public class ForgotAdminPassword extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField usernameField, emailField, phoneField;
    JPasswordField newPasswordField, confirmPasswordField;
    JButton resetButton, backButton;

    public ForgotAdminPassword() {
        setTitle("Forgot Admin Password");
        setLayout(null);

        JLabel title = new JLabel("QUÊN MẬT KHẨU ADMIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 40, 700, 40);
        add(title);

        JLabel userLabel = new JLabel("Tên đăng nhập:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setBounds(130, 120, 150, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 120, 250, 30);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setBounds(130, 170, 150, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(300, 170, 250, 30);
        emailField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(emailField);

        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        phoneLabel.setBounds(130, 220, 150, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(300, 220, 250, 30);
        phoneField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(phoneField);
        
        JLabel newPassLabel = new JLabel("Mật khẩu mới:");
        newPassLabel.setForeground(Color.WHITE);
        newPassLabel.setFont(new Font("Arial", Font.BOLD, 16));
        newPassLabel.setBounds(130, 270, 150, 30);
        add(newPassLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(300, 270, 250, 30);
        newPasswordField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(newPasswordField);

        JLabel confirmLabel = new JLabel("Nhập lại mật khẩu:");
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 16));
        confirmLabel.setBounds(130, 320, 150, 30);
        add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(300, 320, 250, 30);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(confirmPasswordField);

        resetButton = new JButton("Đổi mật khẩu");
        resetButton.setBounds(300, 380, 120, 35);
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        add(resetButton);

        backButton = new JButton("Quay lại");
        backButton.setBounds(430, 380, 120, 35);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        add(backButton);

        resetButton.addActionListener(this);
        backButton.addActionListener(this);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/backg.png"));
        Image bgImg = bgIcon.getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH);
        JLabel bg = new JLabel(new ImageIcon(bgImg));
        bg.setBounds(0, 0, 700, 500);
        add(bg);

        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            setVisible(false);
            new AdminLogin();
            return;
        }

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.equals("") || email.equals("") || phone.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {	// false
            JOptionPane.showMessageDialog(null, "Mật khẩu nhập lại không khớp!");
            return;
        }

        try {
            Conn c = new Conn();

            ResultSet rs = c.s.executeQuery(
                    "SELECT * FROM admin WHERE username='" + username + 
                    "' AND email='" + email + "' AND phone='" + phone + "'"
            );	// Xác thực tài khoản để lấy lại mk

            if (!rs.next()) {	// false
                JOptionPane.showMessageDialog(null, "Username, Email hoặc Số điện thoại không đúng!");
                return;
            }

            c.s.executeUpdate(
                    "UPDATE admin SET password='" + newPassword + "' WHERE username='" + username + "'"
            );

            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!");

            setVisible(false);
            new AdminLogin();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể đổi mật khẩu!");
        }
    }
}
