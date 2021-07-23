package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public interface GanzerKuchen {
    Hersteller getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
    BigDecimal getPreis();
    Date getInspektionsdatum();
    int getFachnummer();
    Date getEinfügeDate();
    String getName();
    KuchenTypen getKuchenTyp();
    Duration verbleibendeHaltbarkeit(Date date);
}
