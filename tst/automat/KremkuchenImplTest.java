package automat;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class KremkuchenImplTest {
    private static final Hersteller MOCKHERSTELER = mock(HerstellerImpl.class);
    private static final GeschäftslogikImpl MOCKGESCHAÄFTSLOGIK = mock(GeschäftslogikImpl.class);
    private static final Date MOCKDATE = mock(Date.class);


    @Test
    public void getKremsorte() {
        KremkuchenImpl kremkuchen = new KremkuchenImpl("krem" ,MOCKHERSTELER, EnumSet.allOf(Allergen.class)
                , 450, Duration.ofDays(30), new BigDecimal(23), MOCKDATE, 0, MOCKGESCHAÄFTSLOGIK);
        assertEquals("krem", kremkuchen.getKremsorte());
    }

}