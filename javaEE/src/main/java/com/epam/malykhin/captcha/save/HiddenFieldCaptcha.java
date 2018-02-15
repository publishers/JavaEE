package com.epam.malykhin.captcha.save;

public class HiddenFieldCaptcha extends AbstractCaptcha {

    @Override
    public void save(Integer idCaptcha) {
        request.setAttribute(ID_CAPTCHA, idCaptcha);
    }

    @Override
    public Integer getIdCaptcha() {
        String param = request.getParameter(ID_CAPTCHA);
        return param == null ? null :
                (param.matches("\\d+") ? Integer.parseInt(param) : null);
    }
}
