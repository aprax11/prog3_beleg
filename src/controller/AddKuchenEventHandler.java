package controller;

import java.util.ArrayList;
import java.util.List;

public class AddKuchenEventHandler {
    private List<AddKuchenEventListener> list = new ArrayList<>();

    public void add(AddKuchenEventListener listener) {
        this.list.add(listener);
    }
    public void remove(AddKuchenEventListener listener) {
        this.list.remove(listener);
    }
    public void handle(AddKuchenEvent event) {
        for(AddKuchenEventListener l : this.list) {
            l.onAddKuchenEvent(event);
        }
    }
}
