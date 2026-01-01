package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT6_OpenIncidentsQueue {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT6 : Open Incidents Queue ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations)
         *
         * from SafetyIncident s
         * where s.mineId = :mineId and s.status = 'OPEN'
         * order by s.incidentDate desc
         */

        String hql = """
            from SafetyIncident s
            where s.mineId = :mineId
              and s.status = 'OPEN'
            order by s.incidentDate desc
        """;

        Query<?> query = session.createQuery(hql);
        query.setParameter("mineId", mineId);

        List<?> incidents = query.list();

        System.out.println("\n--- Open Incidents Queue ---");
        System.out.println("IncidentID | Date | Type | Severity | Cost | Status");

        incidents.forEach(obj -> {
            var s = (com.mining.entity.SafetyIncident) obj;
            System.out.println(
                s.getIncidentId() + " | " +
                s.getIncidentDate() + " | " +
                s.getType() + " | " +
                s.getSeverity() + " | " +
                s.getCost() + " | " +
                s.getStatus()
            );
        });

        session.close();
        sc.close();
    }
}