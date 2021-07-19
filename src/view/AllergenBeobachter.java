package view;

import automat.Allergen;
import beobachterMusterInterfaces.Beobachter;
import automat.GeschäftslogikImpl;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AllergenBeobachter implements Beobachter { //TODO beobachter über handler
    private Set<Allergen> oldState;
    private GeschäftslogikImpl gl;

    public AllergenBeobachter(GeschäftslogikImpl gl) {
        this.gl = gl;
        this.gl.meldeAn(this);
        this.oldState = gl.getAllergenList(true);
    }
    private void allergenWasAdded(Set<Allergen> added) {
        System.out.println("allergen(e) hinzugefügt "+added.toString());

    }
    private void allergenWasRemoved(Set<Allergen> removed) {
        System.out.println("allergen(e) entfernt "+removed.toString());
    }

    @Override
    public void aktualisiere() {
        Set<Allergen> newState;
        Set<Allergen> oldStateCopy = new HashSet<>();
        oldStateCopy.addAll(this.oldState);
        newState = this.gl.getAllergenList(true);
        Set<Allergen> toRemove = new HashSet<>();

        for(Allergen a : newState) {
            if(oldStateCopy.contains(a)) {
                toRemove.add(a);
            }
        }
        for(Allergen a : toRemove) {
            newState.remove(a);
            oldStateCopy.remove(a);
        }
        toRemove.clear();
        if(oldStateCopy.size() > newState.size()) {
            this.allergenWasRemoved(oldStateCopy);
            toRemove.addAll(oldStateCopy);
            for(Allergen a : toRemove) {
                this.oldState.remove(a);
            }
        }else if(newState.size() > oldStateCopy.size()) {
            this.allergenWasAdded(newState);
            this.oldState.addAll(newState);
        }
    }
}
