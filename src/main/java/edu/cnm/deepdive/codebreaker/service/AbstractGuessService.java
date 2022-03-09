package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface AbstractGuessService {

  Guess submitGuess(Guess guess, UUID gameId, User user);

  Optional<Guess> get(UUID guessId, UUID gameId, User user);

  Optional<Iterable<Guess>> get(UUID gameId, User user);

}
