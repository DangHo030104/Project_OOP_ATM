package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticsPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    JLabel totalAccountLabel, totalTransactionLabel;
    JLabel totalDepositLabel, totalWithdrawLabel, totalBalanceLabel;

    JTable table;
    DefaultTableModel model;

    JButton refreshButton, backButton;
    JLabel updateTimeLabel;
    
    StatisticsPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("THỐNG KÊ HỆ THỐNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(31, 78, 121));
        titleLabel.setBounds(0, 20, 1200, 40);
        add(titleLabel);

        // ===== CARD THỐNG KÊ =====
        totalAccountLabel = new JLabel("0");
        JPanel accountCard = createStatCard(
                "Tổng tài khoản",
                totalAccountLabel,
                new Color(0, 102, 204),
                "icons/user.png"
        );
        accountCard.setBounds(45, 90, 250, 90);
        add(accountCard);

        totalTransactionLabel = new JLabel("0");
        JPanel transactionCard = createStatCard(
                "Tổng giao dịch",
                totalTransactionLabel,
                new Color(111, 66, 193),
                "icons/transaction.png"
        );
        transactionCard.setBounds(320, 90, 250, 90);
        add(transactionCard);

        totalDepositLabel = new JLabel("0 VND");
        JPanel depositCard = createStatCard(
                "Tổng tiền nạp",
                totalDepositLabel,
                new Color(40, 167, 69),
                "icons/deposit.png"
        );
        depositCard.setBounds(595, 90, 270, 90);
        add(depositCard);

        totalWithdrawLabel = new JLabel("0 VND");
        JPanel withdrawCard = createStatCard(
                "Tổng tiền rút",
                totalWithdrawLabel,
                new Color(220, 53, 69),
                "icons/withdraw.png"
        );
        withdrawCard.setBounds(890, 90, 255, 90);
        add(withdrawCard);

        // ===== TỔNG SỐ DƯ =====
        JPanel balancePanel = new JPanel(null);
        balancePanel.setBackground(new Color(255, 248, 220));
        balancePanel.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7)));
        balancePanel.setBounds(45, 200, 1100, 50);

        totalBalanceLabel = new JLabel("Tổng số dư toàn hệ thống: 0 VND");
        totalBalanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        totalBalanceLabel.setForeground(new Color(255, 140, 0));
        totalBalanceLabel.setBounds(25, 8, 900, 35);

        ImageIcon balanceIcon = new ImageIcon(ClassLoader.getSystemResource("icons/balance.png"));
        Image i1 = balanceIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        totalBalanceLabel.setIcon(new ImageIcon(i1));
        
        balancePanel.add(totalBalanceLabel);
        add(balancePanel);

        // ===== BẢNG THỐNG KÊ GIAO DỊCH =====
        JLabel tableTitle = new JLabel("Bảng thống kê theo loại giao dịch");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBounds(45, 265, 400, 30);
        add(tableTitle);

        updateTimeLabel = new JLabel("Cập nhật lần cuối: --/--/---- --:--:--");
        updateTimeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        updateTimeLabel.setForeground(Color.darkGray);
        updateTimeLabel.setBounds(850, 265, 300, 20);
        add(updateTimeLabel);
        
        String[] columns = {
                "Loại giao dịch", "Số lượng", "Tổng tiền"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 300, 1100, 145);
        add(scrollPane);

        // ===== BUTTON =====
        refreshButton = new JButton("Tải lại");
        refreshButton.setBounds(850, 520, 130, 30);
        refreshButton.setBackground(new Color(0, 122, 204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon refreshIcon = new ImageIcon(ClassLoader.getSystemResource("icons/refresh.png"));
        Image i2 = refreshIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        refreshButton.setIcon(new ImageIcon(i2));
        add(refreshButton);

        backButton = new JButton("Quay lại");
        backButton.setBounds(1015, 520, 130, 30);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon backIcon = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i3 = backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        backButton.setIcon(new ImageIcon(i3));
        add(backButton);

        refreshButton.addActionListener(this);
        backButton.addActionListener(this);

        loadStatistics();
    }

    // Tạo ô thống kê nhanh
    private JPanel createStatCard(String title, JLabel valueLabel, Color color, String iconPath) {
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
        titleLabel.setBounds(55, 15, 180, 25);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(color);

        // ===== VALUE =====
        valueLabel.setBounds(20, 50, 240, 35);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
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
    
    // Load toàn bộ dữ liệu thống kê
    private void loadStatistics() {
        try {
            Conn c = new Conn();
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

            // 1. Tổng tài khoản
            ResultSet rsAccount = c.s.executeQuery(
                    "SELECT COUNT(*) AS total FROM signup2"
            );

            if (rsAccount.next()) {
                totalAccountLabel.setText(formatter.format(rsAccount.getInt("total")));
            }

            // 2. Tổng giao dịch
            ResultSet rsTransaction = c.s.executeQuery(
                    "SELECT COUNT(*) AS total FROM bank"
            );

            if (rsTransaction.next()) {
                totalTransactionLabel.setText(formatter.format(rsTransaction.getInt("total")));
            }

            // 3. Tổng tiền nạp
            ResultSet rsDeposit = c.s.executeQuery(
                    "SELECT IFNULL(SUM(SOTIEN), 0) AS total FROM bank " +
                    "WHERE LOAIGD = 'Nạp tiền' OR LOAIGD = 'Nhận chuyển khoản'"
            );

            double totalDeposit = 0;

            if (rsDeposit.next()) {
                totalDeposit = rsDeposit.getDouble("total");
                totalDepositLabel.setText(formatter.format(totalDeposit) + " VND");
            }

            // 4. Tổng tiền rút
            ResultSet rsWithdraw = c.s.executeQuery(
                    "SELECT IFNULL(SUM(SOTIEN), 0) AS total FROM bank " +
                    "WHERE LOAIGD = 'Rút tiền' OR LOAIGD = 'Rút Tiền' OR LOAIGD = 'Chuyển khoản đi'"
            );

            double totalWithdraw = 0;

            if (rsWithdraw.next()) {
                totalWithdraw = rsWithdraw.getDouble("total");
                totalWithdrawLabel.setText(formatter.format(totalWithdraw) + " VND");
            }

            // 5. Tổng số dư toàn hệ thống
            double totalBalance = totalDeposit - totalWithdraw;

            totalBalanceLabel.setText(
                    "Tổng số dư toàn hệ thống: " + formatter.format(totalBalance) + " VND"
            );

            // 6. Load bảng thống kê giao dịch và cập nhật thời gian
            loadTransactionTable(formatter);
            updateLastRefreshTime();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu thống kê!");
        }
    }

    // Load bảng thống kê theo loại giao dịch
    private void loadTransactionTable(NumberFormat formatter) {
        try {
            model.setRowCount(0);

            Conn c = new Conn();

            String query =
                    "SELECT LOAIGD, COUNT(*) AS soLuong, IFNULL(SUM(SOTIEN), 0) AS tongTien " +
                    "FROM bank " +
                    "GROUP BY LOAIGD";

            ResultSet rs = c.s.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("LOAIGD"),
                        formatter.format(rs.getInt("soLuong")),
                        formatter.format(rs.getDouble("tongTien")) + " VND"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải bảng thống kê giao dịch từ DB!");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == refreshButton) {
            loadStatistics();	

        } else if (ae.getSource() == backButton) {
            Container parent = getParent();
            CardLayout cl = (CardLayout) parent.getLayout();
            cl.show(parent, "HOME");
        }
    }
}
