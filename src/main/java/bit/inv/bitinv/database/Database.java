package bit.inv.bitinv.database;

import java.util.List;

import bit.inv.bitinv.database.entities.*;

public interface Database {
  public void addUser(String login, String passHash, Boolean value);
  public User getUserByLogin(String login);
  public List<Bit> getUserBits(long userId);
}
