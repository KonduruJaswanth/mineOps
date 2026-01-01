package com.mining.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "EquipmentUsage")
public class EquipmentUsage {

    @Id
    private int usageId;

    private int mineId;
    private int equipmentId;
    private LocalDate usageDate;
    private double runningHours;

    @Enumerated(EnumType.STRING)
    private Breakdown breakdown;

    private Double downtimeHours;   // can be NULL

    public enum Breakdown {
        Y, N
    }

    // ---- Getters and Setters ----

    public int getUsageId() {
        return usageId;
    }

    public void setUsageId(int usageId) {
        this.usageId = usageId;
    }

    public int getMineId() {
        return mineId;
    }

    public void setMineId(int mineId) {
        this.mineId = mineId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }

    public double getRunningHours() {
        return runningHours;
    }

    public void setRunningHours(double runningHours) {
        this.runningHours = runningHours;
    }

    public Breakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Breakdown breakdown) {
        this.breakdown = breakdown;
    }

    public Double getDowntimeHours() {
        return downtimeHours;
    }

    public void setDowntimeHours(Double downtimeHours) {
        this.downtimeHours = downtimeHours;
    }
}