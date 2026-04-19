package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup2 extends JFrame implements ActionListener {

	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
	JButton b;
	JRadioButton r1, r2, r3, r4;
	JTextField t1, t2, t3;
	JComboBox c1, c2, c3, c4, c5;
	String formno;  // Lưu số Form -> Liên kết data (formno) giữa các form đăng ký.

	Signup2(String formno) {
		
		this.formno = formno;
		
		setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");
		setLayout(null);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
		Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel label = new JLabel(i3);
		label.setBounds(150, 0, 100, 100);
		add(label);

		l1 = new JLabel("Page 2: Additonal Details");
		l1.setFont(new Font("Raleway", Font.BOLD, 22));
		l1.setBounds(280, 40, 600, 40);
		add(l1);

		// Religion
		l2 = new JLabel("Religion:");
		l2.setFont(new Font("Raleway", Font.BOLD, 18));
		l2.setBounds(100, 120, 100, 30);
		add(l2);
		
		String religion[] = { "Hindu", "Muslim", "Sikh", "Christian", "Other" };
		c1 = new JComboBox(religion);
		c1.setBackground(Color.WHITE);
		c1.setFont(new Font("Raleway", Font.BOLD, 14));
		c1.setBounds(350, 120, 320, 30);
		add(c1);
		
		// Category
		l3 = new JLabel("Category:");
		l3.setFont(new Font("Raleway", Font.BOLD, 18));
		l3.setBounds(100, 170, 100, 30);
		add(l3);

		String category[] = { "General", "OBC", "SC", "ST", "Other" };
		c2 = new JComboBox(category);
		c2.setBackground(Color.WHITE);
		c2.setFont(new Font("Raleway", Font.BOLD, 14));
		c2.setBounds(350, 170, 320, 30);
		add(c2);
		
		// Income
		l4 = new JLabel("Income:");
		l4.setFont(new Font("Raleway", Font.BOLD, 18));
		l4.setBounds(100, 220, 100, 30);
		add(l4);

		String income[] = { "Null", "<1,50,000", "<2,50,000", "<5,00,000", "Upto 10,00,000", "Above 10,00,000" };
		c3 = new JComboBox(income);
		c3.setBackground(Color.WHITE);
		c3.setFont(new Font("Raleway", Font.BOLD, 14));
		c3.setBounds(350, 220, 320, 30);
		add(c3);
		
		// Educational Qualification
		l5 = new JLabel("Educational Qualification: ");
		l5.setFont(new Font("Raleway", Font.BOLD, 18));
		l5.setBounds(100, 270, 250, 30);
		add(l5);

		String education[] = { "Non-Graduate", "Graduate", "Post-Graduate", "Doctrate", "Others" };
		c4 = new JComboBox(education);
		c4.setBackground(Color.WHITE);
		c4.setFont(new Font("Raleway", Font.BOLD, 14));
		c4.setBounds(350, 270, 320, 30);
		add(c4);
		
		// Occupation
		l6 = new JLabel("Occupation:");
		l6.setFont(new Font("Raleway", Font.BOLD, 18));
		l6.setBounds(100, 340, 150, 30);
		add(l6);

		String occupation[] = { "Salaried", "Self-Employmed", "Business", "Student", "Retired", "Others" };
		c5 = new JComboBox(occupation);
		c5.setBackground(Color.WHITE);
		c5.setFont(new Font("Raleway", Font.BOLD, 14));
		c5.setBounds(350, 340, 320, 30);
		add(c5);
		
		// PAN Number
		l7 = new JLabel("PAN Number:");
		l7.setFont(new Font("Raleway", Font.BOLD, 18));
		l7.setBounds(100, 390, 150, 30);
		add(l7);
		
		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 14));
		t1.setBounds(350, 390, 320, 30);
		t1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		add(t1);
		
		// Aadhar Number
		l8 = new JLabel("Aadhar Number:");
		l8.setFont(new Font("Raleway", Font.BOLD, 18));
		l8.setBounds(100, 440, 180, 30);
		add(l8);

		t2 = new JTextField();
		t2.setFont(new Font("Raleway", Font.BOLD, 14));
		t2.setBounds(350, 440, 320, 30);
		t2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		add(t2);
		
		// Senior Citizen
		l9 = new JLabel("Senior Citizen:");
		l9.setFont(new Font("Raleway", Font.BOLD, 18));
		l9.setBounds(100, 490, 150, 30);
		add(l9);

		r1 = new JRadioButton("Yes");
		r1.setFont(new Font("Raleway", Font.BOLD, 14));
		r1.setBackground(Color.WHITE);
		r1.setBounds(350, 490, 100, 30);
		add(r1);

		r2 = new JRadioButton("No");
		r2.setFont(new Font("Raleway", Font.BOLD, 14));
		r2.setBackground(Color.WHITE);
		r2.setBounds(460, 490, 100, 30);
		add(r2);
		
		ButtonGroup group1 = new ButtonGroup(); // ButtonGroup: chỉ được chọn 1 in 2
		group1.add(r1);
		group1.add(r2);
		
		// Existing Account
		l10 = new JLabel("Existing Account:");
		l10.setFont(new Font("Raleway", Font.BOLD, 18));
		l10.setBounds(100, 540, 180, 30);
		add(l10);

		r3 = new JRadioButton("Yes");
		r3.setFont(new Font("Raleway", Font.BOLD, 14));
		r3.setBackground(Color.WHITE);
		r3.setBounds(350, 540, 100, 30);
		add(r3);

		r4 = new JRadioButton("No");
		r4.setFont(new Font("Raleway", Font.BOLD, 14));
		r4.setBackground(Color.WHITE);
		r4.setBounds(460, 540, 100, 30);
		add(r4);
		
		ButtonGroup group2 = new ButtonGroup(); // ButtonGroup: chỉ được chọn 1 in 2
		group2.add(r3);
		group2.add(r4);
		
		// Form No
		l11 = new JLabel("Form No:");
		l11.setFont(new Font("Raleway", Font.BOLD, 13));
		l11.setBounds(650, 10, 60, 30);
		add(l11);
		
		l12 = new JLabel(formno);
		l12.setFont(new Font("Raleway", Font.BOLD, 13));
		l12.setBounds(720, 10, 60, 30);
		add(l12);

		// Btn Next
		b = new JButton("Next");
		b.setFont(new Font("Raleway", Font.BOLD, 14));
		b.setBackground(Color.BLACK);
		b.setForeground(Color.WHITE);
		b.setBounds(570, 640, 100, 30);
		add(b);

		b.addActionListener(this);  // Đăng ký listener cho btn.

		getContentPane().setBackground(Color.WHITE);
		setSize(800, 750);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String religion = (String) c1.getSelectedItem();  // getSelectedItem(): lấy giá trị đang chọn trong combobox
		String category = (String) c2.getSelectedItem();
		String income = (String) c3.getSelectedItem();
		String education = (String) c4.getSelectedItem();
		String occupation = (String) c5.getSelectedItem();

		String pan = t1.getText();
		String aadhar = t2.getText();

		String scitizen = "";
		if (r1.isSelected()) {           // .isSelected() → kiểm tra r1 có được chọn không
			scitizen = "Yes";
		} else if (r2.isSelected()) {
			scitizen = "No";
		}

		String eaccount = "";
		if (r3.isSelected()) {
			eaccount = "Yes";
		} else if (r4.isSelected()) {
			eaccount = "No";
		}

		try {
			if (t2.getText().equals("")) {     // Nếu Aadhar trống → báo lỗi
				JOptionPane.showMessageDialog(null, "Fill all the required fields");
			} else {
				Conn c1 = new Conn();
				
				// Tạo cmd query thêm dữ liệu vào DB
				String q1 = "insert into signup2 values('" + formno + "','" + religion + "','" + category + "','"
						+ income + "','" + education + "','" + occupation + "','" + pan + "','" + aadhar + "','"
						+ scitizen + "','" + eaccount + "')";
				
				c1.s.executeUpdate(q1);  // Chạy cmd INSERT

				new Signup3(formno).setVisible(true);  // Nhấn Next mở Signup3
				setVisible(false);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Signup2("");
	}
}
