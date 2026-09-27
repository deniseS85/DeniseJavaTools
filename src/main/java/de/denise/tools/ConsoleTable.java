package de.denise.tools;
import java.lang.reflect.Field;
import java.util.List;

public class ConsoleTable {
    public static void print(List<?> list) {
        // Prüfen, ob die Liste leer ist
        if (list == null || list.isEmpty()) {
            System.out.println("Keine Daten vorhanden.");
            return;
        }

        // Erstes Objekt aus der Liste holen Object
        Object firstObject = list.get(0);

        // Klasse des Objekts herausfinden
        Class<?> firstObjectClass = firstObject.getClass();

        // Alle Felder der Klasse herausfinden
        Field[] fields = firstObjectClass.getDeclaredFields();

        // Breite jeder Spalte
        int[] widths = new int[fields.length];

        // ------------------------------------------------
        // Breite anhand der Überschriften bestimmen
        // ------------------------------------------------
        for (int i = 0; i < fields.length; i++) {
            widths[i] = fields[i].getName().length();
        }

        // ------------------------------------------------
        // Breite anhand der Werte bestimmen
        // ------------------------------------------------
        for (Object element : list) {
            for (int i = 0; i < fields.length; i++) {
                try {
                    // Zugriff auf private Felder erlauben
                    fields[i].setAccessible(true);

                    // Wert des Feldes auslesen Object
                    Object value = fields[i].get(element);

                    // Wert in String umwandeln
                    String text = String.valueOf(value);

                    // Prüfen, ob der Wert länger ist
                    if (text.length() > widths[i]) {
                        widths[i] = text.length();
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Feld konnte nicht gelesen werden.");
                }
            }
        }

        topLine(widths);

        // ------------------------------------------------
        // Überschriften
        // ------------------------------------------------
        System.out.print("│");

        for (int i = 0; i < fields.length; i++) {
            System.out.printf(
                    " %-" + widths[i] + "s │",
                    fields[i].getName()
            );
        }

        System.out.println();

        middleLine(widths);

        // ------------------------------------------------
        // Daten ausgeben
        // ------------------------------------------------
        for (Object element : list) {
            System.out.print("│");

            for (int i = 0; i < fields.length; i++) {
                try {
                    Object value = fields[i].get(element);
                    System.out.printf(
                            " %-" + widths[i] + "s │",
                            String.valueOf(value)
                    );
                } catch (IllegalAccessException e) {
                    System.out.printf(
                            " %-" + widths[i] + "s │",
                            "Fehler"
                    );
                }
            }

            System.out.println(); }

        bottomLine(widths);
    }

    private static void topLine(int[] widths) {
        System.out.print("┌");

        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < widths[i] + 2; j++) {
                System.out.print("─");
            }

            if (i < widths.length - 1) {
                System.out.print("┬");
            }
        }

        System.out.println("┐");
    }

    private static void middleLine(int[] widths) {
        System.out.print("├");

        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < widths[i] + 2; j++) {
                System.out.print("─");
            }

            if (i < widths.length - 1) {
                System.out.print("┼");
            }
        }

        System.out.println("┤");
    }

    private static void bottomLine(int[] widths) {
        System.out.print("└");

        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < widths[i] + 2; j++) {
                System.out.print("─");
            }

            if (i < widths.length - 1) {
                System.out.print("┴");
            }
        }

        System.out.println("┘");
    }
}
