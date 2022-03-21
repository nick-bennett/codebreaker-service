package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.dao.GamePerformanceRepository;
import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PerformanceService implements AbstractPerformanceService {

  private final GamePerformanceRepository repository;

  @Autowired
  public PerformanceService(GamePerformanceRepository repository) {
    this.repository = repository;
  }

  @Override
  public Iterable<GamePerformance> getGameRankingsByDuration(
      int poolSize, int codeLength, int count) {
    Sort sort = Sort
        .by("duration")
        .ascending()
        .and(
            Sort
                .by("guessCount")
                .ascending()
        );
    Pageable pageable = PageRequest.of(0, count, sort);
    return repository.findAllByPoolSizeAndLength(poolSize, codeLength, pageable);
  }

  @Override
  public Iterable<GamePerformance> getGameRankingsByGuessCount(
      int poolSize, int codeLength, int count) {
    Sort sort = Sort
        .by("guessCount")
        .ascending()
        .and(
            Sort
                .by("duration")
                .ascending()
        );
    Pageable pageable = PageRequest.of(0, count, sort);
    return repository.findAllByPoolSizeAndLength(poolSize, codeLength, pageable);
  }

}
