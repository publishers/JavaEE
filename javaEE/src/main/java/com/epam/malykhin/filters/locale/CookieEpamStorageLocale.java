package com.epam.malykhin.filters.locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static java.util.Objects.isNull;

/**
 * Created by Serhii_Malykhin on 12/23/2016.
 */
public class CookieEpamStorageLocale implements EpamStorageLocale {
    private Integer maxAge;

    @Override
    public Locale getLocale(HttpServletRequest request, HttpServletResponse response) {
        String lang = getLang(request.getCookies());
        return isNull(lang) ? null : new Locale(lang);
    }

    private String getLang(Cookie[] cookies) {
        String lang = null;
        if (!isNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lang")) {
                    lang = cookie.getValue();
                    break;
                }
            }
        }
        return lang;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Cookie cookie = new Cookie("lang", locale.getLanguage());
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}