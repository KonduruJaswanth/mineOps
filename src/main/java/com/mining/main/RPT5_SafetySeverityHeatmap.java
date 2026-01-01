package com.mining.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT5_SafetySeverityHeatmap {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT5 : Safety Severity Heatmap ===");

        System.out.print("Enter From Date (YYYY-MM-DD): ");
        String fromDateInput = sc.nextLine();

        System.out.print("Enter To Date (YYYY-MM-DD): ");
        String toDateInput = sc.nextLine();

        LocalDate fromDate = LocalDate.parse(fromDateInput);
        LocalDate toDate = LocalDate.parse(toDateInput);

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations)
         *
         * select s.mineId, s.severity, count(s.incidentId)
         * from SafetyIncident s
         * where s.incidentDate between :from and :to
         * group by s.mineId, s.severity
         * order by s.mineId asc, s.severity desc
         */

        String hql = """
            select s.mineId, s.severity, count(s.incidentId)
            from SafetyIncident s
            where s.incidentDate between :from and :to
            group by s.mineId, s.severity
            order by s.mineId asc, s.severity desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("from", fromDate);
        query.setParameter("to", toDate);

        List<Object[]> results = query.list();

        System.out.println("\n--- Safety Severity Heatmap ---");
        System.out.println("Mine ID | Severity | Incident Count");

        for (Object[] row : results) {
            Integer mineId = (Integer) row[0];
            Integer severity = (Integer) row[1];
            Long count = (Long) row[2];

            System.out.println(mineId + "      | " + severity + "        | " + count);
        }

        session.close();
        sc.close();
    }
}