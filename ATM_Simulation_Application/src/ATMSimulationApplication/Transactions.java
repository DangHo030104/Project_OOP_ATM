package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.sql.*;

public class Transactions extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel l1;
	JButton b1, b2, b3, b4, b5, b6, b7;
	String pin;

	Transactions(String pin) {

		this.pin = pin;

		setTitle("ATM Simulation Application");
		setLayout(null);

		// Tạo ảnh nền ATM phủ toàn bộ JFrame.
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));  // Lấy file ảnh atm.jpg từ thư mục resource icons -> Tạo 1 ImageIcon từ ảnh đó.
		Image i2 = i1.getImage().getScaledInstance(800, 750, Image.SCALE_DEFAULT);     // Lấy ảnh gốc -> Resize 800 x 750
		ImageIcon i3 = new ImageIcon(i2);											   // Chuyển i2 lại thành ImageIcon
		JLabel l2 = new JLabel(i3);													   // Đặt ảnh vào JLabel
		l2.setBounds(0, 0, 800, 750);    	// Ảnh nền sẽ phủ lên toàn bộ frame.
		add(l2);						  	// Add l2 vào Frame.

		l1 = new JLabel("Vui lòng lựa chọn loại giao dịch");
		l1.setForeground(Color.WHITE);
		l1.setFont(new Font("System", Font.BOLD, 16));
		l1.setBounds(175, 250, 300, 30);
		l2.add(l1);

		b1 = new JButton("NẠP TIỀN");
		b1.setFont(new Font("System", Font.BOLD, 10));
		b1.setBounds(150, 300, 140, 30);
		l2.add(b1);

		b2 = new JButton("RÚT TIỀN");
		b2.setFont(new Font("System", Font.BOLD, 10));
		b2.setBounds(310, 300, 140, 30);
		l2.add(b2);

		b3 = new JButton("CHUYỂN KHOẢN");
		b3.setFont(new Font("System", Font.BOLD, 10));
		b3.setBounds(150, 340, 140, 30);
		l2.add(b3);

		b4 = new JButton("IN SAO KÊ");
		b4.setFont(new Font("System", Font.BOLD, 10));
		b4.setBounds(310, 340, 140, 30);
		l2.add(b4);

		b5 = new JButton("ĐỔI PIN");
		b5.setFont(new Font("System", Font.BOLD, 10));
		b5.setBounds(150, 380, 140, 30);
		l2.add(b5);

		b6 = new JButton("TRUY VẤN SỐ DƯ");
		b6.setFont(new Font("System", Font.BOLD, 10));
		b6.setBounds(310, 380, 140, 30);
		l2.add(b6);

		b7 = new JButton("THOÁT");
		b7.setFont(new Font("System", Font.BOLD, 10));
		b7.setBounds(310, 420, 140, 30);
		l2.add(b7);

		// Đăng ký listener cho btn.
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);

		setSize(800, 750);
		setLocationRelativeTo(null); // Cửa sổ giữa màn hình
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {				// Btn NẠP TIỀN
			setVisible(false);
			new Deposit(pin).setVisible(true);
			
		} else if (e.getSource() == b2) {	    // Btn RÚT TIỀN
			setVisible(false);
			new FastCash(pin).setVisible(true);
			
		} else if (e.getSource() == b3) {		// Btn CHUYỂN KHOẢN
			setVisible(false);
			new Transfer(pin).setVisible(true);
			
		} else if (e.getSource() == b4) {		// Btn IN SAO KÊ
			new MiniStatement(pin).setVisible(true);
			
		} else if (e.getSource() == b5) {		// Btn ĐỔI PIN
			setVisible(false);
			new PinChange(pin).setVisible(true);
			
		} else if (e.getSource() == b6) {		// Btn TRUY VẤN SỐ DƯ
			this.setVisible(false);
			new BalanceEnquiry(pin).setVisible(true);
			
		} else if (e.getSource() == b7) {       // Btn THOÁT
			this.setVisible(false);
			new Login().setVisible(true);
		}
	}

	public static void main(String[] args) {
		new Transactions("");
	}
}