package com.wbm.testng.demo.dto;

/**
 * Http请求返回结果
 *
 * @author WangBangMing
 * @create 2018/8/8 10:59
 */
public class HttpResult {
  private int code;
  private String result;

  public HttpResult() {}

  public HttpResult(int code, String result) {
    this.code = code;
    this.result = result;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}
