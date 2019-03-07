package com.gitee.taven;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * MyBatis中批量处理的操作类
 * 目前只实现了批量插入
 *
 * 由于使用XML来拼接SQL的效率太差
 * 所以使用Java API的方式来解决这个问题
 *
 * @author <a href="mailto:puras@mooko.net">Puras.He</a>
 * @version $Revision 1.0 $ 2013年8月21日 上午9:56:58
 */
public class MyBatisBatchSupport {

    /**
     * 批量插入
     *
     * @param sqlSessionFactory
     * @param statement 在Mapper文件中定义的namespce加上Mapper中定义的标识符
     * @param objList 要入库的数据列表
     */
    public static void batchInsert(SqlSessionFactory sqlSessionFactory, String statement, List<?> objList) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);

        try {
            for (Object obj : objList) {
                session.insert(statement, obj);
            }
            session.flushStatements();
            session.commit();
            session.clearCache();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
    }

    /**
     * 批量插入
     * @param sqlSessionFactory
     * @param mapperClass 要使用的Mapper的Class
     * @param pojoClass 列表中POJO对象的Class
     * @param methodName 要执行的Mapper类中的方法名
     * @param objList 要入库的数据列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> void batchInsertByMapper(SqlSessionFactory sqlSessionFactory, Class<?> mapperClass, Class pojoClass, String methodName, List<?> objList) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);

        T mapper = (T) session.getMapper(mapperClass);
        try {
            Class[] paramTypes = new Class[1];
            paramTypes[0] = Class.forName(pojoClass.getName());
            Method method = mapperClass.getMethod(methodName, paramTypes);
            for (Object obj : objList) {
                method.invoke(mapper, obj);
            }
            session.flushStatements();
            session.commit();
            session.clearCache();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.rollback();
            throw new RuntimeException(ex.getMessage());

        } finally {
            session.close();
        }
    }
}
