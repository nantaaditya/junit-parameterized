package com.nantaaditya.parameterized.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

// 1
@RunWith(Parameterized.class)
public class OddCheckerTest {
  // 2
  private Integer inputNumber;
  private Boolean expectedResult;

  // 3
  public OddCheckerTest(Integer inputNumber, Boolean expectedResult) {
    this.inputNumber = inputNumber;
    this.expectedResult = expectedResult;
  }
  // 4
  @Parameterized.Parameters(name="Test - {0} is odd number: {1}")
  public static Collection primeNumbers() {
    return Arrays.asList(new Object[][] {
        { 1, true },
        { 6, false },
        { 19, true },
        { 22, false },
        { 23, true }
    });
  }
  // 5
  @Test
  public void testOddNumberChecker() {
    System.out.println("Parameterized Number is: " + inputNumber);
    assertEquals(expectedResult, inputNumber % 2 == 1);
  }
}
