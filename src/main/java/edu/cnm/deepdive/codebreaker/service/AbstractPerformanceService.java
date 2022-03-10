package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.view.Performance;

public interface AbstractPerformanceService {

  Iterable<Performance> getRankingsByDuration(int poolSize, int codeLength, int count);

  Iterable<Performance> getRankingsByGuessCount(int poolSize, int codeLength, int count);

}
