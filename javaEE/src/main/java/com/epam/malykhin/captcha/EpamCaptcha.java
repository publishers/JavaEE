package com.epam.malykhin.captcha;

import java.io.Serializable;

/**
 * Created by Serhii_Malykhin on 04.12.16.
 */
public class EpamCaptcha implements Serializable {
    private int captcha;
    private long time;

    public EpamCaptcha(int captcha, long time) {
        this.captcha = captcha;
        this.time = time;
    }

    public int getCaptcha() {
        return captcha;
    }

    public void setCaptcha(int captcha) {
        this.captcha = captcha;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpamCaptcha that = (EpamCaptcha) o;

        return captcha == that.captcha;
    }

    @Override
    public int hashCode() {
        return (captcha ^ (captcha >>> 32));
    }
}
