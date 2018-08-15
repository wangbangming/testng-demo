package com.wbm.testng.demo.testcase;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.wbm.testng.demo.service.RdsConnection;
import com.wbm.testng.demo.util.JsonUtil;

/**
 * 数据库连接测试
 *
 * @author WangBangMing
 * @create 2018/8/8 11:07
 */
public class JdbcConnectTest {

  @Test(groups = {"JDBCTest"})
  public void jdbcConnect() throws SQLException {
    Reporter.log("【正常用例】:测试数据库查询");
    String sql = "select * from tb_test";
    Reporter.log("【正常用例】:执行SQL为：" + sql);
    List<Map<String, Object>> result = RdsConnection.getConnection(sql);
    Reporter.log("返回结果: " + JsonUtil.objToString(result));
    Reporter.log("【正常用例】:测试数据库查询Success");
  }
  
  @Test(groups = {"JDBCTest"})
  public void checkData() throws SQLException {
    Reporter.log("【正常用例】:数据库查询, 字段内容匹配");
    String sql = "select * from tb_test";
    Reporter.log("【正常用例】:执行SQL为：" + sql);
    List<Map<String, Object>> result = RdsConnection.getConnection(sql);
    String name = JsonUtil.getJsonValue(JsonUtil.objToString(result.get(0)), "name");
    Reporter.log("返回结果: " + JsonUtil.objToString(result));
    Assert.assertEquals(name, "a");
    Reporter.log("【正常用例】:Success");
  }

}
