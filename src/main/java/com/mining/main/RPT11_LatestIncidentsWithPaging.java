package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.entity.SafetyIncident;
import com.mining.util.HibernateUtil;

public class RPT11_LatestIncidentsWithPaging {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT11 : Latest Incidents With Paging ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();

        System.out.print("Enter Page Number (starting from 1): ");
        int pageNo = sc.nextInt();

        System.out.print("Enter Page Size: ");
        int pageSize = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations)
         *
         * from SafetyIncident s
         * where s.mineId = :mineId
         * order by s.incidentDate desc
         */

        String hql = """
            from SafetyIncident s
            where s.mineId = :mineId
            order by s.incidentDate desc
        """;

        Query<SafetyIncident> query =
                session.createQuery(hql, SafetyIncident.class);

        query.setParameter("mineId", mineId);

        // 🔥 Pagination logic
        int firstResult = (pageNo - 1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        List<SafetyIncident> incidents = query.list();

        System.out.println("\n--- Latest Incidents (Page " + pageNo + ") ---");
        System.out.println("ID | Date | Type | Severity | Cost | Status");

        for (SafetyIncident s : incidents) {
            System.out.println(
                s.getIncidentId() + " | " +
                s.getIncidentDate() + " | " +
                s.getType() + " | " +
                s.getSeverity() + " | " +
                s.getCost() + " | " +
                s.getStatus()
            );
        }

        session.close();
        sc.close();
    }
}