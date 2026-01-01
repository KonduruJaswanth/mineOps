package com.mining.main;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.MineSite;
import com.mining.entity.ShiftLog;
import com.mining.entity.ShiftLog.ShiftType;
import com.mining.entity.Worker;
import com.mining.util.HibernateUtil;

public class UC6_CreateShiftLog {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC6 : Create Shift Log ===");

        System.out.print("Enter Shift ID: ");
        int shiftId = sc.nextInt();

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();

        System.out.print("Enter Supervisor ID: ");
        int supervisorId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Shift Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();

        System.out.print("Enter Shift Type (DAY / NIGHT): ");
        String shiftTypeInput = sc.nextLine();

        LocalDate shiftDate = LocalDate.parse(dateInput);

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

        // 🔍 Validate Supervisor exists (must be a Worker)
        Worker supervisor = session.get(Worker.class, supervisorId);
        if (supervisor == null) {
            System.out.println("❌ Invalid Supervisor ID.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // 🔍 Validate Shift ID uniqueness
        ShiftLog existingShift = session.get(ShiftLog.class, shiftId);
        if (existingShift != null) {
            System.out.println("❌ Duplicate Shift ID. Creation rejected.");
            tx.rollback();
        } else {
            ShiftLog shift = new ShiftLog();
            shift.setShiftId(shiftId);
            shift.setMineId(mineId);
            shift.setSupervisorId(supervisorId);
            shift.setShiftDate(shiftDate);
            shift.setShiftType(ShiftType.valueOf(shiftTypeInput.toUpperCase()));

            session.save(shift);
            tx.commit();
            System.out.println("✅ Shift log created successfully.");
        }

        session.close();
        sc.close();
    }
}