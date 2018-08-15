package com.wbm.testng.demo.common;

/**
 * 常量配置类
 *
 * @author WangBangMing
 * @create 2018/8/8 11:02
 */
public final class Constans {

  public static final String COLON = ":";
  public static final String DATA_FAC_HOST = "http://127.0.0.1";
  public static final String DATA_FAC_PORT = "7001";
  public static final String ALIYUN_PK = "123";

  /**
   * 数据工厂 接口列表
   */
  public static final String CLOUD_RESOURCE_LIST = "/dataResource/list?aliyunPK=%s&type=%s";
  public static final String CLOUD_RESOURCE_UNIQUE =
      "/dataResource/schemaCode/unique?aliyunPK=%s&schemaCode=%s";
}
