package bit.inv.bitinv.database.entities;

public record UserData(long id, String login, String passHash, boolean value) {
}
