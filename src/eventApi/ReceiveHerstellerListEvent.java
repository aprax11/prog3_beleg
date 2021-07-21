package eventApi;

import automat.Hersteller;

import java.util.EventObject;
import java.util.HashMap;

public class ReceiveHerstellerListEvent extends EventObject {
    private HashMap <Hersteller, Integer> herstellerList;

    public ReceiveHerstellerListEvent(Object source, HashMap <Hersteller, Integer> herstellerList) {
        super(source);
        this.herstellerList = herstellerList;
    }

    public HashMap<Hersteller ,Integer> getHerstellerList() {
        return this.herstellerList;
    }
}
