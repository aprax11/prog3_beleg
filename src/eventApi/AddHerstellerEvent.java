package eventApi;



import automat.Hersteller;

import java.util.EventObject;

public class AddHerstellerEvent extends EventObject {
    private Hersteller hersteller;
    private boolean b;
    private boolean show;

    public AddHerstellerEvent(Object source, Hersteller hersteller, boolean b, Boolean show) {
        super(source);
        this.hersteller = hersteller;
        this.b = b;
        this.show = show;
    }

    public Hersteller getHerstellerName() {
        return this.hersteller;
    }
    public boolean getBool() {
        return this.b;
    }
    public boolean getShow() { return this.show; }
}
