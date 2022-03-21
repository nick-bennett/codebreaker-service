package edu.cnm.deepdive.codebreaker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.service.AbstractUserService;
import edu.cnm.deepdive.codebreaker.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final AbstractUserService service;

  @Autowired
  public UserController(AbstractUserService service) {
    this.service = service;
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Private.class)
  public User get() {
    return service.getCurrentUser();
  }

  @PutMapping(value = "/me",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Private.class)
  public User put(@RequestBody User user) {
    return service.updateUser(user);
  }

  @GetMapping(value = "/me/incognito", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean isIncognito() {
    return service
        .getCurrentUser()
        .isIncognito();
  }

  @PutMapping(value = "/me/incognito",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean setIncognito(@RequestBody boolean incognito) {
    User user = service.getCurrentUser();
    user.setIncognito(incognito);
    return service
        .updateUser(user)
        .isIncognito();
  }

}
