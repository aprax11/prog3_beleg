package automat.gl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImpl extends Automatenobjekt implements Obstkuchen{
    private final String obstsorte;


    public ObstkuchenImpl(Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit, String obstsorte, BigDecimal preis, Date inspektionsDatum, int fachnummer, GeschäftslogikImpl gl) {

        super(hersteller, allergens, nährwert, haltbarkeit, preis, inspektionsDatum, fachnummer, gl);
        this.obstsorte = obstsorte;
    }

    @Override
    public String getObstsorte() {
        return this.obstsorte;
    }
}
