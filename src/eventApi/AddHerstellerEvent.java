package eventApi;



import automat.Hersteller;

import java.util.EventObject;

public class AddHerstellerEvent extends EventObject {
    private Hersteller hersteller;
    private boolean b;

    public AddHerstellerEvent(Object source, Hersteller hersteller, boolean b) {
        super(source);
        this.hersteller = hersteller;
        this.b = b;
    }

    public Hersteller getHerstellerName() {
        return this.hersteller;
    }
    public boolean getBool() {
        return this.b;
    }
}
