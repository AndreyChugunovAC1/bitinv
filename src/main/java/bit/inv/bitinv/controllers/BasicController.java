package bit.inv.bitinv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BasicController {
  @GetMapping("/")
  public String getStart() {
    return "redirect:/login";
  }
}
