package ATMSimulationApplication.admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import ATMSimulationApplication.database.Conn;

public class AdminRegister extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField usernameField, emailField, phoneField;
    JPasswordField passwordField, confirmPasswordField;
    JButton registerButton, backButton;
    JRadioButton maleButton, femaleButton;
    ButtonGroup genderGroup;

    public AdminRegister() {
        setTitle("Admin Register");
        setLayout(null);

        JLabel title = new JLabel("ADMIN REGISTER", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
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
        
        JLabel genderLabel = new JLabel("Giới tính:");
        genderLabel.setForeground(Color.WHITE);
        genderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        genderLabel.setBounds(130, 270, 150, 30);
        add(genderLabel);

        maleButton = new JRadioButton("Nam");
        maleButton.setBounds(300, 270, 80, 30);
        maleButton.setForeground(Color.WHITE);
        maleButton.setBackground(new Color(50, 120, 160));
        add(maleButton);

        femaleButton = new JRadioButton("Nữ");
        femaleButton.setBounds(390, 270, 80, 30);
        femaleButton.setForeground(Color.WHITE);
        femaleButton.setBackground(new Color(50, 120, 160));
        add(femaleButton);

        genderGroup = new ButtonGroup();	
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        
        JLabel passLabel = new JLabel("Mật khẩu:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setBounds(130, 320, 150, 30);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 320, 250, 30);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(passwordField);

        JLabel confirmLabel = new JLabel("Nhập lại mật khẩu:");
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 16));
        confirmLabel.setBounds(130, 370, 150, 30);
        add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(300, 370, 250, 30);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		)); 
        add(confirmPasswordField);

        registerButton = new JButton("Đăng ký");
        registerButton.setBounds(300, 430, 120, 35);
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        add(registerButton);

        backButton = new JButton("Quay lại");
        backButton.setBounds(430, 430, 120, 35);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        add(backButton);

        registerButton.addActionListener(this);
        backButton.addActionListener(this);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/backg.png"));
        Image bgImg = bgIcon.getImage().getScaledInstance(700, 550, Image.SCALE_SMOOTH);
        JLabel bg = new JLabel(new ImageIcon(bgImg));
        bg.setBounds(0, 0, 700, 550);
        add(bg);

        //getContentPane().setBackground(new Color(50, 120, 160));
        setSize(700, 550);
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
        
        String gender = null;
        if(maleButton.isSelected()) {
        	gender = "Nam";
        } else if(femaleButton.isSelected()) {
        	gender = "Nữ";
        }
        
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.equals("") || email.equals("") || phone.equals("") || gender == null || password.equals("") || confirmPassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!password.equals(confirmPassword)) {	// false
            JOptionPane.showMessageDialog(null, "Mật khẩu nhập lại không khớp!");
            return;
        }

        try {
            Conn c = new Conn();

            ResultSet rs = c.s.executeQuery(
                    "SELECT * FROM admin WHERE username='" + username + 
                    "' OR email='" + email + "' OR phone='" + phone + "'"
            );

            if (rs.next()) {	// true
                JOptionPane.showMessageDialog(null, "Username, Email hoặc Số điện thoại đã tồn tại!");
                return;
            }

            String query = "INSERT INTO admin(username, email, phone, gender, password) VALUES('" 
                    + username + "', '" + email + "', '" + phone + "', '" + gender + "', '" + password + "')";

            c.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Đăng ký admin thành công!");

            setVisible(false);
            new AdminLogin();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể đăng ký admin!");
        }
    }
}
