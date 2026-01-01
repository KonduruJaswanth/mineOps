package com.mining.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.MineSite;
import com.mining.entity.MineSite.Status;
import com.mining.util.HibernateUtil;

public class UC1_RegisterMineSite {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC1 : Register Mine Site ===");

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Mine Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Location: ");
        String location = sc.nextLine();

        System.out.print("Enter Status (ACTIVE / INACTIVE): ");
        String statusInput = sc.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Validate mineId uniqueness
        MineSite existingMine = session.get(MineSite.class, mineId);

        if (existingMine != null) {
            System.out.println("❌ Mine already exists. Registration failed.");
            tx.rollback();
        } else {
            MineSite mine = new MineSite();
            mine.setMineId(mineId);
            mine.setName(name);
            mine.setLocation(location);
            mine.setStatus(Status.valueOf(statusInput.toUpperCase()));

            session.save(mine);
            tx.commit();
            System.out.println("✅ Mine registered successfully.");
        }

        session.close();
        sc.close();
    }
}