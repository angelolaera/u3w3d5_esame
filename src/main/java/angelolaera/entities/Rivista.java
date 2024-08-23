package angelolaera.entities;

import angelolaera.entities.CatalogoBiblioteca;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("RIVISTA")
public class Rivista extends CatalogoBiblioteca {

    public enum Periodicita {
        SETTIMANALE, MENSILE, SEMESTRALE
    }

    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    public Rivista() {}

    public Rivista(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    // Getters e Setters


    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }
}
