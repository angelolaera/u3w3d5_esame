package angelolaera.DAO;

import angelolaera.entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class LibroDAO {
    private EntityManager em;

    public LibroDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Libro libro) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(libro);
            t.commit();
            System.out.println("Libro - " + libro.getTitolo() + " - creato!");
        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio del libro: " + e.getMessage());
        }
    }

    public void deleteByISBN(String isbn) {
        try {
            EntityTransaction t = em.getTransaction();
            Libro found = em.find(Libro.class, isbn);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Libro con ISBN " + isbn + " eliminato");
            } else {
                System.out.println("Libro non trovato con ISBN " + isbn);
            }
        } catch (Exception e) {
            System.out.println("Errore durante l'eliminazione del libro: " + e.getMessage());
        }
    }

    public Libro findByISBN(String isbn) {
        return em.find(Libro.class, isbn);
    }

    public List<Libro> getLibriByAutore(String autore) {
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }

    public List<Libro> getLibriByAnnoPubblicazione(int anno) {
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.annoPubblicazione = :anno", Libro.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    public List<Libro> getLibriByTitolo(String titolo) {
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.titolo LIKE :titolo", Libro.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }
}
