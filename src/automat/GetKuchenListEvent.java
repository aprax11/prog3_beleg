package automat;

import automat.gl.Automatenobjekt;

import java.util.EventObject;

public class GetKuchenListEvent extends EventObject {
    private Class<? extends  Automatenobjekt> cl;

    public GetKuchenListEvent(Object source, Class<? extends Automatenobjekt> cl) {
        super(source);
        this.cl = cl;
    }

    public Class<? extends Automatenobjekt> getCl() {
        return cl;
    }
}
