package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Deposit extends JFrame implements ActionListener{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
        
        l1 = new JLabel("Nhập số tiền muốn nạp:");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 14));
        l1.setBounds(150, 250, 320, 30);
        l2.add(l1);
        
        // Ô nhập tiền
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 20));
        t1.setBounds(150, 290, 300, 30);
        t1.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(Color.GRAY, 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			)); // Tạo viền màu xám, dày 1 pixel và thêm khoảng cách bên trong (padding) 5 pixel ở trên/dưới và 10 pixel ở trái/phải
        l2.add(t1);
        
        b1 = new JButton("NẠP TIỀN");
        b1.setBounds(310, 380, 140, 30);
        l2.add(b1);
        
        b2 = new JButton("QUAY LẠI");
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
    public void actionPerformed(ActionEvent ae){
        try{        
            String amount = t1.getText().trim();	
            
            if(ae.getSource() == b1){		// Btn NẠP TIỀN
                if(amount.equals("")){	// Nếu t1 rỗng
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền bạn muốn gửi!");
                    return;                    
                }
           	
                Integer.parseInt(amount);
                String cardno = null;
                	
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("SELECT SOTHE FROM login WHERE MA_PIN = '" + pin + "'");
                	
                if (rs.next()) {
					cardno = rs.getString("SOTHE");
				}
                	
                if (cardno == null) {
                	JOptionPane.showMessageDialog(null, "Không tìm thấy số thẻ!");
                    return;
                }
                    
                Conn c1 = new Conn();

                c1.s.executeUpdate(
                		"INSERT INTO bank(SOTHE, NGAYGD, LOAIGD, SOTIEN) " +
                		"VALUES('" + cardno + "', NOW(), 'Nạp tiền', '" + amount + "')"
                );

                JOptionPane.showMessageDialog(null, "Nạp tiền thành công!");

                setVisible(false);
                new Transactions(pin).setVisible(true);
                                
            }else if(ae.getSource() == b2){		// Btn QUAY LẠI
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
