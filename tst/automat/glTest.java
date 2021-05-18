package automat;

import eventApi.ReceiveKuchenListEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class glTest {
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

        ReceiveKuchenListEventHandler receiveKuchenListEventHandler = new ReceiveKuchenListEventHandler();
        this.gl.setReceiveKuchenListEventHandler(receiveKuchenListEventHandler);
    }
    @Test
    public void mockitoAddHerstellerTest() {
        Hersteller hersteller = mock(HerstellerImpl.class);
        assertTrue(this.gl.addHersteller(hersteller));
        Map resHersteller = this.gl.getHerstellerList();
        assertTrue(resHersteller.containsKey(hersteller));
    }
    @Test
    public void mockitoDoubleAddHerstellerTest() {
        Hersteller hersteller = new HerstellerImpl("paul");
        this.gl.addHersteller(hersteller);
        assertFalse(this.gl.addHersteller(hersteller));
        // one hersteller should still be added
        Map resHersteller = this.gl.getHerstellerList();
        assertTrue(resHersteller.containsKey(hersteller));
    }
    @Test
    public void mockitoAddObsttorteNoHerstellerTest() throws InterruptedException {
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
        assertNotNull(res[0]);
    }
    @Test
    public void checkDateTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS));
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
    public void addKuchentypencheckFachnummerTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Kremkuchen",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION,"", PREIS);
            this.gl.addKuchen("Obstkuchen","",HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            this.gl.addKuchen("Obsttorte",KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        }
        Automatenobjekt[] res = this.gl.listKuchen(null);
        for (int i = 0; i < res.length; i++) {
            assertEquals(i, res[i].getFachnummer());
        }

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
    public void listHerstellerTest() throws InterruptedException {
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
    /*
    @Test
    public void addDateTest() throws InterruptedException {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen("Obstkuchen","",HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
            Thread.sleep(2000);
        }
        Automatenobjekt[] res = this.gl.listKuchen(null);
        Automatenobjekt kuchen = res[0];
        this.gl.setInspektionsdatum(0);
        Date date = new Date();
        assertEquals(date, kuchen.getInspektionsdatum());
    }

     */
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
    public void deleteHerstellerrTest() throws InterruptedException {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen("Obsttorte", KREMSORTE, HERSTELLER, allergens, NÄHRWERT, DURATION, "apfel", PREIS);
        this.gl.löscheHersteller(HERSTELLER);
        Automatenobjekt[] res = this.gl.listKuchen(null);
        Map resHersteller = this.gl.getHerstellerList();
        assertEquals(0, res.length);
        assertFalse(resHersteller.containsKey(HERSTELLER));
    }


}
