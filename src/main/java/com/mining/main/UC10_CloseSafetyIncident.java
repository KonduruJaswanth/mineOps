package com.mining.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.SafetyIncident;
import com.mining.entity.SafetyIncident.Status;
import com.mining.util.HibernateUtil;

public class UC10_CloseSafetyIncident {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC10 : Close Safety Incident ===");

        System.out.print("Enter Incident ID: ");
        int incidentId = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // 🔍 Find incident by incidentId
        SafetyIncident incident = session.get(SafetyIncident.class, incidentId);

        if (incident == null) {
            System.out.println("❌ Incident not found.");
            tx.rollback();
        } else if (incident.getStatus() == Status.CLOSED) {
            System.out.println("⚠ Incident is already CLOSED.");
            tx.rollback();
        } else {
            incident.setStatus(Status.CLOSED);
            session.update(incident);

            tx.commit();
            System.out.println("✅ Safety incident closed successfully.");
        }

        session.close();
        sc.close();
    }
}