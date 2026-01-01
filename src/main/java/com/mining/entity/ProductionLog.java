package com.mining.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ProductionLog")
public class ProductionLog {

    @Id
    private int prodId;

    private int mineId;
    private Integer shiftId;   // can be NULL
    private LocalDate logDate;
    private double tonnes;
    private double grade;

    // ---- Getters and Setters ----

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public int getMineId() {
        return mineId;
    }

    public void setMineId(int mineId) {
        this.mineId = mineId;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public double getTonnes() {
        return tonnes;
    }

    public void setTonnes(double tonnes) {
        this.tonnes = tonnes;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}