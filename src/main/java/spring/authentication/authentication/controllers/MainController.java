package spring.authentication.authentication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public String home() {
        return "Hello world";
    }

    @GetMapping("user/profile")
    public String profile() {
        return "My profile";
    }

    @GetMapping("/admin/**")
    public String admin() {
        return "Admin";
    }
}
