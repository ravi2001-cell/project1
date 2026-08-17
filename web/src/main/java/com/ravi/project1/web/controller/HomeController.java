package com.ravi.project1.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final RestTemplate rest = new RestTemplate();

    @Value("${api.base-url:http://localhost:8081}")
    private String apiBase;

    @GetMapping("/")
    public String index(Model model) {
        String url = apiBase + "/api/users";
        List<Map> users = rest.getForObject(url, List.class);
        model.addAttribute("users", users);
        return "index";
    }
}
