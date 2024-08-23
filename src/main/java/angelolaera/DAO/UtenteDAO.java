package angelolaera.DAO;

import angelolaera.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UtenteDAO {
    private EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente utente) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(utente);
            t.commit();
            System.out.println("Utente - " + utente.getNome() + " " + utente.getCognome() + " - creato!");
        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio dell'utente: " + e.getMessage());
        }
    }

    public Utente findByNumeroTessera(String numeroTessera) {
        return em.find(Utente.class, numeroTessera);
    }
}
