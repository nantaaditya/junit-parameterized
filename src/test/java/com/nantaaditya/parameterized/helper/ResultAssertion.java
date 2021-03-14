package com.nantaaditya.parameterized.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hamcrest.Matcher;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * created by pramuditya.anantanur
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultAssertion {
  private HttpStatus status;
  private Map<String, Matcher> results;
  
  public static ResultAssertion constructOk() {
    return ResultAssertion.builder()
        .status(HttpStatus.OK)
        .build();
  }

  public static ResultAssertion constructOk(Map<String, Matcher> results) {
    return ResultAssertion.builder()
        .status(HttpStatus.OK)
        .results(results)
        .build();
  }

  public static ResultAssertion constructError(HttpStatus status, int httpCode, Map<String, Matcher> results) {
    return ResultAssertion.builder()
        .status(status)
        .results(results)
        .build();
  }

  public static ResultAssertion constructError(HttpStatus status, Map<String, Matcher> results) {
    return ResultAssertion.builder()
        .status(status)
        .results(results)
        .build();
  }

}
