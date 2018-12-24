package util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SessionFactoryUtil {
    // 创建需要单例的对象实例
    private static SqlSessionFactory sessionFactory;
    private static final Map<String, SqlSessionFactory> sqlSessionFactorys = new HashMap<>();

    // 私有化构造
    private SessionFactoryUtil(){}

    // 对外提供访问接口
    public static synchronized SqlSession getSession(String ds){
        try {
            sessionFactory = sqlSessionFactorys.get(ds);
            if(sessionFactory == null) {
                InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
                // 判断SqlSessionFactory是否为空，如果为空则创建
                if(sessionFactory==null){
                    sessionFactory = new SqlSessionFactoryBuilder().build(stream, ds);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory.openSession();
    }
}
