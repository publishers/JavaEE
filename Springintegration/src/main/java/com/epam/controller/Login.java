package com.epam.controller;

import com.epam.controller.pages.Pages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login {

    @GetMapping("/login")
    protected String login() {
        return Pages.ACCOUNT;
    }
}
