package de.denise.tools;
import java.lang.reflect.Field;
import java.util.List;

public class ConsoleTable {
    public static void print(List<?> liste) {
        // Prüfen, ob die Liste leer ist
        if (liste == null || liste.isEmpty()) {
            System.out.println("Keine Daten vorhanden.");
            return;
        }

        // Erstes Objekt aus der Liste holen Object
        Object erstesObjekt = liste.getFirst();

        // Klasse des Objekts herausfinden
        Class<?> klasse = erstesObjekt.getClass();

        // Alle Felder der Klasse herausfinden
        Field[] felder = klasse.getDeclaredFields();

        // Breite jeder Spalte
        int[] breiten = new int[felder.length];

        // ------------------------------------------------
        // 1. Breite anhand der Überschriften bestimmen
        // ------------------------------------------------
        for (int i = 0; i < felder.length; i++) {
            breiten[i] = felder[i].getName().length();
        }

        // ------------------------------------------------
        // 2. Breite anhand der Werte bestimmen
        // ------------------------------------------------
        for (Object element : liste) {
            for (int i = 0; i < felder.length; i++) {
                try {
                    // Zugriff auf private Felder erlauben
                    felder[i].setAccessible(true);

                    // Wert des Feldes auslesen Object
                    Object wert = felder[i].get(element);

                    // Wert in String umwandeln
                    String text = String.valueOf(wert);

                    // Prüfen, ob der Wert länger ist
                    if (text.length() > breiten[i]) {
                        breiten[i] = text.length();
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Feld konnte nicht gelesen werden.");
                }
            }
        }

        // ------------------------------------------------
        // 3. Obere Linie
        // ------------------------------------------------
        obereLinie(breiten);

        // ------------------------------------------------
        // 4. Überschriften
        // ------------------------------------------------
        System.out.print("│");

        for (int i = 0; i < felder.length; i++) {
            System.out.printf(
                    " %-" + breiten[i] + "s │",
                    felder[i].getName()
            );
        }

        System.out.println();

        // ------------------------------------------------
        // 5. Mittlere Linie
        // ------------------------------------------------
        mittlereLinie(breiten);

        // ------------------------------------------------
        // 6. Daten ausgeben
        // ------------------------------------------------
        for (Object element : liste) {
            System.out.print("│");

            for (int i = 0; i < felder.length; i++) {
                try {
                    Object wert = felder[i].get(element);
                    System.out.printf(
                            " %-" + breiten[i] + "s │",
                            String.valueOf(wert)
                    );
                } catch (IllegalAccessException e) {
                    System.out.printf(
                            " %-" + breiten[i] + "s │",
                            "Fehler"
                    );
                }
            }

            System.out.println(); }

        // ------------------------------------------------
        // 7. Untere Linie
        // ------------------------------------------------
        untereLinie(breiten);
    }

    // ====================================================
    // Obere Linie
    // ====================================================
    private static void obereLinie(int[] breiten) {
        System.out.print("┌");

        for (int i = 0; i < breiten.length; i++) {
            for (int j = 0; j < breiten[i] + 2; j++) {
                System.out.print("─");
            }

            if (i < breiten.length - 1) {
                System.out.print("┬");
            }
        }

        System.out.println("┐");
    }

    // ====================================================
    // Mittlere Linie
    // ====================================================
    private static void mittlereLinie(int[] breiten) {
        System.out.print("├");

        for (int i = 0; i < breiten.length; i++) {
            for (int j = 0; j < breiten[i] + 2; j++) {
                System.out.print("─");
            }

            if (i < breiten.length - 1) {
                System.out.print("┼");
            }
        }

        System.out.println("┤");
    }

    // ====================================================
    // Untere Linie
    // ====================================================
    private static void untereLinie(int[] breiten) {
        System.out.print("└");

        for (int i = 0; i < breiten.length; i++) {
            for (int j = 0; j < breiten[i] + 2; j++) {
                System.out.print("─");
            }

            if (i < breiten.length - 1) {
                System.out.print("┴");
            }
        }

        System.out.println("┘");
    }
}
