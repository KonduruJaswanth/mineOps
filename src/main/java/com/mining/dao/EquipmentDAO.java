package com.mining.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mining.entity.Equipment;
import com.mining.util.HibernateUtil;

public class EquipmentDAO {

    public void save(Equipment equipment) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(equipment);

        tx.commit();
        session.close();
    }

    public Equipment getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Equipment eq = session.get(Equipment.class, id);
        session.close();
        return eq;
    }
}