package ATMSimulationApplication.atm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import ATMSimulationApplication.database.Conn;

public class ForgotPin extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    JTextField cardField, cccdField, phoneField;
    JPasswordField newPinField, confirmPinField;
    JButton resetButton, backButton;

    public ForgotPin() {
        setTitle("Forgot PIN");
        setLayout(null);

        JLabel title = new JLabel("QUÊN MÃ PIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 35, 700, 40);
        add(title);

        JLabel cardLabel = new JLabel("Số thẻ:");
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cardLabel.setBounds(130, 110, 150, 30);
        add(cardLabel);

        cardField = new JTextField();
        cardField.setBounds(300, 110, 250, 30);
        cardField.setFont(new Font("Arial", Font.PLAIN, 14));
        cardField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        add(cardField);

        JLabel cccdLabel = new JLabel("CCCD/CMND:");
        cccdLabel.setForeground(Color.WHITE);
        cccdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cccdLabel.setBounds(130, 160, 150, 30);
        add(cccdLabel);

        cccdField = new JTextField();
        cccdField.setBounds(300, 160, 250, 30);
        cccdField.setFont(new Font("Arial", Font.PLAIN, 14));
        cccdField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        add(cccdField);

        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        phoneLabel.setBounds(130, 210, 150, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(300, 210, 250, 30);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        add(phoneField);

        JLabel newPinLabel = new JLabel("PIN mới:");
        newPinLabel.setForeground(Color.WHITE);
        newPinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        newPinLabel.setBounds(130, 260, 150, 30);
        add(newPinLabel);

        newPinField = new JPasswordField();
        newPinField.setBounds(300, 260, 250, 30);
        newPinField.setFont(new Font("Arial", Font.BOLD, 14));
        newPinField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        add(newPinField);

        JLabel confirmPinLabel = new JLabel("Nhập lại PIN mới:");
        confirmPinLabel.setForeground(Color.WHITE);
        confirmPinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        confirmPinLabel.setBounds(130, 310, 150, 30);
        add(confirmPinLabel);

        confirmPinField = new JPasswordField();
        confirmPinField.setBounds(300, 310, 250, 30);
        confirmPinField.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPinField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        add(confirmPinField);

        resetButton = new JButton("Đổi mã PIN");
        resetButton.setBounds(300, 370, 120, 35);
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(resetButton);

        backButton = new JButton("Quay lại");
        backButton.setBounds(430, 370, 120, 35);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(backButton);

        resetButton.addActionListener(this);
        backButton.addActionListener(this);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/backg.png"));
        Image bgImg = bgIcon.getImage().getScaledInstance(700, 470, Image.SCALE_SMOOTH);
        JLabel bg = new JLabel(new ImageIcon(bgImg));
        bg.setBounds(0, 0, 700, 470);
        add(bg);

        setSize(700, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            setVisible(false);
            new ATMLogin();
            return;
        }

        String cardno = cardField.getText().trim();
        String cccd = cccdField.getText().trim();
        String phone = phoneField.getText().trim();
        String newPin = new String(newPinField.getPassword());
        String confirmPin = new String(confirmPinField.getPassword());

        if (cardno.equals("") || cccd.equals("") || phone.equals("") ||
                newPin.equals("") || confirmPin.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!newPin.matches("\\d{6}")) {	// false
            JOptionPane.showMessageDialog(null, "Mã PIN mới phải gồm đúng 6 chữ số!");
            return;
        }

        if (!newPin.equals(confirmPin)) {	// false
            JOptionPane.showMessageDialog(null, "Mã PIN nhập lại không khớp!");
            return;
        }

        try {
            Conn c = new Conn();

            String checkQuery =
                    "SELECT * FROM login l " +
                    "JOIN signup1 s1 ON l.FORMNO = s1.FORMNO " +
                    "WHERE l.SOTHE = '" + cardno + "' " +
                    "AND s1.CCCD_CMND = '" + cccd + "' " +
                    "AND s1.DIENTHOAI = '" + phone + "'";

            ResultSet rs = c.s.executeQuery(checkQuery);

            if (!rs.next()) {	// false
                JOptionPane.showMessageDialog(null, "Thông tin xác minh không đúng!");
                return;
            }

            String updateQuery =
                    "UPDATE login SET MA_PIN = '" + newPin + "' " +
                    "WHERE SOTHE = '" + cardno + "'";

            c.s.executeUpdate(updateQuery);

            JOptionPane.showMessageDialog(null, "Đổi mã PIN thành công!");

            setVisible(false);
            new ATMLogin();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể đổi mã PIN!");
        }
    }

    public static void main(String[] args) {
        new ForgotPin();
    }
}
