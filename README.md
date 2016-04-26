# NAVIscore
Input test Microsoft Dynamics NAV for Lucerne University of Applied Sciences and Arts
Erstellt 15.02 - 08.03.2012 von der Projektgruppe NAVIscore der University of Applied Sciences Augsburg

NaviScore.class beinhaltet main
MySQL Datenbank mit Lösungen wird verwendet
Externe libraries: hsqldb, sqljdbc4

Der Student gibt zu Beginn den Namen seiner PC AG in die Konsole ein,
aufgefordert wird er durch "Bitte ihre PC AG eingeben:" überprüft wird die
Eingabe durch eine SQL Abfrage auf der Microsoft SQL Datenbank in der Tabelle
"company" das Programm kann durch "exit" verlassen werden.
War die Eingabe korrekt erscheint die Meldung "Bitte Aufgabe eingeben:"
und die Information, dass es möglich ist das Programm durch "exit" 
und eigentlich auch durch "EXIT" mit der Nachricht "exit um zu beenden" verlassen zu können.
Die Eingabe der Aufgabe erfolgt durch eintippen der ganzen Zahlen, für die Aufgabe
5.8 (Kreditor anlegen) soll also als 58 in die Konsole eingegeben werden.
Da Eingaben als String interpretiert werden muss eine parseInt Funktion angewendet werden
die praktischer weise den String nach integer Werten durchsucht.
Nun, da das System mit der Aufgabe gefüttert wurde kann damit begonnen werden
die Lösungsdatenbank für die Überprüfung einzugrenzen. Die Datenbank wurde so erstellt
dass es möglich ist für jede Aufgabe im Zahlenbereich 001 bis 999 Einträge zu erstellen
wobei die 000 als Dummy leer bleibt, um später gegebenenfalls für die GUI den Aufgabenbereich
bereitzustellen der überprüft werden kann. Nachdem der Aufgabenbereich festgelegt wurde
wird so oft wie es Einträge in der eingegrenzten Lösungsdatenbank gibt überprüft
ob die Eingaben des Studenten mit der von uns erstellten Lösung übereinstimmen.
Die Position der von Studenten eingegebenen Daten wird anhand weiterer Einträge in der
Lösungsdatenbank bereitgestellt. Um den Anteil an richtigen Eingaben zu errechnen
zählen zwei Hilfsvariablen die jeweiligen Durchläufe, einmal für jede Zeile in
der Lösungsdatenbank und die andere für die als gleichwertig gesetzten Eingaben.
Errechnet wird das Ergebnis durch einen Dreisatz.

Die NAVIscore.class beinhaltet die main, connectDb und disconnectDb Methoden.
Klassenvariablen sind studentDb vom Typ Database und solutionDb vom Typ Solution.

In der main Methode wird der Programmablauf bestimmt, die beiden connect Methoden
stellen die Verbindung her oder trennen diese.

Die Database.class beinhaltet connect, disconnect, query, read und mergeQuery Methoden
mit Klassenvariablen con Connection, state Statement, rs Resultset und den Strings
driver, url, db und user.

connect verbindet mit den bei der Instanziierung übergebenen Treiber, 
Datenbank, Name und Passwort eine Variable mit der entsprechenden Datenbank.

disconnect schließt das ResultSet, Statment und beendet die Verbindung mit der Datenbank.

Die query Methode erstellt zunächst mittels der createStatement Methode des Java SQL Pakets
ein Statement um dieses anschließend das executeQuery (ebenfalls im Java Paket enthalten)
mit einem übergebenen String zu befüllen und in einem ResultSet zu speichern.

read wertet anhand des übergebenen int variable das ResultSet aus.

mergeQuery dient einzig zum Zusammenfügen der Strings um daraus ein SQL Statement zu bilden.

Solution.class ist eine Sohnklasse zur Database.class und erweitert diese um readSolDb und 
calcRange

die readSolDb ist dabei das Pendant zur query aus der Database nur, dass hier das ResultSet
für den Lösungsbereich festgelegt wird welcher durch einen String übergeben wird.

calcRange setzt den Lösungsbereich anhand einer integer fest um sie für die readSolDb
als String bereitzustellen.

Die als Klasse vorgesehene Calculation.class beinhaltet den comparator und die calculation
Methoden

Der comparator vergleicht die zwei übergebenen Strings und zählt richtige Ergebnisse

calculation übergibt als double wert den Anteil an richtig erfüllten Aufgaben

Die Variable vom Typ Connection ist eine Java SQL Klasse die die Verbindung mit einem Server
bereitstellt. Benötigt wird dazu die ServerURL (oder Dateipfad), der Name der Datenbank und 
ein Benutzername mit Passwort der leserechte auf der jeweiligen Datenbank hat.

Variablen vom Typ Statement ermöglichen es SQL Abfragen auf der Datenbank durchzuführen.

ResultSets sind verkettete Listen die sich aus den Abfragen durch die Statements ergeben.

