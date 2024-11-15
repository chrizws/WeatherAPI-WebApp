package dev.cjsun.weatherapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
