package automat;

import beobachterMusterInterfaces.Beobachter;
import beobachterMusterInterfaces.Subjekt;
import events.ReceiveKuchenListEvent;
import handler.ReceiveKuchenListEventHandler;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GeschäftslogikImpl implements Subjekt {
    private List<Beobachter> beobachterList = new LinkedList<>();
    private Automatenobjekt[] list ;
    private int fachnummer = 0;
    private Map<Automatenobjekt, Integer> fachnummerverwaltung = new HashMap();
    private Map<Hersteller, Integer> herstellerverwaltung = new HashMap();
    private Set<Allergen> allergenList = new HashSet<>();
    private ReceiveKuchenListEventHandler receiveKuchenListEventHandler;
    private int listGröße;


    public GeschäftslogikImpl(int i) {
        if(i > 0) {
            this.listGröße = i;
            this.list = new Automatenobjekt[i];
        }
    }

    public void setReceiveKuchenListEventHandler(ReceiveKuchenListEventHandler receiveKuchenListEventHandler) {
        this.receiveKuchenListEventHandler = receiveKuchenListEventHandler;
    }

    private Hersteller checkHersteller(Hersteller hersteller) {
        for (Map.Entry<Hersteller, Integer> e : this.herstellerverwaltung.entrySet()){
            if(e.getKey().getName().equalsIgnoreCase(hersteller.getName())) {
                return e.getKey();
            }
        }
        return null;
    }

    public boolean addHersteller(Hersteller hersteller) {
        Hersteller h = this.checkHersteller(hersteller);
        if (h != null) {
            return false;
        } else {
            this.herstellerverwaltung.put(hersteller, 0);
            return true;

        }
    }

    public synchronized boolean addKuchen(String name, String kremsorte, Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit, String obstsorte, BigDecimal preis) throws InterruptedException {
        Hersteller h = this.checkHersteller(hersteller);
        if (h != null && this.fachnummer < this.listGröße) {
            Date inspektionsDate = new Date();
            switch (name) {
                case "Kremkuchen":
                    Automatenobjekt kremkuchen = new KremkuchenImpl(kremsorte, hersteller, allergens, nährwert, haltbarkeit, preis, inspektionsDate, this.fachnummer, this);
                    list[this.fachnummer] = kremkuchen;
                    this.fachnummerverwaltung.put(kremkuchen, this.fachnummer);
                    break;

                case "Obstkuchen":
                    Automatenobjekt obstkuchen = new ObstkuchenImpl(hersteller, allergens, nährwert, haltbarkeit, obstsorte, preis, inspektionsDate, this.fachnummer, this);
                    list[this.fachnummer] = obstkuchen;
                    this.fachnummerverwaltung.put(obstkuchen, this.fachnummer);
                    break;

                case "Obsttorte":
                    Automatenobjekt kuchen = new ObsttorteImpl(kremsorte, hersteller, allergens, nährwert, haltbarkeit, obstsorte, preis, inspektionsDate, this.fachnummer, this);
                    list[this.fachnummer] = kuchen;
                    this.fachnummerverwaltung.put(kuchen, this.fachnummer);
                    break;
                default:
                    return false;
            }
            int anzahl = this.herstellerverwaltung.get(h);
            anzahl++;
            this.herstellerverwaltung.put(h, anzahl);
            this.allergenList.addAll(allergens);
            this.fachnummer++;
            this.benachrichtige();
            return true;
        } else {
            return false;
        }

    }

    public synchronized Automatenobjekt[] listKuchen(Class<? extends Automatenobjekt> cl) {
        if(cl == null) {
            Automatenobjekt[] copyArray = new Automatenobjekt[this.fachnummer];
            for (int i = 0; i < this.fachnummer; i++) {
                copyArray[i] = this.list[i];
            }
            return copyArray;
        }
        int count = 0;
        for (int i = 0; i < this.fachnummer; i++) {
            if (this.list[i].getClass() == cl) {
                count++;
            }
        }
        Automatenobjekt[] copyArray = new Automatenobjekt[count];
        int kuchenCount = 0;
        for (int i = 0; i < this.fachnummer; i++) {
            if(this.list[i].getClass() == cl) {
                copyArray[kuchenCount] = this.list[i];
                kuchenCount++;
            }
        }
        return copyArray;
    }

    public void setInspektionsdatum(int position) {
        Automatenobjekt kuchen = this.list[position];
        kuchen.callForInspektionsdatum();
    }
    public Date returnDate() {
        Date inspektionsDate = new Date();
        return inspektionsDate;
    }



    public synchronized void löscheKuchen(int position) throws InterruptedException {
        if (position <= this.fachnummer && this.list[0] != null) {
            this.fachnummer--;
            Automatenobjekt kuchen = this.list[fachnummer];
            Automatenobjekt remKuchen = this.list[position];
            Hersteller h = this.checkHersteller(remKuchen.getHersteller());
            int anzahl = this.herstellerverwaltung.get(h);
            anzahl--;
            this.herstellerverwaltung.put(remKuchen.getHersteller(), anzahl);
            if (position == this.fachnummer) {
                this.list[position] = null;
                this.fachnummerverwaltung.remove(remKuchen);

            } else {
                this.list[position] = this.list[this.fachnummer];
                this.fachnummerverwaltung.put(kuchen, position);
                this.fachnummerverwaltung.remove(remKuchen);
                this.list[this.fachnummer] = null;
                kuchen.callForFachnummer(kuchen);
            }
            this.benachrichtige();
        }

    }

    public int getFachnummerForObject(Object kuchen) {
        int ret = this.fachnummerverwaltung.get(kuchen);
        return ret;
    }
    public Map<Hersteller, Integer> getHerstellerList() {
        Map<Hersteller, Integer> copy = new HashMap();
        copy.putAll(this.herstellerverwaltung);
        return copy;
    }
    //TODO: das löschen verbessern
    public synchronized void löscheHersteller(Hersteller hersteller) throws InterruptedException {
        for (int i = 0; i < this.fachnummer; i++) {
            Automatenobjekt ao = this.list[i];
            if(ao.getHersteller().getName().equalsIgnoreCase(hersteller.getName())) {
                this.löscheKuchen(ao.getFachnummer());
            }
        }
        this.herstellerverwaltung.remove(hersteller);
    }

    public Set<Allergen> getAllergenList(boolean b) {
        HashSet<Allergen> copy;
        this.allergenList.clear();
        for (int i = 0; i < this.fachnummer; i++) {
            Automatenobjekt ao = this.list[i];
            this.allergenList.addAll(ao.getAllergene());
        }
        if (b) {
            copy = new HashSet<>();
            copy.addAll(this.allergenList);
            return copy;
        }else {
            EnumSet<Allergen> allAllergens = EnumSet.allOf(Allergen.class);
            allAllergens.removeIf(a -> this.allergenList.contains(a));
            return allAllergens;
        }
    }

    @Override
    public void meldeAn(Beobachter beobachter) {
        this.beobachterList.add(beobachter);
    }

    @Override
    public void meldeAb(Beobachter beobachter) {
        this.beobachterList.remove(beobachter);
    }

    @Override
    public void benachrichtige() {
        for(Beobachter b : this.beobachterList) {
            b.aktualisiere();
        }
    }
    public int getFachnummer() {
        int ret = this.fachnummer;
        return ret;
    }
    public int getListGröße() {
        int ret = this.listGröße;
        return ret;
    }
}
