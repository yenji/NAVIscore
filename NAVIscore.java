package packageNAVIscore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/* Erstellt von Projektgruppe NAVIScore der Hochschule Augsburg Deutschland
 * für die Hochschule Luzern Schweiz im Zeitraum 15.02 - 08.03.2012,
 * wir wünschen eine erfolgreiche Zusammenarbeit der beiden Hochschulen*/

/* Klasse NAVIscore beeinhaltet die main Methode, instanziiert die Datanbankaufrufe,
 * startet und koordiniert den Programmablauf und fängt die Exceptions */
public class NAVIscore {

	/* die Studentendatenbank wird zum Auslesen instanziiert */
	static Database studentDb = new Database(
			"com.microsoft.sqlserver.jdbc.SQLServerDriver", 						// Microsoft SQL Treiber
			"jdbc:sqlserver://localhost:1433;", 									// Server URL
			"DatabaseName=Demo Database NAV (6-0);", 								// Datenbank
			"user=NAVIScore;password="); 											// Benutzer mit Leserechten

	/* Um die Studenteneingaben zu überprüfen wird eine selbst erstellte
	 * Lösungsdatenbank instanziiert */
	static Solution solutionDb = new Solution(
			"org.hsqldb.jdbcDriver",												// Hyper SQL Treiber
			"jdbc:hsqldb:file:data/",												// Pfad zur Lokalen Datenbank
			"NAVIscore;",															// Datenbank
			"user=NAVISCORE;password=ZRBX");										// Benutzer mit Leserechten

	/* Verbindung mit Datenbanken herstellen */
	private static void connectDb() throws SQLException, ClassNotFoundException {
		solutionDb.connect();
		studentDb.connect();

	}

	/* Verbindung mit Datenbanken trennen */
	private static void disconnectDb() throws SQLException {
		studentDb.disconnect();
		solutionDb.disconnect();
	}

	public static void main(String [] args) {
		try {
			Calculation calc;														// Berechnungsklasse
			String company, aufg;													// Angabe der PC AG und der Aufgabe
			int aufgInt = 0;														// Aufgabe von string in int umwandeln
			boolean run = true;														// Programm läuft

			connectDb(); 															// mit Datenbanken verbinden

			BufferedReader input = new BufferedReader(new InputStreamReader(		// BufferedReader zur Eingabe aus Konsole
					System.in)); 														// (Scanner wäre over the top)
			
			/* Auswahl der PC AG */
			do {
				System.out.println("Bitte ihre PC AG eingeben:");
				company = input.readLine(); 										// PC AG eingabe
				
				/* Programm beenden */
				if (company.contentEquals("EXIT") || company.contentEquals("exit")) {
					run = false;
					System.exit(0);

				}
				
				/* Ueberpruefung der PC AG in der Studentendatenbank */
				studentDb.query("SELECT Name FROM Company WHERE Name = '" + company + "'");
				studentDb.rs.next();
			} while (!company.contentEquals(studentDb.read(1)) || company.length() == 0);
			
			/* Aufgabenueberpruefung */
			while (run) {
				if (aufgInt <= 1014) {
					calc = new Calculation(); 										// Neue Instanz der Berechnungsklasse
					
					System.out.println("Bitte Aufgabe eingeben:");
					System.out.println("exit um zu beenden");
					aufg = input.readLine();										// Aufgabennummer eingeben

					/* Programm beenden */
					if (aufg.contentEquals("EXIT") || aufg.contentEquals("exit")) {
						run = false;
						disconnectDb();
						System.exit(0);

					}
					
					try{
					aufgInt = Integer.parseInt(aufg);								// Aufgabenstring umwandeln in Integer

					/* Bereich der Lösungstabelle bestimmen und ResultSet setzen */
					solutionDb.readSolDb(solutionDb.calcRange(aufgInt));
					
					solutionDb.rs.next();											// Erstes Element Dummy, daher überspringen
					
					while (solutionDb.rs.next()) { 									// Bis zum letzten Element laufen
						
						/* Eine Abfrage der Studentendatenbank starten,
						 * zusammengesetzt aus Elementen der Lösungsdatenbank
						 * und der zuvor eingegebenen PC AG */
						studentDb.query(studentDb.mergeQuery(solutionDb.read(3),
										company, solutionDb.read(4)));	
						studentDb.rs.next();										// Linked List startet bei 0
						
						/*Vergleich der Studenteneingaben mit Loesung*/
						// System.out.println("student: " + studentDb.read(1) + " solution: " + solutionDb.read(2) + " feld: " + solutionDb.read(5));
						calc.comparator(studentDb.read(1), solutionDb.read(2));
					}
					
					/* Ausgabe und Berechnung */
					System.out.println();
					System.out.println(calc.calculation() + "% erf\u00FCllt");
					}catch(NumberFormatException nf){								// faengt falsch eingegebene
						System.out.println("Eingabe keine Zahl");						// Aufgaben
					}
				}
			}
			
		/*Exceptions fangen*/
		} catch (SQLException sql) {
			sql.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
