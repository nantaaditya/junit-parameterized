package com.nantaaditya.parameterized.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * created by pramuditya.anantanur
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestSpecification {
  private String name;
  private String path;
  private HttpMethod httpMethod;
  private Object request;
  private Map<String, Object> headers;
  private Map<String, Object> params;
  private BeforeTest beforeTest;
  private AfterTest afterTest;
  private ResultAssertion resultAssertion;

  @Override
  public String toString() {
    return this.name;
  }
  public static TestSpecification request(String name, String path, HttpMethod method,
      Object request, Map<String, Object> params, BeforeTest beforeTest, AfterTest afterTest, ResultAssertion resultAssertion) {
    return TestSpecification.builder()
        .name(name)
        .path(path)
        .httpMethod(method)
        .request(request)
        .params(params)
        .beforeTest(beforeTest)
        .afterTest(afterTest)
        .resultAssertion(resultAssertion)
        .build();
  }

  public static TestSpecification request(String name, String path, HttpMethod method,
      Object request, Map<String, Object> params, ResultAssertion resultAssertion) {
    return TestSpecification.builder()
        .name(name)
        .path(path)
        .httpMethod(method)
        .request(request)
        .params(params)
        .resultAssertion(resultAssertion)
        .build();
  }

  public static TestSpecification request(String name, String path, HttpMethod method,
      Object request, Map<String, Object> headers, Map<String, Object> params, ResultAssertion resultAssertion) {
    return TestSpecification.builder()
        .name(name)
        .path(path)
        .httpMethod(method)
        .request(request)
        .headers(headers)
        .params(params)
        .resultAssertion(resultAssertion)
        .build();
  }
}
