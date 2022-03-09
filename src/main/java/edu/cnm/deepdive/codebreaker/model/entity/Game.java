package edu.cnm.deepdive.codebreaker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"id", "created", "pool", "length", "solved", "text"})
public class Game {

  public static final int MAX_POOL_SIZE = 255;
  public static final int MIN_POOL_SIZE = 1;
  public static final int MAX_CODE_LENGTH = 20;
  public static final int MIN_CODE_LENGTH = 1;

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "game_id", updatable = false, nullable = false, columnDefinition = "UUID")
  @JsonIgnore
  private UUID id;

  @NonNull
  @Column(updatable = false, nullable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private UUID externalKey;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false, nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Date created;

  @NonNull
  @Column(nullable = false, updatable = false, length = MAX_POOL_SIZE)
  @NotNull
  @NotBlank
  @Size(min = MIN_POOL_SIZE, max = MAX_POOL_SIZE)
  private String pool;

  @Column(nullable = false, updatable = false)
  @JsonIgnore
  private int poolSize;

  @Column(nullable = false, updatable = false)
  @Min(MIN_CODE_LENGTH)
  @Max(MAX_CODE_LENGTH)
  private int length;

  @NonNull
  @Column(name = "game_text", nullable = false, updatable = false, length = 20)
  @JsonIgnore
  private String text;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  @JsonIgnore
  private User user;

  @NonNull
  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("created ASC")
  @JsonIgnore
  private final List<Guess> guesses = new LinkedList<>();

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public UUID getExternalKey() {
    return externalKey;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public void setPoolSize(int poolSize) {
    this.poolSize = poolSize;
  }

  @NonNull
  public String getPool() {
    return pool;
  }

  public void setPool(@NonNull String pool) {
    this.pool = pool;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  @NonNull
  public String getText() {
    return text;
  }

  public void setText(@NonNull String text) {
    this.text = text;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  @NonNull
  public List<Guess> getGuesses() {
    return guesses;
  }

  public boolean isSolved() {
    return guesses
        .stream()
        .anyMatch(Guess::isSolution);
  }

  @JsonProperty("text")
  public String getSolution() {
    return isSolved() ? text : null;
  }

  @PrePersist
  private void generateAdditionalFields() {
    externalKey = UUID.randomUUID();
    poolSize = (int) pool
        .codePoints()
        .count();
  }

}
