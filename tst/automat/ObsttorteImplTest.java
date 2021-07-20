package automat;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ObsttorteImplTest {

    @Test
    public void getKremsorte() {
        ObsttorteImpl obsttorte = new ObsttorteImpl("krem" ,mock(HerstellerImpl.class), EnumSet.allOf(Allergen.class)
                , 450, Duration.ofDays(30), "obst", new BigDecimal(23), new Date(), 0, new Date()
                , mock(GeschäftslogikImpl.class));

        assertEquals("krem", obsttorte.getKremsorte());
    }
    @Test
    public void getObstsorte() {
        ObsttorteImpl obsttorte = new ObsttorteImpl("krem" ,mock(HerstellerImpl.class), EnumSet.allOf(Allergen.class)
                , 450, Duration.ofDays(30), "obst", new BigDecimal(23), new Date(), 0,new Date()
                , mock(GeschäftslogikImpl.class));

        assertEquals("obst", obsttorte.getObstsorte());
    }
}