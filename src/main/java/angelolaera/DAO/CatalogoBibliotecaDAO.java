package angelolaera.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class CatalogoBibliotecaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
