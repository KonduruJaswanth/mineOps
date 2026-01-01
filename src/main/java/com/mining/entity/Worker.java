package com.mining.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Worker")
public class Worker {

    @Id
    private int workerId;

    private int mineId;
    private String name;
    private String role;
    private String phone;

    // ---- Getters and Setters ----

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}