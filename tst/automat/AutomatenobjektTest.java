package automat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AutomatenobjektTest {
    private static final Hersteller MOCKHERSTELER = mock(HerstellerImpl.class);
    private static final GeschäftslogikImpl MOCKGESCHAÄFTSLOGIK = mock(GeschäftslogikImpl.class);
    private static final Date MOCKDATE = mock(Date.class);
    private static final Date einfügeDatum = new GregorianCalendar(2021, Calendar.JULY, 19).getTime();
    private static final Date aktuellesDatum = new GregorianCalendar(2021, Calendar.JULY, 20).getTime();


    private static final Automatenobjekt AUTOMATENOBJEKT = new Automatenobjekt(MOCKHERSTELER, EnumSet.allOf(Allergen.class), 450, Duration.ofDays(30), new BigDecimal(23), MOCKDATE, 0, einfügeDatum, MOCKGESCHAÄFTSLOGIK);

    @Test
    public void callForInspektionsdatum() {
        AUTOMATENOBJEKT.callForInspektionsdatum();
        verify(MOCKGESCHAÄFTSLOGIK).returnDate();
    }

    @Test
    public void getVerbleibendeHaltbarkeit() {
        assertEquals(29L, AUTOMATENOBJEKT.getVerbleibendeHaltbarkeit(aktuellesDatum).toDays());
    }
    @Test
    public void getHersteller() {
        assertEquals(MOCKHERSTELER, AUTOMATENOBJEKT.getHersteller());
    }
    @Test
    public void getAllergene() {
        assertEquals(EnumSet.allOf(Allergen.class), AUTOMATENOBJEKT.getAllergene());
    }
    @Test
    public void getNaehrwert() {
        assertEquals(450, AUTOMATENOBJEKT.getNaehrwert());
    }
    @Test
    public void getHaltbarkeit() {
        assertEquals(Duration.ofDays(30), AUTOMATENOBJEKT.getHaltbarkeit());
    }
    @Test
    public void getPreis() {
        assertEquals(new BigDecimal(23), AUTOMATENOBJEKT.getPreis());
    }
    @Test
    public void getInspektionsdatum() {
        assertEquals(MOCKDATE, AUTOMATENOBJEKT.getInspektionsdatum());
    }
    @Test
    public void getFachnummer() {
        assertEquals(0, AUTOMATENOBJEKT.getFachnummer());
    }
}