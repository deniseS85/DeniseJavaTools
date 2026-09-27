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
    void testNormalList() {

        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Denise", 40, "Münster"));
        persons.add(new Person("Anna", 32, "Köln"));
        persons.add(new Person("Peter", 45, "Berlin"));

        assertDoesNotThrow(() -> ConsoleTable.print(persons));
    }


    // ==================================================
    // TEST 2: Unterschiedlich lange Werte
    // ==================================================
    @Test
    void testDifferentLengths() {

        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Denise", 40, "Münster"));
        persons.add(new Person("Alexander", 123, "Frankfurt am Main"));
        persons.add(new Person("Tom", 7, "Köln"));

        assertDoesNotThrow(() -> ConsoleTable.print(persons));
    }


    // ==================================================
    // TEST 3: Nur ein Objekt
    // ==================================================
    @Test
    void testOneObject() {

        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Denise", 40, "Münster"));

        assertDoesNotThrow(() -> ConsoleTable.print(persons));
    }


    // ==================================================
    // TEST 4: Leere Liste
    // ==================================================
    @Test
    void testEmptyList() {

        List<Person> persons = new ArrayList<>();

        assertDoesNotThrow(() -> ConsoleTable.print(persons));
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
    void testDifferentDataTypes() {

        List<TestData> data = new ArrayList<>();

        data.add(new TestData(
                "Denise",
                40,
                1.75,
                true
        ));

        data.add(new TestData(
                "Anna",
                32,
                1.68,
                false
        ));

        assertDoesNotThrow(() -> ConsoleTable.print(data));
    }


    // ==================================================
    // TEST 7: Andere Klasse
    // ==================================================
    @Test
    void testOtherClass() {

        List<Car> cars = new ArrayList<>();

        cars.add(new Car("BMW", "320", 2020));
        cars.add(new Car("Audi", "A4", 2022));
        cars.add(new Car("VW", "Golf", 2021));

        assertDoesNotThrow(() -> ConsoleTable.print(cars));
    }


    // ==================================================
    // Testklasse Person
    // ==================================================
    static class Person {

        private String name;
        private int age;
        private String place;

        public Person(String name, int age, String place) {
            this.name = name;
            this.age = age;
            this.place = place;
        }
    }


    // ==================================================
    // Testklasse für verschiedene Datentypen
    // ==================================================
    static class TestData {

        private String name;
        private int age;
        private double tall;
        private boolean active;

        public TestData(
                String name,
                int age,
                double tall,
                boolean active) {

            this.name = name;
            this.age = age;
            this.tall = tall;
            this.active = active;
        }
    }


    // ==================================================
    // Komplette andere Klasse
    // ==================================================
    static class Car {

        private String brand;
        private String model;
        private int year;

        public Car(String brand, String model, int year) {
            this.brand = brand;
            this.model = model;
            this.year = year;
        }
    }

    @Test
    void testOutput() {

        // Konsolenausgabe abfangen
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        try {

            List<Person> persons = new ArrayList<>();

            persons.add(new Person("Denise", 40, "Münster"));
            persons.add(new Person("Anna", 32, "Köln"));

            ConsoleTable.print(persons);

        } finally {

            // Normale Konsole wiederherstellen
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("name"));
        assertTrue(result.contains("age"));
        assertTrue(result.contains("place"));

        assertTrue(result.contains("Denise"));
        assertTrue(result.contains("Münster"));
        assertTrue(result.contains("Anna"));
        assertTrue(result.contains("Köln"));
    }
}
