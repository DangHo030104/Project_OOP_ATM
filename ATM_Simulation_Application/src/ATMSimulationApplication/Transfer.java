package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class Transfer extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    JTextField tfCard, tfAmount;
    JButton b1, b2;
    JLabel l1, l2, l3;
    String pin;

    Transfer(String pin) {
        this.pin = pin;

        setTitle("ATM Simulation Application");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(800, 750, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel bg = new JLabel(i3);
        bg.setBounds(0, 0, 800, 750);
        add(bg);

        l1 = new JLabel("Vui lòng nhập thông tin chuyển khoản");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("Arial", Font.BOLD, 16));
        l1.setBounds(155, 240, 300, 30);
        bg.add(l1);

        l2 = new JLabel("Số thẻ nhận:");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("Arial", Font.BOLD, 14));
        l2.setBounds(150, 285, 130, 30);
        bg.add(l2);

        tfCard = new JTextField();
        tfCard.setBounds(270, 285, 180, 28);
        tfCard.setFont(new Font("Arial", Font.BOLD, 14));
        tfCard.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
        bg.add(tfCard);

        l3 = new JLabel("Số tiền:");
        l3.setForeground(Color.WHITE);
        l3.setFont(new Font("Arial", Font.BOLD, 14));
        l3.setBounds(150, 325, 130, 30);
        bg.add(l3);

        tfAmount = new JTextField();
        tfAmount.setBounds(270, 325, 180, 28);
        tfAmount.setFont(new Font("Arial", Font.BOLD, 14));
        tfAmount.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
        bg.add(tfAmount);

        b1 = new JButton("CHUYỂN KHOẢN");
        b1.setBounds(310, 380, 140, 30);
        bg.add(b1);

        b2 = new JButton("QUAY LẠI");
        b2.setBounds(310, 420, 140, 30);
        bg.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(800, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b2) {		// Btn QUAY LẠI
                setVisible(false);
                new Transactions(pin).setVisible(true);
                return;
            }

            String receiverCard = tfCard.getText().trim();
            String amountText = tfAmount.getText().trim();

            if (receiverCard.equals("") || amountText.equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Số tiền không hợp lệ!");
                return;
            }

            Conn c = new Conn();

            ResultSet receiverRs = c.s.executeQuery(
                    "select * from login where SOTHE = '" + receiverCard + "'"
            );

            if (!receiverRs.next()) {
                JOptionPane.showMessageDialog(null, "Số thẻ nhận không tồn tại!");
                return;
            }

            String receiverPin = receiverRs.getString("MA_PIN");

            if (receiverPin.equals(pin)) {	// False
                JOptionPane.showMessageDialog(null, "Không thể chuyển khoản cho chính mình!");
                return;
            }

            ResultSet senderRs = c.s.executeQuery(
                    "select * from bank where pin = '" + pin + "'"
            );

            int balance = 0;

            while (senderRs.next()) {
                if (senderRs.getString("type").equalsIgnoreCase("Deposit") || senderRs.getString("type").equalsIgnoreCase("Transfer In")) {	// Type = Deposit hoặc Transfer In
                    balance += Integer.parseInt(senderRs.getString("amount"));
                } else {														// Type = Withdrawal hoặc Transfer Out	
                    balance -= Integer.parseInt(senderRs.getString("amount"));
                }
            }

            if (balance < amount) {
                JOptionPane.showMessageDialog(null, "Số dư không đủ để chuyển khoản!");
                return;
            }

            Date date = new Date();

            c.s.executeUpdate("insert into bank values('" + pin + "', '" + date + "', 'Transfer Out', '" + amount + "')");			// Người gửi
            c.s.executeUpdate("insert into bank values('" + receiverPin + "', '" + date + "', 'Transfer In', '" + amount + "')");	// Người nhận

            JOptionPane.showMessageDialog(null, "Chuyển khoản thành công!");

            setVisible(false);
            new Transactions(pin).setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Transfer("");
    }
}