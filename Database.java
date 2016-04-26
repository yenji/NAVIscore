package packageNAVIscore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public Connection con;
	public Statement state;
	public ResultSet rs;
	private String driver, url, db, user;

	/* Konstruktor Belegen mit (Treiberpfad, serverURL, Datenbankname, Benutzer)
	 * Benutzer = "user=?????;password=?????" */
	Database(String driverImp, String urlImp, String dbImp, String userImp) {
		driver = driverImp;
		url = urlImp;
		db = dbImp;
		user = userImp;
		
	}

	/* Stellt Verbindung her */
	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver); 														// Treiber
		con = DriverManager.getConnection(
				url + 																// ServerURL
				db + 																// Datenbank
				user); 																// Benutzer

	}

	/* Trennt Verbindung  */
	public void disconnect() throws SQLException {
		rs.close(); 																// ResultSet beenden
		state.close(); 																// Statement beenden
		con.close(); 																// Verbindung beenden

	}

	/* Durch SQL Query ein ResultSet erstellen */
	public void query(String mergedQuery) throws SQLException {
		state = con.createStatement();
		rs = state.executeQuery(mergedQuery); 										// SQL Query
	}
	
	/* ResultSet an variabler Position zurückgeben */
	public String read(int pos) throws SQLException {
		if (rs.getRow() == 0) {
			return "";
		}
		return rs.getString(pos);

	}

	/* SQL Statement zusammenfügen */
	public String mergeQuery(String table, String db, String filter) {
		return table + db + filter;
	}

}
