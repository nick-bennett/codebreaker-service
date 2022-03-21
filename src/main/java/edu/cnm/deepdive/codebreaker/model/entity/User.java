package edu.cnm.deepdive.codebreaker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.codebreaker.view.UserView;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    name = "user_profile"
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"id", "created", "displayName", "avatar", "incognito"})
@JsonView(UserView.Private.class)
public class User {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "user_profile_id", updatable = false, nullable = false, columnDefinition = "UUID")
  @JsonIgnore
  private UUID id;

  @NonNull
  @Column(updatable = false, nullable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  @JsonView(UserView.Public.class)
  private UUID externalKey;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false, nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Date created;

  @NonNull
  @Column(updatable = false, nullable = false, unique = true, length = 30)
  @JsonIgnore
  private String oauthKey;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @JsonIgnore
  private Date connected;

  @NonNull
  @Column(nullable = false, unique = true)
  @JsonView(UserView.Public.class)
  private String displayName;

  @Column(length = 255)
  @JsonView(UserView.Public.class)
  private String avatar;

  @Column(nullable = false)
  private boolean incognito;

  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("created DESC")
  @JsonIgnore
  private final List<Game> games = new LinkedList<>();

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

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  @NonNull
  public Date getConnected() {
    return connected;
  }

  public void setConnected(@NonNull Date connected) {
    this.connected = connected;
  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public boolean isIncognito() {
    return incognito;
  }

  public void setIncognito(boolean incognito) {
    this.incognito = incognito;
  }

  @NonNull
  public List<Game> getGames() {
    return games;
  }

  @PrePersist
  private void generateExternalKey() {
    externalKey = UUID.randomUUID();
  }

}
