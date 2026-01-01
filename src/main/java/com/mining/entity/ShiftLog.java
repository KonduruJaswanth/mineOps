package com.mining.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ShiftLog")
public class ShiftLog {

    @Id
    private int shiftId;

    private int mineId;
    private LocalDate shiftDate;

    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;

    private int supervisorId;

    public enum ShiftType {
        DAY, NIGHT
    }

    // ---- Getters and Setters ----

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getMineId() {
        return mineId;
    }

    public void setMineId(int mineId) {
        this.mineId = mineId;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }
}