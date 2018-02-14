package com.epam.controller;

import com.epam.controller.pages.Pages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Logout {

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request) {
        logout(request);
        return "redirect:/"+Pages.SERVLET_INDEX;
    }

    private void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}