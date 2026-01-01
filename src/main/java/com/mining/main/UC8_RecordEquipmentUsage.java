package com.mining.main;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.Equipment;
import com.mining.entity.EquipmentUsage;
import com.mining.entity.EquipmentUsage.Breakdown;
import com.mining.entity.MineSite;
import com.mining.util.HibernateUtil;

public class UC8_RecordEquipmentUsage {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC8 : Record Equipment Usage ===");

        System.out.print("Enter Usage ID: ");
        int usageId = sc.nextInt();

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();

        System.out.print("Enter Equipment ID: ");
        int equipmentId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Usage Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();

        System.out.print("Enter Running Hours: ");
        double runningHours = sc.nextDouble();

        System.out.print("Breakdown occurred? (Y / N): ");
        String breakdownInput = sc.next();

        Double downtimeHours = null;
        if (breakdownInput.equalsIgnoreCase("Y")) {
            System.out.print("Enter Downtime Hours: ");
            downtimeHours = sc.nextDouble();
        }

        LocalDate usageDate = LocalDate.parse(dateInput);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // 🔍 Validate Mine exists
        MineSite mine = session.get(MineSite.class, mineId);
        if (mine == null) {
            System.out.println("❌ Invalid Mine ID.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // 🔍 Validate Equipment exists
        Equipment equipment = session.get(Equipment.class, equipmentId);
        if (equipment == null) {
            System.out.println("❌ Equipment not found.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // 🔍 Validate Equipment belongs to the Mine
        if (equipment.getMineId() != mineId) {
            System.out.println("❌ Equipment belongs to another mine. Transfer it first.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // 🔍 Validate Usage ID uniqueness
        EquipmentUsage existingUsage = session.get(EquipmentUsage.class, usageId);
        if (existingUsage != null) {
            System.out.println("❌ Duplicate Usage ID.");
            tx.rollback();
        } else {
            EquipmentUsage usage = new EquipmentUsage();
            usage.setUsageId(usageId);
            usage.setMineId(mineId);
            usage.setEquipmentId(equipmentId);
            usage.setUsageDate(usageDate);
            usage.setRunningHours(runningHours);
            usage.setBreakdown(Breakdown.valueOf(breakdownInput.toUpperCase()));
            usage.setDowntimeHours(downtimeHours);

            session.save(usage);
            tx.commit();
            System.out.println("✅ Equipment usage recorded successfully.");
        }

        session.close();
        sc.close();
    }
}