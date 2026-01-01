package com.mining.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mining.entity.SafetyIncident;
import com.mining.util.HibernateUtil;

public class SafetyIncidentDAO {

    public void save(SafetyIncident si) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(si);

        tx.commit();
        session.close();
    }

    public SafetyIncident getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SafetyIncident si = session.get(SafetyIncident.class, id);
        session.close();
        return si;
    }
}