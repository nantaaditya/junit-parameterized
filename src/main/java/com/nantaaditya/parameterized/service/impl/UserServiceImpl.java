package com.nantaaditya.parameterized.service.impl;

import com.nantaaditya.parameterized.entity.User;
import com.nantaaditya.parameterized.repository.UserRepository;
import com.nantaaditya.parameterized.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * created by pramuditya.anantanur
 **/
@Service
public class UserServiceImpl implements UserService {
  
  @Autowired
  private UserRepository userRepository;
  
  @Override
  public User save(User user) {
    Optional<User> existingUser = userRepository.findById(user.getId());
    
    if (existingUser.isPresent()) {
      BeanUtils.copyProperties(user, existingUser, "id");
      return userRepository.save(user);
    } else {
      return userRepository.save(user);
    }
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User get(String id) {
    Optional<User> existingUser = userRepository.findById(id);
    
    if (existingUser.isPresent()) {
      return existingUser.get();
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  public boolean delete(String id) {
    Optional<User> existingUser = userRepository.findById(id);

    if (existingUser.isPresent()) {
      userRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }
}
