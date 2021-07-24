package automat;

import beobachterMusterInterfaces.Beobachter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.KuchenHinzufügenBeobachter;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeschäftslogikImplTest { //TODO before each
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
        this.gl.addHersteller(hersteller);
        Map resHersteller = this.gl.getHerstellerList();
        assertTrue(resHersteller.containsKey(hersteller));
    }

    @Test
    public void addMoreHerstellerTest() {
        Hersteller hersteller = new HerstellerImpl("jan");
        Hersteller hersteller2 = new HerstellerImpl("jan2");
        Hersteller hersteller3 = new HerstellerImpl("jan3");
        Hersteller hersteller4 = new HerstellerImpl("jan4");
        this.gl.addHersteller(hersteller);
        this.gl.addHersteller(hersteller2);
        this.gl.addHersteller(hersteller3);
        this.gl.addHersteller(hersteller4);
        Map resHersteller = this.gl.getHerstellerList();

        //weiß nicht glaube hier komme ich nicht um mehrere asserts herum
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
    public void deleteHerstellerTest() {
        Hersteller hersteller = new HerstellerImpl("paul");
        this.gl.addHersteller(hersteller);
        this.gl.löscheHersteller("paul");
        Map resHersteller = this.gl.getHerstellerList();
        assertFalse(resHersteller.containsKey(hersteller));
    }


    @Test
    public void deleteHerstellerWithCackeTest() {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte));
        this.gl.löscheHersteller(HERSTELLER.getName());
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(0, res.length);
    }
    @Test
    public void deleteMoreHerstellerWithCackeTest() {
        Hersteller hersteller = new HerstellerImpl("paul");
        Hersteller hersteller2 = new HerstellerImpl("paul2");

        this.gl.addHersteller(hersteller);
        this.gl.addHersteller(hersteller2);

        this.gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte));


        this.gl.löscheHersteller("paul2");

        GanzerKuchen[]res = this.gl.listKuchen(null);

        assertTrue(1 == res.length && res[0].getHersteller().getName().equals("paul"));
    }

    @Test
    public void listHerstellerWithCountTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte));
        }
        Map res = this.gl.getHerstellerList();

        assertEquals(3, res.get(HERSTELLER));
    }

    @Test
    public void mockitoAddKuchenNoHerstellerTest() {
        Hersteller mock = mock(HerstellerImpl.class);
        assertFalse(this.gl.addKuchen(new ArrayList<>(), new Container(mock, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte)));
    }

    @Test
    public void addBodenTest()  {
        this.gl.addHersteller(HERSTELLER);
        assertTrue(this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte)));
    }

    @Test
    public void schachtelDekoriererTest() {
        this.gl.addHersteller(HERSTELLER);
        Container boden = new Container(HERSTELLER, null, 0, null, null, null, KuchenTypen.Obsttorte);
        Container belag = new Container(null, EnumSet.allOf(Allergen.class), 432, Duration.ofDays(30), new BigDecimal("22"), "butter", null);
        Container belag2 = new Container(null, EnumSet.allOf(Allergen.class), 432, Duration.ofDays(30), new BigDecimal("22"), "Sahner", null);

        ArrayList<Container> liste = new ArrayList<>();
        liste.add(belag);
        liste.add(belag2);
        this.gl.addKuchen(liste, boden);
        assertEquals(864, this.gl.listKuchen(null)[0].getNaehrwert());
    }


    @Test
    public void addKremkuchenTest() {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Kremkuchen));
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(KuchenTypen.Kremkuchen, res[0].getKuchenTyp());
    }

    @Test
    public void addObstkuchenTest() {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(KuchenTypen.Obstkuchen, res[0].getKuchenTyp());
    }

    @Test
    public void addKuchenWhenFullTest() {
        if (this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            assertFalse(this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen)));
        }
    }
    @Test
    public void raiseFachnummerWhenAddedTest() {
        if (this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));

            assertEquals(1, this.gl.getFachnummer());
        }
    }
    @Test
    public void dontRaiseFachnummerWhenFullTest() {
        if (this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            assertEquals(3, this.gl.getFachnummer());
        }
    }

    @Test
    public void listKuchenTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        }
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(3, res.length);
    }

    @Test
    public void checkDateTest() {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertNotNull(res[0].getInspektionsdatum());
    }

    @Test
    public void setInspektionsdatumTest() {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));

        this.gl.setInspektionsdatum(0);
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertNotNull(res[0].getInspektionsdatum());
    }

    @Test
    public void setInspektionsdatumEmptyTest() {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));

        try {
            this.gl.setInspektionsdatum(2);
        }catch (Exception e) {
            fail();
        }

    }


    @Test
    public void checkFachnummerTest() {
        this.gl.addHersteller(HERSTELLER);
        this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(0, res[0].getFachnummer());
    }


    @Test
    public void addKuchenCheckFachnummerTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        }
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(1, res[1].getFachnummer());
    }
    @Test
    public void listEinKremkuchenTest(){
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Kremkuchen));
        }
        GanzerKuchen[] res = this.gl.listKuchen(KuchenTypen.Kremkuchen);

        assertEquals(KuchenTypen.Kremkuchen, res[0].getKuchenTyp());
    }


    @Test
    public void listKremkuchenTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Kremkuchen));
        }
        GanzerKuchen[] res = this.gl.listKuchen(KuchenTypen.Kremkuchen);

        assertEquals(KuchenTypen.Kremkuchen, res[0].getKuchenTyp());
    }


    @Test
    public void listAllergensTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));

        }
        Set<Allergen> all = this.gl.getAllergenList(true);

        assertTrue(all.contains(Allergen.Erdnuss));
    }

    @Test
    public void listFalseAllergensTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            EnumSet<Allergen> allergen = EnumSet.allOf(Allergen.class);
            allergen.remove(Allergen.Gluten);
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergen, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));

        }
        Set<Allergen> all = this.gl.getAllergenList(false);

        assertTrue(all.contains(Allergen.Gluten));
    }

    @Test
    public void updateAllergensTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            Collection<Allergen> update = new ArrayList<>();
            update.add(Allergen.Haselnuss);
            Collection<Allergen> update2 = new ArrayList<>();
            update.add(Allergen.Sesamsamen);
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, update, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, update2, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        }
        Set<Allergen> rest = this.gl.getAllergenList(false);
        assertTrue(rest.contains(Allergen.Gluten));
    }

    @Test
    public void updateWithDeleteAllergensTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            Collection<Allergen> update = new ArrayList<>();
            update.add(Allergen.Haselnuss);
            Collection<Allergen> update2 = new ArrayList<>();
            update.add(Allergen.Gluten);
            Collection<Allergen> update3 = new ArrayList<>();
            update.add(Allergen.Sesamsamen);
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER,update2 , NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, update, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, update3, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.löscheKuchen(1);
        }
        Set<Allergen> rest = this.gl.getAllergenList(false);
        assertTrue(rest.contains(Allergen.Gluten));
    }

    @Test
    public void löscheErstenTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        }
        this.gl.löscheKuchen(0);
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(0, res.length);
    }

    @Test
    public void löscheKremkuchenTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Kremkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obsttorte));
        }
        this.gl.löscheKuchen(0);
        GanzerKuchen[] res = this.gl.listKuchen(null);

        assertEquals(KuchenTypen.Obsttorte, res[0].getKuchenTyp());

    }
    @Test
    public void löscheLetztenTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Kremkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        }
        this.gl.löscheKuchen(1);
        GanzerKuchen[] res = this.gl.listKuchen(null);
        assertEquals(KuchenTypen.Kremkuchen, res[0].getKuchenTyp());

    }
    @Test
    public void löscheWhenEmptyTest() {
        this.gl.löscheKuchen(2);
        try {
            GanzerKuchen[] res = this.gl.listKuchen(null);
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void löscheNotFilledTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
        }
        this.gl.löscheKuchen(3);
        GanzerKuchen[]res = this.gl.listKuchen(null);
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
    public void getFachnummerLoweringEmptyTest() {
        if(this.gl.addHersteller(HERSTELLER)) {
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.addKuchen(new ArrayList<>(), new Container(HERSTELLER, allergens, NÄHRWERT, DURATION, PREIS, "", KuchenTypen.Obstkuchen));
            this.gl.löscheKuchen(3);
        }
        assertEquals(2, this.gl.getFachnummer());
    }

    @Test
    public void getListGrößteTest() {
        assertEquals(3, this.gl.getListGröße());
    }
}
