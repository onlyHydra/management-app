package concurs.repo;

import concurs.model.Concurs;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.List;


public class RepoConcursHibernate implements IRepoConcurs {

    HibernateUtils utils;
    public RepoConcursHibernate(HibernateUtils utils){
        this.utils = utils;
    }

    public void save(Concurs concurs) {
        SessionFactory sessionFactory = utils.getInstance();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(concurs);
            session.getTransaction().commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public int size() {
        SessionFactory sessionFactory = utils.getInstance();
        Integer nr = 0;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Query query = session.createQuery("select count (*) from Concurs");
            Long count = (Long) query.uniqueResult();
            System.out.println("Rez=" + count);
            nr = Integer.parseInt(count.toString());
            session.getTransaction().commit();
        }
        return nr;
    }

    @Override
    public void update(Integer id, Concurs entity) {
        SessionFactory sessionFactory = utils.getInstance();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Concurs concurs = (Concurs) session.load(Concurs.class, id);
            concurs.setNumar(entity.getNumar());
            session.getTransaction().commit();
        }
    }

    @Override
    public Iterable<Concurs> findByCap(Integer cap) {
        List<Concurs> rez = new ArrayList<>();
        SessionFactory sessionFactory = utils.getInstance();
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction();
            Query query = session.createQuery("from  Concurs WHERE capacitate = :myCap");
            query.setParameter("myCap", cap);
            List result = query.list();
            rez = (List<Concurs>)result;
        }
        return rez;
    }

    @Override
    public Concurs findOne(Integer integer) {
        List<Concurs> rez = new ArrayList<>();
        SessionFactory sessionFactory = utils.getInstance();
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction();
            Query query = session.createQuery("from  Concurs WHERE id = :myId");
            query.setParameter("myId", integer);
            List result = query.list();
            rez = (List<Concurs>)result;
        }
        if (rez.size() > 0)
            return rez.get(0);
        else return null;
    }

    @Override
    public Iterable<Concurs> findAll() {
        List<Concurs> rez = new ArrayList<>();
        SessionFactory sessionFactory = utils.getInstance();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("from Concurs ").list();
            rez = (List<Concurs>) result;
        }
        return rez;
    }
}
