package ATMSimulationApplication;

import java.sql.*;

public class Conn {
	Connection c;
	Statement s;

	public Conn() {
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM_Simulation_Application", "root", "030104"); 	// Kết nối tới DB MySQL
			s = c.createStatement(); 																						// Tạo Statement để chạy cmd SQL
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
              