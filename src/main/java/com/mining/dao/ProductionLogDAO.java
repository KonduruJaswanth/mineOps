package com.mining.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mining.entity.ProductionLog;
import com.mining.util.HibernateUtil;

public class ProductionLogDAO {

    public void save(ProductionLog pl) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(pl);

        tx.commit();
        session.close();
    }

    public ProductionLog getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProductionLog pl = session.get(ProductionLog.class, id);
        session.close();
        return pl;
    }
}