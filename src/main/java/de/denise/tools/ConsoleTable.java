package de.denise.tools;
import java.lang.reflect.Field;
import java.util.List;

public class ConsoleTable {

    public static void print(List<?> list) {

        // Prüfen, ob überhaupt Daten vorhanden sind
        if (list == null || list.isEmpty()) {
            System.out.println("Keine Daten vorhanden.");
            return;
        }

        // Erstes Objekt holen, um die Klasse und ihre Felder zu bestimmen
        Object firstObject = list.get(0);

        Class<?> firstObjectClass = firstObject.getClass();

        // Alle Felder der Klasse auslesen
        Field[] fields = firstObjectClass.getDeclaredFields();

        // Breite jeder Spalte speichern
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

                    // Wert des Feldes auslesen
                    Object value = fields[i].get(element);

                    // Wert in einen String umwandeln
                    String text = String.valueOf(value);


                    //Ein Feld kann mehrere Zeilen enthalten.
                    String[] lines = text.split("\\R", -1);

                    for (String line : lines) {

                        if (line.length() > widths[i]) {
                            widths[i] = line.length();
                        }
                    }

                } catch (IllegalAccessException e) {

                    System.out.println("Feld konnte nicht gelesen werden.");
                }
            }
        }

        // Obere Begrenzung der Tabelle
        topLine(widths);

        // ------------------------------------------------
        // Überschriften ausgeben
        // ------------------------------------------------
        System.out.print("│");

        for (int i = 0; i < fields.length; i++) {

            System.out.printf(
                    " %-" + widths[i] + "s │",
                    fields[i].getName()
            );
        }

        System.out.println();

        // Trennlinie unter den Überschriften
        middleLine(widths);

        // ------------------------------------------------
        // Daten ausgeben
        // ------------------------------------------------
        for (Object element : list) {

            try {

                /*
                 * Zuerst werden alle Feldwerte des Objekts
                 * ausgelesen.
                 */
                String[] values = new String[fields.length];

                // Anzahl der benötigten Tabellenzeilen
                int maxLines = 1;

                for (int i = 0; i < fields.length; i++) {

                    Object value = fields[i].get(element);

                    values[i] = String.valueOf(value);

                    // Anzahl der Zeilen dieses Feldes bestimmen
                    String[] lines = values[i].split("\\R", -1);

                    if (lines.length > maxLines) {
                        maxLines = lines.length;
                    }
                }

                /*
                 * Wenn beispielsweise ein Feld drei Zeilen enthält,
                 * wird das komplette Objekt über drei Tabellenzeilen
                 * ausgegeben.
                 */
                for (int line = 0; line < maxLines; line++) {

                    System.out.print("│");

                    for (int i = 0; i < fields.length; i++) {

                        String[] lines = values[i].split("\\R", -1);

                        /*
                         * Wenn dieses Feld in der aktuellen Zeile
                         * keinen Inhalt mehr hat, wird eine leere
                         * Zelle ausgegeben.
                         */
                        String text;

                        if (line < lines.length) {
                            text = lines[line];
                        } else {
                            text = "";
                        }

                        /*
                         * Zahlen werden rechtsbündig ausgegeben.
                         * Andere Werte werden linksbündig ausgegeben.
                         *
                         * Die Zahlen werden nur in der ersten Zeile
                         * ausgerichtet. In weiteren Zeilen ist die
                         * entsprechende Zelle leer.
                         */
                        if (line == 0 && fields[i].get(element) instanceof Number) {

                            System.out.printf(
                                    " %" + widths[i] + "s │",
                                    text
                            );

                        } else {

                            System.out.printf(
                                    " %-" + widths[i] + "s │",
                                    text
                            );
                        }
                    }

                    System.out.println();
                }

            } catch (IllegalAccessException e) {

                System.out.println("Feld konnte nicht gelesen werden.");
            }
        }

        // Untere Begrenzung der Tabelle
        bottomLine(widths);
    }


    // ==================================================
    // Obere Tabellenlinie
    // ==================================================
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


    // ==================================================
    // Trennlinie unter der Überschrift
    // ==================================================
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


    // ==================================================
    // Untere Tabellenlinie
    // ==================================================
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