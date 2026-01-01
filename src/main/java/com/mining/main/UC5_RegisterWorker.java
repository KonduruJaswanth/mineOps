package com.mining.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mining.entity.MineSite;
import com.mining.entity.Worker;
import com.mining.util.HibernateUtil;

public class UC5_RegisterWorker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== UC5 : Register Worker ===");

        System.out.print("Enter Worker ID: ");
        int workerId = sc.nextInt();

        System.out.print("Enter Mine ID: ");
        int mineId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Worker Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Role: ");
        String role = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // 🔍 Validate Mine exists
        MineSite mine = session.get(MineSite.class, mineId);
        if (mine == null) {
            System.out.println("❌ Invalid Mine ID. Please create Mine first.");
            tx.rollback();
            session.close();
            sc.close();
            return;
        }

        // 🔍 Validate Worker ID uniqueness
        Worker existingWorker = session.get(Worker.class, workerId);
        if (existingWorker != null) {
            System.out.println("❌ Worker already exists.");
            tx.rollback();
        } else {
            Worker worker = new Worker();
            worker.setWorkerId(workerId);
            worker.setMineId(mineId);
            worker.setName(name);
            worker.setRole(role);
            worker.setPhone(phone);

            session.save(worker);
            tx.commit();
            System.out.println("✅ Worker registered successfully.");
        }

        session.close();
        sc.close();
    }
}