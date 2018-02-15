package com.epam.malykhin.captcha.save;

import javax.servlet.http.Cookie;

public class CookieCaptcha extends AbstractCaptcha {
    @Override
    public void save(Integer idCaptcha) {
        response.addCookie(new Cookie(ID_CAPTCHA, idCaptcha.toString()));
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
}
