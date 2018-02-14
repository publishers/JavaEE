package com.epam.malykhin.filters;

import com.epam.malykhin.filters.locale.EpamStorageLocale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

import static com.epam.malykhin.util.StaticTransformVariable.TYPE_LOCALE_STORAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.Mock;

/**
 * Created by Serhii Malykhin on 27.12.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest {
    @Mock
    private EpamStorageLocale epamStorageLocale;
    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private Enumeration<Locale> browserLocales;
    @InjectMocks
    private LocaleFilter localeFilter;

    @Test
    public void shouldReturnDefaultLocaleWhenItFirstVisitToSite() throws IOException, ServletException {
        Locale expected = new Locale("en");
        when(filterConfig.getInitParameter("locales")).thenReturn("ru en");
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(TYPE_LOCALE_STORAGE)).thenReturn(epamStorageLocale);
        when(request.getParameter("lang")).thenReturn(null);
        when(request.getLocales()).thenReturn(browserLocales);
        when(browserLocales.hasMoreElements()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(browserLocales.nextElement()).thenReturn(new Locale("en")).thenReturn(new Locale("ru"));
        when(epamStorageLocale.getLocale(request, response)).thenReturn(null);
        localeFilter.init(filterConfig);
        localeFilter.doFilter(request, response, chain);
        verify(request).setAttribute("lang", expected);
    }

    @Test
    public void shouldReturnUserLocaleWhenUserUpdatePageWithLocaleSet() throws IOException, ServletException {
        Locale expected = new Locale("en");
        when(filterConfig.getInitParameter("locales")).thenReturn("ru en");
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(TYPE_LOCALE_STORAGE)).thenReturn(epamStorageLocale);
        when(request.getParameter("lang")).thenReturn(null);
        when(request.getLocales()).thenReturn(browserLocales);
        when(browserLocales.hasMoreElements()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(browserLocales.nextElement()).thenReturn(new Locale("ru")).thenReturn(new Locale("en"));
        when(epamStorageLocale.getLocale(request, response)).thenReturn(new Locale("en"));
        localeFilter.init(filterConfig);
        localeFilter.doFilter(request, response, chain);
        verify(request).setAttribute("lang", expected);
    }

    @Test
    public void shouldReturnUserLocaleWhenUserSetNewExistLocale() throws IOException, ServletException {
        Locale expected = new Locale("ru");
        when(filterConfig.getInitParameter("locales")).thenReturn("ru en");
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(TYPE_LOCALE_STORAGE)).thenReturn(epamStorageLocale);
        when(request.getParameter("lang")).thenReturn("ru");
        when(request.getLocales()).thenReturn(browserLocales);
        when(browserLocales.hasMoreElements()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(browserLocales.nextElement()).thenReturn(new Locale("ru")).thenReturn(new Locale("en"));
        when(epamStorageLocale.getLocale(request, response)).thenReturn(new Locale("en"));
        localeFilter.init(filterConfig);
        localeFilter.doFilter(request, response, chain);
        verify(request).setAttribute("lang", expected);
    }

    @Test
    public void shouldReturnFirstGoodsLocaleForBrowserWhenUserSetNotExistLocale() throws IOException, ServletException {
        Locale expected = new Locale("ru");
        when(filterConfig.getInitParameter("locales")).thenReturn("ru en");
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(TYPE_LOCALE_STORAGE)).thenReturn(epamStorageLocale);
        when(request.getParameter("lang")).thenReturn("tre");
        when(request.getLocales()).thenReturn(browserLocales);
        when(browserLocales.hasMoreElements()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(browserLocales.nextElement()).thenReturn(new Locale("ru")).thenReturn(new Locale("en"));
        when(epamStorageLocale.getLocale(request, response)).thenReturn(new Locale("en"));
        localeFilter.init(filterConfig);
        localeFilter.doFilter(request, response, chain);
        verify(request).setAttribute("lang", expected);
    }

    @Test
    public void shouldReturnDefaultLocaleApplicationWhenBrowserDoesntSupportUserLocaleSet() throws IOException, ServletException {
        Locale expected = new Locale("en");
        when(filterConfig.getInitParameter("locales")).thenReturn("ru en");
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(TYPE_LOCALE_STORAGE)).thenReturn(epamStorageLocale);
        when(request.getParameter("lang")).thenReturn("tre");
        when(request.getLocales()).thenReturn(browserLocales);
        when(browserLocales.hasMoreElements()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(browserLocales.nextElement()).thenReturn(new Locale("uk")).thenReturn(new Locale("fr"));
        when(epamStorageLocale.getLocale(request, response)).thenReturn(new Locale("en"));
        localeFilter.init(filterConfig);
        localeFilter.doFilter(request, response, chain);
        verify(request).setAttribute("lang", expected);
    }
}