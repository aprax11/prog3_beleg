package controller;

import java.util.ArrayList;
import java.util.List;

public class AddHerstellerEventHandler {
    private List<AddHerstellerEventListener> listener = new ArrayList<>();

    public void add(AddHerstellerEventListener listener) {
        this.listener.add(listener);
    }
    public void remove(AddHerstellerEventListener listener) {
        this.listener.remove(listener);
    }
    public void handle(AddHerstellerEvent event) {
        for(AddHerstellerEventListener l : this.listener) {
            l.onAddHerstellerEvent(event);
        }
    }
}
