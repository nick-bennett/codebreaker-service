package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.service.AbstractGuessService;
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
@RequestMapping("/games/{gameId}/guesses")
public class GuessController {

  private final AbstractGuessService guessService;
  private final AbstractUserService userService;

  @Autowired
  public GuessController(AbstractGuessService guessService, AbstractUserService userService) {
    this.guessService = guessService;
    this.userService = userService;
  }

  @GetMapping(value = "/{guessId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Guess get(@PathVariable UUID gameId, @PathVariable UUID guessId) {
    return guessService
        .get(guessId, gameId, userService.getCurrentUser())
        .orElseThrow();
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Guess> post(@PathVariable UUID gameId, @Valid @RequestBody Guess guess) {
    Guess created = guessService.submitGuess(guess, gameId, userService.getCurrentUser());
    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(GuessController.class)
                .get(gameId, created.getExternalKey())
        )
        .toUri();
    return ResponseEntity
        .created(location)
        .body(created);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  Iterable<Guess> get(@PathVariable UUID gameId) {
    return guessService
        .get(gameId, userService.getCurrentUser())
        .orElseThrow();
  }

}
