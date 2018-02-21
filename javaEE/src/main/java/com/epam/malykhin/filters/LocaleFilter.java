package com.epam.malykhin.filters;

import com.epam.malykhin.filters.locale.EpamStorageLocale;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import static com.epam.malykhin.util.StaticTransformVariable.TYPE_LOCALE_STORAGE;
import static java.util.Objects.isNull;


public class LocaleFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(LocaleFilter.class);
    public static final Locale DEFAULT_LOCALE = new Locale("en");
    private EpamStorageLocale epamLocale;
    private List<Locale> locales;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        locales = new ArrayList<>();
        String locales = filterConfig.getInitParameter("locales");
        StringTokenizer tokenLocale = new StringTokenizer(locales, " ");
        this.epamLocale = (EpamStorageLocale) filterConfig.getServletContext().getAttribute(TYPE_LOCALE_STORAGE);
        initLocales(tokenLocale);
    }

    private void initLocales(StringTokenizer tokenizerLocales) {
        while (tokenizerLocales.hasMoreElements()) {
            locales.add(new Locale(tokenizerLocales.nextToken()));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Locale fitLocale = getFitLocale(request, epamLocale.getLocale((HttpServletRequest) request, (HttpServletResponse) response));
        epamLocale.setLocale((HttpServletRequest) request, (HttpServletResponse) response, fitLocale);
        request.setAttribute("lang", fitLocale);
        chain.doFilter(getWrapper((HttpServletRequest) request, fitLocale), response);
    }

    private HttpServletRequestWrapper getWrapper(HttpServletRequest request, final Locale locale) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public Locale getLocale() {
                return locale;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return new Enumeration<Locale>() {
                    private boolean hasNextElement = true;

                    @Override
                    public boolean hasMoreElements() {
                        return hasNextElement;
                    }

                    @Override
                    public Locale nextElement() {
                        hasNextElement = false;
                        return locale;
                    }
                };
            }
        };
    }

    private Locale getFitLocale(ServletRequest request, Locale locale) {
        String lang = request.getParameter("lang");
        if (!isNull(locale) && isNull(lang)) {
            return locale;
        }
        lang = checkUserLang(lang);
        Locale userLocale = new Locale(lang);
        if (!contains(userLocale)) {
            userLocale = checkBrowserLocales((HttpServletRequest) request);
        }
        return checkUserLocale(userLocale);
    }

    private Locale checkUserLocale(Locale userLocale) {
        return isNull(userLocale) ? DEFAULT_LOCALE : userLocale;
    }

    private String checkUserLang(String lang) {
        return isNull(lang) ? "" : lang;
    }

    private Locale checkBrowserLocales(HttpServletRequest request) {
        Enumeration<Locale> localeEnumeration = request.getLocales();
        while (localeEnumeration.hasMoreElements()) {
            Locale nextLocale = localeEnumeration.nextElement();
            if (contains(nextLocale)) {
                return nextLocale;
            }
        }
        return null;
    }

    public boolean contains(Locale locale) {
        return locales.contains(locale);
    }

    @Override
    public void destroy() {
        LOG.info("LocaleFilter destroy()");
    }
}