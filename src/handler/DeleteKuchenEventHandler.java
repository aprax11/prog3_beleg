package handler;

import controller.DeleteKuchenEventListener;
import events.DeleteKuchenEvent;

import java.util.ArrayList;
import java.util.List;

public class DeleteKuchenEventHandler {
    private List<DeleteKuchenEventListener> list = new ArrayList<>();

    public void add(DeleteKuchenEventListener listener) {
        this.list.add(listener);
    }
    public void delete(DeleteKuchenEventListener listener) {
        this.list.remove(listener);
    }
    public void handle(DeleteKuchenEvent event) {
        for(DeleteKuchenEventListener l : this.list) {
            l.onDeleteKuchenEvent(event);
        }
    }
}
