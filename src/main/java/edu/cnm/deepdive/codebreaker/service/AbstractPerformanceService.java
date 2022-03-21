package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import edu.cnm.deepdive.codebreaker.model.view.UserPerformance;

public interface AbstractPerformanceService {

  Iterable<GamePerformance> getGameRankingsByDuration(int poolSize, int codeLength, int count);

  Iterable<GamePerformance> getGameRankingsByGuessCount(int poolSize, int codeLength, int count);

  Iterable<UserPerformance> getUserRankingsByDuration(
      int poolSize, int codeLength, int minGamesCompleted, int count);

  Iterable<UserPerformance> getUserRankingsByGuessCount(
      int poolSize, int codeLength, int minGamesCompleted, int count);

}
