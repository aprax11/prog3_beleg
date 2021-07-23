package view;

import automat.*;
import beobachterMusterInterfaces.Beobachter;
import automat.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class AllergenBeobachterTest {

    @Test
    public void allergenAddBeobachterTest() throws IOException, InterruptedException {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        Hersteller hersteller = new HerstellerImpl("paul");
        Beobachter beobachter = new AllergenBeobachter(gl);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);

        gl.addHersteller(hersteller);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        assertEquals("allergen(e) hinzugefügt [Gluten]"+System.lineSeparator(), byteArrayOutputStream.toString());
        byteArrayOutputStream.close();
        System.setOut(System.out);
    }

    //TODO: verify besser formulieren
/*
    @Test
    public void moreAllergenAddBeobachterTest() {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        Hersteller hersteller = new HerstellerImpl("paul");
        ViewClass view = mock(ViewClass.class);
        Beobachter beobachter = new AllergenBeobachter(gl, view);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        Collection<Allergen> allergens2 = new HashSet<>();
        allergens.add(Allergen.Gluten);
        gl.addHersteller(hersteller);
        allergens.add(Allergen.Erdnuss);
        allergens2.add(Allergen.Erdnuss);
        allergens2.add(Allergen.Sesamsamen);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst",preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens2, 123, duration, "obst",preis);
        verify(view).printBeobachterBenachrichtigung("allergen hinzugefügt [Gluten, Erdnuss]");
    }
 */


    @Test
    public void allergenRemoveBeobachterTest() throws IOException, InterruptedException {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        Hersteller hersteller = new HerstellerImpl("paul");
        Beobachter beobachter = new AllergenBeobachter(gl);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);

        gl.addHersteller(hersteller);
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        gl.löscheKuchen(0);

        assertEquals("allergen(e) entfernt [Gluten]"+System.lineSeparator(), byteArrayOutputStream.toString());

        byteArrayOutputStream.close();
        System.setOut(System.out);
    }



}
