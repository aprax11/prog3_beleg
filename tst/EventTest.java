import automat.*;
import automat.gl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class EventTest {
    private GeschäftslogikImpl gl;
    @BeforeEach
    public void set() {
        this.gl = new GeschäftslogikImpl(1);
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(gl);
        Hersteller hersteller = new HerstellerImpl("Paul");
        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller);

        handler.add(listener);
        handler.handle(event);
    }



    @Test
    public void addHerstellerEventTest() {
        Map<Hersteller, Integer> res = gl.getHerstellerList();
        for(Map.Entry<Hersteller, Integer> e : res.entrySet()) {
            assertTrue(e.getKey().getName().equalsIgnoreCase("paul"));
        }
    }
    @Test
    public void addKuchenEventTest() {
        AddKuchenEventHandler handler = new AddKuchenEventHandler();
        AddKuchenEventListener listener = new AddKuchenEventListenerImpl(this.gl);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<String> allergens = new HashSet<>();
        allergens.add("Erdnuss");
        allergens.add("Gluten");

        AddKuchenEvent event = new AddKuchenEvent(this, "Kremkuchen", "krem", "paul", allergens, 320, duration, "obst", preis);
        handler.add(listener);
        handler.handle(event);

        Automatenobjekt[] res = this.gl.listKuchen(null);
        Collection<Allergen> ares = this.gl.getAllergenList(true);
        assertEquals(2, ares.size());
        assertEquals(KremkuchenImpl.class, res[0].getClass());
    }
    @Test
    public void deleteKuchenEventTest() {
        AddKuchenEventHandler handler = new AddKuchenEventHandler();
        AddKuchenEventListener listener = new AddKuchenEventListenerImpl(this.gl);
        Duration duration = Duration.ofDays(32);
        BigDecimal preis = new BigDecimal("4.20");
        Collection<String> allergens = new HashSet<>();
        allergens.add("Erdnuss");
        allergens.add("Gluten");

        AddKuchenEvent event = new AddKuchenEvent(this, "Kremkuchen", "krem", "paul", allergens, 320, duration, "obst", preis);
        handler.add(listener);
        handler.handle(event);
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
