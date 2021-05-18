package eventApi;

import eventApi.ReceiveKuchenListEvent;

import java.util.EventListener;

public interface ReceiveKuchenListEventListener extends EventListener {
    void onReceiveKuchenListEvent(ReceiveKuchenListEvent event);
}
