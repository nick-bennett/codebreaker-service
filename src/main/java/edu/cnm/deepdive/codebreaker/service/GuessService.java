package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.dao.GuessRepository;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuessService implements AbstractGuessService {

  private final GuessRepository guessRepository;
  private final AbstractGameService gameService;

  @Autowired
  public GuessService(GuessRepository guessRepository, AbstractGameService gameService) {
    this.guessRepository = guessRepository;
    this.gameService = gameService;
  }

  @Override
  public Guess submitGuess(Guess guess, UUID gameId, User user) {
    return gameService
        .get(gameId, user)
        .map((game) -> validate(guess, game))
        .map((g) -> {
          // TODO Compute number of exact matches & near matches
          // TODO Set fields of guess with computation results.
          return g;
        })
        .map(guessRepository::save)
        .orElseThrow();
  }

  @Override
  public Optional<Guess> get(UUID guessId, UUID gameId, User user) {
    return gameService.get(gameId, user)
        .flatMap((game) -> guessRepository.findByGameAndExternalKey(game, guessId));
  }

  @Override
  public Optional<Iterable<Guess>> get(UUID gameId, User user) {
    return gameService.get(gameId, user)
        .map(Game::getGuesses);
  }

  private Guess validate(Guess guess, Game game) {
    int[] codePoints = codePoints(guess.getText());
    if (codePoints.length != game.getLength()
        || !assemble(codePoints(game.getPool())).containsAll(assemble(codePoints))) {
      throw new IllegalArgumentException();
    }
    return guess;
  }

  private int[] codePoints(String input) {
    return input
        .codePoints()
        .toArray();
  }

  private Set<Integer> assemble(int[] values) {
    return IntStream
        .of(values)
        .boxed()
        .collect(Collectors.toSet());
  }
}
