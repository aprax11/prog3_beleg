package controller;

import automat.Automatenobjekt;

import java.util.EventObject;

public class ReceiveKuchenListEvent extends EventObject {
    private Automatenobjekt[] list;

    public ReceiveKuchenListEvent(Object source, Automatenobjekt[] list) {
        super(source);
        this.list = list;
    }
    public Automatenobjekt[] getList() {
        return this.list;
    }
}
