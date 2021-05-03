package automat;

import automat.gl.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class CliClass {
    private String lastCommand;
    private AddKuchenEventHandler kuchenEventHandler;
    private AddHerstellerEventHandler herstellerEventHandler;
    private DeleteKuchenEventHandler deleteKuchenEventHandler;
    private GetKuchenListEventHandler getKuchenListEventHandler;

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
    private void handeln(String s1) {
        String[] parse = s1.split(" ");
        boolean korrektheit = true;
        String name = null;
        String obstsorte = null;
        String kremsorte = null;
        Hersteller herstellerNameKuchen = null;
        BigDecimal preis = null;
        int nährwert = 0;
        Duration haltbarkeit = null;
        Collection<Allergen> allergens = null;

        if(this.lastCommand.equals(":c")) {
            if (parse.length < 6) {
                Hersteller hersteller = new HerstellerImpl(parse[0]);
                AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, hersteller, true);
                this.herstellerEventHandler.handle(addHerstellerEvent);

            }else if(parse.length > 6) {
                if(this.checkString(parse[0])) {
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
                            if(parse.length > 7) {
                                kremsorte = parse[7];
                                break;
                            }else {
                                korrektheit = false;
                                break;
                            }
                        default:
                            System.out.println("Bitte eine zulässige kuchenart angeben");
                            korrektheit = false;
                    }
                }else {
                    korrektheit = false;
                }
                if(this.checkString(parse[1])) {
                    herstellerNameKuchen = new HerstellerImpl(parse[1]);
                }else {
                    System.out.println("Fehler bei Hersteller");
                    korrektheit = false;
                }
                String[] konv = parse[2].split(",");
                String add = konv[0]+"."+konv[1];
                preis = new BigDecimal(add);

                if(this.checkInt(parse[3])) {
                    nährwert = Integer.parseInt(parse[3]);
                }else {
                    korrektheit = false;
                }if(this.checkInt(parse[4])) {
                    haltbarkeit = Duration.ofDays(Integer.parseInt(parse[4]));
                }else {
                    korrektheit = false;
                }
                String[] parseAllergene = parse[5].split(",");
                allergens = new HashSet<>();
                if(parseAllergene.length > 0) {
                    for (String a : parseAllergene) {
                        switch(a) {
                            case "Gluten":
                                allergens.add(Allergen.Gluten);
                                break;
                            case "Erdnuss":
                                allergens.add(Allergen.Erdnuss);
                                break;
                            case "Haselnuss":
                                allergens.add(Allergen.Haselnuss);
                                break;
                            case "Sesamsamen":
                                allergens.add(Allergen.Sesamsamen);
                                break;
                            default:
                                System.out.println("Fehler bei allergen");
                        }
                    }
                }
                if(korrektheit) {
                    AddKuchenEvent addKuchenEvent = new AddKuchenEvent(this, name, kremsorte, herstellerNameKuchen, allergens, nährwert, haltbarkeit, obstsorte, preis);
                    this.kuchenEventHandler.handle(addKuchenEvent);
                    System.out.println("kuchen added");
                }
            }
        }else if(this.lastCommand.equals(":d")) {
            if(this.checkInt(s1)) {
                int pos = Integer.parseInt(s1);
                DeleteKuchenEvent deleteKuchenEvent = new DeleteKuchenEvent(this, pos);
                this.deleteKuchenEventHandler.handle(deleteKuchenEvent);
            }else if(this.checkString(s1)) {
                Hersteller hersteller = new HerstellerImpl(s1);
                AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, hersteller, false);
                this.herstellerEventHandler.handle(addHerstellerEvent);
            }
        }else if(this.lastCommand.equals(":r")) {
            if(this.checkString(s1)) {
                String[] split = s1.split(" ");
                Class<? extends Automatenobjekt> cl = null;
                switch (split[0]) {
                    case "kuchen":
                        if(split.length == 1) {
                            GetKuchenListEvent getKuchenListEvent = new GetKuchenListEvent(this, null);
                            this.getKuchenListEventHandler.handle(getKuchenListEvent);
                        }else if(split.length == 2) {
                            switch(split[1]) {
                                case "[Kremkuchen]":
                                    cl = KremkuchenImpl.class;
                                    break;
                                case "[Obstkuchen]":
                                    cl = ObstkuchenImpl.class;
                                    break;
                                case "[Obsttorte]":
                                    cl = ObsttorteImpl.class;
                                    break;
                            }
                            GetKuchenListEvent getKuchenListEvent = new GetKuchenListEvent(this, cl);
                            this.getKuchenListEventHandler.handle(getKuchenListEvent);
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
               }
               this.handeln(s1);

            } while (true);
        }
    }
}
