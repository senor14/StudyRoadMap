package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/index")
    public String homeTest() {
        return "index";
    }
    @GetMapping("/community")
    public String community() {
        return "/community/community";
    }

    @GetMapping("/comment")
    public String comment() {
        return "/community/comment";
    }
}
