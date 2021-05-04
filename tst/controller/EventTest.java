package controller;

import automat.*;
import controller.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class EventTest {
    private GeschäftslogikImpl gl;
    @BeforeEach
    public void set() {
        this.gl = new GeschäftslogikImpl(1);

        ReceiveKuchenListEventHandler receiveKuchenListEventHandler = new ReceiveKuchenListEventHandler();
        this.gl.setReceiveKuchenListEventHandler(receiveKuchenListEventHandler);
    }



    @Test
    public void addHerstellerEventTest() {
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(gl);
        Hersteller hersteller = new HerstellerImpl("Paul");
        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller, true);

        handler.add(listener);
        handler.handle(event);
        Map<Hersteller, Integer> res = gl.getHerstellerList();
        for(Map.Entry<Hersteller, Integer> e : res.entrySet()) {
            assertTrue(e.getKey().getName().equalsIgnoreCase("paul"));
        }
    }
    @Test
    public void addKuchenEventTest() {
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(gl);
        Hersteller hersteller = new HerstellerImpl("Paul");
        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller, true);

        handler.add(listener);
        handler.handle(event);

        AddKuchenEventHandler kuchenHandler = new AddKuchenEventHandler();
        AddKuchenEventListener kuchenListener = new AddKuchenEventListenerImpl(this.gl);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Sesamsamen);
        allergens.add(Allergen.Erdnuss);

        AddKuchenEvent kuchenEvent = new AddKuchenEvent(this, "Kremkuchen", "krem", hersteller, allergens, 320, duration, "obst", preis);
        kuchenHandler.add(kuchenListener);
        kuchenHandler.handle(kuchenEvent);

        Automatenobjekt[] res = this.gl.listKuchen(null);
        Collection<Allergen> ares = this.gl.getAllergenList(true);
        assertEquals(2, ares.size());
        assertEquals(KremkuchenImpl.class, res[0].getClass());
    }
    @Test
    public void deleteKuchenEventTest() {
        // Add Hersteller things
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(gl);
        Hersteller hersteller = new HerstellerImpl("Paul");
        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller, true);

        handler.add(listener);
        handler.handle(event);

        //Add Kuchen things
        AddKuchenEventHandler kuchenHandler = new AddKuchenEventHandler();
        AddKuchenEventListener kuchenListener = new AddKuchenEventListenerImpl(this.gl);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Sesamsamen);
        allergens.add(Allergen.Erdnuss);


        AddKuchenEvent kuchenEvent = new AddKuchenEvent(this, "Kremkuchen", "krem", hersteller, allergens, 320, duration, "obst", preis);
        kuchenHandler.add(kuchenListener);
        kuchenHandler.handle(kuchenEvent);

        //establish delete things
        DeleteKuchenEventHandler deleteHandler = new DeleteKuchenEventHandler();
        DeleteKuchenEventListener deleteListener = new DeleteKuchenEventListenerImpl(this.gl);
        DeleteKuchenEvent deleteEvent = new DeleteKuchenEvent(this, 0);
        deleteHandler.add(deleteListener);
        deleteHandler.handle(deleteEvent);

        Automatenobjekt[] res = this.gl.listKuchen(null);
        Collection<Allergen> ares = this.gl.getAllergenList(true);
        assertEquals(0, ares.size());
        assertEquals(0, res.length);
    }
}
