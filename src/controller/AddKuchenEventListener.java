package controller;

import events.AddKuchenEvent;

import java.util.EventListener;

public interface AddKuchenEventListener extends EventListener {
    void onAddKuchenEvent(AddKuchenEvent event);
}
