package controller;

import automat.Automatenobjekt;
import automat.GeschäftslogikImpl;
import controller.GlWrapper;
import eventApi.GetKuchenListEvent;
import eventApi.GetKuchenListEventListener;
import eventApi.ReceiveKuchenListEvent;
import eventApi.ReceiveKuchenListEventHandler;

public class GetKuchenEventListenerImpl implements GetKuchenListEventListener {
    private GlWrapper gl;
    private ReceiveKuchenListEventHandler receiveKuchenListEventHandler;

    public  GetKuchenEventListenerImpl(GlWrapper gl) {
        this.gl = gl;
    }
    @Override
    public void onGetKuchenEvent(GetKuchenListEvent event) {
        Automatenobjekt[] list = this.gl.getGl().listKuchen(event.getCl());
        ReceiveKuchenListEvent event2 = new ReceiveKuchenListEvent(this, list);
        this.receiveKuchenListEventHandler.handle(event2);
    }

    public void setReceiveKuchenListEventHandler(ReceiveKuchenListEventHandler receiveKuchenListEventHandler) {
        this.receiveKuchenListEventHandler = receiveKuchenListEventHandler;
    }
}
