import automat.AllergenBeobachter;
import automat.Beobachter;
import automat.gl.Allergen;
import automat.gl.GeschäftslogikImpl;
import automat.gl.Hersteller;
import automat.gl.HerstellerImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class BeobachterTest {
/*
    @Test
    public void allergenBeobachterTest() {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        Hersteller hersteller = new HerstellerImpl("paul");
        Beobachter beobachter = new AllergenBeobachter(gl);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Erdnuss);
        allergens.add(Allergen.Gluten);

        gl.addHersteller(hersteller);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst",preis);
        gl.löscheKuchen(0);

    }

 */
}
