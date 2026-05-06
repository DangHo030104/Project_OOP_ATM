package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.sql.*;

public class Withdrawal extends JFrame implements ActionListener{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField t1;
    JButton b1,b2;
    JLabel l1,l2;
    String pin;
    
    Withdrawal(String pin){
        this.pin = pin;
        
		setTitle("ATM Simulation Application");
		setLayout(null);
        
		// Tạo ảnh nền ATM phủ toàn bộ JFrame.
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));  // Lấy file ảnh atm.jpg từ thư mục resource icons -> Tạo 1 ImageIcon từ ảnh đó.
		Image i2 = i1.getImage().getScaledInstance(800, 750, Image.SCALE_DEFAULT);     // Lấy ảnh gốc -> Resize 800 x 750
		ImageIcon i3 = new ImageIcon(i2);											   // Chuyển i2 lại thành ImageIcon
		JLabel l3 = new JLabel(i3);													   // Đặt ảnh vào JLabel
		l3.setBounds(0, 0, 800, 750);    	// Ảnh nền sẽ phủ lên toàn bộ frame.
		add(l3);						  	// Add l3 vào Frame.
        
        l1 = new JLabel("Số tiền rút tối đa là 10.000.000 VND");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(150, 250, 300, 30);
        l3.add(l1);
        
        l2 = new JLabel("Nhập số tiền muốn rút:");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setBounds(150, 280, 300, 30);
        l3.add(l2);
        
        // Ô rút tiền
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 20));
        t1.setBounds(150, 320, 300, 30);
        t1.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
        l3.add(t1);
        
        b1 = new JButton("RÚT TIỀN");
        b1.setBounds(310, 380, 140, 30);
        l3.add(b1);
        
        b2 = new JButton("QUAY LẠI");
        b2.setBounds(310, 420, 140, 30);
        l3.add(b2);
        
        // Đăng ký listener cho btn.          
        b1.addActionListener(this);
        b2.addActionListener(this);
        
		setSize(800, 750);
		setLocationRelativeTo(null); // Cửa sổ giữa màn hình
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }
    
    // Xử lý sự kiện Btn
    public void actionPerformed(ActionEvent ae) {
        try{        
            String amount = t1.getText();
            Date date = new Date();			// Lưu thời gian giao dịch rút tiền.
            
            if(ae.getSource() == b1){		// Btn RÚT TIỀN
                if(t1.getText().equals("")){	
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền bạn muốn rút!");
                }else{
                    Conn c1 = new Conn();
                    
                    ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");	// Statemnet chạy cmd SELECT và rs đọc data mà SELECT trả về từ DB. -> Lấy toàn bộ giao dịch của người dùng theo PIN
                    
                    int balance = 0;	// Khởi tạo số dư
                    
                    /* rs.next(): chuyển con trỏ sang dòng tiếp theo trong ResultSet
						- nếu còn dòng → trả về true
						- nếu hết dòng → trả về false */
                    
                    while(rs.next()){	// Lặp qua toàn bộ lịch sử giao dịch của user.
                       if(rs.getString("type").equals("Deposit") || rs.getString("type").equals("Transfer In")){				// Type = Deposit hoặc Transfer In
                           balance += Integer.parseInt(rs.getString("amount"));	// Chuyển string sang Integer -> Cộng vào số dư
                       }else{													// Type = Withdrawal hoặc Transfer Out
                           balance -= Integer.parseInt(rs.getString("amount"));	// Trừ vào số dư
                       }
                    }

                    if (Integer.parseInt(amount) > 10000000) {
                        JOptionPane.showMessageDialog(null, "Số tiền rút tối đa không được vượt quá 10.000.000 VND!");
                        return;
                    }
                    
                    // Kiểm tra số dư còn đủ để rút tiền?
                    if(balance < Integer.parseInt(amount)){		
                        JOptionPane.showMessageDialog(null, "Số dư không đủ để rút tiền!");
                        return;  // Thoát 
                    }
                    
                    // Nếu đủ tiền -> Lưu giao dịch rút tiền vào DB.
                    c1.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Withdrawl', '"+amount+"')");
                    JOptionPane.showMessageDialog(null, "Rút tiền thành công!");		// Đã trừ thành công ? đồng.
                    
                    setVisible(false);
                    new Transactions(pin).setVisible(true);		
                }
            }else if(ae.getSource() == b2){		// Btn BACK
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        }catch(Exception e){
                e.printStackTrace();				// in chi tiết lỗi ra console                
        }
            
    }
    
    public static void main(String[] args) {
        new Withdrawal("");
    }
}

