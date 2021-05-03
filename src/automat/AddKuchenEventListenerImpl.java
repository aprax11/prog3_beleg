package automat;

import automat.gl.Allergen;
import automat.gl.GeschäftslogikImpl;
import automat.gl.Hersteller;
import automat.gl.HerstellerImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class AddKuchenEventListenerImpl implements AddKuchenEventListener{
    private GeschäftslogikImpl gl;

    public AddKuchenEventListenerImpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    @Override
    public void onAddKuchenEvent(AddKuchenEvent event) {
        this.gl.addKuchen(event.getName(), event.getKremsorte(), event.getHersteller(), event.getAllergens(), event.getNährwert(), event.getHaltbarkeit(), event.getObstsorte(), event.getPreis());
    }
}
