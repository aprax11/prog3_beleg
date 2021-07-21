package eventApi;

import java.util.ArrayList;

public class ReceiveAllergeneHandler {
    private ArrayList<ReceiveAllergeneListener> list = new ArrayList<>();

    public void add(ReceiveAllergeneListener listener) {
        this.list.add(listener);
    }

    public void remove(ReceiveAllergeneListener listener) {
        this.list.remove(listener);
    }

    public void handle(ReceiveAllergeneEvent event) {
        for(ReceiveAllergeneListener l : this.list) {
            l.onReceiveAllergeneEvent(event);
        }
    }
}
