package packageNAVIscore;

import java.sql.SQLException;

/*Kindklasse zu Database, ben�tigt �hnliche Funktionalit�t*/
public class Solution extends Database {

	/* Kindklassenkonstruktor Belegen mit (Treiberpfad, serverURL,
	 * Datenbankname, Benutzer) Benutzer = "user=?????;password=?????" */
	Solution(String driverImp, String urlImp, String dbImp, String userImp) {
		super(driverImp, urlImp, dbImp, userImp);
	}

	/* L�sungsdatenbank auslesen */
	public void readSolDb(String range) throws SQLException {
		state = con.createStatement();
		rs = state.executeQuery("SELECT * FROM Loesungstabelle " + range); 		// SQL Query erstellen
		
	}
	
	/* Bereich der L�sungsdatenbank festlegen */
	public String calcRange(int aufg) {
		aufg = aufg * 1000;
		int aufgMAX = aufg + 999;
		return "WHERE No BETWEEN " + aufg + " AND " + aufgMAX;

	}

}
