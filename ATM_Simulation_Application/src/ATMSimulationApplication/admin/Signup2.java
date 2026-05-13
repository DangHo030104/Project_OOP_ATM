package ATMSimulationApplication.admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ATMSimulationApplication.database.Conn;

import java.util.*;

public class Signup2 extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14;
	JRadioButton r1, r2, r3, r4;
	JButton b1, b2;
	JCheckBox c1, c2, c3, c4, c5, c6, c7;
	JTextField t1;
	
	String formno;	 // Biến này để lưu trữ số form, được truyền từ Signup1 sang Signup2 để hiển thị và lưu vào database.
	Signup1 signup1; // Biến này để lưu trữ dữ liệu đã nhập ở Signup1, sau đó có thể sử dụng lại ở Signup2.
	
	Signup2 (String formno, Signup1 signup1) {
		
		this.formno = formno;
		this.signup1 = signup1;
		
		setTitle("NEW ATM ACCOUNT APPLICATION FORM");
		setLayout(null);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo_bank.png"));  // Tải ảnh từ resource icons/logo_bank.png.
		Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);  	// Lấy ảnh ra và resize thành 100x100.
		ImageIcon i3 = new ImageIcon(i2); 												// Tạo lại ImageIcon từ ảnh đã resize.
		JLabel label = new JLabel(i3);													// Đặt icon đó vào JLabel
		label.setBounds(150, 0, 120, 120);
		add(label);																		// Đưa label lên cửa sổ.

		l1 = new JLabel("Trang 2: Thông tin tài khoản");
		l1.setFont(new Font("Raleway", Font.BOLD, 22));
		l1.setBounds(280, 40, 600, 40);
		add(l1);

		// Account Type
		l2 = new JLabel("Loại tài khoản:");
		l2.setFont(new Font("Raleway", Font.BOLD, 18));   // BOLD: chữ đậm
		l2.setBounds(100, 150, 200, 30);
		add(l2);

		r1 = new JRadioButton("Tài khoản thanh toán (ATM)");
		r1.setFont(new Font("Raleway", Font.PLAIN, 16));  // PLAIN: chữ bình thường
		r1.setBackground(Color.WHITE);
		r1.setBounds(100, 180, 250, 30);
		add(r1);

		r2 = new JRadioButton("Tài khoản tiết kiệm");
		r2.setFont(new Font("Raleway", Font.PLAIN, 16));
		r2.setBackground(Color.WHITE);
		r2.setBounds(350, 180, 250, 30);
		add(r2);

		ButtonGroup groupAccType = new ButtonGroup();  // Chỉ cho phép chọn một radio button trong nhóm.
		groupAccType.add(r1);
		groupAccType.add(r2);
		
		// Loại thẻ
		l13 = new JLabel("Loại thẻ:");
		l13.setFont(new Font("Raleway", Font.BOLD, 18));
		l13.setBounds(100, 220, 200, 30);
		add(l13);
		
		r3 = new JRadioButton("Thẻ nội địa");
		r3.setFont(new Font("Raleway", Font.PLAIN, 16));
		r3.setBackground(Color.WHITE);
		r3.setBounds(100, 250, 250, 30);
		add(r3);

		r4 = new JRadioButton("Thẻ quốc tế (Visa/MasterCard)");
		r4.setFont(new Font("Raleway", Font.PLAIN, 16));
		r4.setBackground(Color.WHITE);
		r4.setBounds(350, 250, 250, 30);
		add(r4);

		ButtonGroup groupCardType = new ButtonGroup();
		groupCardType.add(r3);
		groupCardType.add(r4);

		// Chi nhánh mở tài khoản ngân hàng
		l14 = new JLabel("Chi nhánh ngân hàng:");
		l14.setFont(new Font("Raleway", Font.BOLD, 18));
		l14.setBounds(100, 290, 250, 30);
		add(l14);
		
		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 14));
		t1.setBounds(350, 290, 250, 30);
		t1.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t1);
		
		// Card Number
		l3 = new JLabel("Số thẻ:");
		l3.setFont(new Font("Raleway", Font.BOLD, 18));
		l3.setBounds(100, 330, 200, 30);
		add(l3);

		l4 = new JLabel("XXXX-XXXX-XXXX-4184");
		l4.setFont(new Font("Raleway", Font.BOLD, 18));
		l4.setBounds(350, 330, 250, 30);
		add(l4);

		l5 = new JLabel("(Số thẻ của bạn gồm 16 chữ số)");
		l5.setFont(new Font("Raleway", Font.ITALIC, 12));   // ITALIC: chữ nghiêng
		l5.setBounds(100, 350, 200, 20);
		add(l5);

		l6 = new JLabel("(Thông tin này sẽ hiển thị trên thẻ ATM/sổ séc và sao kê tài khoản)");
		l6.setFont(new Font("Raleway", Font.ITALIC, 12));
		l6.setBounds(350, 350, 500, 20);
		add(l6);

		// PIN
		l7 = new JLabel("Mã PIN:");
		l7.setFont(new Font("Raleway", Font.BOLD, 18));
		l7.setBounds(100, 390, 200, 30);
		add(l7);

		l8 = new JLabel("XXXXXX");
		l8.setFont(new Font("Raleway", Font.BOLD, 18));
		l8.setBounds(350, 390, 200, 30);
		add(l8);

		l9 = new JLabel("(Mã PIN của bạn gồm 6 chữ số)");
		l9.setFont(new Font("Raleway", Font.ITALIC, 12));
		l9.setBounds(100, 410, 200, 20);
		add(l9);

		// Services Required (Có thể chọn nhiều dịch vụ cùng lúc)
		l10 = new JLabel("Dịch vụ yêu cầu:");
		l10.setFont(new Font("Raleway", Font.BOLD, 18));
		l10.setBounds(100, 450, 200, 30);
		add(l10);
		
		c1 = new JCheckBox("Thẻ ATM (ATM Card)");
		c1.setBackground(Color.WHITE);
		c1.setFont(new Font("Raleway", Font.PLAIN, 16));
		c1.setBounds(100, 480, 350, 30);
		add(c1);

		c2 = new JCheckBox("Sao kê điện tử (E-Statement)");
		c2.setBackground(Color.WHITE);
		c2.setFont(new Font("Raleway", Font.PLAIN, 16));
		c2.setBounds(450, 480, 350, 30);
		add(c2);

		c3 = new JCheckBox("Ngân hàng di động (Mobile Banking)");
		c3.setBackground(Color.WHITE);
		c3.setFont(new Font("Raleway", Font.PLAIN, 16));
		c3.setBounds(100, 510, 350, 30);
		add(c3);

		c4 = new JCheckBox("Thông báo qua Email (EMAIL Alerts)");
		c4.setBackground(Color.WHITE);
		c4.setFont(new Font("Raleway", Font.PLAIN, 16));
		c4.setBounds(450, 510, 350, 30);
		add(c4);

		c5 = new JCheckBox("Ngân hàng trực tuyến (Internet Banking)");
		c5.setBackground(Color.WHITE);
		c5.setFont(new Font("Raleway", Font.PLAIN, 16));
		c5.setBounds(100, 540, 350, 30);
		add(c5);

		c6 = new JCheckBox("Thông báo qua SMS (SMS Alerts)");
		c6.setBackground(Color.WHITE);
		c6.setFont(new Font("Raleway", Font.PLAIN, 16));
		c6.setBounds(450, 540, 350, 30);
		add(c6);

		c7 = new JCheckBox("Tôi xác nhận các thông tin đã nhập ở trên là chính xác ", false);    // True -> checkbox này được tick sẵn khi mở form
		c7.setBackground(Color.WHITE);
		c7.setFont(new Font("Raleway", Font.BOLD, 12));
		c7.setBounds(100, 620, 600, 20);
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

		// Btn Hoàn thành
		b1 = new JButton("Hoàn thành");
		b1.setFont(new Font("Raleway", Font.BOLD, 14));
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);
		b1.setBounds(600, 660, 120, 30);
		add(b1);

		// Btn Quay lại
		b2 = new JButton("Quay lại");
		b2.setFont(new Font("Raleway", Font.BOLD, 14));
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);
		b2.setBounds(420, 660, 120, 30);
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
	public void actionPerformed(ActionEvent ae) {
		String atype = null;
		if (r1.isSelected()) {        	// .isSelected() → kiểm tra r... có được chọn không
			atype = "Tài khoản thanh toán (ATM)";
		} else if (r2.isSelected()) {
			atype = "Tài khoản tiết kiệm";
		}

		String ctype = null;
		if (r3.isSelected()) {
			ctype = "Thẻ nội địa";
		} else if (r4.isSelected()) {
			ctype = "Thẻ quốc tế (Visa/MasterCard)";
		}
		
		String bankBranch = t1.getText();
		
		Random ran = new Random();
		// Tạo số thẻ ATM random (16 số).
		long first7 = (ran.nextLong() % 90000000L) + 5040936000000000L;
		String cardno = "" + Math.abs(first7);

		// Tạo PIN random (6 số)
		String pin = String.valueOf(
			    100000 + ran.nextInt(900000)
		);  // Sinh số từ 0->899999, sau đó cộng thêm 100000 để đảm bảo PIN luôn có 6 số.

		String facility = "";   // Gom các dịch vụ đã chọn
		if (c1.isSelected()) {
			facility = facility + " Thẻ ATM (ATM Card)";
		}
		if (c2.isSelected()) {
			facility = facility + " Sao kê điện tử (E-Statement)";
		}
		if (c3.isSelected()) {
			facility = facility + " Ngân hàng di động (Mobile Banking)";
		}
		if (c4.isSelected()) {
			facility = facility + " Thông báo qua Email (EMAIL Alerts)";
		}
		if (c5.isSelected()) {
			facility = facility + " Ngân hàng trực tuyến (Internet Banking)";
		}
		if (c6.isSelected()) {
			facility = facility + " Thông báo qua SMS (SMS Alerts)";
		}

		try {
			if (ae.getSource() == b1) {    // Btn Hoàn Thành

				if (atype == null || ctype == null || bankBranch.equals("") || facility.equals("") || !c7.isSelected()) {   // Kiểm tra nếu chưa chọn loại tài khoản, loại thẻ, chi nhánh ngân hàng, dịch vụ yêu cầu hoặc chưa tick vào checkbox xác nhận.
				    JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin!");
				    return;
				} else {
					Conn c1 = new Conn();
					
					String q1 = "insert into signup2 values('" + formno + "','" + atype + "','" + ctype + "','" + bankBranch + "','" + cardno + "','" + facility + "')";
					
					String q2 = "insert into login values('" + formno + "','" + cardno + "','" + pin + "')";
					
					c1.s.executeUpdate(q1);
					c1.s.executeUpdate(q2);
					
					JOptionPane.showMessageDialog(null, "Card Number: " + cardno + "\n Pin:" + pin);
					
					setVisible(false);
					new AccountManagementPanel().setVisible(true);
				}

			} else if (ae.getSource() == b2) {    // Btn Quay lại
				setVisible(false);
				signup1.setVisible(true);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Signup2("", new Signup1());
	}

}
