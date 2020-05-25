package cn.tycoding.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 用于将导入数据时为null的字段改为空字符串（这样在查询时，若字段的输入为空，才能查出对应信息，若值为null则查不出）
 * @author pyf
 *
 */

public class NullValueHandler implements TypeHandler<String> {

	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getString(columnName);
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getString(columnIndex);
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cs.getString(columnIndex);
	}

	@Override
	public void setParameter(PreparedStatement pstmt, int index, String value, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		if(value == null && jdbcType == JdbcType.VARCHAR){
			//判断传入的参数值是否为null
			 pstmt.setString(index,"");//设置当前参数的值为空字符串
			 
		}else{
			pstmt.setString(index,value);//如果不为null，则直接设置参数的值为value
		}
	}

}
