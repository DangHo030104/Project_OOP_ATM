package ATMSimulationApplication.admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ATMSimulationApplication.database.Conn;

import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TransactionManagementPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    JLabel titleLabel, searchLabel;
    JTextField searchField;
    JButton searchButton, refreshButton, backButton;
    JTable table;
    DefaultTableModel model;

    TransactionManagementPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        titleLabel = new JLabel("QUẢN LÝ GIAO DỊCH", SwingConstants.CENTER);
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
        searchField.setBounds(130, 80, 250, 30);
        searchField.setToolTipText("Nhập số thẻ, họ tên hoặc loại giao dịch");
        searchField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
        add(searchField);

        searchButton = new JButton("Tìm kiếm");
        searchButton.setBounds(400, 80, 130, 30);
        searchButton.setBackground(new Color(0, 122, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon searchIcon = new ImageIcon(ClassLoader.getSystemResource("icons/search.png"));
        Image i1 = searchIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        searchButton.setIcon(new ImageIcon(i1));
        add(searchButton);

        refreshButton = new JButton("Tải lại");
        refreshButton.setBounds(850, 540, 130, 30);
        refreshButton.setBackground(new Color(0, 122, 204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon refreshIcon = new ImageIcon(ClassLoader.getSystemResource("icons/refresh.png"));
        Image i2 = refreshIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        refreshButton.setIcon(new ImageIcon(i2));
        add(refreshButton);

        backButton = new JButton("Quay lại");
        backButton.setBounds(1015, 540, 130, 30);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon backIcon = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i3 = backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        backButton.setIcon(new ImageIcon(i3));
        add(backButton);

        String[] columns = {
        	    "ID", "Số thẻ", "Họ tên", "Ngày giao dịch", "Loại giao dịch", "Số tiền"
        	};

        model = new DefaultTableModel(columns, 0);	// Chứa dữ liệu của bảng, khởi tạo với tên cột và 0 dòng ban đầu.
        table = new JTable(model);					// Hiển thị dữ liệu trong model lên bảng.

        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 130, 1100, 390);
        add(scrollPane);

        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);
        backButton.addActionListener(this);

        loadTransactions("");	// Load tất cả giao dịch khi khởi tạo panel
    }

    // Lấy giao dịch từ DB và hiển thị lên bảng, có thể lọc theo keyword
    private void loadTransactions(String keyword) {
        try {
            model.setRowCount(0);	// Mỗi lần tìm kiếm/tải lại thì xóa bảng cũ trước, tránh bị trùng dòng.
            
            Conn c = new Conn();

            String query =
                "SELECT b.ID, b.SOTHE, s1.HOTEN, b.NGAYGD, b.LOAIGD, b.SOTIEN " +
                "FROM bank b " +
                "LEFT JOIN login l ON b.SOTHE = l.SOTHE " +
                "LEFT JOIN signup1 s1 ON l.FORMNO = s1.FORMNO ";

            if (keyword != null && !keyword.trim().equals("")) {
                query += "WHERE b.SOTHE LIKE '%" + keyword + "%' " +
                         "OR s1.HOTEN LIKE '%" + keyword + "%' " +
                         "OR b.LOAIGD LIKE '%" + keyword + "%' ";
            }

//            query += "ORDER BY b.NGAYGD DESC";	// Sắp xếp giao dịch mới nhất lên trên
            query += "ORDER BY b.NGAYGD";
            
            ResultSet rs = c.s.executeQuery(query);

            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));	// Format tiền VN (Vd: 1.000.000)

            while (rs.next()) {	// True: mỗi lần lấy 1 giao dịch từ DB rồi thêm vào table
                int amount = rs.getInt("SOTIEN");

                model.addRow(new Object[]{
                    rs.getString("ID"),
                    rs.getString("SOTHE"),
                    rs.getString("HOTEN"),
                    rs.getString("NGAYGD"),
                    convertTransactionType(rs.getString("LOAIGD")),
                    formatter.format(amount) + " VND"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải danh sách giao dịch từ DB!");
        }
    }

    // Đổi loại giao dịch tiếng Anh trong DB sang tiếng Việt để hiển thị
    private String convertTransactionType(String type) {
        if (type == null) return "";

        if (type.equalsIgnoreCase("Nạp tiền")) {
            return "Nạp tiền";
        } else if (type.equalsIgnoreCase("Rút tiền")) {
            return "Rút tiền";
        } else if (type.equalsIgnoreCase("Nhận chuyển khoản")) {
            return "Nhận chuyển khoản";
        } else if (type.equalsIgnoreCase("Chuyển khoản đi")) {
            return "Chuyển khoản đi";
        }

        return type; 
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchButton) {
            String keyword = searchField.getText().trim();
            loadTransactions(keyword);

        } else if (ae.getSource() == refreshButton) {
            searchField.setText("");
            loadTransactions("");	// Xóa ô tìm kiếm và load toàn bộ giao dịch.

        } else if (ae.getSource() == backButton) {
            Container parent = getParent();						// Lấy panel cha -> contentPanel trong AdminDashboard.
            CardLayout cl = (CardLayout) parent.getLayout();	// Lấy CardLayout của panel cha.
            cl.show(parent, "HOME");							// Quay về panel HOME (AdminDashboard).
        }
    }
}