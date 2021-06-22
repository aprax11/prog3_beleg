package automat;

public class Beanitem {
    public String kuchenart;
//    public String getKuchenart() {return kuchenart;}
//    public void setKuchenart(String kuchenart) {this.kuchenart = kuchenart;}

    private String hersteller;
    public String getHersteller(){return hersteller;}
    public void setHersteller(String hersteller) {this.hersteller = hersteller;}

    private String nährwert;
    public String getNährwert(){return nährwert;}
    public void setNährwert(String nährwert) {this.nährwert = nährwert;}

    private String date;
    public String getDate() {return date;}
    public void setDate(String date){this.date = date;}

    private String kremsorte;
    public String getKremsorte(){return kremsorte;}
    public void setKremsorte(String krem){this.kremsorte = krem;}

    private String obstsorte;
    public String getObstsorte(){return obstsorte;}
    public void setObstsorte(String obst) {this.obstsorte = obst;}

    private String preis;
    public String getPreis(){return preis;}
    public void setPreis(String preis){this.preis = preis;}

    private String fachnummer;
    public String getFachnummer(){return fachnummer;}
    public void setFachnummer(String fachnummer){this.fachnummer = fachnummer;}

    private String allergene;
    public String getAllergene(){return  allergene;}
    public void setAllergene(String aller) {this.allergene = aller;}

    public String duration;

    public Beanitem reference;

    @Override
    public String toString() {
        return "Kuchenart: "+this.kuchenart+System.lineSeparator()+"Hersteller: "+this.hersteller+System.lineSeparator()+"Nährwert: "+this.nährwert+System.lineSeparator()+"Allergene: "+this.allergene;
    }

}
