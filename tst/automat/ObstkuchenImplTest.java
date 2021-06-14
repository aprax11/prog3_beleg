package automat;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ObstkuchenImplTest {

    @Test
    public void getObstsorte() {
        Obstkuchen obstkuchen = new ObstkuchenImpl(mock(Hersteller.class), EnumSet.allOf(Allergen.class), 450, Duration.ofDays(5), "obst", new BigDecimal(23), new Date(),0, mock(GeschäftslogikImpl.class));
        assertEquals("obst", obstkuchen.getObstsorte());
    }
}