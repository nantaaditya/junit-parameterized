package com.nantaaditya.parameterized.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Optional;

/**
 * created by pramuditya.anantanur
 **/
@Slf4j
public abstract class BaseIntegrationTest {

  protected TestSpecification testSpecification;

  protected abstract void setUp() throws Exception;

  protected abstract void tearDown() throws Exception;
  
  @Autowired
  protected ObjectMapper objectMapper;

  public BaseIntegrationTest(TestSpecification testSpecification) {
    this.testSpecification = testSpecification;
  }

  @Before
  public void processSetUp() throws Exception {
    RestAssured.port = 18181;

    this.setUp();
  }

  @Test
  public void test() throws Exception {
    if (!ObjectUtils.isEmpty(testSpecification.getBeforeTest())) {
      testSpecification.getBeforeTest().apply();
    }

    this.doTestApi(testSpecification);

    if (!ObjectUtils.isEmpty(testSpecification.getAfterTest())) {
      testSpecification.getAfterTest().apply();
    }
  }

  @After
  public void processTearDown() throws Exception {
    this.tearDown();
  }

  public void doTestApi(TestSpecification testSpecification) throws Exception {
    RequestSpecification requestSpecification = 
        constructApi(testSpecification.getRequest(), testSpecification.getHeaders(), testSpecification.getParams());
    Response response = null;

    switch (testSpecification.getHttpMethod()) {
      case GET:
        response = requestSpecification.get(testSpecification.getPath());
        break;
      case POST:
        response = requestSpecification.post(testSpecification.getPath());
        break;
      case PUT:
        response = requestSpecification.put(testSpecification.getPath());
        break;
      case DELETE:
        response = requestSpecification.delete(testSpecification.getPath());
        break;
    }

    response.prettyPrint();

    ValidatableResponse validatableResponse = response
        .then()
        .statusCode(testSpecification.getResultAssertion().getStatus().value());

    if (!CollectionUtils.isEmpty(testSpecification.getResultAssertion().getResults())) {
      testSpecification.getResultAssertion().getResults().entrySet()
          .forEach(result -> {
            validatableResponse.body(result.getKey(), result.getValue());
          });
    }
  }

  protected RequestSpecification constructApi(Object request, Map<String, Object> headers, Map<String, Object> params) 
      throws JsonProcessingException {
    RequestSpecification requestSpecification = RestAssured.given()
        .header("Accept", MediaType.APPLICATION_JSON_VALUE);

    if (!CollectionUtils.isEmpty(headers)) {
      headers.entrySet().forEach(header -> {
        requestSpecification.header(header.getKey(), header.getValue());
      });
    }

    if (request != null) {
      requestSpecification
          .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
          .body(objectMapper.writeValueAsString(request));
    }

    if (!CollectionUtils.isEmpty(params)) {
      params.entrySet().forEach(param -> {
        requestSpecification.when().queryParam(param.getKey(), param.getValue());
      });
    }

    return requestSpecification;
  }

  private HttpStatus getHttpStatus(TestSpecification testSpecification) {
    return Optional.ofNullable(testSpecification)
        .map(spec -> spec.getResultAssertion())
        .map(assertion -> assertion.getStatus())
        .orElseGet(() -> HttpStatus.OK);
  }
}
