import automat.Allergen;
import persistenz.Beanitem;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Persistenz {
    public static void main(String[] args) {
        int count = 2;
        ArrayList<Beanitem> items = new ArrayList<>(count);
        for(int i = 1; i <= count; i++) {
            Beanitem item = new Beanitem();
            item.setKuchenart(String.format("%04X", i));
            item.setHersteller("bob");
            item.setNährwert(Integer.toString(i));
            items.add(item);
            EnumSet<Allergen> a = EnumSet.allOf(Allergen.class);

        }
        for(int i = 0;i<count;i++) items.get(i).reference=items.get((i+count-1)%count);
        for (Beanitem i:items) System.out.println(i);
        System.out.println("---");

        try(XMLEncoder encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("beanItems.xml")));){
            encoder.writeObject(items);
        }catch(Exception e){
            e.printStackTrace();
        }
        try (XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("beanItems.xml")));){
            List<Beanitem> loadedList=(List<Beanitem>)decoder.readObject();
            for (Beanitem i:loadedList) System.out.println(i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
