package eventApi;


import automat.KuchenTypen;

import java.util.EventObject;

public class GetKuchenListEvent extends EventObject {
    KuchenTypen cl;

    public GetKuchenListEvent(Object source, KuchenTypen cl) {
        super(source);
        this.cl = cl;
    }

    public KuchenTypen getCl() {
        return cl;
    }
}
