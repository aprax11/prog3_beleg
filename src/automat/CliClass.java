package automat;

import automat.gl.Hersteller;
import automat.gl.HerstellerImpl;

import java.util.Scanner;

public class CliClass {
    private String lastCommand;
    private AddKuchenEventHandler kuchenEventHandler;
    private AddHerstellerEventHandler herstellerEventHandler;
    private DeleteKuchenEventHandler deleteKuchenEventHandler;

    public void setKuchenEventHandler(AddKuchenEventHandler kuchenEventHandler) {
        this.kuchenEventHandler = kuchenEventHandler;
    }
    public void setHerstellerEventHandler(AddHerstellerEventHandler herstellerEventHandler) {
        this.herstellerEventHandler = herstellerEventHandler;
    }
    public void setDeleteKuchenEventHandler(DeleteKuchenEventHandler deleteKuchenEventHandler) {
        this.deleteKuchenEventHandler = deleteKuchenEventHandler;
    }
    private boolean checkString(String name) {
        int[] character = name.chars().toArray();
        for (int i = 0; i < name.length(); i++) {
            if(character[i] < 65 || character[i] > 122) {
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
        if(this.lastCommand.equals(":c")) {
            if (parse.length == 1) {
                Hersteller hersteller = new HerstellerImpl(parse[0]);
                AddHerstellerEvent addHerstellerEvent = new AddHerstellerEvent(this, hersteller, true);
                this.herstellerEventHandler.handle(addHerstellerEvent);
                System.out.println("Hersteller added");
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
        }
    }

    public void start() {
        try(Scanner s = new Scanner(System.in)) {
           do {

               String s1 = s.next();
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
