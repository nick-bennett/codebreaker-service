package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.service.AbstractGameService;
import edu.cnm.deepdive.codebreaker.service.AbstractUserService;
import java.net.URI;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  public static final String ID_PATTERN =
      "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}";

  private final AbstractGameService gameService;
  private final AbstractUserService userService;

  /**
   *
   *
   * @param gameService Service providing methods to create and operate on instances of {@link Game}.
   * @param userService
   */
  @Autowired
  public GameController(AbstractGameService gameService, AbstractUserService userService) {
    this.gameService = gameService;
    this.userService = userService;
  }

  /**
   * Starts a game and adds it to the database.
   *
   * @param game
   * @return Fully constructed {@link Game} instance.
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Game> post(@Valid @RequestBody Game game) {
    Game created = gameService.startGame(game, userService.getCurrentUser());
    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(GameController.class)
                .get(created.getExternalKey())
        )
        .toUri();
    return ResponseEntity
        .created(location)
        .body(created);
  }

  @GetMapping(value = "/{id:" + ID_PATTERN + "}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Game get(@PathVariable UUID id) {
    return gameService
        .get(id, userService.getCurrentUser())
        .orElseThrow();
  }

}
