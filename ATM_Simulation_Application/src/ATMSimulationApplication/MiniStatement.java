package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class MiniStatement extends JFrame implements ActionListener{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton b1;
    JLabel l1, l2, l3, l4;
    JTable table;
    DefaultTableModel model;
    
    MiniStatement(String pin){
        setTitle("Mini Statement");
        setLayout(null);
                
        // Tên Bank
        l1 = new JLabel("BLACK BANK");
        l1.setFont(new Font("Raleway", Font.BOLD, 20));
        l1.setBounds(180, 20, 150, 20);
        add(l1);
        
        // Số thẻ
        l2 = new JLabel();
        l2.setBounds(20, 70, 300, 20);
        add(l2);
        
        // Bảng sao kê
        String[] columns = {"Ngày giao dịch", "Loại giao dịch", "Số tiền"};	// Mảng chứa tên các cột
        model = new DefaultTableModel(columns, 0);							// Tạo model cho JTable với tên cột và 0 dòng ban đầu => Lưu dữ liệu của bảng.

        table = new JTable(model);							// Tạo JTable từ model ở trên => Hiển thị dữ liệu trong model lên bảng.
        table.setFont(new Font("Arial", Font.PLAIN, 13));	// Font cho dữ liệu trong bảng
        table.setRowHeight(28);								// Chiều cao mỗi hàng trong bảng
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));	// Font cho header của bảng
        table.getTableHeader().setBackground(new Color(30, 30, 30));
        table.getTableHeader().setForeground(Color.WHITE);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(180); // Ngày giao dịch
        table.getColumnModel().getColumn(1).setPreferredWidth(120); // Loại giao dịch
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Số tiền
        
        JScrollPane scrollPane = new JScrollPane(table);  	// Bọc JTable bằng JScrollPane để có thể cuộn khi có nhiều dữ liệu.
        scrollPane.setBounds(20, 110, 450, 320);
        add(scrollPane);									// Add bảng vào JFrame
        
        // Số dư
        l4 = new JLabel();
        l4.setBounds(20, 450, 300, 20);
        add(l4);
        
        // Btn Thoát
        b1 = new JButton("THOÁT");
        b1.setBounds(370, 500, 100, 25);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Arial", Font.BOLD, 14));
        add(b1);
        
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));	// Dùng NumberFormat để format số có dấu chấm.
        
        // Truy vấn bảng login để lấy số thẻ
        try{
            Conn c = new Conn();
            
            ResultSet rs = c.s.executeQuery("select * from login where MA_PIN = '"+pin+"'");	// Lấy toàn bộ dòng trong bảng login mà có pin đúng với user hiện tại.
            
            while(rs.next()){	// True
                l2.setText("Số thẻ:    " + rs.getString("SOTHE").substring(0, 4) + " XXXX XXXX " + rs.getString("SOTHE").substring(12));	// Hiển thị cardno gồm 4 số đầu và 4 số cuối (1234XXXXXXXX5678)
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        	
        // Truy vấn bảng bank để lấy lịch sử giao dịch và tính số dư
        try{
            int balance = 0;	// Số dư
            
            Conn c1  = new Conn();	// Tạo kết nối DB mới để truy vấn bảng bank.
            
            ResultSet rs = c1.s.executeQuery("SELECT * FROM bank where pin = '"+pin+"'");
            
            while (rs.next()) {	// True
                String type = rs.getString("type");
                int amount = Integer.parseInt(rs.getString("amount"));

                String typeVi;
                String amountDisplay;

                if (type.equalsIgnoreCase("Deposit") || type.equalsIgnoreCase("Transfer In")) {
                    typeVi = "Nạp tiền";
                    balance += amount;
                    amountDisplay = " + " + formatter.format(amount) + " VND";
                } else {
                    typeVi = "Rút tiền";
                    balance -= amount;
                    amountDisplay = " - " + formatter.format(amount) + " VND";
                }

                String dateRaw = rs.getString("date");
                String formattedDate = dateRaw;

                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat(
                        "EEE MMM dd HH:mm:ss z yyyy",
                        Locale.ENGLISH		// vì Java đang lưu ngày theo định dạng tiếng Anh (ví dụ: "Wed Sep 15 14:30:00 GMT 2021")
                    );

                    SimpleDateFormat outputFormat = new SimpleDateFormat(
                        "dd/MM/yyyy - HH:mm:ss"
                    );

                    Date parsedDate = inputFormat.parse(dateRaw);	// Chuyển String → Date object
                    formattedDate = outputFormat.format(parsedDate);		// Format lại Date object

                } catch (Exception ex) {
                    formattedDate = dateRaw;	// Nếu lỗi parse dùng date gốc.
                }

                model.addRow(new Object[]{  	// Thêm 1 dòng dữ liệu vào bảng.
                        formattedDate,
                        typeVi,
                        amountDisplay
                });
            }
            // Hiển thị tổng số dư
            l4.setText("Số dư tài khoản: " + formatter.format(balance) + " VND");
        }catch(Exception e){
            e.printStackTrace();
        }
          
        // Đăng ký sự kiện cho Btn
        b1.addActionListener(this);
                
        setSize(500,600);
        //setLocation(20,20);
        setLocationRelativeTo(null); // Cửa sổ giữa màn hình
        getContentPane().setBackground(Color.WHITE);	// Màu nền JFrame
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        this.setVisible(false);
    }
    
    public static void main(String[] args){
        new MiniStatement("");
    }
    
}

