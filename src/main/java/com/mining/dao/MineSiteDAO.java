package com.mining.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mining.entity.MineSite;
import com.mining.util.HibernateUtil;

public class MineSiteDAO {

    public void save(MineSite mineSite) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(mineSite);

        tx.commit();
        session.close();
    }

    public MineSite getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        MineSite ms = session.get(MineSite.class, id);
        session.close();
        return ms;
    }

    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        MineSite ms = session.get(MineSite.class, id);
        if (ms != null) {
            session.delete(ms);
        }

        tx.commit();
        session.close();
    }
}