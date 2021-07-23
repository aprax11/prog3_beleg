package controller;

import automat.*;
import eventApi.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class CliClass {
    private String lastCommand = ":c";
    private AddKuchenEventHandler kuchenEventHandler;
    private AddHerstellerEventHandler herstellerEventHandler;
    private DeleteKuchenEventHandler deleteKuchenEventHandler;
    private GetKuchenListEventHandler getKuchenListEventHandler;
    private GetAllergenHandler getAllergenHandler;
    private JosEventHandler josHandler;

    public void setAllergenHandler(GetAllergenHandler handler) {
        this.getAllergenHandler = handler;
    }

    public void setKuchenEventHandler(AddKuchenEventHandler kuchenEventHandler) {
        this.kuchenEventHandler = kuchenEventHandler;
    }
    public void setHerstellerEventHandler(AddHerstellerEventHandler herstellerEventHandler) {
        this.herstellerEventHandler = herstellerEventHandler;
    }
    public void setDeleteKuchenEventHandler(DeleteKuchenEventHandler deleteKuchenEventHandler) {
        this.deleteKuchenEventHandler = deleteKuchenEventHandler;
    }
    public void setGetKuchenListEventHandler(GetKuchenListEventHandler getKuchenListEventHandler) {
        this.getKuchenListEventHandler = getKuchenListEventHandler;
    }
    public void setJosEventHandler(JosEventHandler handler) {
        this.josHandler = handler;
    }
    private boolean checkString(String name) {
        int[] character = name.chars().toArray();
        for (int i = 0; i < name.length(); i++) {
            if(character[i] < 48 || character[i] > 122) {
                return false;
            }
        }
        return true;
    }
    private boolean checkInt(String zahl) {
        int[] character = zahl.chars().toArray();
        for (int i = 0; i < zahl.length(); i++) {
            if(character[i] < 48 || character[i] > 57) {
                return false;
            }
        }
        return true;
    }
    public void handeln(String s1) {
        String[] parse = s1.split(" ");
        if (!(parse.length == 0)) {
            boolean korrektheit = true;
            String name = null;
            String obstsorte = null;
            String kremsorte = null;
            Hersteller herstellerNameKuchen = null;
            BigDecimal preis = null;
            int nährwert = 0;
            Duration haltbarkeit = null;
            Collection<Allergen> allergens = null;
            KuchenTypen typ = null;
            Container boden = null;
            ArrayList<Container> liste = new ArrayList<>();

            if (this.lastCommand.equals(":c")) {
                if (parse.length == 1) {
                    AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, parse[0], true, false);
                    this.herstellerEventHandler.handle(addHerstellerEvent);

                } else if (parse.length >= 2) {
                    switch (parse[0]) {
                        case "Kremkuchen":
                            typ = KuchenTypen.Kremkuchen;
                            break;
                        case "Obstkuchen":
                            typ = KuchenTypen.Obstkuchen;
                            break;
                        case "Obsttorte":
                            typ = KuchenTypen.Obsttorte;
                            break;
                        default:
                            korrektheit = false;
                    }
                    if (this.checkString(parse[1])) {
                        herstellerNameKuchen = new HerstellerImpl(parse[1]);
                    } else {
                        korrektheit = false;
                    }
                    if (korrektheit) {
                        boden = new Container(herstellerNameKuchen, null, 0, null, null, null, typ);
                    }
                    if ((parse.length - 2) % 5 == 0) {
                        int pos = 2;
                        for (int i = 0; i < ((parse.length - 2) / 5); i++) {
                            String[] konv = parse[pos].split(",");
                            if (konv.length == 2) {
                                String add = konv[0] + "." + konv[1];
                                preis = new BigDecimal(add);
                            } else {
                                korrektheit = false;
                            }
                            pos++;
                            if (this.checkInt(parse[pos])) {
                                nährwert = Integer.parseInt(parse[pos]);
                            } else {
                                korrektheit = false;
                            }
                            pos++;
                            if (this.checkInt(parse[pos])) {
                                haltbarkeit = Duration.ofDays(Integer.parseInt(parse[pos]));
                            } else {
                                korrektheit = false;
                            }
                            pos++;
                            String[] parseAllergene = parse[pos].split(",");
                            allergens = new HashSet<>();
                            if (parseAllergene.length > 0) {
                                EnumSet<Allergen> allAllergens = EnumSet.allOf(Allergen.class);
                                for (int j = 0; j < parseAllergene.length; j++) {
                                    for (Allergen a : allAllergens) {
                                        if (a.name().equalsIgnoreCase(parseAllergene[j])) {
                                            allergens.add(a);
                                        }
                                    }
                                }
                            }
                            pos++;
                            if (this.checkString(parse[pos])) {
                                name = parse[pos];
                            }
                            pos++;
                            Container belag = new Container(null, allergens, nährwert, haltbarkeit, preis, name, typ);
                            liste.add(belag);
                        }
                    } else {
                        korrektheit = false;
                    }
                    if (korrektheit) {
                        AddKuchenEvent addKuchenEvent = new AddKuchenEvent(this, liste, boden);
                        this.kuchenEventHandler.handle(addKuchenEvent);
                    }
                }
            } else if (this.lastCommand.equals(":d")) {
                if (this.checkInt(s1)) {
                    int pos = Integer.parseInt(s1);
                    DeleteKuchenEvent deleteKuchenEvent = new DeleteKuchenEvent(this, pos, false);
                    this.deleteKuchenEventHandler.handle(deleteKuchenEvent);
                } else if (this.checkString(s1)) {
                    AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, s1, false, false);
                    this.herstellerEventHandler.handle(addHerstellerEvent);
                }
            } else if (this.lastCommand.equals(":r")) {
                String[] split = s1.split(" ");
                KuchenTypen typ1 = null;
                switch (split[0]) {
                    case "kuchen":
                        if (split.length == 1) {
                            GetKuchenListEvent getKuchenListEvent = new GetKuchenListEvent(this, null);
                            this.getKuchenListEventHandler.handle(getKuchenListEvent);
                        } else if (split.length == 2) {
                            switch (split[1]) {
                                case "Kremkuchen":
                                    typ1 = KuchenTypen.Kremkuchen;
                                    break;
                                case "Obstkuchen":
                                    typ1 = KuchenTypen.Obstkuchen;
                                    break;
                                case "Obsttorte":
                                    typ1 = KuchenTypen.Obsttorte;
                                    break;
                            }
                            GetKuchenListEvent getKuchenListEvent = new GetKuchenListEvent(this, typ1);
                            this.getKuchenListEventHandler.handle(getKuchenListEvent);
                        }
                        break;
                    case "hersteller":
                        AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, null, false, true);
                        this.herstellerEventHandler.handle(addHerstellerEvent);
                        break;
                    case "allergene":
                        switch (split[1]) {
                            case "i":
                                GetAllergeneEvent event = new GetAllergeneEvent(this, true);
                                this.getAllergenHandler.handle(event);
                                break;
                            case "e":
                                GetAllergeneEvent event2 = new GetAllergeneEvent(this, false);
                                this.getAllergenHandler.handle(event2);
                                break;
                        }
                }
            } else if (this.lastCommand.equals(":u")) {
                if (this.checkInt(parse[0])) {
                    DeleteKuchenEvent event = new DeleteKuchenEvent(this, Integer.parseInt(parse[0]), true);
                    this.deleteKuchenEventHandler.handle(event);
                }
            } else if (this.lastCommand.equals(":p")) {
                if (this.checkString(parse[0])) {
                    switch (parse[0]) {
                        case "saveJOS":
                            JosEvent event = new JosEvent(this, true);
                            this.josHandler.handle(event);
                            break;
                        case "loadJOS":
                            JosEvent event2 = new JosEvent(this, false);
                            this.josHandler.handle(event2);
                            break;
                    }
                }
            }
        }
    }


    public void start() {
        try(Scanner s = new Scanner(System.in)) {
           do {
               String s1 = s.nextLine();
               switch(s1) {
                   case ":c":
                       this.lastCommand = ":c";
                       continue;
                   case ":d":
                       this.lastCommand = ":d";
                       continue;
                   case ":r":
                       this.lastCommand = ":r";
                       continue;
                   case ":u":
                       this.lastCommand = ":u";
                       continue;
                   case ":p":
                       this.lastCommand = ":p";
                       continue;
               }
               if(this.lastCommand != null) {
                   this.handeln(s1);
               }
            } while (true);
        }
    }
}
