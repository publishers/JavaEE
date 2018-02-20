package com.epam.captcha.save;

import com.epam.captcha.EpamCaptcha;
import com.epam.captcha.MapCaptchas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionCaptcha implements Captcha {
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public void save(Integer idCaptcha) {
        HttpSession session = request.getSession();
        session.setAttribute(ID_CAPTCHA, idCaptcha);
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
        if (idCaptcha == null || mapCaptchas == null) return null;
        return mapCaptchas.getCaptchaById(idCaptcha);
    }

    @Override
    public Integer getIdCaptcha() {
        return (Integer) request.getSession().getAttribute(ID_CAPTCHA);
    }

    @Override
    public boolean isUsed() {
        return true;
    }
}
