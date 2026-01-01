package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT7_TopIncidentCostHotspots {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT7 : Top Incident Cost Hotspots ===");

        System.out.print("Enter Month (1-12): ");
        int month = sc.nextInt();

        System.out.print("Enter Year (YYYY): ");
        int year = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations)
         *
         * select s.mineId, sum(s.cost)
         * from SafetyIncident s
         * where month(s.incidentDate)=:m and year(s.incidentDate)=:y
         * group by s.mineId
         * order by sum(s.cost) desc
         */

        String hql = """
            select s.mineId, sum(s.cost)
            from SafetyIncident s
            where month(s.incidentDate) = :m
              and year(s.incidentDate) = :y
            group by s.mineId
            order by sum(s.cost) desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("m", month);
        query.setParameter("y", year);

        List<Object[]> results = query.list();

        System.out.println("\n--- Top Incident Cost Hotspots ---");
        System.out.println("Mine ID | Total Incident Cost");

        for (Object[] row : results) {
            Integer mineId = (Integer) row[0];
            Double totalCost = (Double) row[1];

            System.out.println(mineId + "      | " + totalCost);
        }

        session.close();
        sc.close();
    }
}