package com.epam.malykhin.servlet;

import com.epam.malykhin.bean.BeanForm;
import com.epam.malykhin.database.entity.User;
import com.epam.malykhin.service.UserService;
import com.epam.malykhin.service.UserServiceBan;
import com.epam.malykhin.servlet.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.epam.malykhin.util.StaticTransformVariable.*;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 */
@WebServlet("/authorization")
public class Authorization extends HttpServlet {
    private UserService userService;
    private UserServiceBan userServiceBan;

    @Override
    public void init() throws ServletException {
        userServiceBan = (UserServiceBan) getServletContext().getAttribute(USER_SERVICE_BAN);
        userServiceBan.init(getServletContext());
        userService = (UserService) getServletContext().getAttribute(USER_SERVICE);
        userService.init(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String page = Pages.SERVLET_INDEX;
        User user = (User) session.getAttribute(USER_SESSION);
        if (user == null) {
            page = Pages.ACCOUNT;
            request.setAttribute("error Message", "Login or password are wrong!");
        }
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BeanForm beanForm = getBeanForm(request);
        User user = extractUser(beanForm);
        if (!userServiceBan.isUserBan(user) && (user = userService.select(user)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION, user);
        }
        response.sendRedirect(request.getRequestURI());
    }

    private User extractUser(BeanForm beanForm) {
        User user = new User();
        Map<String, String> tmpBeanForm = beanForm.getBeans();
        user.setEmail(tmpBeanForm.get(FORM_FIELD_EMAIL));
        user.setPassword(tmpBeanForm.get(FORM_FIELD_PASSWORD));
        return user;
    }

    private BeanForm getBeanForm(HttpServletRequest request) {
        return new BeanForm(request);
    }
}
