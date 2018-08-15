package com.wbm.testng.demo.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.wbm.testng.demo.dto.HttpResult;

/**
 * Http 请求工具
 *
 * @author WangBangMing
 * @create 2018/8/8 10:51
 */
public class HttpConnection {

  private static PoolingHttpClientConnectionManager connectionManager = null;
  private static HttpClientBuilder httpBulder = null;
  private static RequestConfig requestConfig = null;

  private static int MAXCONNECTION = 10;

  private static int DEFAULTMAXCONNECTION = 5;

  static {
    // 设置http的状态参数
    requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
        .setConnectionRequestTimeout(5000).build();

    connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(MAXCONNECTION);
    connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
    httpBulder = HttpClients.custom();
    httpBulder.setConnectionManager(connectionManager);
  }

  /**
   * 获取HttpClient
   */
  public static CloseableHttpClient getConnection() {
    CloseableHttpClient httpClient = httpBulder.build();
    return httpClient;
  }

  /**
   * Http GET 请求，不带参数
   * 
   * @param url http请求地址
   */
  public static HttpResult doGet(String url) throws IOException {
    // 创建http GET请求
    HttpGet httpGet = new HttpGet(url);
    httpGet.setConfig(requestConfig);
    return doRequest(httpGet);
  }

  /**
   * Http GET 请求，携带参数
   * 
   * @param url http请求地址
   * @param params 请求参数 key/value 形式
   */
  public static HttpResult doGet(String url, Map<String, String> params)
      throws URISyntaxException, IOException {
    // 创建 URIBuilder 对象
    URIBuilder builder = new URIBuilder(url);
    // 请求参数拼装
    if (null != params) {
      // 遍历请求参数集合
      for (Map.Entry<String, String> entry : params.entrySet()) {
        builder.addParameter(entry.getKey(), entry.getValue());
      }
    }
    // 使用拼装后的URL调用Http GET 请求
    return doGet(builder.build().toString());
  }

  /**
   * Http GET 请求，携带参数
   * 
   * @param url http请求地址
   * @param params 请求参数 key/value 形式
   */
  public static HttpResult doGet(String url, Map<String, String> params,
      Map<String, String> headers) throws URISyntaxException, IOException {
    // 创建 URIBuilder 对象
    URIBuilder builder = new URIBuilder(url);
    // 请求参数拼装
    if (null != params) {
      // 遍历请求参数集合
      for (Map.Entry<String, String> entry : params.entrySet()) {
        builder.addParameter(entry.getKey(), entry.getValue());
      }
    }
    // 创建http GET请求
    HttpGet httpGet = new HttpGet(url);

    if (null != headers) {
      // 遍历请求头信息
      for (Map.Entry<String, String> header : headers.entrySet()) {
        // 设置请求头信息
        httpGet.setHeader(header.getKey(), header.getValue());
      }
    }
    // 使用拼装后的URL调用Http GET 请求
    return doRequest(httpGet);
  }

  public static HttpResult doPost(String url, Map<String, String> params) throws IOException {
    // 创建http POST请求
    HttpPost httpPost = new HttpPost(url);

    if (null != params) {
      // 设置post参数
      List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
      for (Map.Entry<String, String> entry : params.entrySet()) {
        parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
      }
      // 构造一个form表单式的实体
      UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
      // 将请求实体设置到httpPost对象中
      httpPost.setEntity(formEntity);
    }
    return doRequest(httpPost);
  }

  public static HttpResult doPost(String url, String json, Map<String, String> headers)
      throws IOException {
    // 创建http POST请求
    HttpPost httpPost = new HttpPost(url);

    if (null != headers) {
      // 遍历请求头信息
      for (Map.Entry<String, String> header : headers.entrySet()) {
        // 设置请求头信息
        httpPost.setHeader(header.getKey(), header.getValue());
      }
    }

    if (null != json) {
      // 设置json数据并且指定内容类型为json
      StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
      // 将请求实体设置到httpPost对象中
      httpPost.setEntity(stringEntity);
    }
    return doRequest(httpPost);
  }

  protected static HttpResult doRequest(HttpUriRequest httpRequest) throws IOException {
    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = getConnection();
    try {
      // 执行请求
      response = httpClient.execute(httpRequest);
      return new HttpResult(response.getStatusLine().getStatusCode(),
          EntityUtils.toString(response.getEntity(), "UTF-8"));
    } finally {
      response.close();
    }
  }

}
