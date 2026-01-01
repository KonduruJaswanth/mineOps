package com.mining.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Equipment")
public class Equipment {

    @Id
    private int equipmentId;

    private int mineId;
    private String name;
    private String type;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate purchaseDate;

    public enum Status {
        WORKING, MAINTENANCE, BROKEN
    }

    // ---- Getters and Setters ----

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}