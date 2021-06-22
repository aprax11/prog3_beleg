package persistenz;

import automat.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PersistenzLogic {
    private GeschäftslogikImpl gl;

    public PersistenzLogic(GeschäftslogikImpl gl) {
        this.gl = gl;
    }
    public String allergeneUmrechnung(Automatenobjekt object) {
        Collection<Allergen> a = object.getAllergene();
        StringBuilder builder = new StringBuilder();
        for(Allergen all : a) {
            switch (all) {
                case Gluten:
                    builder.append(1);
                    break;
                case Erdnuss:
                    builder.append(2);
                    break;
                case Sesamsamen:
                    builder.append(3);
                    break;
                case Haselnuss:
                    builder.append(4);
                    break;
            }
        }
        String ret = builder.toString();
        return ret;
    }
    public Collection<Allergen> createAllergene(String s) {
        Collection<Allergen> allergens = new HashSet<>();
        if(s.contains("1")){
            allergens.add(Allergen.Gluten);
        }
        if(s.contains("2")){
            allergens.add(Allergen.Erdnuss);
        }
        if(s.contains("3")){
            allergens.add(Allergen.Sesamsamen);
        }
        if(s.contains("4")){
            allergens.add(Allergen.Haselnuss);
        }
        return allergens;
    }

    public ArrayList<Beanitem> createPersistenzList() {
        Automatenobjekt[] list = this.gl.listKuchen(null);
        ArrayList<Beanitem> bList = new ArrayList<>();

        for(Automatenobjekt a : list) {
            Beanitem bean = new Beanitem();
            bean.setHersteller(a.getHersteller().getName());
            if(a.getClass().equals(KremkuchenImpl.class) || a.getClass().equals(ObsttorteImpl.class)) {
                bean.setKremsorte(((Kremkuchen) a).getKremsorte());
            } else if(a.getClass().equals(ObstkuchenImpl.class) || a.getClass().equals(ObsttorteImpl.class)) {
                bean.setObstsorte(((ObstkuchenImpl) a).getObstsorte());
            }
            bean.setDate(a.getInspektionsdatum().toString());
            bean.setPreis(a.getPreis().toString());
            bean.setNährwert(Integer.toString(a.getNaehrwert()));
            String allergene = this.allergeneUmrechnung(a);
            bean.setAllergene(allergene);
            bList.add(bean);
        }
    return bList;
    }

//    public void loadBeanList(List<Beanitem> list) {
//        int cnt = 0;
//        try {
//            //clear auf die GL?
//            for (Beanitem b : list) {
//                if (Integer.parseInt(b.getFachnummer()) == cnt){
//                    Hersteller h = new HerstellerImpl(b.getHersteller());
//                    Collection<Allergen> allergens = this.createAllergene(b.getAllergene());
//
//                    this.gl.addHersteller(h);
//                    this.gl.addKuchen(b.getKuchenart(), b.getKremsorte(), h, allergens, Integer.parseInt(b.getNährwert()), ,b.getObstsorte(),//Inspektionsdatum im const setzen )
//                }else {
//                    continue;
//                }
//                cnt++;
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

}
