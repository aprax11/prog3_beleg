package sim;

import automat.*;
import automat.Container;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class SimLogic {
    private GeschäftslogikImpl gl;
    private GanzerKuchen[] list;
    private final Hersteller[] herstellers = {new HerstellerImpl("paul"), new HerstellerImpl("peter"), new HerstellerImpl("willi")};
    private final String[] kuchennamen = {"Obsttorte", "Kremkuchen", "Obstkuchen"};
    private final String[]kresorten = {"butter", "sahne", "schoko"};
    private final String[]obstsorten = {"erdbeere", "apfel", "birne"};
    private final EnumSet<Allergen> allergens = EnumSet.allOf(Allergen.class);

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
        int kuchenTyp = new Random().nextInt(3);

        switch(kuchenTyp) {
            case 0:
                Container boden = new Container(h, allergens, nährwert, duration, preis, "Obstkuchen", KuchenTypen.Obstkuchen);
                this.gl.addKuchen(new ArrayList<>(), boden);
                break;
            case 1:
                Container boden2 = new Container(h, allergens, nährwert, duration, preis, "Obsttorte", KuchenTypen.Obsttorte);
                this.gl.addKuchen(new ArrayList<>(), boden2);
                break;
            case 2:
                Container boden3 = new Container(h, allergens, nährwert, duration, preis, "Kremkuchen", KuchenTypen.Kremkuchen);
                this.gl.addKuchen(new ArrayList<>(), boden3);
                break;
        }


        System.out.println("Kuchen wurde eingefügt"); //TODO: + thread name
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
        if (!this.isFull()) throw new IllegalStateException();
        GanzerKuchen obj = this.getOldest(this.gl.listKuchen(null));
        this.gl.löscheKuchen(obj.getFachnummer());
        System.out.println("Kuchen gelöscht");
        notifyAll();
    }
    public synchronized void einfügenSim2() throws InterruptedException {
        while(this.isFull()) wait();
        if (this.isFull()) throw new IllegalStateException();
        this.addRandomKuchen();
        notifyAll();
    }
    public synchronized void deleteSim3(Random rand) throws InterruptedException {
        while (!this.isFull()) wait();
        if (!this.isFull()) throw new IllegalStateException();
        List<GanzerKuchen> list = this.getOldestList(this.gl.listKuchen(null));
        int anzahl = rand.nextInt(this.gl.getFachnummer());

        for (int i = 0; i < anzahl; i++) {
            this.gl.löscheKuchen(i);
            System.out.println("kuchen gelöscht");
        }
        notifyAll();
    }
    public synchronized GanzerKuchen getOldest(GanzerKuchen[] list) {
        GanzerKuchen oldest = list[0];
        for(GanzerKuchen a : list) {
            if(a.getInspektionsdatum().before(oldest.getInspektionsdatum())) {
                oldest = a;
            }
        }
        return oldest;
    }
    private synchronized List<GanzerKuchen> getOldestList(GanzerKuchen[] got) {
        Date date = got[0].getInspektionsdatum();
        List<GanzerKuchen> list = new ArrayList<>();
        for(GanzerKuchen a : got) {
            if(a.getInspektionsdatum().compareTo(date) < 0) {
                date = a.getInspektionsdatum();
            }
        }
        for(GanzerKuchen a : got) {
            if(a.getInspektionsdatum().compareTo(date) == 0) {
                list.add(a);
            }
        }
       return list;
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

