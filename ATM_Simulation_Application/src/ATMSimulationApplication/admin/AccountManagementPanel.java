package ATMSimulationApplication.admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ATMSimulationApplication.database.Conn;

import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class AccountManagementPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    JLabel titleLabel, searchLabel, totalAccountLabel, totalTransactionLabel, totalDepositLabel, totalWithdrawLabel, totalBalanceLabel;
    JTextField searchField;
    JButton searchButton, addButton, refreshButton, backButton, editButton, deleteButton;
    JTable table;
    DefaultTableModel model;

    AccountManagementPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        
        titleLabel = new JLabel("QUẢN LÝ TÀI KHOẢN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(31, 78, 121));
        titleLabel.setBounds(0, 20, 1200, 40);
        add(titleLabel);

        searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 15));
        searchLabel.setBounds(45, 80, 100, 30);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        searchField.setBounds(130, 80, 200, 30);
        searchField.setToolTipText("Nhập họ tên, CCCD, SĐT hoặc số thẻ...");	// Placeholder hướng dẫn người dùng có thể tìm kiếm theo những thông tin nào.
        add(searchField);

        searchButton = new JButton("Tìm kiếm");
        searchButton.setBounds(340, 80, 130, 30);
        searchButton.setBackground(new Color(0,122,204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon searchIcon = new ImageIcon(ClassLoader.getSystemResource("icons/search.png"));
        Image i1 = searchIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        searchButton.setIcon(new ImageIcon(i1));
        add(searchButton);
        
        addButton = new JButton("+ Thêm TK");
        addButton.setBounds(695, 80, 130, 30);
        addButton.setBackground(new Color(40,167,69));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(addButton);

        editButton = new JButton("Sửa TK");
        editButton.setBounds(855, 80, 130, 30);
        editButton.setBackground(new Color(255,153,0));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon editIcon = new ImageIcon(ClassLoader.getSystemResource("icons/edit.png"));
        Image i2 = editIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        editButton.setIcon(new ImageIcon(i2));
        add(editButton);

        deleteButton = new JButton("Xóa TK");
        deleteButton.setBounds(1015, 80, 130, 30);
        deleteButton.setBackground(new Color(220,53,69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon deleteIcon = new ImageIcon(ClassLoader.getSystemResource("icons/delete.png"));
        Image i3 = deleteIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        deleteButton.setIcon(new ImageIcon(i3));
        add(deleteButton);
        
        refreshButton = new JButton("Tải lại");
        refreshButton.setBounds(850, 540, 130, 30);
        refreshButton.setBackground(new Color(0,122,204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon refreshIcon = new ImageIcon(ClassLoader.getSystemResource("icons/refresh.png"));
        Image i4 = refreshIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        refreshButton.setIcon(new ImageIcon(i4));
        add(refreshButton);

        backButton = new JButton("Quay lại");
        backButton.setBounds(1015, 540, 130, 30);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon backIcon = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i5 = backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        backButton.setIcon(new ImageIcon(i5));
        add(backButton);

        String[] columns = {
                "Form No", "Họ tên", "CCCD/CMND", "SĐT", "Email", "Số thẻ", "Loại tài khoản", "Chi nhánh ngân hàng"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(0,102,204)); 
        table.getTableHeader().setForeground(Color.WHITE);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 280, 1100, 250);
        add(scrollPane);
        
        /* Phần thống kê nhanh */
        totalAccountLabel = new JLabel("0");
        JPanel totalAccCard = createStatCard(
                "Tổng tài khoản",
                totalAccountLabel,
                new Color(0,102,204),
                "icons/user.png"
        );
        totalAccCard.setBounds(45,130,250,90);
        add(totalAccCard);
        
        totalTransactionLabel = new JLabel("0");
        JPanel transactionCard = createStatCard(
                "Tổng giao dịch",
                totalTransactionLabel,
                new Color(111,66,193),
                "icons/transaction.png"
        );
        transactionCard.setBounds(320,130,250,90);
        add(transactionCard);
        
        totalDepositLabel = new JLabel("0");
        JPanel depositCard = createStatCard(
                "Tổng tiền nạp",
                totalDepositLabel,
                new Color(40,167,69),
                "icons/deposit.png"
        );
        depositCard.setBounds(595,130,270,90);
        add(depositCard);
        
        totalWithdrawLabel = new JLabel("0");
        JPanel withdrawCard = createStatCard(
                "Tổng tiền rút",
                totalWithdrawLabel,
                new Color(220,53,69),
                "icons/withdraw.png"
        );
        withdrawCard.setBounds(890,130,255,90);
        add(withdrawCard);
        
        JPanel balancePanel = new JPanel(null);
        balancePanel.setBackground(new Color(255,248,220));
        balancePanel.setBorder(
                BorderFactory.createLineBorder(new Color(255,193,7)
                ));
        balancePanel.setBounds(45,230,1100,45);

        totalBalanceLabel = new JLabel("Tổng số dư toàn hệ thống: 0 VND");
        totalBalanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalBalanceLabel.setForeground(new Color(255,140,0));
        totalBalanceLabel.setBounds(20,5,800,35);

        ImageIcon balanceIcon = new ImageIcon(ClassLoader.getSystemResource("icons/balance.png"));
        Image i6 = balanceIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        totalBalanceLabel.setIcon(new ImageIcon(i6));
        
        balancePanel.add(totalBalanceLabel);

        add(balancePanel);
        
        // Đăng ký sự kiện cho Btn
        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        refreshButton.addActionListener(this);
        backButton.addActionListener(this);

        loadAccounts("");
        loadStatistics();
    }

    // Tạo nhanh một ô thống kê.
    private JPanel createStatCard(String title, JLabel valueLabel, Color color, String iconPath) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(color,1));

        // ===== ICON =====
        ImageIcon icon = new ImageIcon(
                ClassLoader.getSystemResource(iconPath)
        );

        Image img = icon.getImage().getScaledInstance(
                35,
                35,
                Image.SCALE_SMOOTH
        );
        JLabel iconLabel = new JLabel(new ImageIcon(img));
        iconLabel.setBounds(15, 10, 32, 32);
        
        // ===== TITLE =====
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(55, 15, 180, 25);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(color);

        // ===== VALUE =====
        valueLabel.setBounds(55,45,250,35);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(color);

        panel.add(iconLabel);
        panel.add(titleLabel);
        panel.add(valueLabel);

        return panel;
    }
    
    // Lấy dữ liệu thống kê từ DB
    private void loadStatistics() {
        try {
            Conn c = new Conn();

            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

            ResultSet rsAccount = c.s.executeQuery(
                    "SELECT COUNT(*) AS total FROM signup2"
            );	// Đếm số tài khoản trong bảng signup2.

            if (rsAccount.next()) {		
                totalAccountLabel.setText(formatter.format(rsAccount.getInt("total")));
            }

            ResultSet rsTransaction = c.s.executeQuery(
                    "SELECT COUNT(*) AS total FROM bank"
            );	// Đếm số giao dịch trong bảng bank.

            if (rsTransaction.next()) {
                totalTransactionLabel.setText(formatter.format(rsTransaction.getInt("total")));	
            }	

            ResultSet rsDeposit = c.s.executeQuery(
                    "SELECT IFNULL(SUM(SOTIEN), 0) AS total FROM bank " +
                    "WHERE LOAIGD = 'Nạp tiền' OR LOAIGD = 'Nhận chuyển khoản'"
            );	// Cộng tất cả số tiền có loại giao dịch là nạp tiền hoặc nhận chuyển khoản.

            double totalDeposit = 0;

            if (rsDeposit.next()) {
                totalDeposit = rsDeposit.getDouble("total");
                totalDepositLabel.setText(formatter.format(totalDeposit) + " VND"); 
            }

            ResultSet rsWithdraw = c.s.executeQuery(
                    "SELECT IFNULL(SUM(SOTIEN), 0) AS total FROM bank " +
                    "WHERE LOAIGD = 'Rút tiền' OR LOAIGD = 'Chuyển khoản đi'"
            );	// Cộng tiền rút và chuyển khoản đi.

            double totalWithdraw = 0;

            if (rsWithdraw.next()) {
                totalWithdraw = rsWithdraw.getDouble("total");
                totalWithdrawLabel.setText(formatter.format(totalWithdraw) + " VND");
            }

            double totalBalance = totalDeposit - totalWithdraw;		// Tổng số dư

            totalBalanceLabel.setText(
                    "Tổng số dư toàn hệ thống: " + formatter.format(totalBalance) + " VND"
            );

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu thống kê từ DB!");
        }
    }
    
    // Lấy dữ liệu DB đổ vào JTable.
    private void loadAccounts(String keyword) {
        try {
            model.setRowCount(0);	// Xóa dữ liệu cũ trước khi tải lại để tránh trùng lặp khi tìm kiếm hoặc refresh.

            Conn c = new Conn();

            String query =
                    "SELECT s1.FORMNO, s1.HOTEN, s1.CCCD_CMND, s1.DIENTHOAI, s1.EMAIL, s2.SOTHE, s2.LOAI_TK, s2.CHINHANH_NH " +
                    "FROM signup1 s1 " +
                    "JOIN signup2 s2 ON s1.FORMNO = s2.FORMNO ";
            
            // Tìm kiếm theo họ tên, CCCD/CMND, SĐT, Email hoặc số thẻ nếu keyword không rỗng.
            if (keyword != null && !keyword.trim().equals("")) {
                query += "WHERE s1.HOTEN LIKE '%" + keyword + "%' " +	// Ví du: keyword = "Nguyen" => HOTEN LIKE '%Nguyen%' => Tìm mọi dữ liệu chứa "Nguyen"
                         "OR s1.CCCD_CMND LIKE '%" + keyword + "%' " +
                         "OR s1.DIENTHOAI LIKE '%" + keyword + "%' " +
                         "OR s1.EMAIL LIKE '%" + keyword + "%' " +
                         "OR s2.SOTHE LIKE '%" + keyword + "%' ";  
            }

            ResultSet rs = c.s.executeQuery(query);

            // Add dữ liệu vào từng hàng của JTable
            while (rs.next()) {	// True
                model.addRow(new Object[]{
                        rs.getString("FORMNO"),
                        rs.getString("HOTEN"),
                        rs.getString("CCCD_CMND"),
                        rs.getString("DIENTHOAI"),
                        rs.getString("EMAIL"),
                        rs.getString("SOTHE"),
                        rs.getString("LOAI_TK"),
                        rs.getString("CHINHANH_NH")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải danh sách tài khoản!");
        }
    }

    // Sửa thông tin tài khoản
    private void editAccount() {
        int selectedRow = table.getSelectedRow();	// Lấy dòng đang click

        if (selectedRow == -1) {	// Chưa chọn dòng nào
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản cần sửa!");
            return;
        }

        // Lấy dữ liệu từ JTable
        String formno = model.getValueAt(selectedRow, 0).toString();

        try {
            Conn c = new Conn();

            String query =
                    "SELECT s1.*, s2.LOAI_TK, s2.LOAI_THE, s2.CHINHANH_NH, s2.SOTHE, s2.DICHVU " +
                    "FROM signup1 s1 " +
                    "JOIN signup2 s2 ON s1.FORMNO = s2.FORMNO " +
                    "WHERE s1.FORMNO = '" + formno + "'";

            ResultSet rs = c.s.executeQuery(query);

            if (!rs.next()) {	// false
                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin tài khoản!");
                return;
            }

            // Tạo các ô nhập chứa dữ liệu cũ
            JTextField formField = new JTextField(rs.getString("FORMNO"));
            formField.setEditable(false);	// Không cho sửa formno vì nó là khóa chính liên kết giữa 2 bảng.

            JTextField nameField = new JTextField(rs.getString("HOTEN"));
            JTextField dobField = new JTextField(rs.getString("NGAYSINH"));
            JTextField genderField = new JTextField(rs.getString("GIOITINH"));
            JTextField cccdField = new JTextField(rs.getString("CCCD_CMND"));
            JTextField phoneField = new JTextField(rs.getString("DIENTHOAI"));
            JTextField emailField = new JTextField(rs.getString("EMAIL"));
            JTextField addressField = new JTextField(rs.getString("DIACHI"));
            JTextField districtField = new JTextField(rs.getString("QUAN_HUYEN"));
            JTextField cityField = new JTextField(rs.getString("TINH_TP"));
            JTextField nationField = new JTextField(rs.getString("QUOCTICH"));

            JTextField cardField = new JTextField(rs.getString("SOTHE"));
            cardField.setEditable(false); // Số thẻ không nên sửa trực tiếp.

            JTextField accTypeField = new JTextField(rs.getString("LOAI_TK"));
            JTextField cardTypeField = new JTextField(rs.getString("LOAI_THE"));
            JTextField branchField = new JTextField(rs.getString("CHINHANH_NH"));

            JTextArea serviceArea = new JTextArea(rs.getString("DICHVU"));
            serviceArea.setLineWrap(true);		// Cho phép xuống dòng khi nhập quá dài.
            serviceArea.setWrapStyleWord(true);	// Xuống dòng tại vị trí khoảng trắng để tránh cắt chữ.
            JScrollPane serviceScroll = new JScrollPane(serviceArea);
            serviceScroll.setPreferredSize(new Dimension(250, 70));

            // Tạo panel form sửa
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 8, 5, 8);		// Khoảng cách giữa các dòng
            gbc.fill = GridBagConstraints.HORIZONTAL;	// ô nhập sẽ mở rộng theo chiều ngang của panel

            addFormRow(panel, gbc, 0, "Form No:", formField);
            addFormRow(panel, gbc, 1, "Họ tên:", nameField);
            addFormRow(panel, gbc, 2, "Ngày sinh:", dobField);
            addFormRow(panel, gbc, 3, "Giới tính:", genderField);
            addFormRow(panel, gbc, 4, "CCCD/CMND:", cccdField);
            addFormRow(panel, gbc, 5, "SĐT:", phoneField);
            addFormRow(panel, gbc, 6, "Email:", emailField);
            addFormRow(panel, gbc, 7, "Địa chỉ:", addressField);
            addFormRow(panel, gbc, 8, "Quận/Huyện:", districtField);
            addFormRow(panel, gbc, 9, "Tỉnh/TP:", cityField);
            addFormRow(panel, gbc, 10, "Quốc tịch:", nationField);
            addFormRow(panel, gbc, 11, "Số thẻ:", cardField);
            addFormRow(panel, gbc, 12, "Loại tài khoản:", accTypeField);
            addFormRow(panel, gbc, 13, "Loại thẻ:", cardTypeField);
            addFormRow(panel, gbc, 14, "Chi nhánh ngân hàng:", branchField);

            gbc.gridx = 0;
            gbc.gridy = 15;
            gbc.weightx = 0;
            panel.add(new JLabel("Dịch vụ đăng ký:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 15;
            gbc.weightx = 1;
            panel.add(serviceScroll, gbc);

            // Hiện form sửa lên màn hình
            int result = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Sửa chi tiết thông tin tài khoản",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String newName = nameField.getText().trim();
                String newDob = dobField.getText().trim();
                String newGender = genderField.getText().trim();
                String newCCCD = cccdField.getText().trim();
                String newPhone = phoneField.getText().trim();
                String newEmail = emailField.getText().trim();
                String newAddress = addressField.getText().trim();
                String newDistrict = districtField.getText().trim();
                String newCity = cityField.getText().trim();
                String newNation = nationField.getText().trim();
                String newAccType = accTypeField.getText().trim();
                String newCardType = cardTypeField.getText().trim();
                String newBranch = branchField.getText().trim();
                String newService = serviceArea.getText().trim();

                if (newName.equals("") || newDob.equals("") || newGender.equals("") ||
                        newCCCD.equals("") || newPhone.equals("") || newEmail.equals("") ||
                        newAddress.equals("") || newDistrict.equals("") || newCity.equals("") ||
                        newNation.equals("") || newAccType.equals("") || newCardType.equals("") ||
                        newBranch.equals("") || newService.equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng không để trống thông tin!");
                    return;
                }

                String q1 = "UPDATE signup1 SET " +
                        "HOTEN='" + newName + "', " +
                        "NGAYSINH='" + newDob + "', " +
                        "GIOITINH='" + newGender + "', " +
                        "CCCD_CMND='" + newCCCD + "', " +
                        "DIENTHOAI='" + newPhone + "', " +
                        "EMAIL='" + newEmail + "', " +
                        "DIACHI='" + newAddress + "', " +
                        "QUAN_HUYEN='" + newDistrict + "', " +
                        "TINH_TP='" + newCity + "', " +
                        "QUOCTICH='" + newNation + "' " +
                        "WHERE FORMNO='" + formno + "'";

                String q2 = "UPDATE signup2 SET " +
                        "LOAI_TK='" + newAccType + "', " +
                        "LOAI_THE='" + newCardType + "', " +
                        "CHINHANH_NH='" + newBranch + "', " +
                        "DICHVU='" + newService + "' " +
                        "WHERE FORMNO='" + formno + "'";

                c.s.executeUpdate(q1);
                c.s.executeUpdate(q2);

                JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thành công!");
                loadAccounts("");	// Refresh lại bảng sau khi cập nhật
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể cập nhật tài khoản!");
        }
    }
    
    // Thêm 1 dòng label + field vào form sửa
    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
    	// Thêm label bên trái
        gbc.gridx = 0;		// cột 0 bên trái
        gbc.gridy = row;	// dòng hiện tại
        gbc.weightx = 0;	// label không cần giãn ngang
        panel.add(new JLabel(labelText), gbc);

        // Thêm field bên phải
        gbc.gridx = 1;		// cột 1 bên phải
        gbc.gridy = row;	// dòng hiện tại = label
        gbc.weightx = 1;	// field sẽ giãn ngang để lấp đầy khoảng trống còn lại của panel
        panel.add(field, gbc);
    }
    
    // Xóa tài khoản
    private void deleteAccount() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản cần xóa!");
            return;
        }

        String formno = model.getValueAt(selectedRow, 0).toString();
        String cardno = model.getValueAt(selectedRow, 5).toString();
     
        int confirm = JOptionPane.showConfirmDialog(  // Popup xác nhận
                null,
                "Bạn có chắc chắn muốn xóa tài khoản số thẻ " + cardno + " không?",
                "Xóa tài khoản",
                JOptionPane.YES_NO_OPTION
        );

        // User nhấn YES
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Conn c = new Conn();

                c.s.executeUpdate("DELETE FROM bank WHERE SOTHE='" + cardno + "'");
                c.s.executeUpdate("DELETE FROM login WHERE FORMNO='" + formno + "'");
                c.s.executeUpdate("DELETE FROM signup2 WHERE FORMNO='" + formno + "'");
                c.s.executeUpdate("DELETE FROM signup1 WHERE FORMNO='" + formno + "'");

                JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công!");
                loadAccounts("");	// Refresh bảng sau khi xóa.
 
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Không thể xóa tài khoản!");
            }
        }
    }
    
    // Xử lý Btn
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchButton) {	
            String keyword = searchField.getText().trim();
            loadAccounts(keyword);
            
        } else if (ae.getSource() == addButton) { 
            new Signup1();
            
    	} else if (ae.getSource() == refreshButton) {
            searchField.setText("");
            loadAccounts("");	// xóa tìm kiếm và load toàn bộ.
            loadStatistics();
            
        } else if (ae.getSource() == backButton) {
            Container parent = getParent();						// Lấy container cha của panel này (AdminDashboard.contentPanel)
            CardLayout cl = (CardLayout) parent.getLayout();	// Lấy layout của contentPanel, ép kiểu sang CardLayout.
            cl.show(parent, "HOME");							// Quay lại hiển thị HomePanel
            
        } else if (ae.getSource() == editButton) {
            editAccount();

        } else if (ae.getSource() == deleteButton) {
            deleteAccount();
        }
    }
}