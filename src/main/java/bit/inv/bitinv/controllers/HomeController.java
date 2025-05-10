package bit.inv.bitinv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bit.inv.bitinv.database.Database;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
  @Autowired
  private Database database;

  @GetMapping("/")
  public String getHomePage(Model model) {
    Integer value = database.getUserBit(0) ? 1 : 0;

    model.addAttribute("value", value);
    model.addAttribute("name", "Invy");
    return "home";
  }
}
