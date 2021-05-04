package view;

import automat.Allergen;
import beobachterMusterInterfaces.Beobachter;
import automat.GeschäftslogikImpl;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AllergenBeobachter implements Beobachter {
    private Set<Allergen> oldState;
    private GeschäftslogikImpl gl;
    private ViewClass view;

    public AllergenBeobachter(GeschäftslogikImpl gl, ViewClass view) {
        this.view = view;
        this.gl = gl;
        this.gl.meldeAn(this);
        this.oldState = gl.getAllergenList(true);
    }
    private void allergenWasAdded(Set<Allergen> added) {
        String benachrichtigung = "allergen(e) hinzugefügt "+added.toString();
        this.view.printBeobachterBenachrichtigung(benachrichtigung);
    }
    private void allergenWasRemoved(Set<Allergen> removed) {
        String benachrichtigung = "allergen(e) entfernt "+removed.toString();
        this.view.printBeobachterBenachrichtigung(benachrichtigung);
    }

    @Override
    public void aktualisiere() {
        Set<Allergen> newState;
        Set<Allergen> oldStateCopy = new HashSet<>();
        oldStateCopy.addAll(this.oldState);
        newState = this.gl.getAllergenList(true);

        for(Iterator<Allergen> iterator = newState.iterator(); iterator.hasNext();) {
            Allergen a = iterator.next();
            if(oldStateCopy.contains(a)) {
                oldStateCopy.remove(a);
                iterator.remove();
            }
        }
        if(oldStateCopy.size() > newState.size()) {
            this.allergenWasRemoved(oldStateCopy);
            for(Iterator<Allergen> iterator = this.oldState.iterator(); iterator.hasNext();) {
                Allergen a = iterator.next();
                iterator.remove();
            }
        }else if(newState.size() > oldStateCopy.size()) {
            this.allergenWasAdded(newState);
            this.oldState.addAll(newState);
        }
    }
}
