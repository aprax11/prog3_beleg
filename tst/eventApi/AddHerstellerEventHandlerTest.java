package eventApi;

import controller.AddHerstellerEventListnerImpl;
import controller.CliClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddHerstellerEventHandlerTest {
    @Test
    public void addListenerTest() {
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener mockListener = mock(AddHerstellerEventListnerImpl.class);
        handler.add(mockListener);

        final Field field;
        ArrayList<AddHerstellerEventListener> list = null;
        try {
            field = handler.getClass().getDeclaredField("listener");
            field.setAccessible(true);
            list = (ArrayList<AddHerstellerEventListener>) field.get(handler);
            assertTrue(list.contains(mockListener));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void removeListenerTest() {
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener mockListener = mock(AddHerstellerEventListnerImpl.class);
        handler.add(mockListener);
        handler.remove(mockListener);

        final Field field;
        ArrayList<AddHerstellerEventListener> list = null;
        try {
            field = handler.getClass().getDeclaredField("listener");
            field.setAccessible(true);
            list = (ArrayList<AddHerstellerEventListener>) field.get(handler);
            assertFalse(list.contains(mockListener));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void handleListenerTest() {
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener mockListener = mock(AddHerstellerEventListnerImpl.class);
        AddHerstellerEvent mockEvent = mock(AddHerstellerEvent.class);
        handler.add(mockListener);
        handler.handle(mockEvent);

        verify(mockListener).onAddHerstellerEvent(mockEvent);
    }
}