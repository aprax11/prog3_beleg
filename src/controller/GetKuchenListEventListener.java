package controller;

import events.GetKuchenListEvent;

import java.util.EventListener;

public interface GetKuchenListEventListener extends EventListener {
    void onGetKuchenEvent(GetKuchenListEvent event);
}
