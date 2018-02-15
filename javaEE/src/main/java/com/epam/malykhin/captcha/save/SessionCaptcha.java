package com.epam.malykhin.captcha.save;

import javax.servlet.http.HttpSession;


public class SessionCaptcha extends AbstractCaptcha {

    @Override
    public void save(Integer idCaptcha) {
        HttpSession session = request.getSession();
        session.setAttribute(ID_CAPTCHA, idCaptcha);
    }

    @Override
    public Integer getIdCaptcha() {
        return (Integer) request.getSession().getAttribute(ID_CAPTCHA);
    }
}
