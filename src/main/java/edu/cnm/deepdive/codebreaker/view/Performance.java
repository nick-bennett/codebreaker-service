package edu.cnm.deepdive.codebreaker.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;

/**
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"created", "displayName", "duration", "guessCount"})
public interface Performance {

  Date getCreated();

  String getDisplayName();

  int getDuration();

  int getGuessCount();

}
