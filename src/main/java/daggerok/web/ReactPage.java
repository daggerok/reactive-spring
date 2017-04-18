package daggerok.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/react")
public class ReactPage {

  @GetMapping({ "", "/" })
  String index() {
    return "/react/react.html";
  }
}
