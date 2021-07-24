package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public interface GanzerKuchen extends Verkaufsobjekt, Kuchen {

    Date getEinfügeDate();
    String getName();
    KuchenTypen getKuchenTyp();
    Duration verbleibendeHaltbarkeit(Date date);
}
