package automat;

import java.util.ArrayList;
import java.util.List;

public class ReceiveKuchenListEventHandler {
    private List<ReceiveKuchenListEventListener> list = new ArrayList<>();

    public void add(ReceiveKuchenListEventListener listener) {
        this.list.add(listener);
    }
    public void remove(ReceiveKuchenListEventListener listener) {
        this.list.remove(listener);
    }
    public void handle(ReceiveKuchenListEvent event) {
        for(ReceiveKuchenListEventListener l : this.list) {
            l.onReceiveKuchenListEvent(event);
        }
    }
}
