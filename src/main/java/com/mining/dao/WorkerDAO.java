package com.mining.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mining.entity.Worker;
import com.mining.util.HibernateUtil;

public class WorkerDAO {

    public void save(Worker worker) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(worker);

        tx.commit();
        session.close();
    }

    public Worker getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Worker w = session.get(Worker.class, id);
        session.close();
        return w;
    }
}