package com.epam.malykhin.captcha.save;

import com.epam.malykhin.captcha.EpamCaptcha;
import com.epam.malykhin.captcha.MapCaptchas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractCaptcha implements Captcha {
    protected HttpServletResponse response;
    protected HttpServletRequest request;

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
        if (idCaptcha == null || mapCaptchas == null) return null;
        return mapCaptchas.getCaptchaById(idCaptcha);
    }

    @Override
    public boolean isUsed() {
        return true;
    }
}
