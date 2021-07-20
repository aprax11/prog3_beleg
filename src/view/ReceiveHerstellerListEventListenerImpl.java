package view;

import eventApi.ReceiveHerstellerListEvent;
import eventApi.ReceiveHerstellerListEventListener;

public class ReceiveHerstellerListEventListenerImpl implements ReceiveHerstellerListEventListener {
    private ViewClass view;

    public ReceiveHerstellerListEventListenerImpl(ViewClass view) {
        this.view = view;
    }
    @Override
    public void onReceiveHerstellerListEvent(ReceiveHerstellerListEvent event) {
        this.view.printHersteller(event.getHerstellerList());
    }
}
