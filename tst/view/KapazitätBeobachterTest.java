package view;

import automat.*;
import automat.Container;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class KapazitätBeobachterTest {
    @Test
    public void füllgradTest() throws InterruptedException, IOException {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(10);
        KapazitätBeobachter beobachter = new KapazitätBeobachter(gl);

        Hersteller hersteller = new HerstellerImpl("paul");
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);

        gl.addHersteller(hersteller);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));


        assertEquals("Füllstand von 90% wurde erreicht"+System.lineSeparator(), byteArrayOutputStream.toString());
        byteArrayOutputStream.close();
        System.setOut(System.out);
    }

    @Test
    public void füllgradNoSoutTest() throws InterruptedException, IOException {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(10);
        KapazitätBeobachter beobachter = new KapazitätBeobachter(gl);

        Hersteller hersteller = new HerstellerImpl("paul");
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);

        gl.addHersteller(hersteller);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        gl.addKuchen(new ArrayList<>(), new Container(hersteller, allergens, 123, duration, preis, "krem", KuchenTypen.Kremkuchen));;

        assertEquals("" ,byteArrayOutputStream.toString());
        byteArrayOutputStream.close();
        System.setOut(System.out);
    }
}