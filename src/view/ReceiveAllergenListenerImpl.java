package view;

import eventApi.ReceiveAllergeneEvent;
import eventApi.ReceiveAllergeneListener;

public class ReceiveAllergenListenerImpl implements ReceiveAllergeneListener {
    private ViewClass view;


    public ReceiveAllergenListenerImpl(ViewClass view) {
        this.view = view;
    }
    @Override
    public void onReceiveAllergeneEvent(ReceiveAllergeneEvent event) {
        this.view.printAllergene(event.getList());
    }
}
