import automat.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Persistenz {
    public static void main(String[] args) throws InterruptedException {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        gl.addHersteller(new HerstellerImpl("Paul"));
        gl.addKuchen("Kremkuchen", "butter", new HerstellerImpl("Paul"), EnumSet.allOf(Allergen.class), 299, Duration.ofDays(30), "", new BigDecimal("12.4"));
        gl.addKuchen("Obsttorte", "sahne", new HerstellerImpl("Paul"), EnumSet.allOf(Allergen.class), 230, Duration.ofDays(30), "apfel", new BigDecimal("44.4"));
        gl.addKuchen("Obstkuchen", "", new HerstellerImpl("Paul"), EnumSet.allOf(Allergen.class), 267, Duration.ofDays(30), "apfel", new BigDecimal("31.4"));
        Automatenobjekt[] ao = gl.listKuchen(null);
        for(Automatenobjekt a : ao) {
            System.out.println(a.getClass()+" "+a.getNaehrwert()+" "+a.getPreis());
        }
        AutomatPojo ap = gl.schreibeAutomat();

        System.out.println("----------");
        try(XMLEncoder encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("beanItems.xml")));){
            encoder.writeObject(ap);
        }catch(Exception e){
            e.printStackTrace();
        }
        try (XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("beanItems.xml")));){
            AutomatPojo got = (AutomatPojo) decoder.readObject();
            gl.überschreibeAutomat(got);
            Automatenobjekt[] al = gl.listKuchen(null);
            for(Automatenobjekt a : al) {
                System.out.println(a.getClass()+" "+a.getNaehrwert()+" "+a.getPreis());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
