package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;

public interface AbstractPerformanceService {

  Iterable<GamePerformance> getGameRankingsByDuration(int poolSize, int codeLength, int count);

  Iterable<GamePerformance> getGameRankingsByGuessCount(int poolSize, int codeLength, int count);

}
