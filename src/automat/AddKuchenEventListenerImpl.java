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
        Hersteller hersteller = new HerstellerImpl(event.getHersteller());
        Collection<Allergen> allergens = new HashSet<>();
        for(String s : event.getAllergens()) {
            switch (s) {
                case "Gluten":
                    allergens.add(Allergen.Gluten);
                    continue;
                case "Erdnuss":
                    allergens.add(Allergen.Erdnuss);
                    continue;
                case "Haselnuss":
                    allergens.add(Allergen.Haselnuss);
                    continue;
                case "Sesamsamen":
                    allergens.add(Allergen.Sesamsamen);
                    continue;

                default:
                    throw new IllegalStateException("Unexpected value: " + s);
            }
        }
        this.gl.addKuchen(event.getName(), event.getKremsorte(), hersteller, allergens, event.getNährwert(), event.getHaltbarkeit(), event.getObstsorte(), event.getPreis());
    }
}
