package com.epam.malykhin.captcha.save;

import com.epam.malykhin.captcha.EpamCaptcha;
import com.epam.malykhin.captcha.MapCaptchas;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Serhii_Malykhin on 04.12.16.
 */
public class CookieCaptcha implements Captcha {
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public void save(Integer idCaptcha) {
        response.addCookie(new Cookie(ID_CAPTCHA, idCaptcha.toString()));
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public EpamCaptcha getCaptchaById() {
        Integer idCaptcha = getIdCaptcha();
        MapCaptchas mapCaptchas = (MapCaptchas) request.getServletContext().getAttribute(MAP_CAPTCHA);
        if (mapCaptchas == null || idCaptcha == null) return null;
        return mapCaptchas.getCaptchaById(idCaptcha);
    }

    @Override
    public Integer getIdCaptcha() {
        Integer idCaptcha = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(ID_CAPTCHA)) {
                idCaptcha = Integer.parseInt(cookies[i].getValue());
                break;
            }
        }
        return idCaptcha;
    }

    @Override
    public boolean isUsed() {
        return true;
    }
}
