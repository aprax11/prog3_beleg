package eventApi;

import automat.Allergen;

import java.util.EventObject;
import java.util.Set;

public class ReceiveAllergeneEvent extends EventObject {
    private Set<Allergen> list;

    public ReceiveAllergeneEvent(Object source, Set<Allergen> list) {
        super(source);
        this.list = list;
    }
    public Set<Allergen> getList() {
        return this.list;
    }
}
