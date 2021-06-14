package automat;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ObsttorteImplTest {
    private static final ObsttorteImpl OBSTTORTE = new ObsttorteImpl("krem" ,mock(HerstellerImpl.class), EnumSet.allOf(Allergen.class), 450, Duration.ofDays(30), "obst", new BigDecimal(23), new Date(), 0, mock(GeschäftslogikImpl.class));

    @Test
    public void getKremsorte() {
        assertEquals("krem", OBSTTORTE.getKremsorte());
    }
    @Test
    public void getObstsorte() {
        assertEquals("obst", OBSTTORTE.getObstsorte());
    }
}