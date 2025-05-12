package bit.inv.bitinv.database;

import java.util.*;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import bit.inv.bitinv.database.entities.*;
import jakarta.annotation.PostConstruct;

@Component
public class RAMDatabase implements DatabaseDAO {
  private Map<Long, UserData> users = new HashMap<>();

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @PostConstruct
  private void testInit() {
    addUser("root", "123", false);
    addUser("Ann", "a", true);
    addUser("Bob", "b", false);
    addUser("Chr", "c", true);
  }

  private void addUser(String login, String pass, boolean value) {
    updateUser(new UserData((long) users.size(), login, passwordEncoder.encode(pass), value));
  }

  @Override
  public void updateUser(UserData user) {
    users.put((long) users.size(), user);
  }

  @Override
  public UserData getUserDataByLogin(String login) {
    return users.values().stream().filter(u -> u.login().equals(login)).findAny().orElse(null);
  }

  @Override
  public void updateUserValueByLogin(String login, boolean newValue) {
    UserData user = getUserDataByLogin(login);
    UserData updatedUser = new UserData(user.id(), user.login(), user.passHash(), newValue);

    updateUser(updatedUser);
  }
}
