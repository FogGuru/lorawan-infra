package eu.fogguru.pojo;

import java.io.Serializable;
import java.util.Date;

public class SensorRead implements Cloneable, Serializable {
    private static final long serialVersionUID = -3555130785188765225L;
    private Long id;
    private Long timestamp;
    private Long reading;

    public SensorRead() {
        this(0L, new Date(), 0L);
    }

    public SensorRead(Long reading) {
        this(0L, new Date().getTime(), reading);
    }

    public SensorRead(Long id, Long timestamp, Long reading) {
        this.id = id;
        this.timestamp = timestamp;
        this.reading = reading;
    }

    public SensorRead(Long id, Date timestamp, Long reading) {
        this.id = id;
        this.timestamp = timestamp.getTime();
        this.reading = reading;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) { this.id = id; }

    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long newTimestamp) { this.timestamp = newTimestamp; }

    public Long getReading() {
        return this.reading;
    }
    public void setReading(Long reading) { this.reading = reading; }

    @Override
    public String toString() {
        return "{\"value\": " + this.reading.toString() + "}";
    }
}
