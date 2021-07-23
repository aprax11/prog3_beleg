package automat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KuchenBoden implements GanzerKuchen, Serializable { //TODO default boden
    private Hersteller hersteller;
    private Collection<Allergen> allergens;
    private int nährwert;
    private Duration haltbarkeit;
    private BigDecimal preis;
    private Date einfügeDate;
    private Date inspektionsdatum;
    private int fachnummer;
    private String name;
    private KuchenTypen typ;

    public KuchenBoden(Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit
            , BigDecimal preis, Date einfügeDate, Date inspektionsdatum, int fachnummer, String name, KuchenTypen typ) {
        this.hersteller = hersteller;
        this.allergens = allergens;
        this.nährwert = nährwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
        this.einfügeDate = einfügeDate;
        this.inspektionsdatum = inspektionsdatum;
        this.fachnummer = fachnummer;
        this.name = name;
        this.typ = typ;
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
        return this.inspektionsdatum;
    }

    @Override
    public int getFachnummer() {
        return this.fachnummer;
    }
    @Override
    public Date getEinfügeDate() {
        return einfügeDate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public KuchenTypen getKuchenTyp() {
        return this.typ;
    }

    @Override
    public Duration verbleibendeHaltbarkeit(Date date) {
        long diff = date.getTime() - this.einfügeDate.getTime();
        diff = (diff / (1000 * 60 * 60 * 24));
        return this.haltbarkeit.minusDays(diff);
    }
    void setInspektionsdatum(Date date) {
        this.inspektionsdatum = date;
    }

    void setFachnummer(int fach) {
        this.fachnummer = fach;
    }
}
