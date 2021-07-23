package eventApi;


import automat.GanzerKuchen;

import java.util.EventObject;

public class ReceiveKuchenListEvent extends EventObject {
    private GanzerKuchen[] list;

    public ReceiveKuchenListEvent(Object source, GanzerKuchen[] list) {
        super(source);
        this.list = list;
    }
    public GanzerKuchen[] getList() {
        return this.list;
    }
}
