package bit.inv.bitinv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bit.inv.bitinv.database.DatabaseDAO;
import bit.inv.bitinv.database.entities.UserData;
import bit.inv.bitinv.utils.TokenProvider;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/home")
public class HomeController {
  @Autowired
  private DatabaseDAO database;
  @Autowired
  private TokenProvider tokenProvider;

  private void renderHomeForUser(String login, boolean value, Model model) {
    model.addAttribute("name", login);
    model.addAttribute("value", value ? 1 : 0);
  }

  @PostMapping("/change-value")
  public String postChangeValue(@RequestParam String token /* !!!!! */, @RequestParam String login, @RequestParam String newValue, Model model) {
    newValue = newValue.strip().toLowerCase();
    boolean newBool;
    if (newValue.equals("1") || newValue.equals("true")) {
      newBool = true;
    } else if (newValue.equals("0") || newValue.equals("false")) {
      newBool = false;
    } else {
      return "";
    }
    database.updateUserValueByLogin(login, newBool);
    renderHomeForUser(login, newBool, model);
    return "home";
  }

  @GetMapping
  public String getHome(@RequestParam String token, Model model) {
    System.out.println("Здрасьте");
    if (tokenProvider.validateToken(token)) {
      String login = tokenProvider.getLoginFromToken(token);
      UserData user = database.getUserDataByLogin(login);

      renderHomeForUser(user.login(), user.value(), model);
      return "home";
    }
    return "redirect:/error";
  }
}
