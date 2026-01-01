package com.mining.main;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.Equipment;
import com.mining.entity.Equipment.Status;
import com.mining.entity.MineSite;
import com.mining.util.HibernateUtil;

public class UC3_RegisterEquipment {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC3 : Register Equipment ===");

        System.out.print("Enter Equipment ID: ");
        int equipmentId = sc.nextInt();

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Equipment Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Equipment Type: ");
        String type = sc.nextLine();

        System.out.print("Enter Status (WORKING / MAINTENANCE / BROKEN): ");
        String statusInput = sc.nextLine();

        System.out.print("Enter Purchase Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();

        LocalDate purchaseDate = LocalDate.parse(dateInput);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // 🔍 Validate Mine exists
        MineSite mine = session.get(MineSite.class, mineId);
        if (mine == null) {
            System.out.println("❌ Invalid Mine ID. Please create Mine first.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // 🔍 Validate Equipment ID uniqueness
        Equipment existingEquipment = session.get(Equipment.class, equipmentId);
        if (existingEquipment != null) {
            System.out.println("❌ Equipment already exists.");
            tx.rollback();
        } else {
            Equipment eq = new Equipment();
            eq.setEquipmentId(equipmentId);
            eq.setMineId(mineId);
            eq.setName(name);
            eq.setType(type);
            eq.setStatus(Status.valueOf(statusInput.toUpperCase()));
            eq.setPurchaseDate(purchaseDate);

            session.save(eq);
            tx.commit();
            System.out.println("✅ Equipment registered successfully.");
        }

        session.close();
        sc.close();
    }
}