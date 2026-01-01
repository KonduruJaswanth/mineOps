package com.mining.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MineSite")
public class MineSite {

    @Id
    private int mineId;

    private String name;
    private String location;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVE, INACTIVE
    }

    // ---- Getters and Setters ----

    public int getMineId() {
        return mineId;
    }

    public void setMineId(int mineId) {
        this.mineId = mineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}