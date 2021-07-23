package automat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class KuchenBodenTest {
    private Hersteller HERSTELLER;
    private Collection<Allergen> ALLERGENS;
    private KuchenBoden KUCHENBODEN;
    private Date einfügeDate;
    private Date aktuellesDate;

    @BeforeEach
    public void start() {
        this.HERSTELLER = new HerstellerImpl("Paul");
        this.ALLERGENS = EnumSet.allOf(Allergen.class);
        this.einfügeDate = new GregorianCalendar(2021, Calendar.JULY, 19).getTime();
        this.aktuellesDate =  new GregorianCalendar(2021, Calendar.JULY, 20).getTime();
        this.KUCHENBODEN = new KuchenBoden(HERSTELLER,ALLERGENS, 23
                , Duration.ofDays(2), new BigDecimal("22"), this.einfügeDate, this.aktuellesDate, 12, "boden", KuchenTypen.Obstkuchen);
    }

    @Test
    public void getHerstellerTest() {
        assertEquals(HERSTELLER, KUCHENBODEN.getHersteller());
    }
    @Test
    public void getAllergensTest() {
        assertEquals(ALLERGENS, KUCHENBODEN.getAllergene());
    }
    @Test
    public void getNährwertTest() {
        assertEquals(23, KUCHENBODEN.getNaehrwert());
    }
    @Test
    public void getHaltbarkeitTest() {
        assertEquals(Duration.ofDays(2), KUCHENBODEN.getHaltbarkeit());
    }
    @Test
    public void getPreisTest() { assertEquals(new BigDecimal("22"), KUCHENBODEN.getPreis()); }
    @Test
    public void getEinfügeDateTest() { assertEquals(this.einfügeDate, KUCHENBODEN.getEinfügeDate()); }
    @Test
    public void getIsnpektionDateTest() { assertEquals(this.aktuellesDate, KUCHENBODEN.getInspektionsdatum()); }
    @Test
    public void getFachnummerTest() { assertEquals(12, KUCHENBODEN.getFachnummer()); }
    @Test
    public void getNameTest() { assertEquals("boden", KUCHENBODEN.getName()); }
    @Test
    public void getKuchenTypTest() { assertEquals(KuchenTypen.Obstkuchen, KUCHENBODEN.getKuchenTyp()); }
    @Test
    public void getverbleibendeHaltbarkenTest() { assertEquals(1L, KUCHENBODEN.verbleibendeHaltbarkeit(this.aktuellesDate).toDays()); }
    @Test
    public void setInspektionsDateTest() {
        this.KUCHENBODEN.setInspektionsdatum(new Date());
        assertEquals(new Date(), this.KUCHENBODEN.getInspektionsdatum());
    }

    @Test
    public void setFachnummerDateTest() {
        this.KUCHENBODEN.setFachnummer(3);
        assertEquals(3, this.KUCHENBODEN.getFachnummer());
    }
}