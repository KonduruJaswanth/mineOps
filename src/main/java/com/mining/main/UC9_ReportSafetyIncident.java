package com.mining.main;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.Equipment;
import com.mining.entity.MineSite;
import com.mining.entity.SafetyIncident;
import com.mining.entity.SafetyIncident.Status;
import com.mining.entity.Worker;
import com.mining.util.HibernateUtil;

public class UC9_ReportSafetyIncident {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC9 : Report Safety Incident ===");

        System.out.print("Enter Incident ID: ");
        int incidentId = sc.nextInt();

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Incident Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();

        System.out.print("Enter Incident Type: ");
        String type = sc.nextLine();

        System.out.print("Enter Severity (1 to 5): ");
        int severity = sc.nextInt();

        System.out.print("Enter Cost: ");
        double cost = sc.nextDouble();

        System.out.print("Add Equipment ID? (Y / N): ");
        String addEquipment = sc.next();

        Integer equipmentId = null;
        if (addEquipment.equalsIgnoreCase("Y")) {
            System.out.print("Enter Equipment ID: ");
            equipmentId = sc.nextInt();
        }

        System.out.print("Add Worker ID? (Y / N): ");
        String addWorker = sc.next();

        Integer workerId = null;
        if (addWorker.equalsIgnoreCase("Y")) {
            System.out.print("Enter Worker ID: ");
            workerId = sc.nextInt();
        }

        // 🔍 Validate severity range
        if (severity < 1 || severity > 5) {
            System.out.println("❌ Severity must be between 1 and 5.");
            sc.close();
            return;
        }

        LocalDate incidentDate = LocalDate.parse(dateInput);

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

        // 🔍 Validate Equipment (if provided)
        if (equipmentId != null) {
            Equipment eq = session.get(Equipment.class, equipmentId);
            if (eq == null) {
                System.out.println("❌ Invalid Equipment ID.");
                tx.rollback();
                session.close();
                sc.close();
                return;
            }
        }

        // 🔍 Validate Worker (if provided)
        if (workerId != null) {
            Worker worker = session.get(Worker.class, workerId);
            if (worker == null) {
                System.out.println("❌ Invalid Worker ID.");
                tx.rollback();
                session.close();
                sc.close();
                return;
            }
        }

        // 🔍 Validate Incident ID uniqueness
        SafetyIncident existingIncident = session.get(SafetyIncident.class, incidentId);
        if (existingIncident != null) {
            System.out.println("❌ Duplicate Incident ID.");
            tx.rollback();
        } else {
            SafetyIncident incident = new SafetyIncident();
            incident.setIncidentId(incidentId);
            incident.setMineId(mineId);
            incident.setIncidentDate(incidentDate);
            incident.setType(type);
            incident.setSeverity(severity);
            incident.setCost(cost);
            incident.setStatus(Status.OPEN);
            incident.setEquipmentId(equipmentId);
            incident.setWorkerId(workerId);

            session.save(incident);
            tx.commit();
            System.out.println("✅ Safety incident reported successfully (Status: OPEN).");
        }

        session.close();
        sc.close();
    }
}