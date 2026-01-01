package com.mining.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mining.entity.EquipmentUsage;
import com.mining.util.HibernateUtil;

public class EquipmentUsageDAO {

    public void save(EquipmentUsage eu) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(eu);

        tx.commit();
        session.close();
    }

    public EquipmentUsage getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EquipmentUsage eu = session.get(EquipmentUsage.class, id);
        session.close();
        return eu;
    }
}