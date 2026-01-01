package com.mining.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SafetyIncident")
public class SafetyIncident {

    @Id
    private int incidentId;

    private int mineId;
    private Integer equipmentId;   // can be NULL
    private Integer workerId;      // can be NULL
    private LocalDate incidentDate;
    private String type;
    private int severity;
    private double cost;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        OPEN, CLOSED
    }

    // ---- Getters and Setters ----

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public int getMineId() {
        return mineId;
    }

    public void setMineId(int mineId) {
        this.mineId = mineId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}