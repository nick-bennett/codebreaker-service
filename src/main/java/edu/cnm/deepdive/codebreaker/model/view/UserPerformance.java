package edu.cnm.deepdive.codebreaker.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.view.UserView;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Immutable
@Subselect("SELECT * FROM user_performance")
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"gamesCompleted", "averageDuration", "averageGuessCount", "user"})
@JsonView(UserView.Public.class)
public class UserPerformance {

  @Id
  @Column(name = "user_id")
  @JsonIgnore
  private UUID id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  @JsonIgnore
  private User user;

  @JsonIgnore
  private int poolSize;

  @JsonIgnore
  private int length;

  private int gamesCompleted;

  private double averageDuration;

  private double averageGuessCount;

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public int getLength() {
    return length;
  }

  public int getGamesCompleted() {
    return gamesCompleted;
  }

  public double getAverageDuration() {
    return averageDuration;
  }

  public double getAverageGuessCount() {
    return averageGuessCount;
  }

  @JsonProperty("user")
  public User getPublicUser() {
    return user.isIncognito() ? null : user;
  }

}
