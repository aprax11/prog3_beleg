package automat;

import automat.gl.Allergen;
import automat.gl.GeschäftslogikImpl;


import java.util.HashSet;
import java.util.Set;

public class AllergenBeobachter implements Beobachter {
    private Set<Allergen> oldState = new HashSet<>();
    private GeschäftslogikImpl gl;

    public AllergenBeobachter(GeschäftslogikImpl gl) {
        this.gl = gl;
        this.gl.meldeAn(this);
        this.oldState = gl.getAllergenList(true);
    }
    private void allergenWasAdded(Set<Allergen> added) {
        System.out.println("allergen added "+added);
    }
    private void allergenWasRemoved(Set<Allergen> removed) {
        System.out.println("allergen removed "+removed);
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
