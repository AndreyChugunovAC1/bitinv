package bit.inv.bitinv.database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import bit.inv.bitinv.database.entities.*;
import jakarta.annotation.PostConstruct;

@Component
public class RAMDatabase implements Database {
  private Set<User> users = new HashSet<>();
  private Set<Bit> bits = new HashSet<>();
  private Set<UserHasBit> userHasBits = new HashSet<>();

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @PostConstruct
  private void init() {
    addUser("Andrey", "a", true);
    addUser("Bob", "b", false);
    addUser("George", "g", false);
  }

  @Override
  public void addUser(String login, String pass, Boolean value) {
    userHasBits.add(new UserHasBit((long) userHasBits.size(), (long) users.size(), (long) bits.size()));
    bits.add(new Bit((long) bits.size(), value, (long) users.size()));
    users.add(new User((long) users.size(), login, passwordEncoder.encode(pass)));
  }

  @Override
  public User getUserByLogin(String login) {
    return users.stream().filter(u -> u.login().equals(login)).findAny().orElse(null);
  }

  @Override
  public List<Bit> getUserBits(long userId) {
    return bits.stream().filter(b -> b.owner().equals(userId)).toList();
  }

  /*
   * public List<Bit> getUnownedUserBits(long userId) {
   * var userSBits = getUserBits(userId);
   * 
   * return userHasBits.stream()
   * .filter(uob -> uob.owner().equals(userId))
   * .filter(uob -> userSBits.stream().allMatch(b -> !b.id().equals(uob.bitId())))
   * }
   */
}
