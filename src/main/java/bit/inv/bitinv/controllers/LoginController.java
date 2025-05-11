package bit.inv.bitinv.controllers;

import java.io.StringReader;
import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import bit.inv.bitinv.database.Database;
import bit.inv.bitinv.database.entities.User;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class LoginController {
  @Autowired
  private Database database;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  // @GetMapping("/login/check-user")
  // @ResponseBody
  // public ResponseEntity<String> checkIfUserExist(@RequestParam String login) {
  //   if (login == null || login.isBlank()) {
  //     return ResponseEntity.badRequest().body("Expected non-empty login");
  //   }
  //   boolean exist = database.userExist(login);
  //   return ResponseEntity.ok(exist ? "OK" : "User not found");
  // }

  @PostMapping("/login/auth")
  public Object getLogIn(@RequestParam String login, @RequestParam String pass, Model model) {
    if (login == null || login.isBlank() || pass == null || pass.isBlank()) {
      return ResponseEntity.badRequest().body("Login and password can not be empty");
    }

    User user = database.getUserByLogin(login);
    if (user == null) {
      return ResponseEntity.badRequest().body("User not found");
    }

    if (passwordEncoder.matches(pass, user.passHash())) {
      model.addAttribute("name", user.login());
      model.addAttribute("value", database.getUserBits(user.id()).getFirst().value() ? 1 : 0);
      return "home";
    }
    return ResponseEntity.badRequest().body("Incorrect password");
  }

  @GetMapping("/login")
  public String getLogin() {
    return "login";
  }

  @GetMapping("/")
  public String getStart() {
    return "redirect:/login";
  }
}