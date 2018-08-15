package com.wbm.testng.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RDS 连接工具类
 * 
 * @author WangBangMing
 * @create 2018/8/8 10:51
 */
public class RdsConnection {
  private static final String MYSQL_TEMPLATE = "jdbc:mysql://%s:%s/%s";
  private static String host = "127.0.0.1";
  private static String port = "3306";
  private static String dataBase = "test";
  private static String userName = "test";
  private static String passWord = "test_123";

  public static List<Map<String, Object>> getConnection(String ddl) throws SQLException {
    // 获取JDBC连接
    Connection conn = DriverManager
        .getConnection(String.format(MYSQL_TEMPLATE, host, port, dataBase), userName, passWord);

    // 获取SQL执行对象
    Statement st = conn.createStatement();

    // 执行SQL语句，获取查询结果
    ResultSet result = st.executeQuery(ddl);

    // 获取结果字段信息
    ResultSetMetaData meta = result.getMetaData();
    // 获得列数
    int columnCount = meta.getColumnCount();
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    // 遍历结果集
    while (result.next()) {
      Map<String, Object> rowData = new HashMap<String, Object>();
      for (int i = 1; i <= columnCount; i++) {
        rowData.put(meta.getColumnName(i), result.getObject(i));
      }
      list.add(rowData);
    }

    // 关闭资源
    st.close();
    conn.close();
    return list;
  }

}
