package com.mining.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mining.entity.ShiftLog;
import com.mining.util.HibernateUtil;

public class ShiftLogDAO {

    public void save(ShiftLog shiftLog) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(shiftLog);

        tx.commit();
        session.close();
    }

    public ShiftLog getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ShiftLog sl = session.get(ShiftLog.class, id);
        session.close();
        return sl;
    }
}