package automat;

import beobachterMusterInterfaces.Beobachter;
import beobachterMusterInterfaces.Subjekt;
import eventApi.ReceiveKuchenListEventHandler;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class GeschäftslogikImpl implements Subjekt {
    private transient List<Beobachter> beobachterList = new LinkedList<>();
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

    private synchronized Hersteller checkHersteller(Hersteller hersteller) {
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
            Date einfügeDate = new Date();
            for (int i = 0; i < this.listGröße; i++) {
                if (this.list[i] == null) {
                    switch (name) {
                        case "Kremkuchen":
                            Automatenobjekt kremkuchen = new KremkuchenImpl(kremsorte, hersteller, allergens, nährwert, haltbarkeit, preis, einfügeDate, this.fachnummer, einfügeDate, this);
                            list[i] = kremkuchen;
                            this.fachnummerverwaltung.put(kremkuchen, i);
                            break;

                        case "Obstkuchen":
                            Automatenobjekt obstkuchen = new ObstkuchenImpl(hersteller, allergens, nährwert, haltbarkeit, obstsorte, preis, einfügeDate, this.fachnummer, einfügeDate, this);
                            list[i] = obstkuchen;
                            this.fachnummerverwaltung.put(obstkuchen, i);
                            break;

                        case "Obsttorte":
                            Automatenobjekt kuchen = new ObsttorteImpl(kremsorte, hersteller, allergens, nährwert, haltbarkeit, obstsorte, preis, einfügeDate, this.fachnummer, einfügeDate, this);
                            list[this.fachnummer] = kuchen;
                            this.fachnummerverwaltung.put(kuchen, i);
                            break;
                        default:
                            return false;
                    }
                }
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
    private void sortFachnummer(Automatenobjekt[] list) {
        Automatenobjekt[] resList = new Automatenobjekt[list.length];
        for (int i = 0; i < list.length; i++) {
            list[i].setFachnummer(i);
        }
    }

    public synchronized Automatenobjekt[] listKuchen(Class<? extends Automatenobjekt> cl) {
        if(cl == null) {
            Automatenobjekt[] copyArray = new Automatenobjekt[this.fachnummer];
            int cnt = 0;
            for (int i = 0; i < this.listGröße; i++) {
                if(this.list[i] != null) {
                    copyArray[cnt] = this.list[i];
                    cnt++;
                }
            }
            return copyArray;
        }
        int count = 0;
        for (int i = 0; i < this.listGröße; i++) {
            if(this.list[i] != null) {
                if (this.list[i].getClass() == cl) {
                    count++;
                }
            }
        }
        Automatenobjekt[] copyArray = new Automatenobjekt[count];
        int kuchenCount = 0;
        for (int i = 0; i < this.listGröße; i++) {
            if(this.list[i] != null) {
                if (this.list[i].getClass() == cl) {
                    copyArray[kuchenCount] = this.list[i];
                    kuchenCount++;
                }
            }
        }
        return copyArray;
    }

    public synchronized void setInspektionsdatum(int position) {
        if(this.list[position] != null && position <= this.listGröße) {
            Automatenobjekt kuchen = this.list[position];
            kuchen.callForInspektionsdatum();
            this.benachrichtige();
        }
    }
    public synchronized Date returnDate() {
        Date inspektionsDate = new Date();
        return inspektionsDate;
    }



    public synchronized void löscheKuchen(int position) throws InterruptedException {
        if (position < this.fachnummer && this.list[0] != null) {
            this.fachnummer--;
            Automatenobjekt remKuchen = this.list[position];
            Hersteller h = this.checkHersteller(remKuchen.getHersteller());
            int anzahl = this.herstellerverwaltung.get(h);
            anzahl--;
            this.herstellerverwaltung.put(remKuchen.getHersteller(), anzahl);
            this.list[0] = null;

        }
        this.benachrichtige();
    }


    public HashMap<Hersteller, Integer> getHerstellerList() {
        HashMap<Hersteller, Integer> copy = new HashMap();
        copy.putAll(this.herstellerverwaltung);
        return copy;
    }

    public synchronized void löscheHersteller(String hersteller) throws InterruptedException {
        int steps = this.fachnummer;
        for (int i = 0; i < steps; i++) {
            Automatenobjekt ao = this.list[i];
            if(this.list[i] != null) {
                if (ao.getHersteller().getName().equalsIgnoreCase(hersteller)) {
                    this.löscheKuchen(i);
                }
            }
        }
        Hersteller hersteller1 = this.checkHersteller(new HerstellerImpl(hersteller));
        this.herstellerverwaltung.remove(hersteller1);
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
    public synchronized int getFachnummer() {
        int ret = this.fachnummer;
        return ret;
    }
    public synchronized int getListGröße() {
        int ret = this.listGröße;
        return ret;
    }

//    public AutomatPojo schreibeAutomat() {
//        AutomatPojo ap = new AutomatPojo();
//        Beanitem[] newList = new Beanitem[this.list.length];
//        for(int i = 0; i < this.fachnummer; i++) {
//            Automatenobjekt ao = this.list[i];
//            Beanitem bi = new Beanitem();
//            bi.kuchenart = ao.getClass().getName();
//            bi.setAllergene(this.allergeneUmrechnung(ao));
//            bi.setDate(Long.toString(ao.getInspektionsdatum().getTime()));
//            bi.setHersteller(ao.getHersteller().getName());
//            bi.setNährwert(Integer.toString(ao.getNaehrwert()));
//            bi.setFachnummer(Integer.toString(ao.getFachnummer()));
//            bi.setPreis(ao.getPreis().toString());
//            bi.duration = Long.toString(ao.getHaltbarkeit().toMillis());
//
//            if (KremkuchenImpl.class.equals(ao.getClass())) {
//                bi.setKremsorte(((KremkuchenImpl) ao).getKremsorte());
//            } else if (ObstkuchenImpl.class.equals(ao.getClass())) {
//                bi.setObstsorte(((ObstkuchenImpl) ao).getObstsorte());
//            } else if (ObsttorteImpl.class.equals(ao.getClass())) {
//                bi.setObstsorte(((ObsttorteImpl) ao).getObstsorte());
//                bi.setKremsorte(((ObsttorteImpl) ao).getKremsorte());
//            }
//            newList[i] = bi;
//        }
//        ap.list = newList;
//        ap.größe = Integer.toString(this.listGröße);
//        for (Map.Entry<Hersteller, Integer> e : this.herstellerverwaltung.entrySet()) {
//            ap.hersteller.put(e.getKey().getName(), e.getValue());
//        }
//        return ap;
//    }
//    public void überschreibeAutomat(AutomatPojo ap) throws InterruptedException {
//        this.herstellerverwaltung = new HashMap<>();
//        this.list = new Automatenobjekt[Integer.parseInt(ap.größe)];
//        this.fachnummer = 0;
//        this.fachnummerverwaltung = new HashMap<>();
//        this.allergenList = new HashSet<>();
//        for (Map.Entry<String, Integer> e : ap.hersteller.entrySet()) {
//            Hersteller h = new HerstellerImpl(e.getKey());
//            this.herstellerverwaltung.put(h, e.getValue());
//        }
//        Date date = new Date();
//        this.listGröße = Integer.parseInt(ap.größe);
//        Automatenobjekt[] newList = new Automatenobjekt[Integer.parseInt(ap.größe)];
//        int cnt = 0;
//        for (int i = 0; i < Integer.parseInt(ap.größe); i++) {
//            if(ap.list[i] != null) {
//                cnt++;
//            }
//        }
//
//        for (int i = 0; i < cnt; i++) {
//            date.setTime(Long.parseLong(ap.list[i].getDate()));
//            switch(ap.list[i].kuchenart) {
//                case "automat.ObstkuchenImpl":
//                    Automatenobjekt obstkuchen = new ObstkuchenImpl(new HerstellerImpl(ap.list[i].getHersteller()),
//                            this.createAllergene(ap.list[i].getAllergene()), Integer.parseInt(ap.list[i].getNährwert()),
//                            Duration.ofMillis(Long.parseLong(ap.list[i].duration)), ap.list[i].getObstsorte(),
//                            new BigDecimal(ap.list[i].getPreis()), date, Integer.parseInt(ap.list[i].getFachnummer()), this);
//                    newList[i] = obstkuchen;
//                    this.fachnummerverwaltung.put(obstkuchen, this.fachnummer);
//                    break;
//                case "automat.KremkuchenImpl":
//                    Automatenobjekt kremkuchen = new KremkuchenImpl(ap.list[i].getKremsorte() ,new HerstellerImpl(ap.list[i].getHersteller()),
//                            this.createAllergene(ap.list[i].getAllergene()), Integer.parseInt(ap.list[i].getNährwert()),
//                            Duration.ofMillis(Long.parseLong(ap.list[i].duration)), new BigDecimal(ap.list[i].getPreis()),
//                            date, Integer.parseInt(ap.list[i].getFachnummer()), this);
//                    newList[i] = kremkuchen;
//                    this.fachnummerverwaltung.put(kremkuchen, this.fachnummer);
//                    break;
//                    case "automat.ObsttorteImpl":
//                    Automatenobjekt obsttorte = new ObsttorteImpl(ap.list[i].getKremsorte() ,new HerstellerImpl(ap.list[i].getHersteller()),
//                            this.createAllergene(ap.list[i].getAllergene()), Integer.parseInt(ap.list[i].getNährwert()),
//                            Duration.ofMillis(Long.parseLong(ap.list[i].duration)), ap.list[i].getObstsorte(), new BigDecimal(ap.list[i].getPreis()),
//                            date, Integer.parseInt(ap.list[i].getFachnummer()), this);
//                        newList[i] = obsttorte;
//                        this.fachnummerverwaltung.put(obsttorte, this.fachnummer);
//                    break;
//            }
//            this.allergenList.addAll(this.createAllergene(ap.list[i].getAllergene()));
//            this.fachnummer++;
//        }
//        this.list = newList;
//    }
//
//    private String allergeneUmrechnung(Automatenobjekt object) {
//        Collection<Allergen> a = object.getAllergene();
//        StringBuilder builder = new StringBuilder();
//        for(Allergen all : a) {
//            switch (all) {
//                case Gluten:
//                    builder.append(1);
//                    break;
//                case Erdnuss:
//                    builder.append(2);
//                    break;
//                case Sesamsamen:
//                    builder.append(3);
//                    break;
//                case Haselnuss:
//                    builder.append(4);
//                    break;
//            }
//        }
//        String ret = builder.toString();
//        return ret;
//    }
//    private Collection<Allergen> createAllergene(String s) {
//        Collection<Allergen> allergens = new HashSet<>();
//        if(s.contains("1")){
//            allergens.add(Allergen.Gluten);
//        }
//        if(s.contains("2")){
//            allergens.add(Allergen.Erdnuss);
//        }
//        if(s.contains("3")){
//            allergens.add(Allergen.Sesamsamen);
//        }
//        if(s.contains("4")){
//            allergens.add(Allergen.Haselnuss);
//        }
//        return allergens;
//    }
}
