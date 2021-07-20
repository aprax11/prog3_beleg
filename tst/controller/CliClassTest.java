package controller;

import beobachterMusterInterfaces.Beobachter;
import eventApi.AddHerstellerEvent;
import eventApi.AddHerstellerEventHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CliClassTest {



    @Test
    public void einfügeHerstellerTest() {
        final ArgumentCaptor<AddHerstellerEvent> eventArgumentCaptor = ArgumentCaptor.forClass(AddHerstellerEvent.class);
        CliClass cli = new CliClass();
        AddHerstellerEventHandler mockHandler = mock(AddHerstellerEventHandler.class);
        cli.setHerstellerEventHandler(mockHandler);

        cli.handeln("Paul");
        verify(mockHandler).handle(eventArgumentCaptor.capture());
        AddHerstellerEvent event = eventArgumentCaptor.getValue();

        assertEquals("Paul", event.getHerstellerName().getName());
        assertTrue(event.getBool());
    }

    @Test
    public void entferneHerstellerTest() {
        final ArgumentCaptor<AddHerstellerEvent> eventArgumentCaptor = ArgumentCaptor.forClass(AddHerstellerEvent.class);
        CliClass cli = new CliClass();
        AddHerstellerEventHandler mockHandler = mock(AddHerstellerEventHandler.class);
        cli.setHerstellerEventHandler(mockHandler);


        final Field field;
        try {
            field = CliClass.class.getDeclaredField("lastCommand");
            field.setAccessible(true);
            field.set(cli, ":d");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
        cli.handeln("Paul");
        verify(mockHandler).handle(eventArgumentCaptor.capture());
        AddHerstellerEvent event = eventArgumentCaptor.getValue();

        assertEquals("Paul", event.getHerstellerName().getName());
        assertFalse(event.getBool());
    }

    @Test
    public void zeigeHerstellerTest() {
        final ArgumentCaptor<AddHerstellerEvent> eventArgumentCaptor = ArgumentCaptor.forClass(AddHerstellerEvent.class);
        CliClass cli = new CliClass();
        AddHerstellerEventHandler mockHandler = mock(AddHerstellerEventHandler.class);
        cli.setHerstellerEventHandler(mockHandler);

        final Field field;
        try {
            field = CliClass.class.getDeclaredField("lastCommand");
            field.setAccessible(true);
            field.set(cli, ":r");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
        cli.handeln("hersteller");
        verify(mockHandler).handle(eventArgumentCaptor.capture());
        AddHerstellerEvent event = eventArgumentCaptor.getValue();

        assertTrue(event.getShow());
    }
}