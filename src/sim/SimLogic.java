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

    public synchronized void addRandomKuchen() {
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
    public synchronized void removeRandomKuchen(){
        this.list = this.gl.listKuchen(null);
        if(this.list.length != 0) {
            int pos = (int) ((Math.random() * this.list.length));
            this.gl.löscheKuchen(this.list[pos].getFachnummer());
            System.out.println("Kuchen wurde gelöscht");
        }
    }

    public synchronized void updateRandomKuchen() {
        this.list = this.gl.listKuchen(null);
        if(this.list.length != 0) {
            this.gl.setInspektionsdatum(this.list[(int) (Math.random() * this.list.length)].getFachnummer());
            System.out.println("Inspektionsdatum wurde geändert");
        }
    }

    public synchronized void löscheÄltesten() {
        try {
            while (!this.isFull()) {
                wait();
            }
            if (!this.isFull()) throw new IllegalStateException();
            if(this.getOldest(this.gl.listKuchen(null)) != null) {
                GanzerKuchen obj = this.getOldest(this.gl.listKuchen(null));
                this.gl.löscheKuchen(obj.getFachnummer());
                System.out.println("Kuchen gelöscht");
            }
            notifyAll();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void einfügenSim2() {
        try {
            while(this.isFull()) { wait(); }
            if (this.isFull()) throw new IllegalStateException();
            this.addRandomKuchen();
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void deleteSim3(Random rand){
        try {
            while (!this.isFull()) {
                wait();
            }
            if (!this.isFull()) throw new IllegalStateException();
            if(this.getOldestList(this.gl.listKuchen(null)) != null) {
                List<GanzerKuchen> list = this.getOldestList(this.gl.listKuchen(null));
                int anzahl = rand.nextInt(list.size() + 1);

                for (int i = 0; i < anzahl; i++) {
                    this.gl.löscheKuchen(list.get(i).getFachnummer());
                    System.out.println("kuchen gelöscht");
                }
            }
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized GanzerKuchen getOldest(GanzerKuchen[] list) {
        if(list.length != 0) {
            GanzerKuchen oldest = list[0];
            for (GanzerKuchen a : list) {
                if (a.getInspektionsdatum().before(oldest.getInspektionsdatum())) {
                    oldest = a;
                }
            }
            return oldest;
        }
        return null;
    }
    private synchronized List<GanzerKuchen> getOldestList(GanzerKuchen[] got) {
        if(got.length != 0) {
            Date date = got[0].getInspektionsdatum();
            List<GanzerKuchen> list = new ArrayList<>();
            for (GanzerKuchen a : got) {
                if (a.getInspektionsdatum().compareTo(date) < 0) {
                    date = a.getInspektionsdatum();
                }
            }
            for (GanzerKuchen a : got) {
                if (a.getInspektionsdatum().compareTo(date) == 0) {
                    list.add(a);
                }
            }
            return list;
        }
        return null;
    }
    private boolean isFull() {
        return this.gl.isFull();
    }
}

