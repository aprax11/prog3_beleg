package view;

import automat.Allergen;
import beobachterMusterInterfaces.Beobachter;
import automat.Hersteller;
import automat.HerstellerImpl;
import automat.GeschäftslogikImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;

import static org.mockito.Mockito.*;
public class BeobachterTest {

    @Test
    public void allergenAddBeobachterTest() {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        Hersteller hersteller = new HerstellerImpl("paul");
        ViewClass view = mock(ViewClass.class);
        Beobachter beobachter = new AllergenBeobachter(gl, view);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);

        gl.addHersteller(hersteller);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        verify(view).printBeobachterBenachrichtigung("allergen(e) hinzugefügt [Gluten]");
    }
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
    public void allergenRemoveBeobachterTest() {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        Hersteller hersteller = new HerstellerImpl("paul");
        ViewClass view = mock(ViewClass.class);
        Beobachter beobachter = new AllergenBeobachter(gl, view);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);

        gl.addHersteller(hersteller);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.löscheKuchen(0);

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).printBeobachterBenachrichtigung("allergen(e) hinzugefügt [Gluten]");
        inOrder.verify(view).printBeobachterBenachrichtigung("allergen(e) entfernt [Gluten]");
    }

}
