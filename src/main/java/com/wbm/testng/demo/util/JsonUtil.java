package com.wbm.testng.demo.util;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json 操作工具类
 *
 * @author WangBangMing
 * @create 2018/8/8 11:02
 */
public final class JsonUtil {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static String objToString(Object obj) {
    String json = "";
    try {
      json = MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
    }
    return json;
  }

  /**
   * 解析Json内容, 返回JsonString中key对应的Value
   *
   * @return String
   **/
  public static String getJsonValue(String JsonString, String key) {
    if (JsonString == null || JsonString.trim().length() < 1) {
      return null;
    }
    String jsonValue = "";
    try {
      JsonNode jsonNode = MAPPER.readTree(JsonString);
      jsonValue = jsonNode.get(key).asText();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return jsonValue;
  }
}
