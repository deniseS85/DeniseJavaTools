package de.denise.tools;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConsoleTableTest {

    // ==================================================
    // TEST 1: Normale Liste
    // ==================================================
    @Test
    void testNormaleListe() {

        List<Person> personen = new ArrayList<>();

        personen.add(new Person("Denise", 40, "Münster"));
        personen.add(new Person("Anna", 32, "Köln"));
        personen.add(new Person("Peter", 45, "Berlin"));

        assertDoesNotThrow(() -> ConsoleTable.print(personen));
    }


    // ==================================================
    // TEST 2: Unterschiedlich lange Werte
    // ==================================================
    @Test
    void testUnterschiedlicheLaengen() {

        List<Person> personen = new ArrayList<>();

        personen.add(new Person("Denise", 40, "Münster"));
        personen.add(new Person("Alexander", 123, "Frankfurt am Main"));
        personen.add(new Person("Tom", 7, "Köln"));

        assertDoesNotThrow(() -> ConsoleTable.print(personen));
    }


    // ==================================================
    // TEST 3: Nur ein Objekt
    // ==================================================
    @Test
    void testEinObjekt() {

        List<Person> personen = new ArrayList<>();

        personen.add(new Person("Denise", 40, "Münster"));

        assertDoesNotThrow(() -> ConsoleTable.print(personen));
    }


    // ==================================================
    // TEST 4: Leere Liste
    // ==================================================
    @Test
    void testLeereListe() {

        List<Person> personen = new ArrayList<>();

        assertDoesNotThrow(() -> ConsoleTable.print(personen));
    }


    // ==================================================
    // TEST 5: Null
    // ==================================================
    @Test
    void testNull() {

        assertDoesNotThrow(() -> ConsoleTable.print(null));
    }


    // ==================================================
    // TEST 6: Unterschiedliche Datentypen
    // ==================================================
    @Test
    void testUnterschiedlicheDatentypen() {

        List<TestDaten> daten = new ArrayList<>();

        daten.add(new TestDaten(
                "Denise",
                40,
                1.75,
                true
        ));

        daten.add(new TestDaten(
                "Anna",
                32,
                1.68,
                false
        ));

        assertDoesNotThrow(() -> ConsoleTable.print(daten));
    }


    // ==================================================
    // TEST 7: Andere Klasse
    // ==================================================
    @Test
    void testAndereKlasse() {

        List<Auto> autos = new ArrayList<>();

        autos.add(new Auto("BMW", "320", 2020));
        autos.add(new Auto("Audi", "A4", 2022));
        autos.add(new Auto("VW", "Golf", 2021));

        assertDoesNotThrow(() -> ConsoleTable.print(autos));
    }


    // ==================================================
    // Testklasse Person
    // ==================================================
    static class Person {

        private String name;
        private int alter;
        private String ort;

        public Person(String name, int alter, String ort) {
            this.name = name;
            this.alter = alter;
            this.ort = ort;
        }
    }


    // ==================================================
    // Testklasse für verschiedene Datentypen
    // ==================================================
    static class TestDaten {

        private String name;
        private int alter;
        private double groesse;
        private boolean aktiv;

        public TestDaten(
                String name,
                int alter,
                double groesse,
                boolean aktiv) {

            this.name = name;
            this.alter = alter;
            this.groesse = groesse;
            this.aktiv = aktiv;
        }
    }


    // ==================================================
    // Komplette andere Klasse
    // ==================================================
    static class Auto {

        private String marke;
        private String modell;
        private int baujahr;

        public Auto(String marke, String modell, int baujahr) {
            this.marke = marke;
            this.modell = modell;
            this.baujahr = baujahr;
        }
    }

    @Test
    void testAusgabe() {

        // Konsolenausgabe abfangen
        ByteArrayOutputStream ausgabe = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(ausgabe));

        try {

            List<Person> personen = new ArrayList<>();

            personen.add(new Person("Denise", 40, "Münster"));
            personen.add(new Person("Anna", 32, "Köln"));

            ConsoleTable.print(personen);

        } finally {

            // Normale Konsole wiederherstellen
            System.setOut(originalOut);
        }

        String ergebnis = ausgabe.toString();

        assertTrue(ergebnis.contains("name"));
        assertTrue(ergebnis.contains("alter"));
        assertTrue(ergebnis.contains("ort"));

        assertTrue(ergebnis.contains("Denise"));
        assertTrue(ergebnis.contains("Münster"));
        assertTrue(ergebnis.contains("Anna"));
        assertTrue(ergebnis.contains("Köln"));
    }
}
