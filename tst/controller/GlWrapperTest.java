package controller;

import automat.GeschäftslogikImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlWrapperTest {
    @Test
    public void wrapperGetterTest() {
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);
        GlWrapper wrapper = new GlWrapper(mockGl);

        assertEquals(mockGl, wrapper.getGl());
    }
    @Test
    public void wrapperSetterTest() {
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);
        GeschäftslogikImpl mockGl2 = mock(GeschäftslogikImpl.class);
        GlWrapper wrapper = new GlWrapper(mockGl);
        wrapper.setGl(mockGl2);

        assertEquals(mockGl2, wrapper.getGl());
    }

}