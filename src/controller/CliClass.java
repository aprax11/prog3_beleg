package controller;

import automat.*;
import eventApi.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;

public class CliClass {
    private String lastCommand = ":c";
    private AddKuchenEventHandler kuchenEventHandler;
    private AddHerstellerEventHandler herstellerEventHandler;
    private DeleteKuchenEventHandler deleteKuchenEventHandler;
    private GetKuchenListEventHandler getKuchenListEventHandler;
    private GetAllergenHandler getAllergenHandler;

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

            if (this.lastCommand.equals(":c")) {
                if (parse.length == 1) {
                    Hersteller hersteller = new HerstellerImpl(parse[0]);
                    AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, hersteller, true, false);
                    this.herstellerEventHandler.handle(addHerstellerEvent);

                } else if (parse.length > 6) {
                    if (this.checkString(parse[0])) {
                        switch (parse[0]) {
                            case "Obstkuchen":
                                name = "Obstkuchen";
                                obstsorte = parse[6];
                                break;
                            case "Kremkuchen":
                                name = "Kremkuchen";
                                kremsorte = parse[6];
                                break;
                            case "Obsttorte":
                                name = "Obsttorte";
                                obstsorte = parse[6];
                                if (parse.length > 7) {
                                    kremsorte = parse[7];
                                    break;
                                } else {
                                    korrektheit = false;
                                    break;
                                }
                            default:
                                korrektheit = false;
                        }
                    } else {
                        korrektheit = false;
                    }
                    if (this.checkString(parse[1])) {
                        herstellerNameKuchen = new HerstellerImpl(parse[1]);
                    } else {
                        korrektheit = false;
                    }
                    String[] konv = parse[2].split(",");
                    if (konv.length == 2) {
                        String add = konv[0] + "." + konv[1];
                        preis = new BigDecimal(add);
                    }
                    if (this.checkInt(parse[3])) {
                        nährwert = Integer.parseInt(parse[3]);
                    } else {
                        korrektheit = false;
                    }
                    if (this.checkInt(parse[4])) {
                        haltbarkeit = Duration.ofDays(Integer.parseInt(parse[4]));
                    } else {
                        korrektheit = false;
                    }
                    String[] parseAllergene = parse[5].split(",");
                    allergens = new HashSet<>();
                    if (parseAllergene.length > 0) {
                        EnumSet<Allergen> allAllergens = EnumSet.allOf(Allergen.class);
                        for (int i = 0; i < parseAllergene.length; i++) {
                            for (Allergen a : allAllergens) {
                                if (a.name().equalsIgnoreCase(parseAllergene[i])) {
                                    allergens.add(a);
                                }
                            }
                        }
                    }
                    if (korrektheit) {
                        AddKuchenEvent addKuchenEvent = new AddKuchenEvent(this, name, kremsorte, herstellerNameKuchen, allergens, nährwert, haltbarkeit, obstsorte, preis);
                        this.kuchenEventHandler.handle(addKuchenEvent);
                    }
                }
            } else if (this.lastCommand.equals(":d")) {
                if (this.checkInt(s1)) {
                    int pos = Integer.parseInt(s1);
                    DeleteKuchenEvent deleteKuchenEvent = new DeleteKuchenEvent(this, pos, false);
                    this.deleteKuchenEventHandler.handle(deleteKuchenEvent);
                } else if (this.checkString(s1)) {
                    Hersteller hersteller = new HerstellerImpl(s1);
                    AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, hersteller, false, false);
                    this.herstellerEventHandler.handle(addHerstellerEvent);
                }
            } else if (this.lastCommand.equals(":r")) {

                String[] split = s1.split(" ");
                Class<? extends Automatenobjekt> cl = null;
                switch (split[0]) {
                    case "kuchen":
                        if (split.length == 1) {
                            GetKuchenListEvent getKuchenListEvent = new GetKuchenListEvent(this, null);
                            this.getKuchenListEventHandler.handle(getKuchenListEvent);
                        } else if (split.length == 2) {
                            switch (split[1]) {
                                case "Kremkuchen":
                                    cl = KremkuchenImpl.class;
                                    break;
                                case "Obstkuchen":
                                    cl = ObstkuchenImpl.class;
                                    break;
                                case "Obsttorte":
                                    cl = ObsttorteImpl.class;
                                    break;
                            }
                            GetKuchenListEvent getKuchenListEvent = new GetKuchenListEvent(this, cl);
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
               }
               if(this.lastCommand != null) {
                   this.handeln(s1);
               }
            } while (true);
        }
    }
}
