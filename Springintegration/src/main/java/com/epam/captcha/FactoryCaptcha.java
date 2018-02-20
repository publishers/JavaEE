package com.epam.captcha;

import com.epam.captcha.save.Captcha;
import com.epam.captcha.save.CookieCaptcha;
import com.epam.captcha.save.HiddenFieldCaptcha;
import com.epam.captcha.save.SessionCaptcha;
import org.springframework.stereotype.Component;

@Component
public class FactoryCaptcha {
    private String captcha;

    public Captcha getCaptcha() {
        Captcha captcha;
        switch (this.captcha) {
            case "hidden":
                captcha = new HiddenFieldCaptcha();
                break;
            case "cookie":
                captcha = new CookieCaptcha();
                break;
            default:
                captcha = new SessionCaptcha();
                break;
        }
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
