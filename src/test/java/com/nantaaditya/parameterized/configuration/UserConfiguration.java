package com.nantaaditya.parameterized.configuration;

import com.nantaaditya.parameterized.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * created by pramuditya.anantanur
 **/
@Component
public class UserConfiguration {
  
  public static UserRepository userRepository;
  
  @Autowired
  public UserConfiguration(UserRepository userRepository) {
    UserConfiguration.userRepository = userRepository;
  }
}
