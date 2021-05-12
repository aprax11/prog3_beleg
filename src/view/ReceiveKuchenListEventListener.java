package view;

import events.ReceiveKuchenListEvent;

import java.util.EventListener;

public interface ReceiveKuchenListEventListener extends EventListener {
    void onReceiveKuchenListEvent(ReceiveKuchenListEvent event);
}
