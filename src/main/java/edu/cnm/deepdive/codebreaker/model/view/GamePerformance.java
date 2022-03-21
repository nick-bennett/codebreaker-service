package edu.cnm.deepdive.codebreaker.model.view;

import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Immutable
@Subselect("SELECT * FROM game_performance")
public class GamePerformance {

  @Id
  @Column(name = "game_id")
  private UUID id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @Temporal(TemporalType.TIMESTAMP)
  private Date created;

  private int poolSize;

  private int length;

  private int duration;

  private int guessCount;

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Date getCreated() {
    return created;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public int getLength() {
    return length;
  }

  public int getDuration() {
    return duration;
  }

  public int getGuessCount() {
    return guessCount;
  }

}
