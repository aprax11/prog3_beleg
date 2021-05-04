package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObsttorteImpl extends Automatenobjekt implements Obsttorte {

    private final String kremsorte;
    private final String obstsorte;

    public ObsttorteImpl(String kremsorte, Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit, String obstsorte, BigDecimal preis, Date inspektionsDatum, int fachnummer, GeschäftslogikImpl gl) {
        super(hersteller, allergens, nährwert, haltbarkeit, preis, inspektionsDatum, fachnummer, gl);
        this.kremsorte = kremsorte;
        this.obstsorte = obstsorte;
    }

    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }
    @Override
    public String getObstsorte() {
        return this.obstsorte;
    }
}