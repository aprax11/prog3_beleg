package controller;

import java.util.EventListener;

public interface AddKuchenEventListener extends EventListener {
    void onAddKuchenEvent(AddKuchenEvent event);
}
