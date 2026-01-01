package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT8_EquipmentBreakdownLeaderboard {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT8 : Equipment Breakdown Leaderboard ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();

        System.out.print("Enter Top N (number of equipments): ");
        int topN = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (No Associations)
         *
         * select u.equipmentId, count(u.usageId)
         * from EquipmentUsage u
         * where u.mineId = :mineId and u.breakdown = 'Y'
         * group by u.equipmentId
         * order by count(u.usageId) desc
         */

        String hql = """
            select u.equipmentId, count(u.usageId)
            from EquipmentUsage u
            where u.mineId = :mineId
              and u.breakdown = 'Y'
            group by u.equipmentId
            order by count(u.usageId) desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("mineId", mineId);
        query.setMaxResults(topN);   // 🔥 Top N filter

        List<Object[]> results = query.list();

        System.out.println("\n--- Equipment Breakdown Leaderboard ---");
        System.out.println("Equipment ID | Breakdown Count");

        for (Object[] row : results) {
            Integer equipmentId = (Integer) row[0];
            Long breakdownCount = (Long) row[1];

            System.out.println(equipmentId + "          | " + breakdownCount);
        }

        session.close();
        sc.close();
    }
}