package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT4_HighVolumeMines {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT4 : High-Volume Mines (Threshold Filter) ===");

        System.out.print("Enter Month (1-12): ");
        int month = sc.nextInt();

        System.out.print("Enter Year (YYYY): ");
        int year = sc.nextInt();

        System.out.print("Enter Minimum Tonnes Threshold: ");
        double minTonnes = sc.nextDouble();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations)
         *
         * select p.mineId, sum(p.tonnes)
         * from ProductionLog p
         * where month(p.logDate)=:m and year(p.logDate)=:y
         * group by p.mineId
         * having sum(p.tonnes) >= :minTonnes
         * order by sum(p.tonnes) desc
         */

        String hql = """
            select p.mineId, sum(p.tonnes)
            from ProductionLog p
            where month(p.logDate) = :m
              and year(p.logDate) = :y
            group by p.mineId
            having sum(p.tonnes) >= :minTonnes
            order by sum(p.tonnes) desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("m", month);
        query.setParameter("y", year);
        query.setParameter("minTonnes", minTonnes);

        List<Object[]> results = query.list();

        System.out.println("\n--- High-Volume Mines ---");
        System.out.println("Mine ID | Total Tonnes");

        for (Object[] row : results) {
            Integer mineId = (Integer) row[0];
            Double totalTonnes = (Double) row[1];

            System.out.println(mineId + "      | " + totalTonnes);
        }

        session.close();
        sc.close();
    }
}