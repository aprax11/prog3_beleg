package eventApi;

import java.util.ArrayList;

public class ReceiveHerstellerListEventHandler {
    private ArrayList<ReceiveHerstellerListEventListener> list = new ArrayList<>();

    public void add(ReceiveHerstellerListEventListener listener) {
        this.list.add(listener);
    }
    public void remove(ReceiveHerstellerListEventListener listener) {
        this.list.remove(listener);
    }

    public void handle(ReceiveHerstellerListEvent event) {
        for(ReceiveHerstellerListEventListener l : this.list) {
            l.onReceiveHerstellerListEvent(event);
        }
    }
}
