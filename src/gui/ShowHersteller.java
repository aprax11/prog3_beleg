package gui;

public class ShowHersteller {
    private String name;
    private int anzahl = 0;

    public ShowHersteller(String name, int anzahl) {
        this.name = name;
        this.anzahl = anzahl;
    }

    public String getName() {
        return this.name;
    }
    public int getAnzahl() {
        return this.anzahl;
    }

}
