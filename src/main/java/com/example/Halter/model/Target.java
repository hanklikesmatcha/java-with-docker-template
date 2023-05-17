package com.example.JavaWithDocker.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity(name= "Target") // This tells Hibernate to make a table out of this class
@Table(name = "target")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Target implements Serializable {
    public Target(String deviceId, String targetNumber, String deviceStatus, Location lastLocation) {
        this.deviceId = deviceId;
        this.targetNumber = targetNumber;
        this.deviceStatus = deviceStatus;
        this.lastLocation = lastLocation;
    }
    public Target() {}

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Basic(optional = false)
    @Column(name = "device_id", nullable = false, columnDefinition = "TEXT")
    private String deviceId;

    @Basic(optional = false)
    @Column(name = "target_number", nullable = false, columnDefinition = "TEXT")
    private String targetNumber;

    @Basic(optional = false)
    @Column(name = "device_status", nullable = false, columnDefinition = "TEXT")
    private String deviceStatus;
    
    @Basic(optional = false)
    @Column(name = "last_location", nullable = false, columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Location lastLocation;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(String targetNumber) {
        this.targetNumber = targetNumber;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    @Override
    public String toString() {
        return "Target{" + 
                "id=" + id + 
                ", deviceId='" + deviceId + '\'' + 
                ", targetNumber='" + targetNumber + '\'' +
                ", colalrStatus='" + deviceStatus + '\'' + 
                ", lastLocation=" + lastLocation +
                '}';
    }
}
