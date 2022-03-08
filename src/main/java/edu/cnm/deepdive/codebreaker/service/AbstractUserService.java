package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.entity.User;

public interface AbstractUserService {

  User getOrCreate(String oauthKey, String displayName);

  User getCurrentUser();

  User updateUser(User received);
}
