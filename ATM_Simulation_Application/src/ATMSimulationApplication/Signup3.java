package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Signup3 extends JFrame implements ActionListener {

	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
	JRadioButton r1, r2, r3, r4;
	JButton b1, b2;
	JCheckBox c1, c2, c3, c4, c5, c6, c7;
	String formno;

	Signup3(String formno) {
		
		this.formno = formno;
		
		setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 3");
		setLayout(null);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));  // Tải ảnh từ resource icons/logo.jpg.
		Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);  	// Lấy ảnh ra và resize thành 100x100.
		ImageIcon i3 = new ImageIcon(i2); 												// Tạo lại ImageIcon từ ảnh đã resize.
		JLabel label = new JLabel(i3);													// Đặt icon đó vào JLabel
		label.setBounds(150, 0, 100, 100);
		add(label);																		// Đưa label lên cửa sổ.

		l1 = new JLabel("Page 3: Account Details");
		l1.setFont(new Font("Raleway", Font.BOLD, 22));
		l1.setBounds(280, 40, 600, 40);
		add(l1);

		// Account Type
		l2 = new JLabel("Account Type:");
		l2.setFont(new Font("Raleway", Font.BOLD, 18));   // BOLD: chữ đậm
		l2.setBounds(100, 120, 200, 30);
		add(l2);

		r1 = new JRadioButton("Saving Account");
		r1.setFont(new Font("Raleway", Font.PLAIN, 16));  // PLAIN: chữ bình thường
		r1.setBackground(Color.WHITE);
		r1.setBounds(100, 150, 150, 30);
		add(r1);

		r2 = new JRadioButton("Fixed Deposit Account");
		r2.setFont(new Font("Raleway", Font.PLAIN, 16));
		r2.setBackground(Color.WHITE);
		r2.setBounds(350, 150, 300, 30);
		add(r2);

		r3 = new JRadioButton("Current Account");
		r3.setFont(new Font("Raleway", Font.PLAIN, 16));
		r3.setBackground(Color.WHITE);
		r3.setBounds(100, 180, 250, 30);
		add(r3);

		r4 = new JRadioButton("Recurring Deposit Account");
		r4.setFont(new Font("Raleway", Font.PLAIN, 16));
		r4.setBackground(Color.WHITE);
		r4.setBounds(350, 180, 250, 30);
		add(r4);

		ButtonGroup groupAccType = new ButtonGroup();  // Chỉ cho phép chọn một radio button trong nhóm.
		groupAccType.add(r1);
		groupAccType.add(r2);
		groupAccType.add(r3);
		groupAccType.add(r4);
		
		// Card Number
		l3 = new JLabel("Card Number:");
		l3.setFont(new Font("Raleway", Font.BOLD, 18));
		l3.setBounds(100, 220, 200, 30);
		add(l3);

		l4 = new JLabel("XXXX-XXXX-XXXX-4184");
		l4.setFont(new Font("Raleway", Font.BOLD, 18));
		l4.setBounds(350, 220, 250, 30);
		add(l4);

		l5 = new JLabel("(Your 16-digit Card number)");
		l5.setFont(new Font("Raleway", Font.ITALIC, 12));   // ITALIC: chữ nghiêng
		l5.setBounds(100, 250, 200, 20);
		add(l5);

		l6 = new JLabel("(It would appear on ATM Card/Cheque Book and Statements)");
		l6.setFont(new Font("Raleway", Font.ITALIC, 12));
		l6.setBounds(350, 250, 500, 20);
		add(l6);

		// PIN
		l7 = new JLabel("PIN:");
		l7.setFont(new Font("Raleway", Font.BOLD, 18));
		l7.setBounds(100, 290, 200, 30);
		add(l7);

		l8 = new JLabel("XXXX");
		l8.setFont(new Font("Raleway", Font.BOLD, 18));
		l8.setBounds(350, 290, 200, 30);
		add(l8);

		l9 = new JLabel("(4-digit password)");
		l9.setFont(new Font("Raleway", Font.ITALIC, 12));
		l9.setBounds(100, 320, 200, 20);
		add(l9);

		// Services Required (Có thể chọn nhiều dịch vụ cùng lúc)
		l10 = new JLabel("Services Required:");
		l10.setFont(new Font("Raleway", Font.BOLD, 18));
		l10.setBounds(100, 360, 200, 30);
		add(l10);
		
		c1 = new JCheckBox("ATM CARD");
		c1.setBackground(Color.WHITE);
		c1.setFont(new Font("Raleway", Font.PLAIN, 16));
		c1.setBounds(100, 390, 200, 30);
		add(c1);

		c2 = new JCheckBox("Internet Banking");
		c2.setBackground(Color.WHITE);
		c2.setFont(new Font("Raleway", Font.PLAIN, 16));
		c2.setBounds(350, 390, 200, 30);
		add(c2);

		c3 = new JCheckBox("Mobile Banking");
		c3.setBackground(Color.WHITE);
		c3.setFont(new Font("Raleway", Font.PLAIN, 16));
		c3.setBounds(100, 420, 200, 30);
		add(c3);

		c4 = new JCheckBox("EMAIL Alerts");
		c4.setBackground(Color.WHITE);
		c4.setFont(new Font("Raleway", Font.PLAIN, 16));
		c4.setBounds(350, 420, 200, 30);
		add(c4);

		c5 = new JCheckBox("Cheque Book");
		c5.setBackground(Color.WHITE);
		c5.setFont(new Font("Raleway", Font.PLAIN, 16));
		c5.setBounds(100, 450, 200, 30);
		add(c5);

		c6 = new JCheckBox("E-Statement");
		c6.setBackground(Color.WHITE);
		c6.setFont(new Font("Raleway", Font.PLAIN, 16));
		c6.setBounds(350, 450, 200, 30);
		add(c6);

		c7 = new JCheckBox("I hereby declares that the above entered details correct to th best of my knowledge.",
				true);    // True -> checkbox này được tick sẵn khi mở form
		c7.setBackground(Color.WHITE);
		c7.setFont(new Font("Raleway", Font.BOLD, 12));
		c7.setBounds(100, 550, 600, 20);
		add(c7);
		
		// Form No
		l11 = new JLabel("Form No:");
		l11.setFont(new Font("Raleway", Font.BOLD, 14));
		l11.setBounds(650, 10, 70, 30);
		add(l11);
		
		l12 = new JLabel(formno);
		l12.setFont(new Font("Raleway", Font.BOLD, 14));
		l12.setBounds(720, 10, 40, 30);
		add(l12);

		// Btn Submit
		b1 = new JButton("Submit");
		b1.setFont(new Font("Raleway", Font.BOLD, 14));
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);
		b1.setBounds(250, 600, 100, 30);
		add(b1);

		// Btn Cancel
		b2 = new JButton("Cancel");
		b2.setFont(new Font("Raleway", Font.BOLD, 14));
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);
		b2.setBounds(420, 600, 100, 30);
		add(b2);

		// Đăng ký listener cho btn.
		b1.addActionListener(this);
		b2.addActionListener(this);

		getContentPane().setBackground(Color.WHITE);
		setSize(800, 750);
		setLocationRelativeTo(null);    // Cửa sổ giữa màn hình
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Hàm xử lý sự kiện btn.
	public void actionPerformed(ActionEvent event) {
		String atype = null;
		if (r1.isSelected()) {        	// .isSelected() → kiểm tra r... có được chọn không
			atype = "Saving Account";
		} else if (r2.isSelected()) {
			atype = "Fixed Deposit Account";
		} else if (r3.isSelected()) {
			atype = "Current Account";
		} else if (r4.isSelected()) {
			atype = "Recurring Deposit Account";
		}

		// Tạo số thẻ ATM random.
		Random ran = new Random();
		long first7 = (ran.nextLong() % 90000000L) + 5040936000000000L;
		String cardno = "" + Math.abs(first7);

		// Tạo PIN random
		long first3 = (ran.nextLong() % 9000L) + 1000L;
		String pin = "" + Math.abs(first3);

		String facility = "";   // Gom các dịch vụ đã chọn
		if (c1.isSelected()) {
			facility = facility + " ATM Card";
		}
		if (c2.isSelected()) {
			facility = facility + " Internet Banking";
		}
		if (c3.isSelected()) {
			facility = facility + " Mobile Banking";
		}
		if (c4.isSelected()) {
			facility = facility + " EMAIL Alerts";
		}
		if (c5.isSelected()) {
			facility = facility + " Cheque Book";
		}
		if (c6.isSelected()) {
			facility = facility + " E-Statement";
		}

		try {
			if (event.getSource() == b1) {    // Btn Submit

				if (atype.equals("")) {       // Nếu atype trống → báo lỗi
					JOptionPane.showMessageDialog(null, "Fill all the required fields");
				} else {
					Conn c1 = new Conn();
					
					String q1 = "insert into signup3 values('" + formno + "','" + atype + "','" + cardno + "','" + pin
							+ "','" + facility + "')";
					
					String q2 = "insert into login values('" + formno + "','" + cardno + "','" + pin + "')";
					
					c1.s.executeUpdate(q1);
					c1.s.executeUpdate(q2);
					
					JOptionPane.showMessageDialog(null, "Card Number: " + cardno + "\n Pin:" + pin);

//                    new Deposit(pin).setVisible(true);
//                    setVisible(false);
				}

			} else if (event.getSource() == b2) {    // Btn Cancel
				setVisible(false);
				new Login().setVisible(true);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Signup3("");
	}

}
