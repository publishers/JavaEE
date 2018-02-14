package com.epam.malykhin.captcha.save;

import com.epam.malykhin.captcha.EpamCaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Serhii_Malykhin on 04.12.16.
 */
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
