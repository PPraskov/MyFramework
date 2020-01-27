package core.repository;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import javax.persistence.Table;

public class Repository<E> implements GenericRepository<E> {

    protected E entity;
    private SessionFactory sessionFactory;
    private Class entityClass;

    protected Repository() {

    }

    protected String getTableName() {
        Table table = (Table) this.getEntityClass().getAnnotation(Table.class);
        return table.name();
    }

    protected Class getEntityClass(){
        return this.entityClass;
    }

    void setEntityClass(Class entityClass){
        this.entityClass = entityClass;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public E getSingle(String columnName, String value) {
        System.out.println(this.getClass().getSimpleName());

        Session session = this.sessionFactory.openSession();
        session.setHibernateFlushMode(FlushMode.AUTO);
        Class c = this.getEntityClass();
        try {
            String queryStr = String.format("SELECT * FROM %s AS t " +
                    "WHERE t.%s = :value ", getTableName(), columnName);
            NativeQuery nativeQuery = session.createNativeQuery(queryStr, c).setParameter("value", value);
            session.getTransaction().begin();
            E result = (E) nativeQuery.getSingleResult();
            session.getTransaction().commit();
            return result;
        } catch (HibernateException | ClassCastException e) {
            session.getTransaction().rollback();

            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

//    public List<E> getMultiple(String columnName, String value, Integer count) {
//        this.setSession(this.sessionFactory.openSession());
//        try {
//            this.setTransaction(this.session.beginTransaction());
//            String queryStr = String.format("FROM %s AS e " +
//                    "WHERE e.%s = %s " +
//                    "LIMIT %d", this.entity.getClass().getName(), columnName, value, count);
//            this.transaction.begin();
//            List<E> result = (List<E>) this.session.createNativeQuery(queryStr).getResultList();
//            return result;
//        } catch (HibernateException | ClassCastException e) {
//            if (this.transaction != null) {
//                this.transaction.rollback();
//            }
//            e.printStackTrace();
//            return null;
//        } finally {
//            this.session.close();
//        }
//    }

    public void save(E entity) {
        Session session = this.sessionFactory.openSession();
        session.setHibernateFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        try {
            session.save(entity);
            session.getTransaction().commit();
        } catch (HibernateException | ClassCastException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
