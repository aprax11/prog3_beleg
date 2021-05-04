package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class Automatenobjekt implements Verkaufsobjekt, Kuchen {
    private final GeschäftslogikImpl gl;
    private final Hersteller hersteller;
    private final Collection<Allergen> allergens;
    private final int nährwert;
    private final BigDecimal preis;
    private Date inspektionsDatum;
    private int fachnummer;
    private final Duration haltbarkeit;

    public Automatenobjekt(Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit,  BigDecimal preis, Date inspektionsDatum, int fachnummer, GeschäftslogikImpl gl) {

        this.hersteller = hersteller;
        this.allergens = allergens;
        this.nährwert = nährwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
        this.inspektionsDatum = inspektionsDatum;
        this.fachnummer = fachnummer;
        this.gl = gl;
    }

    public void callForInspektionsdatum() {
        this.inspektionsDatum = this.gl.returnDate();
    }
    public void callForFachnummer(Object kuchen) {
        this.fachnummer = this.gl.getFachnummerForObject(kuchen);
    }

    @Override
    public Hersteller getHersteller() {
        return this.hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return this.allergens;
    }

    @Override
    public int getNaehrwert() {
        return this.nährwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return this.haltbarkeit;
    }

    @Override
    public BigDecimal getPreis() {
        return this.preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.inspektionsDatum;
    }

    @Override
    public int getFachnummer() {
        return this.fachnummer;
    }
}

