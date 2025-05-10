package bit.inv.bitinv.database;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RAMDatabase implements Database {
  private Map<Integer, Boolean> bits;

  @PostConstruct
  private void init() {
    bits = new HashMap<>();
  }

  @Override
  public void setUserBit(Integer userId, Boolean value) {
    bits.put(userId, value);
  }

  @Override
  public Boolean getUserBit(Integer userId) {
    return bits.getOrDefault(userId, false);
  }
}
