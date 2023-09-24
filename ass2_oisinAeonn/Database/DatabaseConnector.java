package ass2_oisinAeonn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// database connectivity
public class DatabaseConnector {

	public void charactersDatabase() {
		// driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String url = "jdbc:mysql://localhost/creator";
		// user
		final String user = "root";
		// pass
		final String password = "";
		Connection con = null;
		Statement st = null;
		try {
			// connection
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// error msg
		if (con == null) {
			System.out.println("JDBC connection is not established");
			return;
		} else
			// connection success
			System.out.println("Congratulations," +
					" JDBC connection is established successfully.\n");
		try {
			// character add to table
			st = (Statement) con.createStatement();
			st.executeUpdate(
					"INSERT INTO characters(`Name`, `Type`, `Health`, `Mana`, `Attack`, `Speed`, `Evil`) VALUES ('Bart', 'Human', 25, 0, 20, 75, false);");
			st.executeUpdate(
					"INSERT INTO characters(`Name`, `Type`, `Health`, `Mana`, `Attack`, `Speed`, `Evil`) VALUES ('Symba', 'Warlock', 25, 0, 20, 75, true);");
			st.executeUpdate(
					"INSERT INTO characters(`Name`, `Type`, `Health`, `Mana`, `Attack`, `Speed`, `Evil`) VALUES ('Farquad', 'Dwarf', 25, 0, 20, 75, true);");
			System.out.println("Updated characters Table");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// similar code (should have used method...)
	public void placesDatabase() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String url = "jdbc:mysql://localhost/creator";
		final String user = "root";
		final String password = "";
		Connection con = null;
		Statement st = null;
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (con == null) {
			System.out.println("JDBC connection is not established");
			return;
		} else
			System.out.println("Congratulations," +
					" JDBC connection is established successfully.\n");
		try {
			// place add to table
			st = (Statement) con.createStatement();
			st.executeUpdate("INSERT INTO places(`Location`, `Size`, `Biome`) VALUES ('Melbourne', 'Large', 'City');");
			st.executeUpdate(
					"INSERT INTO places(`Location`, `Size`, `Biome`) VALUES ('Siberia', 'Extremely Large', 'Desert Tundra');");
			st.executeUpdate(
					"INSERT INTO places(`Location`, `Size`, `Biome`) VALUES ('Amazon', 'Very Large', 'Rainforest');");
			System.out.println("Updated places Table");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}