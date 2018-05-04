package com.epam.controller;

import com.epam.bean.BeanForm;
import com.epam.captcha.EpamCaptcha;
import com.epam.captcha.MapCaptchas;
import com.epam.captcha.save.Captcha;
import com.epam.controller.pages.Pages;
import com.epam.database.entity.User;
import com.epam.database.exception.BusinessException;
import com.epam.io.LoadImage;
import com.epam.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static com.epam.util.StaticTransformVariable.FORM_FIELD_CAPTCHA;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_EMAIL;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_FIRST_NAME;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_PASSWORD;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_SECOND_NAME;
import static com.epam.util.StaticTransformVariable.INCORRECT_REGISTRATION;
import static com.epam.util.StaticTransformVariable.MAP_CAPTCHA;
import static com.epam.util.StaticTransformVariable.NEWSLETTER;
import static com.epam.util.StaticTransformVariable.SAVER_CAPTCHA;
import static com.epam.util.StaticTransformVariable.USER_SESSION;


@Controller
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)// 50MB
public class Registration {
    private static final Logger LOG = Logger.getLogger(Registration.class);
    @Autowired
    private UserService userService;

    @Autowired
    private LoadImage loadImage;

    @Autowired
    @Qualifier("registrationFormValidator")
    private Validator filterValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(filterValidator);
    }

    @GetMapping("/registration")
    protected String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserSessionExist(request)) {
            return "redirect:/" + Pages.INDEX;
        }
        createNewCaptcha(request, response);
        return Pages.REGISTRATION;
    }

    private void createNewCaptcha(HttpServletRequest request, HttpServletResponse response) {
        ServletContext context = request.getServletContext();
        Captcha captcha = getCaptcha(context);
        captcha.setRequest(request);
        captcha.setResponse(response);

        Integer newCaptcha = new Random().nextInt(1000000);
        captcha.save(saveCaptcha(context, newCaptcha));
    }

    private int saveCaptcha(ServletContext application, Integer token) {
        Object obj = application.getAttribute(MAP_CAPTCHA);
        MapCaptchas mapCaptchas = null;
        if (obj instanceof MapCaptchas) {
            mapCaptchas = (MapCaptchas) obj;
        }
        if (mapCaptchas != null) {
            return mapCaptchas.addCaptcha(new EpamCaptcha(token, System.currentTimeMillis()));
        }
        return -1;
    }

    @PostMapping("/registration")
    protected String postRegistration(@Valid BeanForm beanForm,
                                      BindingResult result,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = request.getRequestURI();
        if (!result.hasErrors() && isValidCaptcha(request, beanForm)) {
            User user = extractUser(beanForm);
            try {
                if (userService.selectUserByEmail(user.getEmail()) == null) {
                    if (userService.insert(user)) {
                        loadImage.executor(request, user);
                        response.encodeRedirectURL(Pages.SERVLET_ACCOUNT);
                        redirectPage = Pages.SERVLET_ACCOUNT;
                    }
                } else {
                    request.getSession().setAttribute(INCORRECT_REGISTRATION, "This email is registered");
                }
            } catch (BusinessException ex) {
                LOG.warn("Something was wrong: \n" + ex);
            }
        }
        return "/"+redirectPage;
    }

    private boolean isUserSessionExist(HttpServletRequest request) {
        boolean result = false;
        HttpSession sessionUser = request.getSession();
        if (sessionUser.getAttribute(USER_SESSION) != null) {
            result = true;
        }
        return result;
    }

    private Captcha getCaptcha(ServletContext servletContext) {
        return (Captcha) servletContext.getAttribute(SAVER_CAPTCHA);
    }

    private boolean isValidCaptcha(HttpServletRequest request, BeanForm beanForm) {
        boolean result = false;
        Captcha captcha = getCaptcha(request.getServletContext());
        captcha.setRequest(request);

        EpamCaptcha epamCaptcha = captcha.getCaptchaById();
        if (epamCaptcha != null) {
            result = epamCaptcha.getCaptcha() == getUserAnswerCaptcha(beanForm);
        }
        if (!result) {
            request.getSession().setAttribute("captchaValid", "Invalid value of captcha");
        }
        return result;
    }

    private int getUserAnswerCaptcha(BeanForm beanForm) {
        return Integer.parseInt(beanForm.getCaptcha());
    }

    private User extractUser(BeanForm beanForm) {
        User user = new User();
        user.setFirstName(beanForm.getFirstName());
        user.setSecondName(beanForm.getSecondName());
        user.setEmail(beanForm.getEmail());
        user.setPassword(beanForm.getPassword());
        boolean newsletter = beanForm.getNewletter().equals("on");
        user.setNewsletter(newsletter);
        return user;
    }

}
