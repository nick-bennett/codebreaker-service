package edu.cnm.deepdive.codebreaker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import edu.cnm.deepdive.codebreaker.model.view.UserPerformance;
import edu.cnm.deepdive.codebreaker.service.AbstractPerformanceService;
import edu.cnm.deepdive.codebreaker.view.UserView;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rankings")
public class RankingController {

  private final AbstractPerformanceService performanceService;

  @Autowired
  public RankingController(AbstractPerformanceService performanceService) {
    this.performanceService = performanceService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"aggregated=true"})
  @JsonView(UserView.Public.class)
  public Iterable<UserPerformance> getUserRankings(
      @Min(1) @RequestParam(name = "min-completed-games", required = false, defaultValue = "1")
          int minCompletedGames,
      @Min(Game.MIN_POOL_SIZE) @Max(Game.MAX_POOL_SIZE)
      @RequestParam(name = "pool-size", required = false, defaultValue = "3") int poolSize,
      @Min(Game.MIN_CODE_LENGTH) @Max(Game.MAX_CODE_LENGTH)
      @RequestParam(name = "code-length", required = false, defaultValue = "3") int codeLength,
      @Min(1)
      @RequestParam(required = false, defaultValue = "10") int count,
      @Pattern(regexp = "guesses|time")
      @RequestParam(required = false, defaultValue = "guesses") String order) {
    return order.equals("guesses")
        ? performanceService.getUserRankingsByGuessCount(
            poolSize, codeLength, minCompletedGames, count)
        : performanceService.getUserRankingsByDuration(
            poolSize, codeLength, minCompletedGames, count);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Public.class)
  public Iterable<GamePerformance> getGameRankings(
      @Min(Game.MIN_POOL_SIZE) @Max(Game.MAX_POOL_SIZE)
          @RequestParam(name = "pool-size", required = false, defaultValue = "3") int poolSize,
      @Min(Game.MIN_CODE_LENGTH) @Max(Game.MAX_CODE_LENGTH)
          @RequestParam(name = "code-length", required = false, defaultValue = "3") int codeLength,
      @Min(1)
          @RequestParam(required = false, defaultValue = "10") int count,
      @Pattern(regexp = "guesses|time")
          @RequestParam(required = false, defaultValue = "guesses") String order) {
    return order.equals("guesses")
        ? performanceService.getGameRankingsByGuessCount(poolSize, codeLength, count)
        : performanceService.getGameRankingsByDuration(poolSize, codeLength, count);
  }

}