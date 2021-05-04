package view;

import automat.Allergen;
import beobachterMusterInterfaces.Beobachter;
import automat.GeschäftslogikImpl;


import java.util.HashSet;
import java.util.Set;

public class AllergenBeobachter implements Beobachter {
    private Set<Allergen> oldState = new HashSet<>();
    private GeschäftslogikImpl gl;
    private ViewClass view;

    public AllergenBeobachter(GeschäftslogikImpl gl, ViewClass view) {
        this.view = view;
        this.gl = gl;
        this.gl.meldeAn(this);
        this.oldState = gl.getAllergenList(true);
    }
    private void allergenWasAdded(Set<Allergen> added) {
        String benachrichtigung = "allergen hinzugefügt "+added.toString();
        this.view.printBeobachterBenachrichtigung(benachrichtigung);
    }
    private void allergenWasRemoved(Set<Allergen> removed) {
        String benachrichtigung = "allergen entfernt "+removed.toString();
        this.view.printBeobachterBenachrichtigung(benachrichtigung);
    }

    @Override
    public void aktualisiere() {
        Set<Allergen> newState = new HashSet<>();
        Set<Allergen> oldStateCopy = new HashSet<>();
        oldStateCopy.addAll(this.oldState);
        newState = this.gl.getAllergenList(true);

        for(Allergen a : newState) {
            if(oldStateCopy.contains(a)) {
                oldStateCopy.remove(a);
                newState.remove(a);
            }
        }
        if(oldStateCopy.size() > newState.size()) {
            this.allergenWasRemoved(oldStateCopy);
            for(Allergen a : oldStateCopy) {
                this.oldState.remove(a);
            }
        }else if(newState.size() > oldStateCopy.size()) {
            this.allergenWasAdded(newState);
            for(Allergen a : newState) {
                this.oldState.add(a);
            }
        }
    }
}
