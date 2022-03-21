package edu.cnm.deepdive.codebreaker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.service.AbstractUserService;
import edu.cnm.deepdive.codebreaker.view.UserView;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final AbstractUserService service;
  private final ObjectMapper mapper;

  @Autowired
  public UserController(AbstractUserService service,
      ObjectMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Public.class)
  public User get(@PathVariable UUID id) {
    return service
        .get(id, service.getCurrentUser())
        .orElseThrow();
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Private.class)
  public User get() {
    return service.getCurrentUser();
  }

  @PutMapping(value = "/me",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

  @GetMapping(value = "/me/displayName", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getDisplayName() throws JsonProcessingException {
    String raw = service
        .getCurrentUser()
        .getDisplayName();
    return mapper.writeValueAsString(raw);
  }

  @PutMapping(value = "/me/displayName",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public String setDisplayName(@RequestBody String quotedName) throws JsonProcessingException {
    String unquotedName = mapper.readValue(quotedName, String.class);
    User user = service.getCurrentUser();
    user.setDisplayName(unquotedName);
    return mapper.writeValueAsString(service
        .updateUser(user)
        .getDisplayName());
  }

}
