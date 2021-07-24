package controller;

import automat.*;
import eventApi.*;
import eventApi.AddHerstellerEventHandler;
import eventApi.AddKuchenEventHandler;
import eventApi.DeleteKuchenEventHandler;
import eventApi.ReceiveKuchenListEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
        final ArgumentCaptor<HerstellerImpl> herstellerArgumentCaptor = ArgumentCaptor.forClass(HerstellerImpl.class);

        when(mockWrapper.getGl()).thenReturn(mockGl);

        AddHerstellerEvent event = new AddHerstellerEvent(this, "Paul", true, false);

        handler.add(listener);
        handler.handle(event);
        verify(mockGl).addHersteller(herstellerArgumentCaptor.capture());
        assertEquals("Paul", herstellerArgumentCaptor.getValue().getName());
    }

    @Test
    public void deleteHerstellerListenerTest() throws InterruptedException {
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);
        GlWrapper mockWrapper = mock(GlWrapper.class);
        AddHerstellerEventHandler handler = new AddHerstellerEventHandler();
        AddHerstellerEventListener listener = new AddHerstellerEventListnerImpl(mockWrapper);

        when(mockWrapper.getGl()).thenReturn(mockGl);

        AddHerstellerEvent event = new AddHerstellerEvent(this, "Paul", false, false);

        handler.add(listener);
        handler.handle(event);
        verify(mockGl).löscheHersteller("Paul");
    }
    //TODO glaube der handler ist unnötig
    @Test
    public void showHerstellerListenerTest() {
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
    public void showReceiveHerstellerListenerTest() {
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
