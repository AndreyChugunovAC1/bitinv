package bit.inv.bitinv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import bit.inv.bitinv.controllers.responce.ResponceToken;
import bit.inv.bitinv.database.DatabaseDAO;
import bit.inv.bitinv.database.entities.UserData;
import bit.inv.bitinv.utils.TokenProvider;

@Controller
public class LoginController {
  @Autowired
  private DatabaseDAO database;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private TokenProvider tokenProvider;

  @PostMapping("/login/auth")
  public ResponseEntity<ResponceToken> getLogIn(@RequestParam String login, @RequestParam String pass, Model model) {
    if (login == null || login.isBlank() || pass == null || pass.isBlank()) {
      return ResponseEntity.badRequest()
          .body(new ResponceToken("", "Expected non-empty login and password"));
    }

    UserData user = database.getUserDataByLogin(login);

    if (user == null) {
      return ResponseEntity.badRequest().body(new ResponceToken("", "User not found"));
    }

    if (passwordEncoder.matches(pass, user.passHash())) {
      return ResponseEntity.ok(new ResponceToken(tokenProvider.createToken(login), "OK"));
    }
    return ResponseEntity.badRequest().body(new ResponceToken("", "Incorrect password"));
  }

  @GetMapping("/login")
  public String getLogin() {
    return "login";
  }

  @GetMapping
  public String getStart() {
    return "redirect:/login";
  }
}