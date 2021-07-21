package controller;

import automat.*;
import eventApi.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BSTests {
//    @Test
//    public void addKuchenListenerTest() {
//        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
//        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(gl);
//        Hersteller hersteller = new HerstellerImpl("Paul");
//        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller, true, false);
//
//        handler.add(listener);
//        handler.handle(event);
//
//        AddKuchenEventHandler kuchenHandler = new AddKuchenEventHandler();
//        AddKuchenEventListener kuchenListener = new AddKuchenEventListenerImpl(this.gl);
//        Duration duration = Duration.ofDays(32);
//        BigDecimal preis = new BigDecimal("4.20");
//        Collection<Allergen> allergens = new HashSet<>();
//        allergens.add(Allergen.Sesamsamen);
//        allergens.add(Allergen.Erdnuss);
//
//        AddKuchenEvent kuchenEvent = new AddKuchenEvent(this, "Kremkuchen", "krem", hersteller, allergens, 320, duration, "obst", preis);
//        kuchenHandler.add(kuchenListener);
//        kuchenHandler.handle(kuchenEvent);
//
//        Automatenobjekt[] res = this.gl.listKuchen(null);
//        Collection<Allergen> ares = this.gl.getAllergenList(true);
//        assertEquals(2, ares.size());
//        assertEquals(KremkuchenImpl.class, res[0].getClass());
//    }
//    @Test
//    public void deleteKuchenListenerTest() {
//        // Add Hersteller things
//        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
//        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(gl);
//        Hersteller hersteller = new HerstellerImpl("Paul");
//        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller, true, false);
//
//        handler.add(listener);
//        handler.handle(event);
//
//        //Add Kuchen things
//        AddKuchenEventHandler kuchenHandler = new AddKuchenEventHandler();
//        AddKuchenEventListener kuchenListener = new AddKuchenEventListenerImpl(this.gl);
//        Duration duration = Duration.ofDays(32);
//        BigDecimal preis = new BigDecimal("4.20");
//        Collection<Allergen> allergens = new HashSet<>();
//        allergens.add(Allergen.Sesamsamen);
//        allergens.add(Allergen.Erdnuss);
//
//
//        AddKuchenEvent kuchenEvent = new AddKuchenEvent(this, "Kremkuchen", "krem", hersteller, allergens, 320, duration, "obst", preis);
//        kuchenHandler.add(kuchenListener);
//        kuchenHandler.handle(kuchenEvent);
//
//        //establish delete things
//        DeleteKuchenEventHandler deleteHandler = new DeleteKuchenEventHandler();
//        DeleteKuchenEventListener deleteListener = new DeleteKuchenEventListenerImpl(this.gl);
//        DeleteKuchenEvent deleteEvent = new DeleteKuchenEvent(this, 0, false);
//        deleteHandler.add(deleteListener);
//        deleteHandler.handle(deleteEvent);
//
//        Automatenobjekt[] res = this.gl.listKuchen(null);
//        Collection<Allergen> ares = this.gl.getAllergenList(true);
//        assertEquals(0, ares.size());
//        assertEquals(0, res.length);
//    }
}
