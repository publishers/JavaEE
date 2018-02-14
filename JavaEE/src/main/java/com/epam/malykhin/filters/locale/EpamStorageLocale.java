package com.epam.malykhin.filters.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Serhii_Malykhin on 12/23/2016.
 */
public interface EpamStorageLocale {
    String SESSION_LOCALE = "sessionLocale";

    Locale getLocale(HttpServletRequest request, HttpServletResponse response);

    void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale);
}
