package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

<<<<<<< HEAD
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
=======
    @GetMapping("/")
    public String homeTest() {
        return "index";
    }
>>>>>>> 9767b82f80fdf2faaf74f89e7c13c3663c7ebe71
}
