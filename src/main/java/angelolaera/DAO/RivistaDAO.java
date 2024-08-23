package angelolaera.DAO;

import angelolaera.entities.Rivista;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RivistaDAO {
    private EntityManager em;

    public RivistaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Rivista rivista) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(rivista);
            t.commit();
            System.out.println("Rivista - " + rivista.getTitolo() + " - creato!");
        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio della rivista: " + e.getMessage());
        }
    }

    public void deleteByISBN(String isbn) {
        try {
            EntityTransaction t = em.getTransaction();
            Rivista found = em.find(Rivista.class, isbn);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Rivista con ISBN " + isbn + " eliminato");
            } else {
                System.out.println("Rivista non trovata con ISBN " + isbn);
            }
        } catch (Exception e) {
            System.out.println("Errore durante l'eliminazione della rivista: " + e.getMessage());
        }
    }

    public Rivista findByISBN(String isbn) {
        return em.find(Rivista.class, isbn);
    }

    public List<Rivista> getRivisteByAnnoPubblicazione(int anno) {
        TypedQuery<Rivista> query = em.createQuery("SELECT r FROM Rivista r WHERE r.annoPubblicazione = :anno", Rivista.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    public List<Rivista> getRivisteByTitolo(String titolo) {
        TypedQuery<Rivista> query = em.createQuery("SELECT r FROM Rivista r WHERE r.titolo LIKE :titolo", Rivista.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }
}
