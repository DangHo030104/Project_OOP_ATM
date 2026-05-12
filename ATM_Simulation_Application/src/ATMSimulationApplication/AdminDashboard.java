package ATMSimulationApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminDashboard extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    JMenuBar menuBar;											// Thanh Menu chính ở trên cùng
    JMenu manageMenu, statisticMenu, systemMenu;				// Menu cha
    JMenuItem accountItem, transactionItem;						// Menu con của "Quản lý"
    JMenuItem systemStatisticItem, transactionStatisticItem;	// Menu con của "Thống kê"
    JMenuItem logoutItem, exitItem;								// Menu con của "Hệ thống"	

    JLabel title, welcome, note;

    // Khi bấm menuItem, không mở JFrame mới nữa, chỉ đổi “màn hình con”.
    CardLayout cardLayout;
    JPanel contentPanel;
    
    AdminDashboard() {
        setTitle("Admin Dashboard");
        setLayout(new BorderLayout());

        // ===== MENU BAR =====
        menuBar = new JMenuBar();	
        menuBar.setBackground(Color.LIGHT_GRAY);
                
        manageMenu = new JMenu("Quản lý");
        statisticMenu = new JMenu("Thống kê");
        systemMenu = new JMenu("Hệ thống");

        accountItem = new JMenuItem("Quản lý tài khoản");
        transactionItem = new JMenuItem("Quản lý giao dịch");

        systemStatisticItem = new JMenuItem("Thống kê hệ thống");
        transactionStatisticItem = new JMenuItem("Thống kê giao dịch");

        logoutItem = new JMenuItem("Đăng xuất");
        exitItem = new JMenuItem("Thoát chương trình");

        /* Add menu con vào menu cha */
        manageMenu.add(accountItem);
        manageMenu.add(transactionItem);

        statisticMenu.add(systemStatisticItem);
        statisticMenu.add(transactionStatisticItem);

        systemMenu.add(logoutItem);
        systemMenu.add(exitItem);

        /* Add menu cha vào menu bar */
        menuBar.add(manageMenu);
        menuBar.add(statisticMenu);
        menuBar.add(systemMenu);

        setJMenuBar(menuBar);	// Gắn menu vào JFrame

        // CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(HomePanel(), "HOME");	
        contentPanel.add(new AccountManagementPanel(), "ACCOUNT");
        contentPanel.add(new TransactionManagementPanel(), "TRANSACTION");
        contentPanel.add(createStatisticsPanel(), "STATISTICS");

        add(contentPanel, BorderLayout.CENTER);
        
        cardLayout.show(contentPanel, "HOME");

        // ===== EVENTS =====
        accountItem.addActionListener(this);
        transactionItem.addActionListener(this);
        systemStatisticItem.addActionListener(this);
        transactionStatisticItem.addActionListener(this);
        logoutItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel HomePanel() {
		JPanel panel = new JPanel();

	    ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/backg.png"));
	    Image bgImg = bgIcon.getImage().getScaledInstance(1200, 650, Image.SCALE_SMOOTH);
	    JLabel bg = new JLabel(new ImageIcon(bgImg));
	    bg.setBounds(0, 0, 1200, 650);
	    panel.add(bg);

	    ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/logo_bank.png"));
	    Image logoImg = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
	    JLabel logo = new JLabel(new ImageIcon(logoImg));
	    logo.setBounds(220, 25, 120, 120);
	    bg.add(logo);
        
        // ===== TITLE =====
	    title = new JLabel("BLACK BANK ADMIN SYSTEM", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 35));
	    title.setForeground(Color.WHITE);
	    title.setBounds(0, 70, 1200, 45);
	    bg.add(title);

	    welcome = new JLabel("CHÀO MỪNG ADMIN", SwingConstants.CENTER);
	    welcome.setFont(new Font("Arial", Font.BOLD, 28));
	    welcome.setForeground(Color.WHITE);
	    welcome.setBounds(0, 150, 1200, 40);
	    bg.add(welcome);

	    note = new JLabel(
	        "Vui lòng chọn chức năng trên thanh menu để bắt đầu quản lý hệ thống!",
	        SwingConstants.CENTER
	    );
	    note.setFont(new Font("Arial", Font.PLAIN, 18));
	    note.setForeground(Color.WHITE);
	    note.setBounds(0, 210, 1200, 30);
	    bg.add(note);
		
		return panel;
	} 

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("THỐNG KÊ HỆ THỐNG", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setBounds(0, 200, 1450, 50);
        panel.add(label);

        return panel;
    }
    
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == accountItem) {
        	cardLayout.show(contentPanel, "ACCOUNT");
        } else if (ae.getSource() == transactionItem) {
            cardLayout.show(contentPanel, "TRANSACTION");

        } else if (ae.getSource() == systemStatisticItem) {
            JOptionPane.showMessageDialog(null, "Chức năng thống kê hệ thống sẽ làm sau!");

			//cardLayout.show(contentPanel, "STATISTICS");
        } else if (ae.getSource() == transactionStatisticItem) {
            JOptionPane.showMessageDialog(null, "Chức năng thống kê giao dịch sẽ làm sau!");

        } else if (ae.getSource() == logoutItem) {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc chắn muốn đăng xuất không?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                setVisible(false);
                new AdminLogin().setVisible(true);
            }

        } else if (ae.getSource() == exitItem) {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc chắn muốn thoát chương trình không?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}