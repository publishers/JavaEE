package com.epam.controller;

import com.epam.bean.BeanForm;
import com.epam.bean.validation.ValidatorForm;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static com.epam.util.StaticTransformVariable.COM_EPAM_MALYKHIN_BEAN_FORM;
import static com.epam.util.StaticTransformVariable.COM_EPAM_MALYKHIN_VALID_FIELDS;
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


@WebServlet("/registration")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)// 50MB
public class Registration {
    private static final Logger LOG = Logger.getLogger(Registration.class);
    @Autowired
    private UserService userService;
    @Autowired
    private LoadImage loadImage;

    @GetMapping("/registration")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserSessionExist(request)) {
            response.sendRedirect(Pages.INDEX);
            return;
        }
        resetRegistrationData(request);
        createNewCaptcha(request, response);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + Pages.REGISTRATION);
        requestDispatcher.forward(request, response);
    }

    @PostMapping("/registration")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BeanForm beanForm = getBeanForm(request);
        ValidatorForm validatorForm = getValidatorForm(beanForm);
        validatorForm.startValidation();
        request.getSession().setAttribute(COM_EPAM_MALYKHIN_BEAN_FORM, beanForm);
        request.getSession().setAttribute(COM_EPAM_MALYKHIN_VALID_FIELDS, validatorForm);

        if (validatorForm.isValidForm() && isValidCaptcha(request, beanForm)) {
            User user = extractUser(beanForm);
            try {
                if (userService.selectUserByEmail(user.getEmail()) == null) {
                    if (userService.insert(user)) {
                        loadImage.executor(request, user);
                        response.encodeRedirectURL(Pages.SERVLET_ACCOUNT);
                        response.sendRedirect(Pages.SERVLET_ACCOUNT);
                        return;
                    }
                } else {
                    request.getSession().setAttribute(INCORRECT_REGISTRATION, "This email is registered");
                }
            } catch (BusinessException ex) {
                LOG.warn("Something was wrong: \n" + ex);
            }
        }
        response.sendRedirect(request.getRequestURI());
    }

    private boolean isUserSessionExist(HttpServletRequest request) {
        boolean result = false;
        HttpSession sessionUser = request.getSession();
        if (sessionUser.getAttribute(USER_SESSION) != null) {
            result = true;
        }
        return result;
    }

    private void createNewCaptcha(HttpServletRequest request, HttpServletResponse response) {
        Captcha captcha = getCaptcha(request);
        captcha.setRequest(request);
        captcha.setResponse(response);

        Integer newCaptcha = new Random().nextInt(1000000);
        captcha.save(saveCaptcha(request, newCaptcha));
    }

    private Captcha getCaptcha(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Object obj = servletContext.getAttribute(SAVER_CAPTCHA);
        return (com.epam.captcha.save.Captcha) obj;
    }

    private int saveCaptcha(HttpServletRequest request, Integer token) {
        ServletContext application = request.getServletContext();
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

    private boolean isValidCaptcha(HttpServletRequest request, BeanForm beanForm) {
        boolean result = false;
        Captcha captcha = getCaptcha(request);
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
        return Integer.parseInt(beanForm.getBeans().get(FORM_FIELD_CAPTCHA));
    }

    private User extractUser(BeanForm beanForm) {
        User user = new User();
        Map<String, String> tmpBeanForm = beanForm.getBeans();
        user.setFirstName(tmpBeanForm.get(FORM_FIELD_FIRST_NAME));
        user.setSecondName(tmpBeanForm.get(FORM_FIELD_SECOND_NAME));
        user.setEmail(tmpBeanForm.get(FORM_FIELD_EMAIL));
        user.setPassword(tmpBeanForm.get(FORM_FIELD_PASSWORD));
        boolean newsletter = tmpBeanForm.get(NEWSLETTER).equals("on");
        user.setNewsletter(newsletter);
        return user;
    }

    private ValidatorForm getValidatorForm(BeanForm beanForm) {
        return new ValidatorForm(beanForm);
    }

    private BeanForm getBeanForm(HttpServletRequest request) {
        return new BeanForm(request);
    }

    private void resetRegistrationData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        resetAttributesSessionRequestToRequestScope(request);
        removeAttributesFromSession(session);
    }

    private void resetAttributesSessionRequestToRequestScope(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        request.setAttribute(INCORRECT_REGISTRATION, sesion.getAttribute(INCORRECT_REGISTRATION));
        request.setAttribute(COM_EPAM_MALYKHIN_BEAN_FORM, sesion.getAttribute(COM_EPAM_MALYKHIN_BEAN_FORM));
        request.setAttribute(COM_EPAM_MALYKHIN_VALID_FIELDS, sesion.getAttribute(COM_EPAM_MALYKHIN_VALID_FIELDS));
        request.setAttribute("captchaValid", sesion.getAttribute("captchaValid"));
    }

    private void removeAttributesFromSession(HttpSession session) {
        session.removeAttribute(INCORRECT_REGISTRATION);
        session.removeAttribute(COM_EPAM_MALYKHIN_BEAN_FORM);
        session.removeAttribute(COM_EPAM_MALYKHIN_VALID_FIELDS);
    }
}
