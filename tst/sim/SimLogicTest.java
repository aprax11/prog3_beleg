package sim;
import automat.*;
import handler.ReceiveKuchenListEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimLogicTest {
    private static final GeschäftslogikImpl MOCKGL = mock(GeschäftslogikImpl.class);
    private static final SimLogic SIM = new SimLogic(MOCKGL);
    private static final ObsttorteImpl OBSTTORTE = mock(ObsttorteImpl.class);
    private static final KremkuchenImpl KREMKUCHEN = mock(KremkuchenImpl.class);
    private static final ObstkuchenImpl OBSTKUCHEN = mock(ObstkuchenImpl.class);


    @Captor
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

    @Test
    public void addRandomKuchenTest() throws InterruptedException {
        SIM.addRandomKuchen();
        verify(MOCKGL).addKuchen(anyString(), anyString(), any(HerstellerImpl.class), anyCollection(), anyInt(), any(Duration.class), anyString(), any(BigDecimal.class));
    }

    @Test
    public void removeRandomKuchenTest() throws InterruptedException {
        Automatenobjekt[] list = {KREMKUCHEN, OBSTKUCHEN, OBSTTORTE};

        when(MOCKGL.listKuchen(null)).thenReturn(list);
        SIM.removeRandomKuchen();

        verify(MOCKGL).löscheKuchen(captor.capture());
        int val = captor.getValue();
        switch(val) {
            case 0:
            case 1:
            case 2:
                assertTrue(true);
                break;
            default:
                fail();
        }
    }
}
