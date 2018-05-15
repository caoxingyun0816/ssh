//package com.cxy.dao.basedao;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Map;
//import org.hibernate.Query;
//
//@MonitoredWithSpring
//public abstract interface GenericDao<T, PK extends Serializable>
//{
//    public abstract List<T> getAll();
//
//    public abstract List<T> getAllDistinct();
//
//    public abstract List<T> getAllPage(int paramInt1, int paramInt2);
//
//    public abstract PageList getAllPage(PageInfo paramPageInfo);
//
//    public abstract PageList getAllPageByHql(PageInfo paramPageInfo, String paramString);
//
//    public abstract PageList getAllPageByHql(PageInfo paramPageInfo, String paramString, Object[] paramArrayOfObject);
//
//    public abstract long getAllCount();
//
//    public abstract T get(PK paramPK);
//
//    public abstract boolean exists(PK paramPK);
//
//    public abstract T save(T paramT);
//
//    public abstract void saveOrUpdate(T paramT);
//
//    public abstract void saveOrUpdateAll(List<T> paramList);
//
//    public abstract void remove(PK paramPK);
//
//    public abstract void update(T paramT);
//
//    public abstract void publishUpdate(T paramT);
//
//    public abstract List<T> findByNamedQuery(String paramString, Map<String, Object> paramMap);
//
//    public abstract Long countSql(String paramString);
//
//    public abstract Long countSql(String paramString, Object[] paramArrayOfObject);
//
//    public abstract List querySql(String paramString);
//
//    public abstract List querySql(String paramString, Object[] paramArrayOfObject);
//
//    public abstract List querySql(String paramString, int paramInt1, int paramInt2);
//
//    public abstract boolean executeSql(String paramString);
//
//    public abstract boolean executeSql(String paramString, Object[] paramArrayOfObject);
//
//    public abstract Map<String, Object> querySql2Map(String paramString);
//
//    public abstract Map<String, Object> querySql2Map(String paramString, Object[] paramArrayOfObject);
//
//    public abstract List<Map<String, Object>> querySql2Listmap(String paramString);
//
//    public abstract List<Map<String, Object>> querySql2Listmap(String paramString, Object[] paramArrayOfObject);
//
//    public abstract List<Map<String, Object>> querySql2Listmap(Query paramQuery);
//
//    public abstract List<Map<String, Object>> querySql2Listmap(String paramString, Object[] paramArrayOfObject, int paramInt1, int paramInt2);
//
//    public abstract long count(String paramString);
//
//    public abstract long count(String paramString, Object[] paramArrayOfObject);
//
//    public abstract long count(String paramString, Object[] paramArrayOfObject, Map<String, List<Long>> paramMap);
//
//    public abstract List query(String paramString);
//
//    public abstract List query(String paramString, int paramInt1, int paramInt2);
//
//    public abstract List querySql(String paramString, int paramInt1, int paramInt2, Class paramClass);
//
//    public abstract List query(String paramString, Object[] paramArrayOfObject);
//
//    public abstract List query(String paramString, Object[] paramArrayOfObject, int paramInt1, int paramInt2);
//
//    public abstract List query(String paramString, Object[] paramArrayOfObject, Map<String, List<Long>> paramMap, int paramInt1, int paramInt2);
//
//    public abstract void execute(String paramString);
//
//    public abstract void execute(String paramString, Object[] paramArrayOfObject);
//
//    public abstract void excuteHqlBatch(String paramString, List<String> paramList, List<Object[]> paramList1, int paramInt);
//
//    public abstract List<T> batchInsert(List<T> paramList, int paramInt);
//
//    public abstract List<T> batchInsert(String paramString, List<T> paramList, int paramInt);
//
//    public abstract void flush();
//
//    public abstract void deleteAll(List<T> paramList);
//
//    public abstract void updateClob(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4);
//
//    public abstract void updateClob(String paramString1, String paramString2, Long paramLong, Map<String, String> paramMap);
//
//    public abstract boolean isOracle();
//
//    public abstract List<T> getByIdList(List<PK> paramList);
//
//    public abstract Long countHql(Map<String, Object> paramMap);
//
//    public abstract List<T> getHqlPages(Map<String, Object> paramMap, int paramInt1, int paramInt2, String paramString1, String paramString2);
//
//    public abstract Long countHql(Map<String, Object> paramMap1, Map<String, Object> paramMap2);
//
//    public abstract List<T> getHqlPages(Map<String, Object> paramMap1, Map<String, Object> paramMap2, int paramInt1, int paramInt2, String paramString1, String paramString2);
//}