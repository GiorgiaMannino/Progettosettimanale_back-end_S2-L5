package it.epicode;

import it.epicode.classi.Libro;
import it.epicode.classi.Periodicita;
import it.epicode.classi.Rivista;

public class Main {
    public static void main(String[] args) {

        // Creo libri e riviste
        Libro libro1 = new Libro("1234567890", "Il Signore degli Anelli", 1954, 1178, "J.R.R. Tolkien", "Fantasy");
        Libro libro2 = new Libro("0987654321", "1984", 1949, 328, "George Orwell", "Dystopian");
        Libro libro3 = new Libro("1111111111", "Il Nome della Rosa", 1980, 500, "Umberto Eco", "Narrativa");
        Libro libro4 = new Libro("2222222222", "Il Codice Da Vinci", 2003, 454, "Dan Brown", "Thriller");

        Rivista rivista1 = new Rivista("3333333333", "National Geographic", 1888, 150, Periodicita.MENSILE);
        Rivista rivista2 = new Rivista("4444444444", "Time", 1923, 120, Periodicita.SETTIMANALE);
        Rivista rivista3 = new Rivista("5555555555", "Vanity Fair", 1888, 80, Periodicita.SETTIMANALE);
        Rivista rivista4 = new Rivista("6666666666", "National Geographic Kids", 1888, 60, Periodicita.MENSILE);

        System.out.println( " i libri sono: " + libro1 + libro2 + libro3 + libro4 );

    }

}
