package bit.inv.bitinv.database;

public interface Database {
  void setUserBit(Integer userId, Boolean value);
  Boolean getUserBit(Integer userId);
}
