package com.cdesign.spittr.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Ageev Evgeny on 28.08.2016.
 */
@Entity
public class Spittle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String message;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "spitter")
    private Spitter spitter;

    @NotNull
    @Column(name = "posted_time")
    private Date time;

    private Double latitude;

    private Double longitude;

    //for hibernate
    public Spittle() {}

    public Spittle(String message, Spitter spitter) {
        this(message, spitter, null, null);
    }

    public Spittle(String message, Spitter spitter, Double latitude, Double longitude) {
        this.id = null;
        this.message = message;
        this.spitter = spitter;
        this.time = new Date();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Spitter getSpitter() {
        return spitter;
    }

    @Override
    public boolean equals(Object that) {
        //return EqualsBuilder.reflectionEquals(this, that, "id", "time");
        if (this == that) return true;

        if (that == null) return false;

        if (!(that instanceof Spittle)) return false;

        Spittle objThat = (Spittle) that;
        if (id != objThat.getId() ||
                (message != null && !message.equals(objThat.getMessage())) ||
                time != objThat.getTime() &&
                latitude != objThat.getLatitude() &&
                longitude != objThat.getLongitude()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        //return HashCodeBuilder.reflectionHashCode(this, "id", "time");
        int hash = (int)(id + 31 * time.hashCode());
        hash += 31 * message.hashCode();
        return hash;
    }
}
