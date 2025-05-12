package bit.inv.bitinv.database;

import bit.inv.bitinv.database.entities.*;

public interface DatabaseDAO {
  void updateUser(UserData user);

  UserData getUserDataByLogin(String login);

  void updateUserValueByLogin(String login, boolean newValue);
}
