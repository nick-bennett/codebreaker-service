package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import edu.cnm.deepdive.codebreaker.model.view.UserPerformance;

public interface AbstractPerformanceService {

  Iterable<GamePerformance> getGamePerformancesByDuration(
      int poolSize, int codeLength, int count);

  Iterable<GamePerformance> getGamePerformancesByGuessCount(
      int poolSize, int codeLength, int count);

  Iterable<UserPerformance> getUserPerformancesByDuration(
      int poolSize, int codeLength, int minGamesCompleted, int count);

  Iterable<UserPerformance> getUserPerformancesByGuessCount(
      int poolSize, int codeLength, int minGamesCompleted, int count);
}
