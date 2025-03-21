package it.epicode.classi;

import it.epicode.eccezione.ElementoNonTrovatoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Archivio {

    private static final Logger logger = LoggerFactory.getLogger(Archivio.class);

    public List<Catalogo> collezione = new ArrayList<>();

    public List<Catalogo> getCollezione() {
        return collezione;
    }

    public void elementiIniziali() {
        List<Catalogo> elementiIniziali = List.of(
                new Libro("1234567890", "Il Signore degli Anelli", 1954, 1178, "J.R.R. Tolkien", "Fantasy"),
                new Libro("0987654321", "1984", 1949, 328, "George Orwell", "Dystopian"),
                new Libro("1111111111", "Il Nome della Rosa", 1980, 500, "Umberto Eco", "Narrativa"),
                new Libro("2222222222", "Il Codice Da Vinci", 2003, 454, "Dan Brown", "Thriller"),
                new Rivista("3333333333", "National Geographic", 1888, 150, Periodicita.MENSILE),
                new Rivista("4444444444", "Time", 1923, 120, Periodicita.SETTIMANALE),
                new Rivista("5555555555", "Vanity Fair", 1890, 80, Periodicita.SETTIMANALE),
                new Rivista("6666666666", "National Geographic Kids", 1888, 60, Periodicita.MENSILE)
        );

        collezione.addAll(elementiIniziali);
    }

    public void aggiungiElemento(Catalogo elemento) {
        if (elemento == null) {
            logger.error("Errore! L'elemento da aggiungere non può essere null.");
            return;
        }

        boolean isIsbnPresente = collezione.stream()
                .anyMatch(e -> e.getCodiceISBN().equals(elemento.getCodiceISBN()));

        if (isIsbnPresente) {
            logger.error("Errore! ISBN già presente");
        } else {
            collezione.add(elemento);
            logger.info("Elemento {} aggiunto correttamente", elemento);
        }
    }


    public Catalogo ricercaperISBN(String codiceISBN) throws ElementoNonTrovatoException {
        if (codiceISBN == null || codiceISBN.isEmpty()) {
            throw new IllegalArgumentException("Errore! L'ISBN non può essere nullo o vuoto.");
        }
        return collezione.stream()
                .filter(elemento -> elemento.getCodiceISBN().equals(codiceISBN))
                .findFirst()
                .orElseThrow(() -> new ElementoNonTrovatoException("Elemento non trovato con l'ISBN inserito!"));
    }

    public void rimuoviElemento(String codiceISBN) {
        if (codiceISBN == null || codiceISBN.isEmpty()) {
            logger.error("Errore! L'ISBN fornito non è valido.");
            return;
        }
        boolean removed = collezione.removeIf(elemento -> elemento.getCodiceISBN().equals(codiceISBN));
        if (removed) {
            logger.info("Elemento con ISBN {} rimosso correttamente", codiceISBN);
        } else {
            logger.error("Elemento con ISBN {} non presente", codiceISBN);
        }
    }

    public void ricercaAnnoPubblicazione(int annoPubblicazione) {
        if (annoPubblicazione <= 0) {
            logger.error("Errore! L'anno di pubblicazione non è valido.");
            return;
        }

        boolean[] trovato = { false };

        collezione.stream()
                .filter(elemento -> elemento.getAnnoPubblicazione() == annoPubblicazione)
                .forEach(elemento -> {
                    if (elemento instanceof Libro libro) {
                        logger.info("Libro trovato: {}", libro);
                    } else if (elemento instanceof Rivista rivista) {
                        logger.info("Rivista trovata: {}", rivista);
                    }
                    trovato[0] = true;
                });

        if (!trovato[0]) {
            logger.warn("Nessun elemento trovato per l'anno {}", annoPubblicazione);
        }
    }

    public void ricercaAutore(String autore) {
        if (autore == null || autore.trim().isEmpty()) {
            logger.error("Errore! Il nome dell'autore non può essere nullo o vuoto.");
            return;
        }

        List<Libro> libriTrovati = collezione.stream()
                .filter(elemento -> elemento instanceof Libro libro && libro.getAutore().equalsIgnoreCase(autore))
                .map(elemento -> (Libro) elemento)
                .toList();

        if (libriTrovati.isEmpty()) {
            logger.warn("Nessun libro trovato dell'autore: {}", autore);
        } else {
            logger.info("Libri trovati dell'autore {}:", autore);
            libriTrovati.forEach(libro -> logger.info("{}", libro));
        }
    }

    public void statisticheCatalogo() {
        if (collezione.isEmpty()) {
            logger.warn("L'archivio è vuoto, nessuna statistica disponibile.");
            return;
        }

        long totaleLibri = collezione.stream()
                .filter(elemento -> elemento instanceof Libro)
                .count();

        long totaleRiviste = collezione.stream()
                .filter(elemento -> elemento instanceof Rivista)
                .count();

        Catalogo elementoMaxPagine = collezione.stream()
                .max(Comparator.comparingInt(Catalogo::getNumeroPagine))
                .orElse(null);

        double mediaPagine = collezione.stream()
                .mapToInt(Catalogo::getNumeroPagine)
                .average()
                .orElse(0);

        logger.info("Numero totale di libri: {}", totaleLibri);
        logger.info("Numero totale di riviste: {}", totaleRiviste);
        logger.info("Elemento con il maggior numero di pagine: {}", elementoMaxPagine);
        logger.info("Media delle pagine di tutti gli elementi: {}", mediaPagine);

    }
}
