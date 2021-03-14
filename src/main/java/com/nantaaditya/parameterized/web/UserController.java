package com.nantaaditya.parameterized.web;

import com.nantaaditya.parameterized.entity.User;
import com.nantaaditya.parameterized.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by pramuditya.anantanur
 **/
@RestController
@RequestMapping(value = "/api/users")
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public User save(@RequestBody User user) {
    return userService.save(user);
  }
  
  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public List<User> findAll() {
    return userService.findAll();
  }
  
  @GetMapping(
      value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public User get(@PathVariable String id) {
    return userService.get(id);
  }
  
  @DeleteMapping(
      value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public boolean delete(@PathVariable String id) {
    return userService.delete(id);
  }
}
