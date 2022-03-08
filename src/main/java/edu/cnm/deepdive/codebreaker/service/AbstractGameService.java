package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.UUID;

public interface AbstractGameService {

  Game startGame(Game game, User user);

//  Guess submitGuess(Game game, Guess guess, User user);

  Game get(UUID externalKey, User user);

  // TODO Get rankings (GamePerformance?).

  // TODO Get summaries.
}
