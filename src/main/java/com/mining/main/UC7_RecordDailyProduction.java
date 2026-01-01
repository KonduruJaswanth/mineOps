package com.mining.main;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.MineSite;
import com.mining.entity.ProductionLog;
import com.mining.entity.ShiftLog;
import com.mining.util.HibernateUtil;

public class UC7_RecordDailyProduction {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC7 : Record Daily Production ===");

        System.out.print("Enter Production Log ID: ");
        int prodId = sc.nextInt();

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Log Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();

        System.out.print("Enter Tonnes Produced: ");
        double tonnes = sc.nextDouble();

        System.out.print("Enter Grade (0 - 100): ");
        double grade = sc.nextDouble();

        System.out.print("Enter Shift ID (or -1 if not applicable): ");
        int shiftIdInput = sc.nextInt();

        LocalDate logDate = LocalDate.parse(dateInput);

        // 🔍 Validate ranges
        if (tonnes <= 0 || grade < 0 || grade > 100) {
            System.out.println("❌ Invalid tonnes or grade value.");
            sc.close();
            return;
        }

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

        Integer shiftId = null;

        // 🔍 Validate Shift (optional)
        if (shiftIdInput != -1) {
            ShiftLog shift = session.get(ShiftLog.class, shiftIdInput);
            if (shift == null) {
                System.out.println("❌ Shift not found. Production not recorded.");
                tx.rollback();
                session.close();
                sc.close();
                return;
            }
            shiftId = shiftIdInput;
        }

        // 🔍 Validate Production ID uniqueness
        ProductionLog existingLog = session.get(ProductionLog.class, prodId);
        if (existingLog != null) {
            System.out.println("❌ Duplicate Production Log ID.");
            tx.rollback();
        } else {
            ProductionLog log = new ProductionLog();
            log.setProdId(prodId);
            log.setMineId(mineId);
            log.setLogDate(logDate);
            log.setTonnes(tonnes);
            log.setGrade(grade);
            log.setShiftId(shiftId);

            session.save(log);
            tx.commit();
            System.out.println("✅ Daily production recorded successfully.");
        }

        session.close();
        sc.close();
    }
}