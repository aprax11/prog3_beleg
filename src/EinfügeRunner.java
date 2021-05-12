import automat.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class EinfügeRunner implements Runnable{
    private List<Automatenobjekt> list = new ArrayList<>();
    private Hersteller[] herstellers = {new HerstellerImpl("paul"), new HerstellerImpl("peter"), new HerstellerImpl("willi")};
    private String[] kuchennamen = {"Obsttorte", "Kremkuchen", "Obstkuchen"};
    private String[]kresorten = {"butter", "sahne", "schoko"};
    private String[]obstsorten = {"erdbeere", "apfel", "birne"};
    private EnumSet<Allergen> allergens = EnumSet.allOf(Allergen.class);
    private GeschäftslogikImpl gl;

    public EinfügeRunner(GeschäftslogikImpl gl) {
        this.gl = gl;
    }
    @Override
    public void run() {
        for(Hersteller s : this.herstellers) {
            this.gl.addHersteller(s);
        }
        List<Allergen> coll = new ArrayList<>();
        coll.addAll(this.allergens);

        while(true) {
            String name = this.kuchennamen[(int) (Math.random()*this.kuchennamen.length)];
            String krem = this.kresorten[(int) (Math.random()*this.kresorten.length)];
            Hersteller h = this.herstellers[(int) (Math.random()*this.herstellers.length)];
            int nährwert = (int) (Math.random()*400)+100;
            Duration duration = Duration.ofDays((int) (Math.random()*30)+ 1);
            String obst = this.obstsorten[(int) (Math.random()*this.obstsorten.length)];
            BigDecimal preis = new BigDecimal((int)(Math.random()*13)+10);
            int pos = (int)(Math.random()*coll.size());
            try {
                this.gl.addKuchen(name, krem, h, Collections.singleton(coll.get(pos)), nährwert, duration, obst, preis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
