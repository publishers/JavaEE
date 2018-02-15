package com.epam.malykhin.captcha.save;

import com.epam.malykhin.captcha.EpamCaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Captcha {
    String ID_CAPTCHA = "idTokenCaptcha";
    String MAP_CAPTCHA = "mapCaptcha";

    void setResponse(HttpServletResponse resp);

    void setRequest(HttpServletRequest req);

    void save(Integer idCaptcha);

    EpamCaptcha getCaptchaById();

    Integer getIdCaptcha();

    boolean isUsed();
}
