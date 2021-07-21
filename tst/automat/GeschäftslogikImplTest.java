package automat;

import beobachterMusterInterfaces.Beobachter;
import eventApi.ReceiveKuchenListEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.KuchenHinzufügenBeobachter;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeschäftslogikImplTest {
    private static final Duration DURATION = Duration.ofDays(1);
    private static final BigDecimal PREIS = new BigDecimal("12.2");
    private static final Hersteller HERSTELLER = new HerstellerImpl("Paul");
    private static final int NÄHRWERT = 234;
    private static final String KREMSORTE = "Krem";
    private Collection<Allergen> allergens;
    private GeschäftslogikImpl gl;

    @BeforeEach
    public void setGl() {
        this.gl = new GeschäftslogikImpl(3);
        this.allergens = new ArrayList<>();
        this.allergens.add(Allergen.Erdnuss);

    }
    @Test
    public void mockitoAddHerstellerTest() {
        Hersteller hersteller = mock(HerstellerImpl.class);
        assertTrue(this.gl.addHersteller(hersteller));
        Map resHersteller = this.gl.getHerstellerList();
        assertTrue(resHersteller.containsKey(hersteller));
    }

    @Test
    public void addMoreHerstellerTest() {
        Hersteller hersteller = new HerstellerImpl("jan");
        Hersteller hersteller2 = new HerstellerImpl("jan2");
        Hersteller hersteller3 = new HerstellerImpl("jan3");
        Hersteller hersteller4 = new HerstellerImpl("jan4");
        assertTrue(this.gl.addHersteller(hersteller));
        assertTrue(this.gl.addHersteller(hersteller2));
        assertTrue(this.gl.addHersteller(hersteller3));
        assertTrue(this.gl.addHersteller(hersteller4));
        Map resHersteller = this.gl.getHerstellerList();
        assertTrue(resHersteller.containsKey(hersteller));
        assertTrue(resHersteller.containsKey(hersteller2));
        assertTrue(resHersteller.containsKey(hersteller3));
        assertTrue(resHersteller.containsKey(hersteller4));
    }
    @Test
    public void doubleAddHerstellerTest() {
        Hersteller hersteller = new HerstellerImpl("paul");
        this.gl.addHersteller(hersteller);
        assertFalse(this.gl.addHersteller(hersteller));
        // one hersteller should still be added
        Map resHersteller = this.gl.getHerstellerList();
        assertTrue(resHersteller.containsKey(hersteller));
    }
    @Test
    public void deleteHerstellerTest() throws InterruptedException {
        Hersteller hersteller = new HerstellerImpl("paul");
        this.gl.addHersteller(hersteller);
        this.gl.löscheHersteller("paul");
        Map resHersteller = this.gl.getHerstellerList();
        assertFalse(resHersteller.containsKey(hersteller));
    }


    @Test
    public void deleteHerstellerWithCackeTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        this.gl.löscheHersteller(HERSTELLER.getName());
        Automatenobjekt[] res = this.gl.listKuchen(null);
        Map resHersteller = this.gl.getHerstellerList();
        assertEquals(0, res.length);
        assertFalse(resHersteller.containsKey(HERSTELLER));
    }
    @Test
    public void deleteMoreHerstellerWithCackeTest() throws InterruptedException {
        Hersteller hersteller = new HerstellerImpl("paul");
        Hersteller hersteller2 = new HerstellerImpl("paul2");
        Hersteller hersteller3 = new HerstellerImpl("paul3");
        this.gl.addHersteller(hersteller);
        this.gl.addHersteller(hersteller2);
        this.gl.addHersteller(hersteller3);

        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, hersteller, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        assertTrue(this.gl.addKuchen("Kremkuchen", KREMSORTE, hersteller3, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        this.gl.löscheHersteller("paul");
        this.gl.löscheHersteller("paul2");
        Map resHersteller = this.gl.getHerstellerList();
        Automatenobjekt[]res = this.gl.listKuchen(null);
        assertFalse(resHersteller.containsKey(hersteller));
        assertFalse(resHersteller.containsKey(hersteller2));
        assertTrue(resHersteller.containsKey(hersteller3));
        assertEquals(res.length, 1);
        assertSame(res[0].getClass(), KremkuchenImpl.class);
    }

    @Test
    public void listHerstellerWithCountTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        Map res = this.gl.getHerstellerList();
        assertTrue(res.containsKey(HERSTELLER));
        assertEquals(3, res.get(HERSTELLER));
    }

    @Test
    public void mockitoAddKuchenNoHerstellerTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        Hersteller mock = mock(HerstellerImpl.class);
        assertFalse(this.gl.addKuchen("Obsttorte", KREMSORTE, mock, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(0, res.length);
    }

    @Test
    public void addObsttorteTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(ObsttorteImpl.class, res[0].getClass());
    }
    @Test
    public void addFakekuchenTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertFalse(this.gl.addKuchen("Fakekuchen", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
    }


    @Test
    public void addKremkuchenTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Kremkuchen", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(KremkuchenImpl.class, res[0].getClass());
    }

    @Test
    public void addObstkuchenTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obstkuchen", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(ObstkuchenImpl.class, res[0].getClass());
    }

    @Test
    public void addKuchenWhenFullTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obstkuchen","",HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            assertFalse(this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS));
        }
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(3, res.length);
        assertEquals(KremkuchenImpl.class, res[0].getClass());
        assertEquals(ObstkuchenImpl.class, res[1].getClass());
        assertEquals(ObsttorteImpl.class, res[2].getClass());
    }

    @Test
    public void listKuchenTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obstkuchen","",HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(3, res.length);
        assertEquals(KremkuchenImpl.class, res[0].getClass());
        assertEquals(ObstkuchenImpl.class, res[1].getClass());
        assertEquals(ObsttorteImpl.class, res[2].getClass());
    }

    @Test
    public void checkDateTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertNotNull(res[0].getInspektionsdatum());
    }

    @Test
    public void setInspektionsdatumTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));

        this.gl.setInspektionsdatum(0);
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertNotNull(res[0].getInspektionsdatum());
    }

    @Test
    public void setInspektionsdatumEmptyTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));

        this.gl.setInspektionsdatum(2);
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertNotNull(res[0].getInspektionsdatum());
    }


    @Test
    public void checkFachnummerTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(0, res[0].getFachnummer());
    }


    @Test
    public void addKuchenCheckFachnummerTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obstkuchen","",HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(0, res[0].getFachnummer());
        assertEquals(1, res[1].getFachnummer());
        assertEquals(2, res[2].getFachnummer());
    }

    @Test
    public void addKuchenWhenFullCheckFachnummerTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obstkuchen","",HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
        }
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(0, res[0].getFachnummer());
        assertEquals(1, res[1].getFachnummer());
        assertEquals(2, res[2].getFachnummer());
    }
    @Test
    public void listKremkuchenTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        Automatenobjekt[] res = this.gl.listKuchen(KremkuchenImpl.class);
        assertEquals(2, res.length);
        assertEquals(KremkuchenImpl.class, res[0].getClass());
        assertEquals(KremkuchenImpl.class, res[1].getClass());
    }
    @Test
    public void listEinKremkuchenTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
        }
        Automatenobjekt[] res = this.gl.listKuchen(KremkuchenImpl.class);
        assertEquals(1, res.length);
        assertEquals(KremkuchenImpl.class, res[0].getClass());
    }

    @Test
    public void listAllergensTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        Set<Allergen> all = this.gl.getAllergenList(true);
        Set<Allergen> rest = this.gl.getAllergenList(false);
        assertTrue(all.contains(Allergen.Erdnuss));
        assertEquals(all.size(), 1);
        assertTrue(rest.contains(Allergen.Gluten));
        assertTrue(rest.contains(Allergen.Haselnuss));
        assertTrue(rest.contains(Allergen.Sesamsamen));
        assertEquals(rest.size(), 3);
    }

    @Test
    public void updateAllergensTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            Collection<Allergen> update = new ArrayList<>();
            update.add(Allergen.Haselnuss);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, update, NÄHRWERT, DURATION, "apfel", PREIS);
            this.gl.löscheKuchen(2);
        }
        Set<Allergen> all = this.gl.getAllergenList(true);
        Set<Allergen> rest = this.gl.getAllergenList(false);
        assertTrue(all.contains(Allergen.Erdnuss));
        assertEquals(all.size(), 1);
        assertTrue(rest.contains(Allergen.Gluten));
        assertTrue(rest.contains(Allergen.Haselnuss));
        assertTrue(rest.contains(Allergen.Sesamsamen));
        assertEquals(rest.size(), 3);
    }

    @Test
    public void addDateTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Obstkuchen","",HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        Automatenobjekt[] res = this.gl.listKuchen(null);
        this.gl.setInspektionsdatum(0);
        assertNotNull(res[0].getInspektionsdatum());
    }

    @Test
    public void getDateTest() {
        Date date = this.gl.returnDate();
        assertNotNull(date);
    }

    @Test
    public void löscheErstenTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
        }
        this.gl.löscheKuchen(0);
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(0, res.length);
    }
    @Test
    public void löscheKremkuchenTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        this.gl.löscheKuchen(1);
        Automatenobjekt[] res = this.gl.listKuchen(null);
        Automatenobjekt kuchen = res[1];
        assertEquals(1, kuchen.getFachnummer());
        assertEquals(KremkuchenImpl.class, res[0].getClass());
        assertEquals(ObsttorteImpl.class, res[1].getClass());
    }
    @Test
    public void löscheLetztenTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        this.gl.löscheKuchen(2);
        Automatenobjekt[] res = this.gl.listKuchen(null);
        assertEquals(KremkuchenImpl.class, res[0].getClass());
        assertEquals(KremkuchenImpl.class, res[1].getClass());
    }
    @Test
    public void löscheWhenEmptyTest() throws InterruptedException {
        this.gl.löscheKuchen(2);
        Automatenobjekt[]res = this.gl.listKuchen(null);
        assertEquals(0, res.length);
    }

    @Test
    public void löscheNotFilledTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
        }
        this.gl.löscheKuchen(2);
        Automatenobjekt[]res = this.gl.listKuchen(null);
        assertEquals(2, res.length);
    }

    @Test
    public void benachrichtigeTest() {
        Beobachter beobachter = mock(KuchenHinzufügenBeobachter.class);
        this.gl.meldeAn(beobachter);
        this.gl.benachrichtige();

        verify(beobachter).aktualisiere();
    }

    @Test
    public void meldeAbTest() {
        Beobachter beobachter = mock(KuchenHinzufügenBeobachter.class);
        this.gl.meldeAn(beobachter);
        this.gl.meldeAb(beobachter);
        this.gl.benachrichtige();

        verify(beobachter, never()).aktualisiere();
    }

    @Test
    public void getFachnummerTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
        }
        assertEquals(2, this.gl.getFachnummer());
    }

    @Test
    public void getFachnummerLoweringTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.löscheKuchen(1);
        }
        assertEquals(1, this.gl.getFachnummer());
    }

    @Test
    public void getFachnummerLoweringEmptyTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.löscheKuchen(2);
        }
        assertEquals(2, this.gl.getFachnummer());
    }

    @Test
    public void getFachnummerFullTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
        }
        assertEquals(3, this.gl.getFachnummer());
    }

    @Test
    public void getListGrößteTest() {
        assertEquals(3, this.gl.getListGröße());
    }
}
