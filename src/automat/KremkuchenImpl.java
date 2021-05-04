package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KremkuchenImpl extends Automatenobjekt implements Kremkuchen {
    private final String kremsorte;


    public KremkuchenImpl(String kremsorte, Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit, BigDecimal preis, Date inspektionsDatum, int fachnummer, GeschäftslogikImpl gl) {
        super(hersteller, allergens, nährwert, haltbarkeit, preis, inspektionsDatum, fachnummer, gl);
        this.kremsorte = kremsorte;
    }


    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }
}
