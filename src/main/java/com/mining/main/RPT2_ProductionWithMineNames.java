package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT2_ProductionWithMineNames {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT2 : Production With Mine Names ===");

        System.out.print("Enter Month (1-12): ");
        int month = sc.nextInt();

        System.out.print("Enter Year (YYYY): ");
        int year = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations – Explicit Join)
         *
         * select m.name, sum(p.tonnes)
         * from MineSite m, ProductionLog p
         * where m.mineId = p.mineId
         *   and month(p.logDate)=:mth
         *   and year(p.logDate)=:yr
         * group by m.name
         * order by sum(p.tonnes) desc
         */

        String hql = """
            select m.name, sum(p.tonnes)
            from MineSite m, ProductionLog p
            where m.mineId = p.mineId
              and month(p.logDate) = :mth
              and year(p.logDate) = :yr
            group by m.name
            order by sum(p.tonnes) desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("mth", month);
        query.setParameter("yr", year);

        List<Object[]> results = query.list();

        System.out.println("\n--- Executive Production Summary ---");
        System.out.println("Mine Name | Total Tonnes");

        for (Object[] row : results) {
            String mineName = (String) row[0];
            Double totalTonnes = (Double) row[1];

            System.out.println(mineName + " | " + totalTonnes);
        }

        session.close();
        sc.close();
    }
}