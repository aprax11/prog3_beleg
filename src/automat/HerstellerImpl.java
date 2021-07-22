package automat;

import java.io.Serializable;

public class HerstellerImpl implements Hersteller, Serializable {
    private int kuchenAnzahl;
    private final String hersteller;

    public HerstellerImpl(String name) {
        this.hersteller = name;
    }

    @Override
    public String getName() {
        return this.hersteller;
    }
}

