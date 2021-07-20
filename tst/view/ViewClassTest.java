package view;

import automat.Hersteller;
import automat.HerstellerImpl;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ViewClassTest {
    @Test
    public void listHerstellerTest() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        HashMap<Hersteller, Integer> hersteller = new HashMap<>();
        Hersteller hersteller1 = new HerstellerImpl("Paul");
        hersteller.put(hersteller1, 2);

        ViewClass view = new ViewClass();
        view.printHersteller(hersteller);
        assertEquals("Hersteller: "+"Paul"+" Kuchen Anzahl: "+2+System.lineSeparator(), byteArrayOutputStream.toString());
        System.setOut(System.out);
    }

}