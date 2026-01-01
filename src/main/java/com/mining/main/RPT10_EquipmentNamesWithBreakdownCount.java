package com.mining.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT10_EquipmentNamesWithBreakdownCount {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT10 : Equipment Names With Breakdown Count ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();

        /*
         * HQL (Manual Join – No Associations)
         *
         * select e.name, count(u.usageId)
         * from Equipment e, EquipmentUsage u
         * where e.equipmentId = u.equipmentId
         *   and e.mineId = :mineId
         *   and u.breakdown = 'Y'
         * group by e.name
         * order by count(u.usageId) desc
         */

        String hql = """
            select e.name, count(u.usageId)
            from Equipment e, EquipmentUsage u
            where e.equipmentId = u.equipmentId
              and e.mineId = :mineId
              and u.breakdown = 'Y'
            group by e.name
            order by count(u.usageId) desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("mineId", mineId);

        List<Object[]> results = query.list();

        System.out.println("\n--- Equipment Breakdown Report ---");
        System.out.println("Equipment Name | Breakdown Count");

        for (Object[] row : results) {
            String equipmentName = (String) row[0];
            Long breakdownCount = (Long) row[1];

            System.out.println(equipmentName + " | " + breakdownCount);
        }

        session.close();
        sc.close();
    }
}