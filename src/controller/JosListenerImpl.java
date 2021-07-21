package controller;

import automat.GeschäftslogikImpl;
import persistence.Jos;
import eventApi.JosEvent;
import eventApi.JosEventListener;

public class JosListenerImpl implements JosEventListener {
    private GlWrapper gl;

    public JosListenerImpl(GlWrapper gl) {
        this.gl = gl;
    }

    @Override
    public void onJosEvent(JosEvent event) {
        if(event.getSafe()) {
            Jos.serialize("SafeGl", this.gl.getGl());
        }else if(!event.getSafe()) {
            GeschäftslogikImpl gl = Jos.deserialize("SafeGl");
            this.gl.setGl(gl);
        }
    }
}
