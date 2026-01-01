package com.mining.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.Equipment;
import com.mining.entity.Equipment.Status;
import com.mining.entity.MineSite;
import com.mining.util.HibernateUtil;

public class UC4_TransferEquipmentBetweenMines {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC4 : Transfer Equipment Between Mines ===");

        System.out.print("Enter Equipment ID: ");
        int equipmentId = sc.nextInt();

        System.out.print("Enter New Mine ID: ");
        int newMineId = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // 🔍 Validate Equipment exists
        Equipment equipment = session.get(Equipment.class, equipmentId);
        if (equipment == null) {
            System.out.println("❌ Equipment not found.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // 🔍 Validate Destination Mine exists
        MineSite newMine = session.get(MineSite.class, newMineId);
        if (newMine == null) {
            System.out.println("❌ Destination Mine does not exist.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // ⚠ Optional policy: block transfer if equipment is BROKEN
        if (equipment.getStatus() == Status.BROKEN) {
            System.out.println("❌ Equipment is BROKEN. Transfer not allowed.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // ✅ Update mineId
        equipment.setMineId(newMineId);
        session.update(equipment);

        tx.commit();
        System.out.println("✅ Equipment transferred successfully.");

        session.close();
        sc.close();
    }
}