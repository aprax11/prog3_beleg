package view;

import automat.Allergen;
import automat.GeschäftslogikImpl;
import automat.Hersteller;
import automat.HerstellerImpl;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
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
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);

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
        gl.addKuchen("Kremkuchen", "krem", hersteller, allergens, 123, duration, "obst", preis);

        assertEquals("" ,byteArrayOutputStream.toString());
        byteArrayOutputStream.close();
        System.setOut(System.out);
    }
}