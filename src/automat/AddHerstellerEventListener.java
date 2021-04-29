package automat;

import automat.AddHerstellerEvent;

import java.util.EventListener;

public interface AddHerstellerEventListener extends EventListener {
    void onAddHerstellerEvent(AddHerstellerEvent event);
}
