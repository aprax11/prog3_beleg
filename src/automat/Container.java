package automat;

import automat.Allergen;
import automat.Hersteller;
import automat.KuchenTypen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class Container {
    private Hersteller hersteller;
    private Collection<Allergen> allergens;
    private int nährwert;
    private Duration haltbarkeit;
    private BigDecimal preis;
    private String name;
    private KuchenTypen typ;

    public Container(Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit, BigDecimal preis, String name, KuchenTypen typ) {
        this.hersteller = hersteller;
        this.allergens = allergens;
        this.nährwert = nährwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
        this.name = name;
        this.typ = typ;
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

    public BigDecimal getPreis() {
        return preis;
    }

    public String getName() {
        return name;
    }

    public KuchenTypen getTyp() {
        return typ;
    }
}
