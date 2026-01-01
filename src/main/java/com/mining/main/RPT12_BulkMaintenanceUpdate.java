package com.mining.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.mining.util.HibernateUtil;

public class RPT12_BulkMaintenanceUpdate {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== RPT12 : Bulk Maintenance Update ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        /*
         * HQL UPDATE (No Associations)
         *
         * update Equipment e
         * set e.status = 'MAINTENANCE'
         * where e.mineId = :mineId
         *   and e.status = 'BROKEN'
         */

        String hql = """
            update Equipment 
            set e.status = 'MAINTENANCE'
            where e.mineId = :mineId
              and e.status = 'BROKEN'
        """;

        Query<?> query = session.createQuery(hql);
        query.setParameter("mineId", mineId);

        int updatedCount = query.executeUpdate();

        tx.commit();

        System.out.println("✅ Maintenance Update Completed.");
        System.out.println("Total Equipment Updated: " + updatedCount);

        session.close();
        sc.close();
    }
}