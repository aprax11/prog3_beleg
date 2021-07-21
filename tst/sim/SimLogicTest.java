package sim;
import automat.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimLogicTest {
    private GeschäftslogikImpl mockGL;
    private  SimLogic sIM;
    private static final ObsttorteImpl OBSTTORTE = mock(ObsttorteImpl.class);
    private static final KremkuchenImpl KREMKUCHEN = mock(KremkuchenImpl.class);
    private static final ObstkuchenImpl OBSTKUCHEN = mock(ObstkuchenImpl.class);

    @BeforeEach
    public void start() {
        this.mockGL = mock(GeschäftslogikImpl.class);
        this.sIM = new SimLogic(mockGL);
    }

    @Test
    public void addRandomKuchenTest() throws InterruptedException {
        this.sIM.addRandomKuchen();
        verify(this.mockGL).addKuchen(anyString(), anyString(), any(HerstellerImpl.class), anyCollection(), anyInt(), any(Duration.class), anyString(), any(BigDecimal.class));
    }

    @Test
    public void removeRandomKuchenTest() throws InterruptedException {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        Automatenobjekt[] list = {KREMKUCHEN, OBSTKUCHEN, OBSTTORTE};

        when(this.mockGL.listKuchen(null)).thenReturn(list);
        this.sIM.removeRandomKuchen();

        verify(this.mockGL).löscheKuchen(anyInt());

    }
    @Test
    public void updateRandomKuchenTest() {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        Automatenobjekt[] list = {KREMKUCHEN, OBSTKUCHEN, OBSTTORTE};
        when(this.mockGL.listKuchen(null)).thenReturn(list);
        this.sIM.updateRandomKuchen();

        verify(this.mockGL).setInspektionsdatum(anyInt());
    }

    @Test
    public void addRandomSim2KuchenTest() throws InterruptedException {
        this.sIM.einfügenSim2();
        verify(this.mockGL).addKuchen(anyString(), anyString(), any(HerstellerImpl.class), anyCollection(), anyInt(), any(Duration.class), anyString(), any(BigDecimal.class));
    }
    @Test
    public void löscheÄltestenTest() throws InterruptedException {

        Automatenobjekt[] list = {KREMKUCHEN, OBSTKUCHEN, OBSTTORTE};
        when(this.mockGL.listKuchen(null)).thenReturn(list);
        Date d = new Date(2, 1, 1, 1, 1);

        when(KREMKUCHEN.getInspektionsdatum()).thenReturn(new Date());
        when(OBSTKUCHEN.getInspektionsdatum()).thenReturn(new Date());
        when(OBSTTORTE.getInspektionsdatum()).thenReturn(d);
        when(mockGL.getListGröße()).thenReturn(3);
        when(mockGL.getFachnummer()).thenReturn(3);
        when(KREMKUCHEN.getFachnummer()).thenReturn(0);
        when(OBSTKUCHEN.getFachnummer()).thenReturn(1);
        when(OBSTTORTE.getFachnummer()).thenReturn(2);
        this.sIM.löscheÄltesten();

        verify(this.mockGL).löscheKuchen(2);
    }

    @Test
    public void deleteSim3Test() throws InterruptedException {
        Automatenobjekt[] list = {KREMKUCHEN, OBSTKUCHEN, OBSTTORTE};
        when(this.mockGL.listKuchen(null)).thenReturn(list);
        Date d = new Date(2, 1, 1, 1, 1);

        when(KREMKUCHEN.getInspektionsdatum()).thenReturn(new Date());
        when(OBSTKUCHEN.getInspektionsdatum()).thenReturn(d);
        when(OBSTTORTE.getInspektionsdatum()).thenReturn(d);
        when(mockGL.getListGröße()).thenReturn(3);
        when(mockGL.getFachnummer()).thenReturn(3);
        when(KREMKUCHEN.getFachnummer()).thenReturn(0);
        when(OBSTKUCHEN.getFachnummer()).thenReturn(1);
        when(OBSTTORTE.getFachnummer()).thenReturn(2);

        this.sIM.deleteSim3(new Random(3));

        verify(mockGL, times(2)).löscheKuchen(anyInt());
    }
}
