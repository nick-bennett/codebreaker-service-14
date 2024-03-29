package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.dao.UserRepository;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.view.ScoreSummary;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * This class implements the high-level persistence and business logic for the {@link User} entity.
 */
@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt source) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(source.getSubject(), source.getClaimAsString("name")),
        source.getTokenValue(), grants);
  }

  /**
   * This is the getOrCreate method.
   * @param oauthKey
   * @param displayName
   * @return
   */
  public User getOrCreate(String oauthKey, String displayName) {
    return repository
        .findByOauthKey(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          return repository.save(user);
        });
  }

  public Optional<User> get(UUID id) {
    return repository.findById(id);
  }

  public Optional<User> getByExternalKey(UUID key) {
    return repository.findByExternalKey(key);
  }

  /**
   * 
   * @return
   */
  public Iterable<User> getAll() {
    return repository.getAllByOrderByDisplayNameAsc();
  }

  public User save(User user) {
    return repository.save(user);
  }

  public void delete(User user) {
    repository.delete(user);
  }

  /**
   * Returns the {@link User} instance asspciated with the authenticated sender of the current
   * request.
   *
   * @return
   */
  public User getCurrentUser() {
    return (User) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
  }

  /**
   * Updates the current user records from the provided updated user, and saves the result to the
   * database.
   *
   * @param updatedUser User deserialized from body of request.
   * @param user Current requestor.
   * @return Updated user instance
   */
  public User update(User updatedUser, User user) {
    if (updatedUser.getDisplayName() != null) {
      user.setDisplayName(updatedUser.getDisplayName());
    }
    return save(user);
  }

  public Iterable<ScoreSummary> getRankingStatisticsByGuessCount(int length, int poolSize) {
    return repository.getScoreSummariesOrderByGuessCount(length, poolSize);
  }

  public Iterable<ScoreSummary> getRankingStatisticsByTime(int length, int poolSize) {
    return repository.getScoreSummariesOrderByTime(length, poolSize);
  }

}
