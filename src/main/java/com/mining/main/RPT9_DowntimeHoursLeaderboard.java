package com.mining.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT9_DowntimeHoursLeaderboard {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT9 : Downtime Hours Leaderboard ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();
        sc.nextLine(); // consume newline

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
         * select u.equipmentId, sum(u.downtimeHours)
         * from EquipmentUsage u
         * where u.mineId = :mineId
         *   and u.usageDate between :from and :to
         * group by u.equipmentId
         * order by sum(u.downtimeHours) desc
         */

        String hql = """
            select u.equipmentId, sum(u.downtimeHours)
            from EquipmentUsage u
            where u.mineId = :mineId
              and u.usageDate between :from and :to
            group by u.equipmentId
            order by sum(u.downtimeHours) desc
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("mineId", mineId);
        query.setParameter("from", fromDate);
        query.setParameter("to", toDate);

        List<Object[]> results = query.list();

        System.out.println("\n--- Downtime Hours Leaderboard ---");
        System.out.println("Equipment ID | Total Downtime Hours");

        for (Object[] row : results) {
            Integer equipmentId = (Integer) row[0];
            Double totalDowntime = (Double) row[1];

            System.out.println(equipmentId + "          | " + totalDowntime);
        }

        session.close();
        sc.close();
    }
}