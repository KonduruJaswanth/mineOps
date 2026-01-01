package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT3_GradeQualityLeaderboard {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT3 : Grade Quality Leaderboard ===");

        System.out.print("Enter Month (1-12): ");
        int month = sc.nextInt();

        System.out.print("Enter Year (YYYY): ");
        int year = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations)
         *
         * select p.mineId, avg(p.grade)
         * from ProductionLog p
         * where month(p.logDate)=:m and year(p.logDate)=:y
         * group by p.mineId
         * order by avg(p.grade) desc
         */

        String hql = """
            select p.mineId, avg(p.grade)
            from ProductionLog p
            where month(p.logDate) = :m
              and year(p.logDate) = :y
            group by p.mineId
            order by avg(p.grade) desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("m", month);
        query.setParameter("y", year);

        List<Object[]> results = query.list();

        System.out.println("\n--- Grade Quality Leaderboard ---");
        System.out.println("Mine ID | Avg Grade");

        for (Object[] row : results) {
            Integer mineId = (Integer) row[0];
            Double avgGrade = (Double) row[1];

            System.out.println(mineId + "      | " + avgGrade);
        }

        session.close();
        sc.close();
    }
}