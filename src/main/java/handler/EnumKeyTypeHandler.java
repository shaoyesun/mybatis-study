package handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis枚举类型映射
 */
public class EnumKeyTypeHandler extends BaseTypeHandler<IEnum> {

    private Class<IEnum> type;
    private final IEnum[] enums;
  
    /**
      * 设置配置文件设置的转换类以及枚举类内容，供其他方法更便捷高效的实现
      * @param type 配置文件中设置的转换类
    */
    public EnumKeyTypeHandler(Class<IEnum> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null)
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IEnum sexEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, sexEnum.getValue());
    }

    @Override
    public IEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String val = resultSet.getString(s);
        return locateIEnum(val);
    }

    @Override
    public IEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return locateIEnum(resultSet.getString(i));
    }

    @Override
    public IEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return locateIEnum(callableStatement.getNString(i));
    }

    /**
    * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
    * @param key 数据库中存储的自定义code属性
    * @return code对应的枚举类
    */
    private IEnum locateIEnum(String key) {
        for(IEnum status : enums) {
            if(status.getName().equals(key)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型：" + key + ",请核对" + type.getSimpleName());
    }

}
