package controller;

import events.DeleteKuchenEvent;

import java.util.EventListener;

public interface DeleteKuchenEventListener extends EventListener {
    void onDeleteKuchenEvent(DeleteKuchenEvent event);
}
