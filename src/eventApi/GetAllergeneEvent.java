package eventApi;

import java.util.EventObject;

public class GetAllergeneEvent extends EventObject {
    private boolean vorhanden;

    public GetAllergeneEvent(Object source, boolean vorhanden) {
        super(source);
        this.vorhanden = vorhanden;
    }

    public boolean getVorhanden() {
        return this.vorhanden;
    }
}
