package angelolaera;

import angelolaera.DAO.LibroDAO;
import angelolaera.DAO.PrestitoDAO;
import angelolaera.DAO.RivistaDAO;
import angelolaera.DAO.UtenteDAO;
import angelolaera.entities.Libro;
import angelolaera.entities.Prestito;
import angelolaera.entities.Rivista;
import angelolaera.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();

        LibroDAO libriDAO = new LibroDAO(em);
        RivistaDAO rivisteDAO = new RivistaDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Aggiungi un libro");
            System.out.println("2. Aggiungi una rivista");
            System.out.println("3. Aggiungi un utente");
            System.out.println("4. Aggiungi un prestito");
            System.out.println("5. Rimuovi un libro");
            System.out.println("6. Rimuovi una rivista");
            System.out.println("7. Trova libro per ISBN");
            System.out.println("8. Trova rivista per ISBN");
            System.out.println("9. Esci");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma newline

            switch (scelta) {
                case 1 -> aggiungiLibro(scanner, libriDAO);
                case 2 -> aggiungiRivista(scanner, rivisteDAO);
                case 3 -> aggiungiUtente(scanner, utenteDAO);
                case 4 -> aggiungiPrestito(scanner, prestitoDAO);
                case 5 -> rimuoviLibro(scanner, libriDAO);
                case 6 -> rimuoviRivista(scanner, rivisteDAO);
                case 7 -> trovaLibro(scanner, libriDAO);
                case 8 -> trovaRivista(scanner, rivisteDAO);
                case 9 -> {
                    System.out.println("Uscita dal programma...");
                    em.close();
                    emf.close();
                    return;
                }
                default -> System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private static void aggiungiLibro(Scanner scanner, LibroDAO libriDAO) {
        System.out.println("Inserisci codice ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Inserisci titolo:");
        String titolo = scanner.nextLine();
        System.out.println("Inserisci anno di pubblicazione:");
        int anno = scanner.nextInt();
        System.out.println("Inserisci numero di pagine:");
        int pagine = scanner.nextInt();
        scanner.nextLine(); // Consuma newline
        System.out.println("Inserisci autore:");
        String autore = scanner.nextLine();
        System.out.println("Inserisci genere:");
        String genere = scanner.nextLine();

        Libro libro = new Libro(isbn, titolo, anno, pagine, autore, genere);
        libriDAO.save(libro);
    }

    private static void aggiungiRivista(Scanner scanner, RivistaDAO rivisteDAO) {
        System.out.println("Inserisci codice ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Inserisci titolo:");
        String titolo = scanner.nextLine();
        System.out.println("Inserisci anno di pubblicazione:");
        int anno = scanner.nextInt();
        System.out.println("Inserisci numero di pagine:");
        int pagine = scanner.nextInt();
        scanner.nextLine(); // Consuma newline
        System.out.println("Inserisci periodicità (SETTIMANALE, MENSILE, SEMESTRALE):");
        String periodicita = scanner.nextLine();

        Rivista rivista = new Rivista(isbn, titolo, anno, pagine, Rivista.Periodicita.valueOf(periodicita.toUpperCase()));
        rivisteDAO.save(rivista);
    }

    private static void aggiungiUtente(Scanner scanner, UtenteDAO utenteDAO) {
        System.out.println("Inserisci nome:");
        String nome = scanner.nextLine();
        System.out.println("Inserisci cognome:");
        String cognome = scanner.nextLine();
        System.out.println("Inserisci data di nascita (yyyy-MM-dd):");
        String dataNascita = scanner.nextLine();
        System.out.println("Inserisci numero di tessera:");
        String numeroTessera = scanner.nextLine();

        Utente utente = new Utente(nome, cognome, dataNascita, numeroTessera);
        utenteDAO.save(utente);
    }

    private static void aggiungiPrestito(Scanner scanner, PrestitoDAO prestitoDAO) {
        System.out.println("Inserisci codice ISBN dell'elemento:");
        String isbn = scanner.nextLine();
        System.out.println("Inserisci numero di tessera dell'utente:");
        String numeroTessera = scanner.nextLine();
        System.out.println("Inserisci data inizio prestito (yyyy-MM-dd):");
        String dataInizio = scanner.nextLine();

        // Recupera l'elemento e l'utente
        EntityManager em = emf.createEntityManager();
        Libro libro = new LibroDAO(em).findByISBN(isbn);
        Utente utente = new UtenteDAO(em).findByNumeroTessera(numeroTessera);
        Prestito prestito = new Prestito();
        prestito.setElementoPrestato(libro);
        prestito.setUtente(utente);
        prestito.setDataInizioPrestito(LocalDate.parse(dataInizio));
        prestito.setDataRestituzionePrevista(LocalDate.parse(dataInizio).plusDays(30));

        prestitoDAO.save(prestito);
    }

    private static void rimuoviLibro(Scanner scanner, LibroDAO libriDAO) {
        System.out.println("Inserisci codice ISBN del libro da rimuovere:");
        String isbn = scanner.nextLine();
        libriDAO.deleteByISBN(isbn);
    }

    private static void rimuoviRivista(Scanner scanner, RivistaDAO rivisteDAO) {
        System.out.println("Inserisci codice ISBN della rivista da rimuovere:");
        String isbn = scanner.nextLine();
        rivisteDAO.deleteByISBN(isbn);
    }

    private static void trovaLibro(Scanner scanner, LibroDAO libriDAO) {
        System.out.println("Inserisci codice ISBN del libro da trovare:");
        String isbn = scanner.nextLine();
        Libro libro = libriDAO.findByISBN(isbn);
        if (libro != null) {
            System.out.println("Libro trovato: " + libro);
        } else {
            System.out.println("Libro non trovato.");
        }
    }

    private static void trovaRivista(Scanner scanner, RivistaDAO rivisteDAO) {
        System.out.println("Inserisci codice ISBN della rivista da trovare:");
        String isbn = scanner.nextLine();
        Rivista rivista = rivisteDAO.findByISBN(isbn);
        if (rivista != null) {
            System.out.println("Rivista trovata: " + rivista);
        } else {
            System.out.println("Rivista non trovata.");
        }
    }
}
