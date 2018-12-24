package interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sunjf on 2018/12/23.
 */
//拦截query查询
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageIntercept implements Interceptor {
    private String databaseType;//数据库类型，不同的数据库有不同的分页方法

    /**
     * 拦截后要执行的方法
     */
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //通过MetaObject元数据取得方法名id：com.XXX.queryMessageListByPage
        String id = mappedStatement.getId();
        //匹配在mybatis中定义的与分页有关的查询id
        if (id.matches(".+ByPage$")) {
            //BoundSql中有原始的sql语句和对应的查询参数
            BoundSql boundSql = statementHandler.getBoundSql();
            Map<String, Object> params = (Map<String, Object>) boundSql.getParameterObject();
            Page page = (Page) params.get("page");
            String sql = boundSql.getSql();
            String countSql = "select count(*)from (" + sql + ")a";
            Connection connection = (Connection) invocation.getArgs()[0];
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            parameterHandler.setParameters(countStatement);
            ResultSet rs = countStatement.executeQuery();
            if (rs.next()) {
                //为什么是getInt（1）? 因为数据表的列是从1开始计数
                page.setTotalNumber(rs.getInt(1));
                System.out.println("拦截器得知page的记录总数为：" + page.getTotalNumber());
            }
            String pageSql = sql + " limit " + page.getDbIndex() + "," + page.getDbNumber();
            System.out.println("sql: " + pageSql);
            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        return invocation.proceed();
    }

    /**
     * @param target 被拦截的对象
     */
    public Object plugin(Object target) {
        // 如果将拦截器类比喻为代购票的公司，那this就是代购业务员（进入方法前是无代理购票能力业务员，进入后成为有代理能力的业务员）
        // 通过注解获取拦截目标的信息，如果不符合拦截要求就返回原目标，如果符合则使用动态代理生成代理对象
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub

    }

}
