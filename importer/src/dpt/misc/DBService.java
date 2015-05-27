package dpt.misc;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBService {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("PDTIMPORTERPU", Constants.getInstance().getDbInfo());
    private static final Logger logger = Logger.getLogger(DBService.class.getName());

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static <T> void insertOne(T object) throws DBException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException("I can not insert " + object.toString());
        } finally {
            DBService.returnEntityManager(em);
        }
    }

    public static <T> void insertMany(Collection<T> objects) throws DBException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            for (T item : objects) {
                em.persist(item);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException("I can not insert " + objects.toString());
        } finally {
            DBService.returnEntityManager(em);
        }
    }

    public static void returnEntityManager(EntityManager em) {
        if (em == null) {
            return;
        }
        if (em.getTransaction().isActive()) {
            if (!em.getTransaction().getRollbackOnly()) {
                em.getTransaction().commit();
            }
        }
        try {
            if (em.isOpen()) {
                em.close();
            }
        } catch (Throwable t) {
            logger.error("EntityManagers error", t);
        }
    }

    public static <T> List<T> nativeQuery(QueryBuilder query, Class<T> type) {
        EntityManager em = null;
        List<T> list = new ArrayList<>();
        try {
            // Send query
            em = DBService.getEntityManager();
            Query qpot = em.createNativeQuery(query.getNativeQuery(), type);
            query.setQueryParameters(qpot);
            list = qpot.getResultList();
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error " + type.getCanonicalName(), e);
        } finally {
            DBService.returnEntityManager(em);
        }

        return list;
    }

    public static boolean existRow(QueryBuilder query) {
        EntityManager em = null;
        try {
            // Send query
            em = DBService.getEntityManager();
            Query qpot = em.createNativeQuery(query.getNativeQuery());
            query.setQueryParameters(qpot);
            return !qpot.getResultList().isEmpty();
        } finally {
            DBService.returnEntityManager(em);
        }
    }

    public static <T> T get(String table, String url, Class<T> entityClass, T entity) {
        QueryBuilder sql = new QueryBuilder();
        sql.select("SELECT * FROM " + table + " WHERE url = ?");
        sql.add(url);
        List<T> list = nativeQuery(sql, entityClass);
        if (list.isEmpty()) return entity;
        return list.get(0);
    }

    public static <T> T get(String table, String url, Class<T> entityClass) {
        return get(table, url, entityClass, null);
    }

    public static <T> T getOrCreate(String table, String url, Class<T> entityClass, T entity) {
        T dbEntity = get(table, url, entityClass);
        if (dbEntity == null) {
            try {
                insertOne(entity);
                return entity;
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
        return dbEntity;
    }
}

