package com.epam.controller;

import com.epam.bean.BeanForm;
import com.epam.controller.pages.Pages;
import com.epam.database.entity.User;
import com.epam.service.UserService;
import com.epam.service.UserServiceBan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.epam.util.StaticTransformVariable.FORM_FIELD_EMAIL;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_PASSWORD;
import static com.epam.util.StaticTransformVariable.USER_SESSION;

@Controller("/authorization")
public class Authorization {
    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceBan userServiceBan;

    @GetMapping
    protected String getAuthorizationPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String page = Pages.SERVLET_INDEX;
        User user = (User) session.getAttribute(USER_SESSION);
        if (user == null) {
            page = Pages.ACCOUNT;
            request.setAttribute("error Message", "Login or password are wrong!");
        }
        return page;
    }

    @PostMapping
    protected String postAuthorizationPage(@RequestBody Map<String, String> beanForm,
                                           HttpServletRequest request) {
        User user = extractUser(beanForm);
        if (!userServiceBan.isUserBan(user) && (user = userService.select(user)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION, user);
        }
        return "redirect:/" + request.getRequestURI();
    }

    private User extractUser(Map<String, String> beanForm) {
        User user = new User();
        user.setEmail(beanForm.get(FORM_FIELD_EMAIL));
        user.setPassword(beanForm.get(FORM_FIELD_PASSWORD));
        return user;
    }
}
