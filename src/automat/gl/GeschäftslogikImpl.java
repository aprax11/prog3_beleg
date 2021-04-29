package automat.gl;

import automat.Beobachter;
import automat.Subjekt;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class GeschäftslogikImpl implements Subjekt {
    private List<Beobachter> beobachterList = new LinkedList<>();
    private int listGröße;
    private Automatenobjekt[] list ;
    private int fachnummer = 0;
    private Map<Automatenobjekt, Integer> fachnummerverwaltung = new HashMap();
    private Map<Hersteller, Integer> herstellerverwaltung = new HashMap();
    private Set<Allergen> allergenList = new HashSet<>();

    public GeschäftslogikImpl(int i) {
        this.listGröße = i;
        this.list= new Automatenobjekt[i];
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


    public boolean addKuchen(String name, String kremsorte, Hersteller hersteller, Collection<Allergen> allergens, int nährwert, Duration haltbarkeit, String obstsorte, BigDecimal preis) {
        Hersteller h = this.checkHersteller(hersteller);
        if (h != null) {
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
                default: return false;
            }
            int anzahl = this.herstellerverwaltung.get(h);
            anzahl++;
            this.herstellerverwaltung.put(h, anzahl);
            for(Allergen a : allergens) {
                if(!this.allergenList.contains(a)) {
                    this.allergenList.add(a);
                }
            }
            this.fachnummer++;
            this.benachrichtige();
            return true;
        }else{
            return false;
        }
    }

    public Automatenobjekt[] listKuchen(Class<? extends Automatenobjekt> cl) {
        if(cl == null) {
            Automatenobjekt[] copyArray = new Automatenobjekt[this.fachnummer];
            for (int i = 0; i < this.fachnummer; i++) {
                copyArray[i] = this.list[i];
            }
            return copyArray;
        }
        int count = 0;
        for (int i = 0; i < this.listGröße; i++) {
            if (this.list[i].getClass() == cl) {
                count++;
            }
        }
        Automatenobjekt[] copyArray = new Automatenobjekt[count];
        for (int i = 0; i < this.listGröße; i++) {
            if(this.list[i].getClass() == cl) {
                copyArray[i] = this.list[i];
            }
        }
        return copyArray;

    }

    public void setInspektionsdatum(int position) {
        Automatenobjekt kuchen = this.list[position];
        kuchen.callForInspektionsdatum();
    }
    public Date returnDate() {
        Date inspektionsdate = new Date();
        return inspektionsdate;
    }



    public void löscheKuchen(int position) {
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
        this.updateAllergens();
        this.benachrichtige();
    }

    public int getFachnummerForObject(Object kuchen) {
        int ret = this.fachnummerverwaltung.get(kuchen);
        return ret;
    }
    public Map getHerstellerList() {
        Map<Hersteller, Integer> copy = new HashMap();
        copy.putAll(this.herstellerverwaltung);
        return copy;
    }
    public void löscheHersteller(Hersteller hersteller) {
        for (int i = 0; i < this.fachnummer; i++) {
            Automatenobjekt ao = this.list[i];
            if(ao.getHersteller().getName().equalsIgnoreCase(hersteller.getName())) {
                this.löscheKuchen(ao.getFachnummer());
            }
        }
        this.herstellerverwaltung.remove(hersteller);
    }
    private void updateAllergens() {
        this.allergenList.clear();
        for (int i = 0; i < this.fachnummer-1; i++) {
            Automatenobjekt ao = this.list[i];
            if(!this.allergenList.contains(ao.getAllergene()));
            this.allergenList.addAll(ao.getAllergene());
        }
    }

    public Set<Allergen> getAllergenList(boolean b) {
        HashSet<Allergen> copy;
        if (b) {
            copy = new HashSet<>();
            copy.addAll(this.allergenList);
            return copy;
        }else {
            Set<Allergen> allAllergens = new HashSet<>();
            allAllergens.add(Allergen.Erdnuss);
            allAllergens.add(Allergen.Gluten);
            allAllergens.add(Allergen.Haselnuss);
            allAllergens.add(Allergen.Sesamsamen);

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
}
