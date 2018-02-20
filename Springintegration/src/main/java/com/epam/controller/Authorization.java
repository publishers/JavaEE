package com.epam.controller;

import com.epam.controller.pages.Pages;
import com.epam.database.entity.User;
import com.epam.service.UserService;
import com.epam.service.UserServiceBan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.util.StaticTransformVariable.USER_SESSION;

@Controller
public class Authorization {
    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceBan userServiceBan;

    @GetMapping("/authorization")
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String page = Pages.SERVLET_INDEX;
        User user = (User) session.getAttribute(USER_SESSION);
        if (user == null) {
            page = Pages.ACCOUNT;
            request.setAttribute("error Message", "Login or password are wrong!");
        }
//        request.getRequestDispatcher(page).forward(request, response);
        return page;
    }

    @PostMapping("/authorization")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        BeanForm beanForm = getBeanForm(request);
//        User user = extractUser(beanForm);
//        if (!userServiceBan.isUserBan(user) && (user = userService.select(user)) != null) {
//            HttpSession session = request.getSession();
//            session.setAttribute(USER_SESSION, user);
//        }
//        response.sendRedirect(request.getRequestURI());
    }

//    private User extractUser(BeanForm beanForm) {
//        User user = new User();
//        Map<String, String> tmpBeanForm = beanForm.getBeans();
//        user.setEmail(tmpBeanForm.get(FORM_FIELD_EMAIL));
//        user.setPassword(tmpBeanForm.get(FORM_FIELD_PASSWORD));
//        return user;
//    }
//
//    private BeanForm getBeanForm(HttpServletRequest request) {
//        return new BeanForm(request);
//    }
}
