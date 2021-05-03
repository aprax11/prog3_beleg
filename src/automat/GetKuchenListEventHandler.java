package automat;

import java.util.ArrayList;
import java.util.List;

public class GetKuchenListEventHandler {
    private List<GetKuchenListEventListener> list = new ArrayList<>();

    public void add(GetKuchenListEventListener listener) {
        this.list.add(listener);
    }

    public void remove(GetKuchenListEventListener listener) {
        this.list.remove(listener);
    }

    public void handle(GetKuchenListEvent event) {
        for(GetKuchenListEventListener l : this.list) {
            l.onGetKuchenEvent(event);
        }
    }
}
