package it.epicode.main;

import it.epicode.classi.Archivio;
import it.epicode.classi.Libro;
import it.epicode.classi.Periodicita;
import it.epicode.classi.Rivista;
import it.epicode.classi.Catalogo;
import it.epicode.eccezione.ElementoNonTrovatoException;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    // Crea un logger per la classe Main
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Archivio archivio = new Archivio();
        archivio.elementiIniziali();

        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            System.out.println(" ----------------------------------- ");
            System.out.println("Scegli un'opzione:");
            System.out.println("1 - Aggiungi un elemento");
            System.out.println("2 - Ricerca per ISBN");
            System.out.println("3 - Rimuovi un elemento");
            System.out.println("4 - Ricerca per anno di pubblicazione");
            System.out.println("5 - Ricerca per autore");
            System.out.println("6 - Modifica un elemento");
            System.out.println("7 - Visualizza statistiche");
            System.out.println("0 - Esci");
            System.out.println(" ----------------------------------- ");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1: // Aggiungi un elemento
                    System.out.println("Inserisci il tipo di elemento (libro/rivista):");
                    String tipo = scanner.nextLine();
                    System.out.println("Inserisci il codice ISBN:");
                    String codiceISBN = scanner.nextLine();

                    if (archivio.collezione.stream().anyMatch(e -> e.getCodiceISBN().equals(codiceISBN))) {
                        logger.error("Errore! ISBN già presente");
                        break;
                    }

                    System.out.println("Inserisci il titolo:");
                    String titolo = scanner.nextLine();
                    System.out.println("Inserisci l'anno di pubblicazione:");
                    int annoPubblicazione = scanner.nextInt();
                    System.out.println("Inserisci il numero di pagine:");
                    int numeroPagine = scanner.nextInt();
                    scanner.nextLine();

                    if (tipo.equalsIgnoreCase("libro")) {
                        System.out.println("Inserisci l'autore:");
                        String autore = scanner.nextLine();
                        System.out.println("Inserisci il genere:");
                        String genere = scanner.nextLine();
                        Libro libro = new Libro(codiceISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);
                        archivio.aggiungiElemento(libro);
                    } else if (tipo.equalsIgnoreCase("rivista")) {
                        System.out.println("Inserisci la periodicità (SETTIMANALE/MENSILE/SEMESTRALE):");
                        Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());
                        Rivista rivista = new Rivista(codiceISBN, titolo, annoPubblicazione, numeroPagine, periodicita);
                        archivio.aggiungiElemento(rivista);
                    } else {
                        logger.error("Tipo di elemento non valido!");
                    }
                    break;

                case 2: // Ricerca per ISBN
                    System.out.println("Inserisci l'ISBN da cercare:");
                    String isbnRicerca = scanner.nextLine();
                    try {
                        Catalogo risultato = archivio.ricercaperISBN(isbnRicerca);
                        logger.info("Elemento trovato: " + risultato);
                    } catch (ElementoNonTrovatoException e) {
                        logger.error(e.getMessage());
                    }
                    break;

                case 3: // Rimuovi un elemento
                    System.out.println("Inserisci l'ISBN da rimuovere:");
                    String isbnRimozione = scanner.nextLine();
                    archivio.rimuoviElemento(isbnRimozione);
                    break;

                case 4: // Ricerca per anno di pubblicazione
                    System.out.println("Inserisci l'anno di pubblicazione da cercare:");
                    int annoRicerca = scanner.nextInt();
                    archivio.ricercaAnnoPubblicazione(annoRicerca);
                    break;

                case 5: // Ricerca per autore
                    System.out.println("Inserisci l'autore da cercare:");
                    String autoreRicerca = scanner.nextLine();
                    archivio.ricercaAutore(autoreRicerca);
                    break;

                case 6: // Modifica un elemento
                    System.out.println("Inserisci l'ISBN dell'elemento da modificare:");
                    String isbnModifica = scanner.nextLine();
                    System.out.println("Inserisci il nuovo titolo:");
                    String nuovoTitolo = scanner.nextLine();
                    System.out.println("Inserisci il nuovo anno di pubblicazione:");
                    int nuovoAnno = scanner.nextInt();
                    System.out.println("Inserisci il nuovo numero di pagine:");
                    int nuovoNumeroPagine = scanner.nextInt();
                    scanner.nextLine();

                    // Trova l'elemento tramite l'ISBN
                    Catalogo elementoDaModificare = null;
                    for (Catalogo elemento : archivio.getCollezione()) {
                        if (elemento.getCodiceISBN().equals(isbnModifica)) {
                            elementoDaModificare = elemento;
                            break;
                        }
                    }

                    if (elementoDaModificare == null) {
                        logger.error("Elemento non trovato con l'ISBN inserito.");
                        break;
                    }

                    if (!nuovoTitolo.isEmpty()) {
                        elementoDaModificare.setTitolo(nuovoTitolo);
                    }
                    if (nuovoAnno > 0) {
                        elementoDaModificare.setAnnoPubblicazione(nuovoAnno);
                    }
                    if (nuovoNumeroPagine > 0) {
                        elementoDaModificare.setNumeroPagine(nuovoNumeroPagine);
                    }

                    if (elementoDaModificare instanceof Libro libroDaModificare) {
                        System.out.println("Inserisci il nuovo autore:");
                        String nuovoAutore = scanner.nextLine();
                        System.out.println("Inserisci il nuovo genere:");
                        String nuovoGenere = scanner.nextLine();
                        libroDaModificare.setAutore(nuovoAutore);
                        libroDaModificare.setGenere(nuovoGenere);
                    } else if (elementoDaModificare instanceof Rivista rivistaDaModificare) {
                        System.out.println("Inserisci la nuova periodicità (SETTIMANALE/MENSILE/SEMESTRALE):");
                        String periodicitaInput = scanner.nextLine();
                        try {
                            Periodicita nuovaPeriodicita = Periodicita.valueOf(periodicitaInput.toUpperCase());
                            rivistaDaModificare.setPeriodicita(nuovaPeriodicita);
                        } catch (IllegalArgumentException e) {
                            logger.error("Periodicità non valida! Inserisci uno dei valori validi: SETTIMANALE, MENSILE, SEMESTRALE.");
                        }
                    }

                    logger.info("Elemento con ISBN " + isbnModifica + " modificato correttamente.");
                    break;

                case 7: // Visualizza statistiche
                   System.out.println(" ----------------------------------- ");
                   System.out.println("Statistiche dell'archivio:");
                    archivio.statisticheCatalogo();
                    break;

                case 0: // Esci
                    continua = false;
                    break;

                default:
                    logger.error("Scelta non valida.");
            }
        }

        scanner.close();
    }
}
