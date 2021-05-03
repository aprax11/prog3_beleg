package automat;

import automat.gl.Allergen;
import automat.gl.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.EventObject;

public class AddKuchenEvent extends EventObject {
    private final String name;
    private final String kremsorte;
    private final Hersteller hersteller;
    private final Collection<Allergen> allergens;
    private final int nährwert;
    private final Duration haltbarkeit;
    private final String obstsorte;
    private final BigDecimal preis;


   public AddKuchenEvent(Object source, String name, String kremsorte, Hersteller herstellerName, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit, String obstsorte, BigDecimal preis) {
        super(source);
       this.name = name;
       this.kremsorte = kremsorte;
       this.hersteller = herstellerName;
       this.allergens = allergens;
       this.nährwert = nährwert;
       this.haltbarkeit = haltbarkeit;
       this.obstsorte = obstsorte;
       this.preis = preis;

   }

    public String getName() {
        return name;
    }

    public String getKremsorte() {
        return kremsorte;
    }

    public Hersteller getHersteller() {
        return hersteller;
    }

    public Collection<Allergen> getAllergens() {
        return allergens;
    }

    public int getNährwert() {
        return nährwert;
    }

    public Duration getHaltbarkeit() {
        return haltbarkeit;
    }

    public String getObstsorte() {
        return obstsorte;
    }

    public BigDecimal getPreis() {
        return preis;
    }
}
