package edu.cnm.deepdive.codebreaker.model.dao;

import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePerformanceRepository extends JpaRepository<GamePerformance, UUID> {

  List<GamePerformance> findAllByPoolSizeAndLength(int poolSize, int length, Pageable pageable);

}
