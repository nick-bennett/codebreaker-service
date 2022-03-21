package edu.cnm.deepdive.codebreaker.model.dao;

import edu.cnm.deepdive.codebreaker.model.view.UserPerformance;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPerformanceRepository extends JpaRepository<UserPerformance, UUID> {

  List<UserPerformance> findAllByPoolSizeAndLengthAndGamesCompletedGreaterThanEqual(
      int poolSize, int codeLength, int minGamesCompleted, Pageable pageable);

}
