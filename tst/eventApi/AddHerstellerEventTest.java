package eventApi;

import automat.HerstellerImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddHerstellerEventTest {
    @Test
    public void getHerstellerTest() {
        AddHerstellerEvent event = new AddHerstellerEvent(this, "Paul", true, false);

        assertEquals("Paul", event.getHerstellerName());
    }
    @Test
    public void getBoolTest() {
        AddHerstellerEvent event = new AddHerstellerEvent(this, "Paul", true, false);

        assertTrue(event.getBool());
    }
    @Test
    public void getShowTest() {
        AddHerstellerEvent event = new AddHerstellerEvent(this, "Paul", true, false);

        assertFalse(event.getShow());
    }

}