package com.mining.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.MineSite;
import com.mining.entity.MineSite.Status;
import com.mining.util.HibernateUtil;

public class UC2_UpdateMineStatus {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC2 : Update Mine Status ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter New Status (ACTIVE / INACTIVE): ");
        String statusInput = sc.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // 🔍 Search mine by mineId
        MineSite mine = session.get(MineSite.class, mineId);

        if (mine == null) {
            System.out.println("❌ Mine not found. Status update failed.");
            tx.rollback();
        } else {
            mine.setStatus(Status.valueOf(statusInput.toUpperCase()));
            session.update(mine);

            tx.commit();
            System.out.println("✅ Mine status updated successfully.");
        }

        session.close();
        sc.close();
    }
}