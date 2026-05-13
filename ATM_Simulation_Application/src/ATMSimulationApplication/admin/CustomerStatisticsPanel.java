package ATMSimulationApplication.admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ATMSimulationApplication.database.Conn;

import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomerStatisticsPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    JLabel totalCustomerLabel, savingAccountLabel, paymentAccountLabel, topBranchLabel;
    JTable table;
    DefaultTableModel model;
    JButton refreshButton, backButton;
    JLabel updateTimeLabel;
    
    CustomerStatisticsPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("THỐNG KÊ KHÁCH HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(31, 78, 121));
        titleLabel.setBounds(0, 20, 1200, 40);
        add(titleLabel);

        totalCustomerLabel = new JLabel("0");
        JPanel totalCard = createCard("Tổng khách hàng", totalCustomerLabel, new Color(0,102,204), "icons/totalKH.png");
        totalCard.setBounds(45, 90, 250, 90);
        add(totalCard);

        paymentAccountLabel = new JLabel("0");
        JPanel paymentCard = createCard("Tài khoản thanh toán", paymentAccountLabel, new Color(40,167,69), "icons/paymentTK.png");
        paymentCard.setBounds(320, 90, 250, 90);
        add(paymentCard);

        savingAccountLabel = new JLabel("0");
        JPanel savingCard = createCard("Tải khoản tiết kiệm", savingAccountLabel, new Color(111,66,193), "icons/savingTK.png");
        savingCard.setBounds(595, 90, 250, 90);
        add(savingCard);

        topBranchLabel = new JLabel("...");
        JPanel branchCard = createCard("Chi nhánh nhiều KH", topBranchLabel, new Color(255,153,0), "icons/branchNH.png");
        branchCard.setBounds(870, 90, 275, 90);
        add(branchCard);

        JLabel tableTitle = new JLabel("TOP KHÁCH HÀNG CÓ NHIỀU GIAO DỊCH");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBounds(45, 210, 500, 30);
        add(tableTitle);

        updateTimeLabel = new JLabel("Cập nhật lần cuối: --/--/---- --:--:--");
        updateTimeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        updateTimeLabel.setForeground(Color.darkGray);
        updateTimeLabel.setBounds(850, 220, 300, 20);
        add(updateTimeLabel);
        
        String[] columns = {
                "Form No", "Họ tên", "Số thẻ", "Loại tài khoản", "Chi nhánh", "Số giao dịch", "Tổng tiền giao dịch"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(0,102,204));
        table.getTableHeader().setForeground(Color.WHITE);

        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 250, 1100, 260);
        add(scrollPane);

        refreshButton = new JButton("Tải lại");
        refreshButton.setBounds(850, 540, 130, 30);
        refreshButton.setBackground(new Color(0,122,204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon refreshIcon = new ImageIcon(ClassLoader.getSystemResource("icons/refresh.png"));
        Image i1 = refreshIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        refreshButton.setIcon(new ImageIcon(i1));
        add(refreshButton);

        backButton = new JButton("Quay lại");
        backButton.setBounds(1015, 540, 130, 30);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon backIcon = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i2 = backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        backButton.setIcon(new ImageIcon(i2));
        add(backButton);

        refreshButton.addActionListener(this);
        backButton.addActionListener(this);

        loadCustomerStatistics();
    }

    private JPanel createCard(String title, JLabel valueLabel, Color color, String iconPath) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(color, 1));

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
        titleLabel.setBounds(55, 15, 230, 25);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(color);

        // ===== VALUE =====
        valueLabel.setBounds(55, 45, 240, 35);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(color);

        panel.add(iconLabel);
        panel.add(titleLabel);
        panel.add(valueLabel);

        return panel;
    }

    private void updateLastRefreshTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        updateTimeLabel.setText(
                "Cập nhật lần cuối: " + sdf.format(new Date())
        );
    }
    
    private void loadCustomerStatistics() {
        try {
            Conn c = new Conn();

            ResultSet rsTotal = c.s.executeQuery(
                    "SELECT COUNT(*) AS total FROM signup2"
            );

            if (rsTotal.next()) {
                totalCustomerLabel.setText(rsTotal.getString("total"));
            }

            ResultSet rsPayment = c.s.executeQuery(
                    "SELECT COUNT(*) AS total FROM signup2 " +
                    "WHERE LOAI_TK LIKE '%thanh toán%'"
            );

            if (rsPayment.next()) {
                paymentAccountLabel.setText(rsPayment.getString("total"));
            }

            ResultSet rsSaving = c.s.executeQuery(
                    "SELECT COUNT(*) AS total FROM signup2 " +
                    "WHERE LOAI_TK LIKE '%tiết kiệm%'"
            );

            if (rsSaving.next()) {
                savingAccountLabel.setText(rsSaving.getString("total"));
            }

            ResultSet rsBranch = c.s.executeQuery(
                    "SELECT CHINHANH_NH, COUNT(*) AS total " +
                    "FROM signup2 " +
                    "GROUP BY CHINHANH_NH " +
                    "ORDER BY total DESC " +
                    "LIMIT 1"
            );		// Lấy chi nhánh có nhiều khách hàng nhất

            if (rsBranch.next()) {

                String branch = rsBranch.getString("CHINHANH_NH");
                int total = rsBranch.getInt("total");

                topBranchLabel.setText(branch + " - " + total + " KH");
            }

            loadTopCustomerTable();
            updateLastRefreshTime();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải thống kê khách hàng từ DB!");
        }
    }

    private void loadTopCustomerTable() {
        try {
            model.setRowCount(0);

            Conn c = new Conn();

            String query =
                    "SELECT s1.FORMNO, s1.HOTEN, s2.SOTHE, s2.LOAI_TK, s2.CHINHANH_NH, " +
                    "COUNT(b.ID) AS soGiaoDich, IFNULL(SUM(b.SOTIEN), 0) AS tongTien " +
                    "FROM signup1 s1 " +
                    "JOIN signup2 s2 ON s1.FORMNO = s2.FORMNO " +
                    "LEFT JOIN bank b ON s2.SOTHE = b.SOTHE " +
                    "GROUP BY s1.FORMNO, s1.HOTEN, s2.SOTHE, s2.LOAI_TK, s2.CHINHANH_NH " +
                    "ORDER BY soGiaoDich DESC";

            ResultSet rs = c.s.executeQuery(query);
            
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));	// Dùng NumberFormat để format số có dấu chấm.
            
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("FORMNO"),
                        rs.getString("HOTEN"),
                        rs.getString("SOTHE"),
                        rs.getString("LOAI_TK"),
                        rs.getString("CHINHANH_NH"),
                        rs.getString("soGiaoDich"),
                        formatter.format(Integer.parseInt(rs.getString("tongTien"))) + " VND"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải bảng khách hàng!");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == refreshButton) {
            loadCustomerStatistics();

        } else if (ae.getSource() == backButton) {
            Container parent = getParent();
            CardLayout cl = (CardLayout) parent.getLayout();
            cl.show(parent, "HOME");
        }
    }
}
