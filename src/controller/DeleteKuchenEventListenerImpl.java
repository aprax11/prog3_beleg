package controller;

import automat.GeschäftslogikImpl;
import eventApi.DeleteKuchenEvent;
import eventApi.DeleteKuchenEventListener;

public class DeleteKuchenEventListenerImpl implements DeleteKuchenEventListener {

    private GlWrapper gl;

    public DeleteKuchenEventListenerImpl(GlWrapper gl) {
        this.gl = gl;
    }

    @Override
    public void onDeleteKuchenEvent(DeleteKuchenEvent event) {
        if (!event.getUpdate()) {
            this.gl.getGl().löscheKuchen(event.getPosition());
        }else if(event.getUpdate()) {
            this.gl.getGl().setInspektionsdatum(event.getPosition());
        }
    }
}
