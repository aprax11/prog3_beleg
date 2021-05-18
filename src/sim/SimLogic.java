package sim;

import automat.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class SimLogic {
    private GeschäftslogikImpl gl;
    private Automatenobjekt[] list;
    private Hersteller[] herstellers = {new HerstellerImpl("paul"), new HerstellerImpl("peter"), new HerstellerImpl("willi")};
    private String[] kuchennamen = {"Obsttorte", "Kremkuchen", "Obstkuchen"};
    private String[]kresorten = {"butter", "sahne", "schoko"};
    private String[]obstsorten = {"erdbeere", "apfel", "birne"};
    private EnumSet<Allergen> allergens = EnumSet.allOf(Allergen.class);

    public SimLogic(GeschäftslogikImpl gl) {
        this.gl = gl;
        for(Hersteller s : this.herstellers) {
            this.gl.addHersteller(s);
        }
    }

    public void addRandomKuchen() throws InterruptedException {
        List<Allergen> coll = new ArrayList<>();
        coll.addAll(this.allergens);
        String name = this.kuchennamen[(int) (Math.random()*this.kuchennamen.length)];
        String krem = this.kresorten[(int) (Math.random()*this.kresorten.length)];
        Hersteller h = this.herstellers[(int) (Math.random()*this.herstellers.length)];
        int nährwert = (int) (Math.random()*400)+100;
        Duration duration = Duration.ofDays((int) (Math.random()*30)+ 1);
        String obst = this.obstsorten[(int) (Math.random()*this.obstsorten.length)];
        BigDecimal preis = new BigDecimal((int)(Math.random()*13)+10);
        int pos = (int)(Math.random()*coll.size());

        this.gl.addKuchen(name, krem, h, Collections.singleton(coll.get(pos)), nährwert, duration, obst, preis);
        System.out.println("Kuchen wurde eingefügt");
    }
    public void removeRandomKuchen() throws InterruptedException {
        this.list = this.gl.listKuchen(null);
        int pos = (int) (Math.random() * this.list.length);
        this.gl.löscheKuchen(pos);
        System.out.println("Kuchen wurde gelöscht");
    }

    public synchronized void updateRandomKuchen() {
        this.list = this.gl.listKuchen(null);
        this.gl.setInspektionsdatum((int)(Math.random()*this.list.length));
        System.out.println("Inspektionsdatum wurde geändert");
    }

    public synchronized void löscheÄltesten() throws InterruptedException {
        while(!this.isFull()) wait();
        Automatenobjekt obj = this.getOldest(this.gl.listKuchen(null));
        this.gl.löscheKuchen(obj.getFachnummer());
        System.out.println("Kuchen gelöscht");
        notifyAll();
    }
    public synchronized void einfügenSim2() throws InterruptedException {
        while(this.isFull()) wait();
        this.addRandomKuchen();
        notifyAll();
    }

    private Automatenobjekt getOldest(Automatenobjekt[] list) {
        Automatenobjekt oldest = list[0];
        for(Automatenobjekt a : list) {
            if(a.getInspektionsdatum().before(oldest.getInspektionsdatum())) {
                oldest = a;
            }
        }
        return oldest;
    }
    private boolean isFull() {
        if(this.gl.getFachnummer() == this.gl.getListGröße()) {
            switch(this.gl.getListGröße()) {
                case 0:
                    return false;
                default:
                    return true;
            }
        }else {
            return false;
        }
    }
}

