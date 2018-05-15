//package com.cxy.dao.basedao;
//
////import com.wondertek.mobilevideo.core.util.StringUtil;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.Reader;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.sql.Clob;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Properties;
//import java.util.Set;
//import oracle.sql.CLOB;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.hibernate.Criteria;
//import org.hibernate.Hibernate;
//import org.hibernate.Query;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.CriteriaSpecification;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projection;
//import org.hibernate.criterion.Projections;
//import org.hibernate.engine.SessionFactoryImplementor;
//import org.hibernate.impl.CriteriaImpl;
//import org.hibernate.metadata.ClassMetadata;
//import org.hibernate.transform.Transformers;
//import org.springframework.context.ApplicationContext;
//import org.springframework.orm.hibernate5.HibernateTemplate;
//import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
//
//public class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport
//        implements GenericDao<T, PK>
//{
//    protected final Log log = LogFactory.getLog(getClass());
//    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
//    protected Class<T> persistentClass;
//    private Boolean isOracle = null;
//
//    public GenericDaoHibernate(Class<T> persistentClass)
//    {
//        this.persistentClass = persistentClass;
//    }
//
//    public List<T> getAll()
//    {
//        return super.getHibernateTemplate().loadAll(this.persistentClass);
//    }
//
//    public long getAllCount()
//    {
//        Criteria criteria = getSession().createCriteria(this.persistentClass);
//        Object count = criteria.setProjection(Projections.rowCount()).uniqueResult();
//
//        if ((count instanceof Integer)) {
//            return ((Integer)count).longValue();
//        }
//        return ((Long)count).longValue();
//    }
//
//    public List<T> getAllPage(int start, int limit)
//    {
//        Criteria criteria = getSession().createCriteria(this.persistentClass);
//        CriteriaImpl impl = (CriteriaImpl)criteria;
//        Projection projection = impl.getProjection();
//        criteria.setProjection(projection);
//        if (projection == null) {
//            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//        }
//        if (start > 0) {
//            criteria.setFirstResult(start);
//        }
//        if (limit > 0) {
//            criteria.setMaxResults(limit);
//        }
//        return criteria.list();
//    }
//
//    public PageList getAllPage(PageInfo pageinfo)
//    {
//        Criteria criteria = getSession().createCriteria(this.persistentClass);
//        CriteriaImpl impl = (CriteriaImpl)criteria;
//        Projection projection = impl.getProjection();
//
//        int fullListSize = ((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
//
//        pageinfo.setFullListSize(fullListSize);
//
//        criteria.setProjection(projection);
//        if (projection == null) {
//            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//        }
//
//        if (pageinfo.getFirstRow() > 0) {
//            criteria.setFirstResult(pageinfo.getFirstRow());
//        }
//        if (pageinfo.getObjectsPerPage() > 0) {
//            criteria.setMaxResults(pageinfo.getObjectsPerPage());
//        }
//
//        if ((pageinfo.getSortCriterion() != null) && (!pageinfo.getSortCriterion().equals("")) && (pageinfo.getSortDirection() != 0))
//        {
//            if (pageinfo.getSortDirection() == 2)
//                criteria.addOrder(Order.asc(pageinfo.getSortCriterion()));
//            else {
//                criteria.addOrder(Order.desc(pageinfo.getSortCriterion()));
//            }
//        }
//
//        return new PageList(criteria.list(), pageinfo);
//    }
//
//    public PageList getAllPageByHql(PageInfo pageinfo, String whereHql)
//    {
//        String hql = String.format("from %s %s", new Object[] { this.persistentClass.getName(), whereHql });
//
//        return query(hql, null, pageinfo);
//    }
//
//    public List<T> getAllDistinct()
//    {
//        Collection result = new LinkedHashSet(getAll());
//        return new ArrayList(result);
//    }
//
//    public T get(PK id)
//    {
//        Object entity = super.getHibernateTemplate().get(this.persistentClass, id);
//
//        Hibernate.initialize(entity);
//        return entity;
//    }
//
//    public boolean exists(PK id)
//    {
//        Object entity = null;
//        try {
//            entity = super.getHibernateTemplate().get(this.persistentClass, id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return entity != null;
//    }
//
//    public T save(T object)
//    {
//        return super.getHibernateTemplate().merge(object);
//    }
//
//    public void saveOrUpdate(T object)
//    {
//        super.getHibernateTemplate().saveOrUpdate(object);
//    }
//
//    public void saveOrUpdateAll(List<T> objects)
//    {
//        super.getHibernateTemplate().saveOrUpdateAll(objects);
//    }
//
//    @Deprecated
//    public void remove(PK id)
//    {
//        super.getHibernateTemplate().delete(get(id));
//        super.getHibernateTemplate().flush();
//    }
//
//    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams)
//    {
//        String[] params = new String[queryParams.size()];
//        Object[] values = new Object[queryParams.size()];
//        int index = 0;
//        Iterator i = queryParams.keySet().iterator();
//        while (i.hasNext()) {
//            String key = (String)i.next();
//            params[index] = key;
//            values[(index++)] = queryParams.get(key);
//        }
//        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
//    }
//
//    public long count(String hql)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//
//        Object obj = query.uniqueResult();
//        if ((obj != null) && (!"".equals(obj))) {
//            Long ret = Long.valueOf(((Long)query.uniqueResult()).longValue());
//            releaseSession(session);
//            return ret.longValue();
//        }
//        return 0L;
//    }
//
//    public long count(String hql, Object param)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        query.setParameter(0, param);
//
//        Object obj = query.uniqueResult();
//        if ((obj != null) && (!"".equals(obj))) {
//            Long ret = Long.valueOf(((Long)query.uniqueResult()).longValue());
//            releaseSession(session);
//            return ret.longValue();
//        }
//        return 0L;
//    }
//
//    public long count(String hql, Object[] parms)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        if (parms != null) {
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//
//        }
//
//        Object obj = query.uniqueResult();
//        if ((obj != null) && (!"".equals(obj))) {
//            Long ret = Long.valueOf(((Long)query.uniqueResult()).longValue());
//            releaseSession(session);
//            return ret.longValue();
//        }
//        return 0L;
//    }
//
//    public long count(String hql, Object[] parms, Map<String, List<Long>> parmList)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        if (parms != null) {
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//        }
//        if ((parmList != null) && (parmList.size() > 0)) {
//            Iterator it = parmList.keySet().iterator();
//            while (it.hasNext()) {
//                String columnName = (String)it.next();
//                query.setParameterList(columnName, (Collection)parmList.get(columnName));
//            }
//
//        }
//
//        Object obj = query.uniqueResult();
//        if ((obj != null) && (!"".equals(obj))) {
//            Long ret = Long.valueOf(((Long)query.uniqueResult()).longValue());
//            releaseSession(session);
//            return ret.longValue();
//        }
//        return 0L;
//    }
//
//    public List query(String hql)
//    {
//        return getHibernateTemplate().find(hql);
//    }
//
//    public List query(String hql, int start, int limit)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        query.setFirstResult(start > 0 ? start : 0);
//        query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//        List ls = query.list();
//        releaseSession(session);
//        return ls;
//    }
//
//    public List querySql(String sql, int start, int limit, Class clazz)
//    {
//        Session session = getSession();
//        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz));
//
//        query.setFirstResult(start > 0 ? start : 0);
//        query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//        List ls = query.list();
//        releaseSession(session);
//        return ls;
//    }
//
//    public List query(String hql, Object[] parms)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        if (parms != null) {
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//        }
//        List ls = query.list();
//        releaseSession(session);
//        return ls;
//    }
//
//    public List query(String hql, Object[] parms, int start, int limit)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        if (parms != null) {
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//        }
//        query.setFirstResult(start > 0 ? start : 0);
//        query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//        List ls = query.list();
//        releaseSession(session);
//        return ls;
//    }
//
//    public List query(String hql, Object[] parms, Map<String, List<Long>> parmList, int start, int limit)
//    {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        if (parms != null) {
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//        }
//        if ((parmList != null) && (parmList.size() > 0)) {
//            Iterator it = parmList.keySet().iterator();
//            while (it.hasNext()) {
//                String columnName = (String)it.next();
//                query.setParameterList(columnName, (Collection)parmList.get(columnName));
//            }
//        }
//        query.setFirstResult(start > 0 ? start : 0);
//        query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//        List ls = query.list();
//        releaseSession(session);
//        return ls;
//    }
//
//    public PageList getAllPageByHql(PageInfo pageinfo, String whereHql, Object[] params)
//    {
//        String hql = String.format("from %s %s", new Object[] { this.persistentClass.getName(), whereHql });
//
//        return query(hql, params, pageinfo);
//    }
//
//    public PageList query(String hql, Object[] parms, PageInfo pageinfo) {
//        Session session = getSession();
//        Query query = session.createQuery(hql);
//        if (parms != null) {
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//        }
//        if (pageinfo != null) {
//            if (pageinfo.getFirstRow() > 0) {
//                query.setFirstResult(pageinfo.getFirstRow());
//            }
//            if (pageinfo.getObjectsPerPage() > 0) {
//                query.setMaxResults(pageinfo.getObjectsPerPage());
//            }
//        }
//
//        PageList pl = new PageList(query.list(), pageinfo);
//        releaseSession(session);
//        return pl;
//    }
//
//    public List querySql(String sql)
//    {
//        Session session = getSession();
//        Query query = session.createSQLQuery(sql);
//        List ls = query.list();
//        releaseSession(session);
//        return ls;
//    }
//
//    public List querySql(String sql, int start, int limit)
//    {
//        Session session = getSession();
//        Query query = session.createSQLQuery(sql);
//        query.setFirstResult(start > 0 ? start : 0);
//        query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//        List ls = query.list();
//        releaseSession(session);
//        return ls;
//    }
//
//    public boolean executeSql(String sql)
//    {
//        Session session = getSession();
//        Query query = session.createSQLQuery(sql);
//        boolean ret = query.executeUpdate() > 0;
//        releaseSession(session);
//        return ret;
//    }
//
//    public Long countSql(String sql)
//    {
//        Session session = getSession();
//        Query query = session.createSQLQuery(sql);
//        Object un = query.uniqueResult();
//        Long ret = Long.valueOf(0L);
//        if ((un instanceof BigDecimal))
//            ret = Long.valueOf(((BigDecimal)un).longValue());
//        else if ((un instanceof BigInteger)) {
//            ret = Long.valueOf(((BigInteger)un).longValue());
//        }
//        releaseSession(session);
//        return ret;
//    }
//
//    public Long countSql(String sql, Object[] parms)
//    {
//        Session session = getSession();
//        Query query = session.createSQLQuery(sql);
//        for (int i = 0; i < parms.length; i++) {
//            query.setParameter(i, parms[i]);
//        }
//        Long ret = Long.valueOf(0L);
//        Object un = query.uniqueResult();
//        if ((un instanceof BigDecimal))
//            ret = Long.valueOf(((BigDecimal)un).longValue());
//        else if ((un instanceof BigInteger)) {
//            ret = Long.valueOf(((BigInteger)un).longValue());
//        }
//        releaseSession(session);
//        return ret;
//    }
//
//    private void convertMap(Map<String, Object> map)
//    {
//        for (Map.Entry entry : map.entrySet()) {
//            String key = (String)entry.getKey();
//            Object val = entry.getValue();
//            if ((val instanceof Clob)) {
//                try {
//                    Reader reader = ((Clob)val).getCharacterStream();
//                    if (reader != null) {
//                        StringBuffer sb = new StringBuffer();
//                        char[] charbuf = new char[4096];
//                        for (int i = reader.read(charbuf); i > 0; i = reader.read(charbuf))
//                        {
//                            sb.append(charbuf, 0, i);
//                        }
//                        map.put(key, sb.toString());
//                    }
//                } catch (Exception e) {
//                    this.log.error(e.getMessage());
//                }
//            } else if ((val instanceof Timestamp)) {
//                Timestamp time = (Timestamp)val;
//                map.put(key, new Date(time.getTime()));
//            }
//            else if ((val instanceof BigDecimal)) {
//                map.put(key, Long.valueOf(((BigDecimal)val).longValue()));
//            } else if ((val instanceof BigInteger)) {
//                map.put((String)entry.getKey(), Long.valueOf(((BigInteger)val).longValue()));
//            }
//        }
//    }
//
//    public Map<String, Object> querySql2Map(String sql)
//    {
//        Session session = getSession();
//        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//
//        List ls = query.list();
//        Map map = (ls != null) && (ls.size() > 0) ? (Map)ls.get(0) : null;
//
//        if (map != null) {
//            convertMap(map);
//        }
//        releaseSession(session);
//        return map;
//    }
//
//    public Map<String, Object> querySql2Map(String sql, Object[] parms) {
//        Session session = getSession();
//        Map map;
//        try {
//            Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//            List ls = query.list();
//            map = (ls != null) && (ls.size() > 0) ? (Map)ls.get(0) : null;
//
//            if (map != null)
//                convertMap(map);
//        }
//        finally {
//            releaseSession(session);
//        }
//        return map;
//    }
//    public List<Map<String, Object>> querySql2Listmap(String sql) {
//        Session session = getSession();
//        List maps;
//        try {
//            Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//
//            maps = query.list();
//            for (Map map : maps)
//                convertMap(map);
//        }
//        finally {
//            releaseSession(session);
//        }
//        return maps;
//    }
//    public List<Map<String, Object>> querySql2Listmap(String sql, Object[] parms) {
//        Session session = getSession();
//        List maps;
//        try {
//            Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//            maps = query.list();
//            for (Map map : maps)
//                convertMap(map);
//        }
//        finally {
//            releaseSession(session);
//        }
//        releaseSession(session);
//        return maps;
//    }
//    public List<Map<String, Object>> querySql2Listmap(String sql, Object[] parms, int start, int limit) {
//        Session session = getSession();
//        List maps;
//        try {
//            Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//            query.setFirstResult(start > 0 ? start : 0);
//            query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//            maps = query.list();
//            for (Map map : maps)
//                convertMap(map);
//        }
//        finally {
//            releaseSession(session);
//        }
//        return maps;
//    }
//    public List<Map<String, Object>> querySql2Listmap(Query query) {
//        Session session = getSession();
//        List maps;
//        try {
//            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//
//            maps = query.list();
//            for (Map map : maps)
//                convertMap(map);
//        }
//        finally {
//            releaseSession(session);
//        }
//        return maps;
//    }
//    public List querySql(String sql, Object[] parms) {
//        Session session = getSession();
//        List ls;
//        try {
//            Query query = session.createSQLQuery(sql);
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//            ls = query.list();
//        } finally {
//            releaseSession(session);
//        }
//        return ls;
//    }
//    public List querySql(String sql, Object[] parms, int start, int limit) { Session session = getSession();
//        List ls;
//        try {
//            Query query = session.createSQLQuery(sql);
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//            query.setFirstResult(start > 0 ? start : 0);
//            query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//            ls = query.list();
//        } finally {
//            releaseSession(session);
//        }
//        return ls;
//    }
//
//    public boolean executeSql(String sql, Object[] parms)
//    {
//        Session session = getSession();
//        boolean ret = false;
//        try {
//            Query query = session.createSQLQuery(sql);
//            for (int i = 0; i < parms.length; i++) {
//                query.setParameter(i, parms[i]);
//            }
//            ret = query.executeUpdate() > 0;
//        } finally {
//            releaseSession(session);
//        }
//        return ret;
//    }
//
//    public void update(T object)
//    {
//        super.getHibernateTemplate().update(object);
//    }
//
//    public void publishUpdate(T object)
//    {
//        super.getHibernateTemplate().update(object);
//    }
//
//    public void execute(String hql)
//    {
//        Session session = getSession();
//        try {
//            session.createQuery(hql).executeUpdate();
//            session.flush();
//        } finally {
//            releaseSession(session);
//        }
//    }
//
//    public void execute(String hql, Object[] parms)
//    {
//        Session session = getSession();
//        try {
//            Query query = session.createQuery(hql);
//            if ((parms != null) && (parms.length > 0)) {
//                for (int i = 0; i < parms.length; i++)
//                    query.setParameter(i, parms[i]);
//            }
//            query.executeUpdate();
//        } finally {
//            releaseSession(session);
//        }
//    }
//
//    public void excuteHqlBatch(String sessionFactoryName, List<String> hqls, List<Object[]> paramList, int commitPerCount)
//    {
//        if ((null == hqls) || (hqls.size() < 1))
//            return;
//        SessionFactory sf = (SessionFactory)Constants.ctx.getBean(sessionFactoryName);
//
//        Session session = null;
//        try {
//            session = sf.openSession();
//            Transaction tx = session.beginTransaction();
//            int commitInCount = commitPerCount;
//            if ((commitInCount == 0) || (commitInCount > 1000))
//                commitInCount = 100;
//            for (int i = 0; i < hqls.size(); i++) {
//                String hqlStr = (String)hqls.get(i);
//                Query query = session.createQuery(hqlStr);
//
//                if (paramList.size() > i) {
//                    Object[] params = (Object[])paramList.get(i);
//                    if ((params != null) && (params.length > 0)) {
//                        for (int j = 0; j < params.length; j++) {
//                            query.setParameter(j, params[j]);
//                        }
//                        query.executeUpdate();
//                    }
//                }
//                if (i % commitInCount == 0) {
//                    session.flush();
//                    session.clear();
//                }
//            }
//            tx.commit();
//            releaseSession(session);
//        } catch (Exception e) {
//            this.log.error(e.getMessage() + "\n" + e.getStackTrace());
//        }
//    }
//
//    public List<T> batchInsert(List<T> modelList, int commitPerCount)
//    {
//        List tModelLst = new ArrayList();
//        if ((null == modelList) || (modelList.size() < 1))
//            return null;
//        int i = 0;
//        int commitInCount = commitPerCount;
//        if ((commitInCount == 0) || (commitInCount > 1000))
//            commitInCount = 100;
//        Session session = getSessionFactory().openSession();
//        try {
//            Transaction tx = session.beginTransaction();
//            for (Iterator i$ = modelList.iterator(); i$.hasNext(); ) { Object model = i$.next();
//                model = save(model);
//                tModelLst.add(model);
//                i++;
//                if (i % commitInCount == 0) {
//                    session.flush();
//                    session.clear();
//                }
//            }
//            session.flush();
//            session.clear();
//            tx.commit();
//        } finally {
//            releaseSession(session);
//        }
//        return tModelLst;
//    }
//
//    public List<T> batchInsert(String sessionFactoryName, List<T> modelList, int commitPerCount)
//    {
//        List tModelLst = new ArrayList();
//        if ((null == modelList) || (modelList.size() < 1))
//            return null;
//        int i = 0;
//        int commitInCount = commitPerCount;
//        if ((commitInCount == 0) || (commitInCount > 1000))
//            commitInCount = 100;
//        SessionFactory sf = (SessionFactory)Constants.ctx.getBean(sessionFactoryName);
//        Session session = null;
//        try {
//            session = sf.openSession();
//            Transaction tx = session.beginTransaction();
//            for (Iterator i$ = modelList.iterator(); i$.hasNext(); ) { Object model = i$.next();
//                model = save(model);
//                tModelLst.add(model);
//                i++;
//                if (i % commitInCount == 0) {
//                    session.flush();
//                    session.clear();
//                }
//            }
//            session.flush();
//            session.clear();
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            this.log.error(e.getMessage());
//        } finally {
//            releaseSession(session);
//        }
//        return tModelLst;
//    }
//
//    public void flush()
//    {
//        Session session = getSession(true);
//        if (session != null)
//            session.flush();
//    }
//
//    public void deleteAll(List<T> list)
//    {
//        getHibernateTemplate().deleteAll(list);
//        super.getHibernateTemplate().flush();
//    }
//
//    public void updateClob(String tablename, String idname, Long id, String filedname, String fieldvalue)
//    {
//        Session session = getSession();
//        Connection conn = session.connection();
//        PreparedStatement pStatement = null;
//        ResultSet rs = null;
//        BufferedWriter out = null;
//        try {
//            if (isOracle()) {
//                String sql = "select %s from %s where %s=? for update";
//                pStatement = conn.prepareStatement(String.format(sql, new Object[] { filedname, tablename, idname }));
//
//                pStatement.setLong(1, id.longValue());
//                rs = pStatement.executeQuery();
//                if (rs.next()) {
//                    CLOB clob = (CLOB)rs.getClob(filedname);
//
//                    out = new BufferedWriter(clob.getCharacterOutputStream());
//                    out.append(fieldvalue);
//                    out.flush();
//                    out.close();
//                    out = null;
//                    conn.commit();
//                }
//            }
//            else
//            {
//                String sql = "update %s set %s = ? where %s=?";
//                pStatement = conn.prepareStatement(String.format(sql, new Object[] { tablename, filedname, idname }));
//
//                pStatement.setObject(1, fieldvalue);
//                pStatement.setLong(2, id.longValue());
//                pStatement.executeUpdate();
//                conn.commit();
//            }
//        } catch (Exception e) {
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                this.log.error(e1.getStackTrace());
//            }
//        } finally {
//            if (out != null) {
//                try {
//                    out.close();
//                    out = null;
//                } catch (IOException e) {
//                    this.log.error(e.getStackTrace());
//                }
//            }
//            if (rs != null) {
//                try {
//                    rs.close();
//                    rs = null;
//                } catch (SQLException e) {
//                    this.log.error(e.getStackTrace());
//                }
//            }
//            if (pStatement != null) {
//                try {
//                    pStatement.close();
//                    pStatement = null;
//                } catch (SQLException e) {
//                    this.log.error(e.getStackTrace());
//                }
//            }
//
//            releaseSession(session);
//        }
//    }
//
//    public void updateClob(String tablename, String idname, Long id, Map<String, String> textMap)
//    {
//        Session session = getSession();
//        Connection conn = session.connection();
//        boolean autoCommit = true;
//        PreparedStatement pStatement = null;
//        ResultSet rs = null;
//        BufferedWriter out = null;
//        try {
//            autoCommit = conn.getAutoCommit();
//            conn.setAutoCommit(false);
//
//            if (isOracle()) {
//                String sql = "select %s from " + tablename + " where " + idname + "=? for update";
//
//                String select = "";
//                Iterator it = textMap.keySet().iterator();
//                while (it.hasNext()) {
//                    select = select + (String)it.next();
//                    if (it.hasNext()) {
//                        select = select + ",";
//                    }
//                }
//                pStatement = conn.prepareStatement(String.format(sql, new Object[] { select }));
//                pStatement.setLong(1, id.longValue());
//                rs = pStatement.executeQuery();
//                if (rs.next()) {
//                    Iterator it = textMap.keySet().iterator();
//                    while (it.hasNext()) {
//                        String key = (String)it.next();
//                        CLOB clob = (CLOB)rs.getClob(key);
//
//                        out = new BufferedWriter(clob.getCharacterOutputStream());
//
//                        out.append((CharSequence)textMap.get(key));
//                        out.flush();
//                        out.close();
//                        out = null;
//                    }
//                }
//
//            }
//            else
//            {
//                String sql = "update " + tablename + " set %s where " + idname + "=?";
//
//                String set = "";
//                Object[] arrKey = textMap.keySet().toArray();
//
//                for (Object key : arrKey) {
//                    set = set + key.toString() + " = ? ,";
//                }
//                if (set.endsWith(",")) {
//                    set = set.substring(0, set.length() - 1);
//                }
//
//                pStatement = conn.prepareStatement(String.format(sql, new Object[] { set }));
//                for (int i = 1; i <= arrKey.length; i++) {
//                    pStatement.setObject(i, textMap.get(arrKey[(i - 1)]));
//                }
//                pStatement.setLong(arrKey.length + 1, id.longValue());
//                pStatement.executeUpdate();
//            }
//
//            conn.commit();
//            conn.setAutoCommit(autoCommit);
//        }
//        catch (SQLException e) {
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                this.log.error(e1.getStackTrace());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (out != null) {
//                try {
//                    out.close();
//                    out = null;
//                } catch (IOException e) {
//                    this.log.error(e.getStackTrace());
//                }
//            }
//            if (rs != null) {
//                try {
//                    rs.close();
//                    rs = null;
//                } catch (SQLException e) {
//                    this.log.error(e.getStackTrace());
//                }
//            }
//            if (pStatement != null) {
//                try {
//                    pStatement.close();
//                    pStatement = null;
//                } catch (SQLException e) {
//                    this.log.error(e.getStackTrace());
//                }
//            }
//
//            releaseSession(session);
//        }
//    }
//
//    public boolean isOracle()
//    {
//        if (this.isOracle != null) {
//            return this.isOracle.booleanValue();
//        }
//        Properties props = ((SessionFactoryImplementor)getSessionFactory()).getProperties();
//
//        String dialect = props.getProperty("hibernate.dialect").toUpperCase();
//
//        if (dialect.contains("ORACLE")) {
//            this.isOracle = new Boolean(true);
//            return true;
//        }
//        this.isOracle = new Boolean(false);
//        return false;
//    }
//
//    public List<T> getByIdList(List<PK> idList)
//    {
//        List list = new ArrayList();
//        if ((idList == null) || (idList.size() == 0))
//            return list;
//        Session session = getSession();
//        try {
//            ClassMetadata meta = super.getSessionFactory().getClassMetadata(this.persistentClass);
//
//            String entityName = meta.getEntityName();
//            String pkName = meta.getIdentifierPropertyName();
//            String hql = String.format("from %s where %s in(:idList)", new Object[] { entityName, pkName });
//
//            Query query = session.createQuery(hql);
//            query.setParameterList("idList", idList);
//            list = query.list();
//            session.flush();
//        } finally {
//            releaseSession(session);
//        }
//        return list;
//    }
//
//    public Long countHql(Map<String, Object> paramMap)
//    {
//        Long ret = Long.valueOf(0L);
//        ClassMetadata meta = super.getSessionFactory().getClassMetadata(this.persistentClass);
//
//        String entityName = meta.getEntityName();
//        String pkName = meta.getIdentifierPropertyName();
//        StringBuffer hql = new StringBuffer(String.format("select count(o.%s) from %s o ", new Object[] { pkName, entityName }));
//
//        if ((paramMap != null) && (paramMap.size() > 0)) {
//            hql.append(getHqlParamKey(paramMap, null));
//        }
//
//        Session session = getSession();
//        try {
//            Query query = session.createQuery(hql.toString());
//            query = getHqlParamValue(query, paramMap, null);
//            ret = Long.valueOf(((Long)query.uniqueResult()).longValue());
//        } finally {
//            releaseSession(session);
//        }
//        return ret;
//    }
//
//    public List<T> getHqlPages(Map<String, Object> paramMap, int start, int limit, String sort, String dir)
//    {
//        List list = new ArrayList();
//        ClassMetadata meta = super.getSessionFactory().getClassMetadata(this.persistentClass);
//
//        String entityName = meta.getEntityName();
//        String pkName = meta.getIdentifierPropertyName();
//        StringBuffer hql = new StringBuffer(String.format("from %s o ", new Object[] { entityName }));
//
//        if ((paramMap != null) && (paramMap.size() > 0)) {
//            hql.append(getHqlParamKey(paramMap, null));
//        }
//
//        if (!StringUtil.isNullStr(sort))
//            hql.append(" order by o." + sort + " " + StringUtil.null2Str(dir));
//        else {
//            hql.append(String.format(" order by o.%s desc ", new Object[] { pkName }));
//        }
//
//        Session session = getSession();
//        Query query = session.createQuery(hql.toString());
//        query = getHqlParamValue(query, paramMap, null);
//        query.setFirstResult(start > 0 ? start : 0);
//        query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//        list = query.list();
//        releaseSession(session);
//        return list;
//    }
//
//    public Long countHql(Map<String, Object> paramMap, Map<String, Object> paramOrMap)
//    {
//        Long ret = Long.valueOf(0L);
//        ClassMetadata meta = super.getSessionFactory().getClassMetadata(this.persistentClass);
//
//        String entityName = meta.getEntityName();
//        String pkName = meta.getIdentifierPropertyName();
//        StringBuffer hql = new StringBuffer(String.format("select count(o.%s) from %s o ", new Object[] { pkName, entityName }));
//
//        if (((paramMap != null) && (paramMap.size() > 0)) || ((paramOrMap != null) && (paramOrMap.size() > 0)))
//        {
//            hql.append(getHqlParamKey(paramMap, paramOrMap));
//        }
//
//        Session session = getSession();
//        try {
//            Query query = session.createQuery(hql.toString());
//            query = getHqlParamValue(query, paramMap, paramOrMap);
//            ret = Long.valueOf(((Long)query.uniqueResult()).longValue());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseSession(session);
//        }
//        return ret;
//    }
//
//    public List<T> getHqlPages(Map<String, Object> paramMap, Map<String, Object> paramOrMap, int start, int limit, String sort, String dir)
//    {
//        List list = new ArrayList();
//        ClassMetadata meta = super.getSessionFactory().getClassMetadata(this.persistentClass);
//
//        String entityName = meta.getEntityName();
//        String pkName = meta.getIdentifierPropertyName();
//        StringBuffer hql = new StringBuffer(String.format("from %s o", new Object[] { entityName }));
//
//        if (((paramMap != null) && (paramMap.size() > 0)) || ((paramOrMap != null) && (paramOrMap.size() > 0)))
//        {
//            hql.append(getHqlParamKey(paramMap, paramOrMap));
//        }
//
//        if (!StringUtil.isNullStr(sort))
//            hql.append(" order by o." + sort + " " + StringUtil.null2Str(dir));
//        else {
//            hql.append(String.format(" order by o.%s desc ", new Object[] { pkName }));
//        }
//
//        Session session = getSession();
//        try {
//            Query query = session.createQuery(hql.toString());
//            query = getHqlParamValue(query, paramMap, paramOrMap);
//            query.setFirstResult(start > 0 ? start : 0);
//            query.setMaxResults((limit > 0) && (limit < 2000) ? limit : 2000);
//
//            list = query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseSession(session);
//        }
//        return list;
//    }
//
//    private String getHqlParamKey(Map<String, Object> paramMap, Map<String, Object> paramOrMap)
//    {
//        StringBuffer hqlParamKey = new StringBuffer();
//        Set setParamKey = new HashSet();
//        if ((paramMap != null) && (paramMap.size() > 0)) {
//            Iterator it = paramMap.keySet().iterator();
//            while (it.hasNext()) {
//                String keyStr = (String)it.next();
//                Object object = paramMap.get(keyStr);
//                if (keyStr.indexOf(".") > 0) {
//                    String objName = keyStr.substring(0, keyStr.indexOf("."));
//                    setParamKey.add(String.format("o.%s is not null ", new Object[] { objName }));
//                }
//
//                if ((object instanceof List)) {
//                    hqlParamKey.append(String.format("o.%s in(:%s)", new Object[] { keyStr, keyStr.replace(".", "") }));
//                }
//                else if (((object instanceof String)) && ((object.toString().startsWith("%")) || (object.toString().endsWith("%"))))
//                {
//                    hqlParamKey.append(String.format("o.%s like :%s", new Object[] { keyStr, keyStr.replace(".", "") }));
//                }
//                else if ((object instanceof Date)) {
//                    if (isOracle()) {
//                        hqlParamKey.append("to_char(o." + keyStr + ", 'YYYY-MM-DD HH24:MI:SS') =:" + keyStr.replace(".", ""));
//                    }
//                    else
//                    {
//                        hqlParamKey.append("date_format(o." + keyStr + ", '%Y-%m-%d %H:%i:%s') =:" + keyStr.replace(".", ""));
//                    }
//                }
//                else
//                {
//                    hqlParamKey.append(String.format("o.%s =:%s", new Object[] { keyStr, keyStr.replace(".", "") }));
//                }
//
//                if (it.hasNext())
//                    hqlParamKey.append(" and ");
//            }
//        }
//        if ((paramOrMap != null) && (paramOrMap.size() > 0)) {
//            if ((paramMap != null) && (paramMap.size() > 0))
//                hqlParamKey.append(" and ");
//            StringBuffer hqlOrParamKey = new StringBuffer();
//            Iterator it = paramOrMap.keySet().iterator();
//            while (it.hasNext()) {
//                String keyStr = (String)it.next();
//                Object object = paramOrMap.get(keyStr);
//                if (keyStr.indexOf(".") > 0) {
//                    String objName = keyStr.substring(0, keyStr.indexOf("."));
//                    setParamKey.add(String.format("o.%s is not null ", new Object[] { objName }));
//                }
//
//                if ((object instanceof List)) {
//                    hqlOrParamKey.append(String.format("o.%s in(:%s)", new Object[] { keyStr, keyStr.replace(".", "") }));
//                }
//                else if (((object instanceof String)) && ((object.toString().startsWith("%")) || (object.toString().endsWith("%"))))
//                {
//                    hqlOrParamKey.append(String.format("o.%s like :%s", new Object[] { keyStr, keyStr.replace(".", "") }));
//                }
//                else {
//                    hqlOrParamKey.append(String.format("o.%s =:%s", new Object[] { keyStr, keyStr.replace(".", "") }));
//                }
//
//                if (it.hasNext())
//                    hqlOrParamKey.append(" or ");
//            }
//            hqlParamKey.append(String.format(" (%s)", new Object[] { hqlOrParamKey.toString() }));
//        }
//
//        StringBuffer hqlParam = new StringBuffer();
//        if ((hqlParamKey != null) && (!StringUtil.isNullStr(hqlParamKey.toString())))
//        {
//            hqlParam.append(" where ");
//            Iterator it;
//            if ((setParamKey != null) && (setParamKey.size() > 0)) {
//                for (it = setParamKey.iterator(); it.hasNext(); ) {
//                    hqlParam.append((String)it.next() + " and ");
//                }
//            }
//            hqlParam.append(hqlParamKey.toString());
//        }
//        return hqlParam.toString();
//    }
//
//    private Query getHqlParamValue(Query query, Map<String, Object> paramMap, Map<String, Object> paramOrMap)
//    {
//        if ((paramMap != null) && (paramMap.size() > 0)) {
//            Iterator it = paramMap.keySet().iterator();
//            while (it.hasNext()) {
//                String keyStr = (String)it.next();
//                Object object = paramMap.get(keyStr);
//                if ((object instanceof List)) {
//                    query.setParameterList(keyStr.replace(".", ""), (List)object);
//                }
//                else if ((object instanceof Date)) {
//                    query.setParameter(keyStr.replace(".", ""), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object));
//                }
//                else {
//                    query.setParameter(keyStr.replace(".", ""), object);
//                }
//            }
//        }
//        if ((paramOrMap != null) && (paramOrMap.size() > 0)) {
//            Iterator it = paramOrMap.keySet().iterator();
//            while (it.hasNext()) {
//                String keyStr = (String)it.next();
//                Object object = paramOrMap.get(keyStr);
//                if ((object instanceof List)) {
//                    query.setParameterList(keyStr.replace(".", ""), (List)object);
//                }
//                else {
//                    query.setParameter(keyStr.replace(".", ""), object);
//                }
//            }
//        }
//        return query;
//    }
//}