package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.service.AbstractPerformanceService;
import edu.cnm.deepdive.codebreaker.view.Performance;
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

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Performance> get(
      @Min(Game.MIN_POOL_SIZE) @Max(Game.MAX_POOL_SIZE)
          @RequestParam(name = "pool-size", required = false, defaultValue = "3") int poolSize,
      @Min(Game.MIN_CODE_LENGTH) @Max(Game.MAX_CODE_LENGTH)
          @RequestParam(name = "code-length", required = false, defaultValue = "3") int codeLength,
      @Min(1)
          @RequestParam(required = false, defaultValue = "10") int count,
      @Pattern(regexp = "guesses|time")
          @RequestParam(required = false, defaultValue = "guesses") String order) {
    return order.equals("guesses")
        ? performanceService.getRankingsByGuessCount(poolSize, codeLength, count)
        : performanceService.getRankingsByDuration(poolSize, codeLength, count);
  }

}