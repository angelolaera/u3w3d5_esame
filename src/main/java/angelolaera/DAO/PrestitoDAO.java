package angelolaera.DAO;

import angelolaera.entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class PrestitoDAO {
    private EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prestito prestito) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(prestito);
            t.commit();
            System.out.println("Prestito per il libro/rivista - " + prestito.getElementoPrestato().getTitolo() + " - creato!");
        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio del prestito: " + e.getMessage());
        }
    }

    public List<Prestito> getPrestitiByUtente(String numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera", Prestito.class);
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }

    public List<Prestito> getPrestitiScaduti() {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :dataOggi AND p.dataRestituzioneEffettiva IS NULL", Prestito.class);
        query.setParameter("dataOggi", LocalDate.now());
        return query.getResultList();
    }
}
