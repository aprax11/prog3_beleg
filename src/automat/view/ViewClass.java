package automat.view;

import automat.gl.Automatenobjekt;




public class ViewClass {

    public void printList(Automatenobjekt[] list) {
        for(Automatenobjekt a : list) {
            System.out.println("Kuchenart: "+a.getClass()+System.lineSeparator()+"Fachnummer: "+a.getFachnummer()+System.lineSeparator()+"Inspektionsdatum: "+a.getInspektionsdatum()+System.lineSeparator()+"Haltbarkeit in Tagen:"+a.getHaltbarkeit().toDays());
        }
    }
}
