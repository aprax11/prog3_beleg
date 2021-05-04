package automat;

public class HerstellerImpl implements Hersteller{
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
