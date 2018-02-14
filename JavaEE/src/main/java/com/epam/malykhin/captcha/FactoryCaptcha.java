package com.epam.malykhin.captcha;

import com.epam.malykhin.captcha.save.Captcha;
import com.epam.malykhin.captcha.save.CookieCaptcha;
import com.epam.malykhin.captcha.save.HiddenFieldCaptcha;
import com.epam.malykhin.captcha.save.SessionCaptcha;

/**
 * Created by Serhii_Malykhin on 04.12.16.
 */
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
