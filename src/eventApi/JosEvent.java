package eventApi;

import java.util.EventObject;

public class JosEvent extends EventObject {
    private Boolean safe;

    public JosEvent(Object source, Boolean safe) {
        super(source);
        this.safe = safe;
    }
    public Boolean getSafe() {
        return this.safe;
    }
}
