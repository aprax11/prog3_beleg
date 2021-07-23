package automat;

import beobachterMusterInterfaces.Beobachter;
import beobachterMusterInterfaces.Subjekt;

import java.io.Serializable;
import java.util.*;

public class GeschäftslogikImpl implements Subjekt, Serializable {
    private transient List<Beobachter> beobachterList = new LinkedList<>();
    private GanzerKuchen[] list ;
    private int fachnummer = 0;
    private Map<Hersteller, Integer> herstellerverwaltung = new HashMap();
    private Set<Allergen> allergenList = new HashSet<>();
    private int listGröße;


    public GeschäftslogikImpl(int i) {
        if(i > 0) {
            this.listGröße = i;
            this.list = new GanzerKuchen[i];
        }
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

    public synchronized boolean addKuchen(List<Container> list, Container boden) {
        Hersteller h = this.checkHersteller(boden.getHersteller());
        if (h != null) {


            int pos = 0;
            boolean b = true;
            for (int i = 0; i < this.listGröße; i++) {
                if (this.list[i] == null) {
                    pos = i;
                    b = true;
                    break;
                }else {
                    b = false;
                }
            }
            if(b) {
                GanzerKuchen aktuellerState = new KuchenBoden(boden.getHersteller(), boden.getAllergens(), boden.getNährwert()
                        , boden.getHaltbarkeit(), boden.getPreis(), new Date(), new Date(),
                        this.fachnummer, boden.getName(), boden.getTyp());

                for (int i = 0; i < list.size(); i++) {
                    GanzerKuchen kuchen = new Dekorator(aktuellerState, list.get(i).getName(), list.get(i).getPreis()
                            , list.get(i).getHaltbarkeit(), list.get(i).getAllergens(), list.get(i).getNährwert());
                    aktuellerState = kuchen;
                }
                this.list[pos] = aktuellerState;

                int anzahl = this.herstellerverwaltung.get(h);
                anzahl++;
                this.herstellerverwaltung.put(h, anzahl);
                if(aktuellerState.getAllergene() != null) {
                    this.allergenList.addAll(aktuellerState.getAllergene());
                }
                this.fachnummer++;
                this.benachrichtige();
                return true;
            }
        }
        return false;
    }
    private int getLength() {
        int anzahl = 0;
        for (int i = 0; i < list.length; i++) {
            if(this.list[i] != null) {
                anzahl++;
            }
        }
        return  anzahl;
    }

    public synchronized GanzerKuchen[] listKuchen(KuchenTypen typ) {
        if(typ == null) {
            GanzerKuchen[] copyArray = new GanzerKuchen[this.getLength()];
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
                if (this.list[i].getKuchenTyp() == typ) {
                    count++;
                }
            }
        }
        GanzerKuchen[] copyArray = new GanzerKuchen[count];
        int kuchenCount = 0;
        for (int i = 0; i < this.listGröße; i++) {
            if(this.list[i] != null) {
                if (this.list[i].getKuchenTyp() == typ) {
                    copyArray[kuchenCount] = this.list[i];
                    kuchenCount++;
                }
            }
        }
        return copyArray;
    }

    public synchronized void setInspektionsdatum(int fachnummer) {
        for (int i = 0; i < this.listGröße; i++) {
            if (this.list[i] != null && this.list[i].getFachnummer() == fachnummer) {
                KuchenBoden obj = (KuchenBoden) this.list[i];
                obj.setInspektionsdatum(new Date());
            }
        }
        this.benachrichtige();
    }




    public synchronized void löscheKuchen(int position)  {
        for (int i = 0; i < this.listGröße; i++) {
            if (this.list[i] != null && this.list[i].getFachnummer() == position) {
                GanzerKuchen remKuchen = this.list[i];
                this.checkHersteller(remKuchen.getHersteller());
                int anzahl = this.herstellerverwaltung.get(this.checkHersteller(remKuchen.getHersteller()));
                anzahl--;
                this.herstellerverwaltung.put(this.checkHersteller(remKuchen.getHersteller()), anzahl);
                this.list[i] = null;
            }
        }

        this.benachrichtige();
    }


    public HashMap<Hersteller, Integer> getHerstellerList() {
        HashMap<Hersteller, Integer> copy = new HashMap();
        copy.putAll(this.herstellerverwaltung);
        return copy;
    }

    public synchronized void löscheHersteller(String hersteller)  {

        for (int i = 0; i < this.listGröße; i++) {
            GanzerKuchen ao = this.list[i];
            if(this.list[i] != null) {
                if (ao.getHersteller().getName().equalsIgnoreCase(hersteller)) {
                    this.löscheKuchen(ao.getFachnummer());
                }
            }
        }
        Hersteller hersteller1 = this.checkHersteller(new HerstellerImpl(hersteller));
        this.herstellerverwaltung.remove(hersteller1);
    }


    public Set<Allergen> getAllergenList(boolean b) {
        HashSet<Allergen> copy;
        this.allergenList.clear();
        for (int i = 0; i < this.getLength(); i++) {
            if(this.list[i] != null) {
                GanzerKuchen ao = this.list[i];
                this.allergenList.addAll(ao.getAllergene());
            }
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
        if(!(this.beobachterList == null)) {
            for (Beobachter b : this.beobachterList) {
                b.aktualisiere();
            }
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
