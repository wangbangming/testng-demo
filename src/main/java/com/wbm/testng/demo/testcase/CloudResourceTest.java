package com.wbm.testng.demo.testcase;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.wbm.testng.demo.common.Constans;
import com.wbm.testng.demo.dto.HttpResult;
import com.wbm.testng.demo.service.HttpConnection;
import com.wbm.testng.demo.util.JsonUtil;

/**
 * 云资源管理 自动化测试类
 *
 * @author WangBangMing
 * @create 2018/8/8 11:07
 */
public class CloudResourceTest {

  private String URL = Constans.DATA_FAC_HOST + Constans.COLON + Constans.DATA_FAC_PORT;

  @Test(groups = {"CloudResource"})
  public void getResourceList() throws IOException {
    String type = "ODPS";
    Reporter.log("【正常用例】:获取" + Constans.ACCOUNT + "云资源列表");
    String cloudResourceUrl =
        String.format(URL + Constans.CLOUD_RESOURCE_LIST, Constans.ACCOUNT, type);
    HttpResult result = HttpConnection.doGet(cloudResourceUrl);
    Reporter.log("请求地址: " + cloudResourceUrl);
    Assert.assertEquals(result.getCode(), HttpStatus.OK.value());
    Reporter.log("返回结果: " + JsonUtil.objToString(result));
  }

  @Test(groups = {"CloudResource"})
  public void checkResourceUnique() throws IOException {
    String schemaCode = "test_abc";
    Reporter.log("【正常用例】:云资源标识" + schemaCode + "唯一性校验");
    String cloudResourceUrl =
        String.format(URL + Constans.CLOUD_RESOURCE_UNIQUE, Constans.ACCOUNT, schemaCode);
    HttpResult result = HttpConnection.doGet(cloudResourceUrl);
    Reporter.log("请求地址: " + cloudResourceUrl);
    Assert.assertEquals(result.getCode(), HttpStatus.OK.value());
    Reporter.log("返回结果: " + JsonUtil.objToString(result));
  }

}
