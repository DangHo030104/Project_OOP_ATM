package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.text.SimpleDateFormat;

public class Signup1 extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
	JTextField t1, t2, t3, t4, t5, t6, t7, t8;
	JRadioButton r1, r2;
	JButton b1, b2;
	JDateChooser dateChooser;

	boolean isInserted = false;  // Cờ để kiểm tra xem dữ liệu đã được chèn vào DB hay chưa
	
	// Tạo số form ngẫu nhiên
	Random ran = new Random();
	long first4 = (ran.nextLong() % 9000L) + 1000L; // ran.nextLong(): sinh số kiểu long % 9000L: giới hạn về khoảng
													// 9000 số + 1000L: để tránh số quá nhỏ
	String first = "" + Math.abs(first4); 			// Bỏ số âm, sau đó đổi sang String

	Signup1() {
		setTitle("NEW ATM ACCOUNT APPLICATION FORM");
		setLayout(null);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo_bank.png"));
		Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel label = new JLabel(i3);
		label.setBounds(150, 0, 120, 120);
		add(label);
		
		l1 = new JLabel("Form No: " + first);
		l1.setFont(new Font("Raleway", Font.BOLD, 14));
		l1.setBounds(650, 10, 100, 30);
		add(l1);
		
		l2 = new JLabel("Trang 1: Thông tin cá nhân");
		l2.setFont(new Font("Raleway", Font.BOLD, 22));
		l2.setBounds(290, 40, 600, 40);
		add(l2);

		// Họ và tên
		l3 = new JLabel("Họ và tên:");
		l3.setFont(new Font("Raleway", Font.BOLD, 20));
		l3.setBounds(100, 140, 200, 30);
		add(l3);

		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 14));
		t1.setBounds(300, 140, 400, 30);
		t1.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t1);

		// Ngày sinh
		l4 = new JLabel("Ngày sinh:");
		l4.setFont(new Font("Raleway", Font.BOLD, 20));
		l4.setBounds(100, 190, 200, 30);
		add(l4);

		dateChooser = new JDateChooser();
		dateChooser.setForeground(new Color(105, 105, 105));
		dateChooser.setBounds(300, 190, 400, 30);
		dateChooser.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		dateChooser.setDateFormatString("dd/MM/yyyy");
		add(dateChooser);

		// Giới tính
		l5 = new JLabel("Giới tính:");
		l5.setFont(new Font("Raleway", Font.BOLD, 20));
		l5.setBounds(100, 240, 200, 30);
		add(l5);

		r1 = new JRadioButton("Nam");
		r1.setFont(new Font("Raleway", Font.BOLD, 14));
		r1.setBounds(300, 240, 60, 30);
		r1.setBackground(Color.WHITE);
		add(r1);

		r2 = new JRadioButton("Nữ");
		r2.setFont(new Font("Raleway", Font.BOLD, 14));
		r2.setBounds(450, 240, 120, 30);
		r2.setBackground(Color.WHITE);
		add(r2);

		ButtonGroup groupgender = new ButtonGroup(); // ButtonGroup: chỉ được chọn 1 in 2
		groupgender.add(r1);
		groupgender.add(r2);

		// Số CCCD/CMND
		l6 = new JLabel("Số CCCD/CMND: ");
		l6.setFont(new Font("Raleway", Font.BOLD, 20));
		l6.setBounds(100, 290, 200, 30);
		add(l6);

		t2 = new JTextField();
		t2.setFont(new Font("Raleway", Font.BOLD, 14));
		t2.setBounds(300, 290, 400, 30);
		t2.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t2);
		
		// Số điện thoại
		l7 = new JLabel("Số điện thoại:");
		l7.setFont(new Font("Raleway", Font.BOLD, 20));
		l7.setBounds(100, 340, 200, 30);
		add(l7);

		t3 = new JTextField();
		t3.setFont(new Font("Raleway", Font.BOLD, 14));
		t3.setBounds(300, 340, 400, 30);
		t3.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t3);

		// Email
		l8 = new JLabel("Email:");
		l8.setFont(new Font("Raleway", Font.BOLD, 20));
		l8.setBounds(100, 390, 200, 30);
		add(l8);

		t4 = new JTextField();
		t4.setFont(new Font("Raleway", Font.BOLD, 14));
		t4.setBounds(300, 390, 400, 30);
		t4.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t4);

		// Địa chỉ
		l9 = new JLabel("Địa chỉ:");
		l9.setFont(new Font("Raleway", Font.BOLD, 20));
		l9.setBounds(100, 440, 200, 30);
		add(l9);

		t5 = new JTextField();
		t5.setFont(new Font("Raleway", Font.BOLD, 14));
		t5.setBounds(300, 440, 400, 30);
		t5.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t5);
	
		// Quận/Huyện
		l10 = new JLabel("Quận/Huyện:");
		l10.setFont(new Font("Raleway", Font.BOLD, 20));
		l10.setBounds(100, 490, 200, 30);
		add(l10);

		t6 = new JTextField();
		t6.setFont(new Font("Raleway", Font.BOLD, 14));
		t6.setBounds(300, 490, 400, 30);
		t6.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t6);

		// Tỉnh/Thành phố
		l11 = new JLabel("Tỉnh/Thành phố:");
		l11.setFont(new Font("Raleway", Font.BOLD, 20));
		l11.setBounds(100, 540, 200, 30);
		add(l11);

		t7 = new JTextField();
		t7.setFont(new Font("Raleway", Font.BOLD, 14));
		t7.setBounds(300, 540, 400, 30);
		t7.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t7);

		// Quốc tịch
		l12 = new JLabel("Quốc tịch:");
		l12.setFont(new Font("Raleway", Font.BOLD, 20));
		l12.setBounds(100, 590, 200, 30);
		add(l12);

		t8 = new JTextField();
		t8.setFont(new Font("Raleway", Font.BOLD, 14));
		t8.setBounds(300, 590, 400, 30);
		t8.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
		add(t8);

		// Btn Tiếp tục
		b1 = new JButton("Tiếp tục");
		b1.setFont(new Font("Raleway", Font.BOLD, 14));
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);
		b1.setBounds(600, 660, 100, 30);
		add(b1);

		// Btn Quay lại
		b2 = new JButton("Quay lại");
		b2.setFont(new Font("Raleway", Font.BOLD, 14));
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);
		b2.setBounds(450, 660, 100, 30);
		add(b2);
		
		// Đăng ký listener cho btn
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		getContentPane().setBackground(Color.WHITE);
		setSize(800, 750);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {

		String formno = first;
		String name = t1.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dob = "";
		if (dateChooser.getDate() == null) {
		    JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày sinh!");
		    return;
		}
		dob = sdf.format(dateChooser.getDate());
		
		String gender = null;	
		if (r1.isSelected()) { // .isSelected() → kiểm tra r1 có được chọn không
			gender = "Nam";
		} else if (r2.isSelected()) {
			gender = "Nữ";
		}

		String cccd = t2.getText();
		String sdt = t3.getText();
		String email = t4.getText();
		String address = t5.getText();
		String district = t6.getText();
		String city = t7.getText();
		String qt = t8.getText();

		try {
			if (ae.getSource() == b1) { // Tiếp tục
				if (name.equals("") || dob.equals("") || gender == null || cccd.equals("") || sdt.equals("")
						|| email.equals("") || address.equals("") || district.equals("") || city.equals("") || qt.equals("")) {
					JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin!");
					return;
				}else {
					Conn c1 = new Conn();
					
					// Tạo cmd query thêm dữ liệu vào DB
					String q1;
					if (!isInserted) {
					    q1 = "insert into signup1 values('" + formno + "', '" + name + "', '" + dob + "', '" + gender + "', '" + cccd + "', '" 
					    									+ sdt + "', '" + email + "', '" + address + "', '" + district + "', '" + city + "','" + qt + "')";
					    isInserted = true;
					} else {
					    q1 = "update signup1 set "
					            + "HOTEN='" + name + "', "
					            + "NGAYSINH='" + dob + "', "
					            + "GIOITINH='" + gender + "', "
					            + "CCCD_CMND='" + cccd + "', "
					            + "DIENTHOAI='" + sdt + "', "
					            + "EMAIL='" + email + "', "
					            + "DIACHI='" + address + "', "
					            + "QUAN_HUYEN='" + district + "', "
					            + "TINH_TP='" + city + "', "
					            + "QUOCTICH='" + qt + "' "
					            + "where FORMNO='" + formno + "'";
					}
					
					c1.s.executeUpdate(q1);    // Chạy cmd INSERT
				
					setVisible(false);
					new Signup2(first, this).setVisible(true);  // Nhấn Next mở Signup2
				}
			} else if (ae.getSource() == b2) { // Quay lại
				setVisible(false);
				new AccountManagementPanel().setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Signup1();
	}
}
