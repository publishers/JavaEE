package com.epam.malykhin.filters.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Serhii_Malykhin on 12/23/2016.
 */
public class SessionEpamStorageLocale implements EpamStorageLocale {

    @Override
    public Locale getLocale(HttpServletRequest request, HttpServletResponse response) {
        return (Locale) request.getSession().getAttribute(SESSION_LOCALE);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        request.getSession().setAttribute(SESSION_LOCALE, locale);
    }
}
