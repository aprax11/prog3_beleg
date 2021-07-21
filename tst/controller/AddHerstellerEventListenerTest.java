package controller;

import automat.*;
import eventApi.*;
import eventApi.AddHerstellerEventHandler;
import eventApi.AddKuchenEventHandler;
import eventApi.DeleteKuchenEventHandler;
import eventApi.ReceiveKuchenListEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class AddHerstellerEventListenerTest {
    private GeschäftslogikImpl gl;
    @BeforeEach
    public void set() {
        this.gl = new GeschäftslogikImpl(2);

    }



    @Test
    public void addHerstellerListenerTest() {
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);
        GlWrapper mockWrapper = mock(GlWrapper.class);
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(mockWrapper);

        when(mockWrapper.getGl()).thenReturn(mockGl);

        Hersteller hersteller = new HerstellerImpl("Paul");
        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller, true, false);

        handler.add(listener);
        handler.handle(event);
        verify(mockGl).addHersteller(hersteller);
    }

    @Test
    public void deleteHerstellerListenerTest() throws InterruptedException {
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);
        GlWrapper mockWrapper = mock(GlWrapper.class);
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(mockWrapper);

        when(mockWrapper.getGl()).thenReturn(mockGl);

        Hersteller hersteller = new HerstellerImpl("Paul");
        AddHerstellerEvent event = new AddHerstellerEvent(this, hersteller, false, false);

        handler.add(listener);
        handler.handle(event);
        verify(mockGl).löscheHersteller(hersteller.getName());
    }
    //TODO glaube der handler ist unnötig
    @Test
    public void showHerstellerListenerTest() throws InterruptedException {
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);
        GlWrapper mockWrapper = mock(GlWrapper.class);

        when(mockWrapper.getGl()).thenReturn(mockGl);

        ReceiveHerstellerListEventHandler mockReceiveHandler = mock(ReceiveHerstellerListEventHandler.class);
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListnerImpl listener = new AddHerstellerEventListnerImpl(mockWrapper);
        listener.setHandler(mockReceiveHandler);
        AddHerstellerEvent event = new AddHerstellerEvent(this, null, false, true);

        handler.add(listener);
        handler.handle(event);

        verify(mockGl).getHerstellerList();
    }
    @Test
    public void showReceiveHerstellerListenerTest() throws InterruptedException {
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);
        GlWrapper mockWrapper = mock(GlWrapper.class);

        when(mockWrapper.getGl()).thenReturn(mockGl);

        ReceiveHerstellerListEventHandler mockReceiveHandler = mock(ReceiveHerstellerListEventHandler.class);
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListnerImpl listener = new AddHerstellerEventListnerImpl(mockWrapper);
        listener.setHandler(mockReceiveHandler);
        AddHerstellerEvent event = new AddHerstellerEvent(this, null, false, true);

        handler.add(listener);
        handler.handle(event);

        verify(mockReceiveHandler).handle(any(ReceiveHerstellerListEvent.class));
    }
}
