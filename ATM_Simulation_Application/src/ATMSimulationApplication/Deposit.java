package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Deposit extends JFrame implements ActionListener{
    
    JTextField t1;
    JButton b1,b2;
    JLabel l1;
    String pin;
    
    Deposit(String pin) {
        this.pin = pin;
        
		setTitle("ATM Simulation Application");
		setLayout(null);
		
		// Tạo ảnh nền ATM phủ toàn bộ JFrame.
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));  // Lấy file ảnh atm.jpg từ thư mục resource icons -> Tạo 1 ImageIcon từ ảnh đó.
		Image i2 = i1.getImage().getScaledInstance(800, 750, Image.SCALE_DEFAULT);     // Lấy ảnh gốc -> Resize 800 x 750
		ImageIcon i3 = new ImageIcon(i2);											   // Chuyển i2 lại thành ImageIcon
		JLabel l2 = new JLabel(i3);													   // Đặt ảnh vào JLabel
		l2.setBounds(0, 0, 800, 750);    	// Ảnh nền sẽ phủ lên toàn bộ frame.
		add(l2);						  	// Add l3 vào Frame.
        
        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 14));
        l1.setBounds(150, 250, 320, 30);
        l2.add(l1);
        
        // Ô nhập tiền
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        t1.setBounds(150, 290, 300, 30);
        l2.add(t1);
        
        b1 = new JButton("DEPOSIT");
        b1.setBounds(310, 380, 140, 30);
        l2.add(b1);
        
        b2 = new JButton("BACK");
        b2.setBounds(310, 420, 140, 30);
        l2.add(b2);
     
        // Đăng ký listener cho btn.
        b1.addActionListener(this);
        b2.addActionListener(this);
        
		setSize(800, 750);
		setLocationRelativeTo(null); // Cửa sổ giữa màn hình
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }
    
    // Xử lý sự kiện Btn
    public void actionPerformed(ActionEvent event){
        try{        
            String amount = t1.getText();	
            Date date = new Date(); 			// Lưu thời gian nạp tiền
            if(event.getSource() == b1){		// Btn DEPOSIT
                if(t1.getText().equals("")){	// Nếu t1 rỗng
                    JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Deposit");
                }else{
                    Conn c1 = new Conn();	
                    c1.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Deposit', '"+amount+"')");
                    JOptionPane.showMessageDialog(null, "Rs. "+amount+" Deposited Successfully");	// Hiện thông báo nạp tiền thành công.
                    setVisible(false);							// Close cửa sổ hiện tại
                    new Transactions(pin).setVisible(true);		// Quay lại màn hình Transactions
                }
            }else if(event.getSource() == b2){		// Btn BACK
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    
    public static void main(String[] args){
        new Deposit("");
    }
}
