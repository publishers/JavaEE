package com.epam.captcha.save;

import com.epam.captcha.EpamCaptcha;
import com.epam.captcha.MapCaptchas;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(ID_CAPTCHA)) {
                idCaptcha = Integer.parseInt(cookie.getValue());
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
