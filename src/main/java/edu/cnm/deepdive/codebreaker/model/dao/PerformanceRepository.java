package edu.cnm.deepdive.codebreaker.model.dao;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.view.Performance;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface PerformanceRepository extends Repository<Game, UUID> {

  @Query(value = "SELECT "
        + "MIN(ga.created) as created, "
        + "COUNT(*) AS guessCount, "
        + "DATEDIFF(SECOND, MIN(gu.created), MAX(gu.created)) AS duration, "
        + "MAX(gu.exact_matches) AS matches, "
        + "MAX(ga.length) AS code_length, "
        + "MAX(CASE u.incognito WHEN TRUE THEN NULL ELSE u.display_name END) AS displayName "
      + "FROM game AS ga "
      + "JOIN guess AS gu ON gu.game_id = ga.game_id "
      + "JOIN user_profile AS u ON u.user_profile_id = ga.user_id "
      + "WHERE ga.pool_size = :poolSize AND ga.length = :codeLength "
      + "GROUP BY ga.game_id "
      + "HAVING matches = code_length "
      + "ORDER BY duration ASC, guessCount ASC "
      + "LIMIT :count", nativeQuery = true)
  Iterable<Performance> getRankingsByTime(int poolSize, int codeLength, int count);

  @Query(value = "SELECT "
      + "MIN(ga.created) as created, "
      + "COUNT(*) AS guessCount, "
      + "DATEDIFF(SECOND, MIN(gu.created), MAX(gu.created)) AS duration, "
      + "MAX(gu.exact_matches) AS matches, "
      + "MAX(ga.length) AS code_length, "
      + "MAX(CASE u.incognito WHEN TRUE THEN NULL ELSE u.display_name END) AS displayName "
      + "FROM game AS ga "
      + "JOIN guess AS gu ON gu.game_id = ga.game_id "
      + "JOIN user_profile AS u ON u.user_profile_id = ga.user_id "
      + "WHERE ga.pool_size = :poolSize AND ga.length = :codeLength "
      + "GROUP BY ga.game_id "
      + "HAVING matches = code_length "
      + "ORDER BY guessCount ASC, duration ASC "
      + "LIMIT :count", nativeQuery = true)
  Iterable<Performance> getRankingsByGuesses(int poolSize, int codeLength, int count);

}
