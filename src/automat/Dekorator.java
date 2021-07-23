package automat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class Dekorator implements GanzerKuchen, Serializable {
    private GanzerKuchen kuchenBoden;
    private String name;
    private BigDecimal preis;
    private Duration haltbarkeit;
    private Collection<Allergen> allergens;
    private int nährwert;

    public Dekorator(GanzerKuchen kuchenBoden, String name, BigDecimal preis, Duration haltbarkeit, Collection<Allergen> allergens, int nährwert) {
        this.kuchenBoden = kuchenBoden;
        this.name = name;
        this.preis = preis;
        this.haltbarkeit = haltbarkeit;
        this.allergens = allergens;
        this.nährwert = nährwert;
    }

    @Override
    public Hersteller getHersteller() {
        return this.kuchenBoden.getHersteller();
    }

    @Override
    public Collection<Allergen> getAllergene() {
        if(this.kuchenBoden.getAllergene() != null && this.allergens != null) {
//            HashSet<Allergen> a = (HashSet<Allergen>) this.kuchenBoden.getAllergene();
            this.allergens.addAll(this.kuchenBoden.getAllergene());
        }
        return this.allergens;
    }

    @Override
    public int getNaehrwert() {
        this.nährwert = this.nährwert + this.kuchenBoden.getNaehrwert();
        return this.nährwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        if (this.kuchenBoden.getHaltbarkeit() != null) {
            if(this.kuchenBoden.getHaltbarkeit().compareTo(this.haltbarkeit) > 0) {
                return this.haltbarkeit;

            }else if(this.kuchenBoden.getHaltbarkeit().compareTo(this.haltbarkeit) < 0) {
                this.haltbarkeit = this.kuchenBoden.getHaltbarkeit();
                return this.haltbarkeit;

            }else if(this.kuchenBoden.getHaltbarkeit().compareTo(this.haltbarkeit) == 0) {
                return this.haltbarkeit;
            }
        }
        return this.haltbarkeit;
    }

    @Override
    public BigDecimal getPreis() {
        if (this.kuchenBoden.getPreis() != null) {
            this.preis = this.preis.add(this.kuchenBoden.getPreis());
        }
        return this.preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.kuchenBoden.getInspektionsdatum();
    }

    @Override
    public int getFachnummer() {
        return this.kuchenBoden.getFachnummer();
    }

    @Override
    public Date getEinfügeDate() {
        return this.kuchenBoden.getEinfügeDate();
    }

    @Override
    public String getName() {
        if(this.kuchenBoden.getName() != null) {
            return this.name+" "+this.kuchenBoden.getName();
        }
        return this.name;
    }

    @Override
    public KuchenTypen getKuchenTyp() {
        return this.kuchenBoden.getKuchenTyp();
    }

    @Override
    public Duration verbleibendeHaltbarkeit(Date date) {
        long diff = date.getTime() - this.kuchenBoden.getEinfügeDate().getTime();
        diff = (diff / (1000 * 60 * 60 * 24));
        return this.haltbarkeit.minusDays(diff);
    }
}
