package eventApi;

import java.util.ArrayList;

public class JosEventHandler {
    private ArrayList<JosEventListener> list = new ArrayList<>();

    public void add(JosEventListener listener) {
        this.list.add(listener);
    }

    public void remove(JosEventListener listener) {
        this.list.remove(listener);
    }

    public void handle(JosEvent event) {
        for(JosEventListener l : this.list) {
            l.onJosEvent(event);
        }
    }
}
