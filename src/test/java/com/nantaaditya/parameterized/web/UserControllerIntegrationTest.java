package com.nantaaditya.parameterized.web;

import com.nantaaditya.parameterized.configuration.UserConfiguration;
import com.nantaaditya.parameterized.entity.User;
import com.nantaaditya.parameterized.helper.BaseIntegrationTest;
import com.nantaaditya.parameterized.helper.ResultAssertion;
import com.nantaaditya.parameterized.helper.TestSpecification;
import org.hamcrest.Matcher;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * created by pramuditya.anantanur
 **/
@RunWith(Parameterized.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerIntegrationTest extends BaseIntegrationTest {

  @ClassRule
  public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

  @Rule
  public final SpringMethodRule springMethodRule = new SpringMethodRule();

  @Autowired
  public UserConfiguration userConfiguration;

  public UserControllerIntegrationTest(TestSpecification testSpecification) {
    super(testSpecification);
  }

  @Override
  protected void setUp() throws Exception {
    // do before each test    
  }

  @Parameterized.Parameters(name = "User API - {0}")
  public static Collection<TestSpecification> data() {
    return Arrays.asList(
        TestSpecification.request(
            "save user",
            "/api/users",
            HttpMethod.POST, 
            User.builder()
                .id("1")
                .name("name")
                .email("email")
                .phoneNumber("phoneNumber")
                .build(),
            null, 
            ResultAssertion.constructOk(new HashMap<String, Matcher>() {{
              put("name", equalTo("name"));
              put("email", equalTo("email"));
              put("phoneNumber", equalTo("phoneNumber"));
            }})
        ),
        TestSpecification.request(
            "save user (update)",
            "/api/users",
            HttpMethod.POST,
            User.builder()
                .id("1")
                .name("name update")
                .email("email update")
                .phoneNumber("phoneNumber update")
                .build(),
            null,
            ResultAssertion.constructOk(new HashMap<String, Matcher>() {{
              put("name", equalTo("name update"));
              put("email", equalTo("email update"));
              put("phoneNumber", equalTo("phoneNumber update"));
            }})
        ),
        TestSpecification.request(
            "find all user",
            "/api/users",
            HttpMethod.GET,
            null,
            null,
            ResultAssertion.constructOk(new HashMap<String, Matcher>() {{
              put("[0].name", equalTo("name update"));
              put("[0].email", equalTo("email update"));
              put("[0].phoneNumber", equalTo("phoneNumber update"));
            }})
        ),
        TestSpecification.request(
            "get user",
            "/api/users/1",
            HttpMethod.GET,
            null,
            null,
            ResultAssertion.constructOk(new HashMap<String, Matcher>() {{
              put("name", equalTo("name update"));
              put("email", equalTo("email update"));
              put("phoneNumber", equalTo("phoneNumber update"));
            }})
        ),
        TestSpecification.request(
            "get user (error)",
            "/api/users/2",
            HttpMethod.GET,
            null,
            null,
            ResultAssertion.constructError(HttpStatus.INTERNAL_SERVER_ERROR, new HashMap<String, Matcher>() {{
              put("status", equalTo(500));
              put("error", equalTo("Internal Server Error"));
            }})
        ),
        TestSpecification.request(
            "delete user",
            "/api/users/1",
            HttpMethod.DELETE,
            null,
            null,
            ResultAssertion.constructOk()
        ),
        TestSpecification.request(
            "delete user with initial data",
            "/api/users/2",
            HttpMethod.DELETE,
            null,
            null,
            () -> {
              UserConfiguration.userRepository.save(User.builder()
                  .id("2")
                  .name("name")
                  .email("email")
                  .phoneNumber("phoneNumber")
                  .build());
            }, 
            () -> {
              // do nothing after test
            },
            ResultAssertion.constructOk()
        )
    );
  }

  @Override
  protected void tearDown() throws Exception {
    // do after each test
  }
}
