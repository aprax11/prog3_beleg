package gui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ShowKuchen {
    private String hersteller;
    private long duration;
    private int fachnummer;
    private LocalDate inspektion;

    public ShowKuchen(String hersteller, long duration, int fachnummer, Date date) {
        this.hersteller = hersteller;
        this.fachnummer = fachnummer;
        this.duration = duration;
        this.inspektion = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public int getFachnummer() {
        return fachnummer;
    }

    public long getDuration() {
        return duration;
    }

    public String getHersteller() {
        return hersteller;
    }

    public LocalDate getInspektion() {
        return inspektion;
    }
}
