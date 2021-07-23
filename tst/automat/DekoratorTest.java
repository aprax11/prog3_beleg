package automat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DekoratorTest {
    private Hersteller HERSTELLER;
    private Collection<Allergen> ALLERGENS;
    private GanzerKuchen kuchen;
    private Date einfügeDate;
    private Date aktuellesDate;

    @BeforeEach
    public void start() {
        this.HERSTELLER = new HerstellerImpl("Paul");
        this.ALLERGENS = EnumSet.allOf(Allergen.class);
        this.einfügeDate = new GregorianCalendar(2021, Calendar.JULY, 19).getTime();
        this.aktuellesDate =  new GregorianCalendar(2021, Calendar.JULY, 20).getTime();

        this.kuchen = new Dekorator( new Dekorator(new KuchenBoden(HERSTELLER,null, 0
                , null, null, this.einfügeDate, this.aktuellesDate, 0, null, KuchenTypen.Obstkuchen),"Butter", new BigDecimal("22"), Duration.ofDays(3)
                , new HashSet<>(Arrays.asList(Allergen.Gluten)), 21),"Sahne", new BigDecimal("22")
                , Duration.ofDays(2), new HashSet<>(Arrays.asList(Allergen.Haselnuss)), 21);
    }

    @Test
    public void getHerstellerTest() {
        assertEquals(this.HERSTELLER.getName(), this.kuchen.getHersteller().getName());
    }
    @Test
    public void getEinfügeDateTest() {
        assertEquals(this.einfügeDate.getTime(), this.kuchen.getEinfügeDate().getTime());
    }
    @Test
    public void getInspectionsDateTest() {
        assertEquals(this.aktuellesDate.getTime(), this.kuchen.getInspektionsdatum().getTime());
    }
    @Test
    public void getFachnummerTest() {
        assertEquals(0, this.kuchen.getFachnummer());
    }
    @Test
    public void getKuchenTypTest() {
        assertEquals(KuchenTypen.Obstkuchen, this.kuchen.getKuchenTyp());
    }
    @Test
    public void getAggregatedNameTest() {
        assertEquals("Sahne Butter", this.kuchen.getName());
    }
    @Test
    public void getAggregatedAllergene1Test() {
        assertTrue(this.kuchen.getAllergene().contains(Allergen.Haselnuss));
    }
    @Test
    public void getAggregatedAllergene2Test() {
        assertTrue(this.kuchen.getAllergene().contains(Allergen.Gluten));
    }
    @Test
    public void getAggregatedNährwertTest() {
        assertEquals(42, this.kuchen.getNaehrwert());
    }
    @Test
    public void getAggregatedHaltbarkeitTest() {
        assertEquals(Duration.ofDays(2).toDays(), this.kuchen.getHaltbarkeit().toDays());
    }
    @Test
    public void getAggregatedPreisTest() {
        assertEquals(new BigDecimal("44").toString(), this.kuchen.getPreis().toString());
    }

}