package eventApi;



import automat.Hersteller;

import java.util.EventObject;

public class AddHerstellerEvent extends EventObject {
    private String hersteller;
    private boolean b;
    private boolean show;

    public AddHerstellerEvent(Object source, String hersteller, boolean b, Boolean show) {
        super(source);
        this.hersteller = hersteller;
        this.b = b;
        this.show = show;
    }

    public String getHerstellerName() {
        return this.hersteller;
    }
    public boolean getBool() {
        return this.b;
    }
    public boolean getShow() { return this.show; }
}
