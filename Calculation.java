package packageNAVIscore;

import java.sql.SQLException;

public class Calculation {
	private int counter = 0;														// Zählt richtige Ergebnisse
	public int max = 0;																// Zählt maximale Ergebnisse
	private boolean correct = true;

	public void comparator(String solStudent, String solDatabase) throws SQLException{
		max++;
		// System.out.println("max :" + max + " counter :" + counter);
		
		/* Überprüft Ergebnis mit contentEquals Funktion */	
		if (solDatabase.contentEquals(solStudent) || ((solDatabase.contentEquals(" ") && solStudent.contentEquals("")))) {
			counter++;
			
		}else{
			if (correct){
			System.out.println("Folgende Felder sind falsch: ");
			System.out.println();
			correct = false;
			}
			System.out.println(NAVIscore.solutionDb.read(5));
			System.out.println("Richtig w\u00E4re: " + NAVIscore.solutionDb.read(2));
			System.out.println("---");
		}
		

	}

	public double calculation() {
		if (max == 0){
			System.out.println("Keine Lösung vorhanden");
			return 0.0;
		}
		
		/* Berechnet per Dreisatz den Prozentualen Anteil
		 * richtiger Ergebnisse */
		return (double)(((int)((((double) counter * 100.00) / (double) max) * 100))/100.0);
	}

}
