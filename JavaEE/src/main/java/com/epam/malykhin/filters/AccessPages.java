package com.epam.malykhin.filters;

import com.epam.malykhin.bean.SecurityData;
import com.epam.malykhin.database.entity.User;
import com.epam.malykhin.servlet.pages.Pages;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.malykhin.util.StaticTransformVariable.USER_SESSION;
import static java.util.Objects.isNull;

/**
 * Created by Serhii_Malykhin on 12/29/2016.
 */
public class AccessPages implements Filter {
    private static final String[] EXCEPTION_PAGES = {"css", "js", "images", "fonts", "WEB-INF"};
    private SecurityData securityData;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        securityData = new SecurityData();
        String accessFile = filterConfig.getInitParameter("accessFile");
        String pathAccessFile = filterConfig.getServletContext().getRealPath("/") + accessFile;
        securityData.parser(pathAccessFile);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        String url = httpRequest.getRequestURI();
        if (!isPagesStatic(url)) {
            startNonStaticFilter(httpRequest, httpResponse, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void startNonStaticFilter(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) throws IOException, ServletException {
        String url = httpRequest.getRequestURI();
        if (securityData.isContainPageInSecurity(url)) {
            checkSecurity(httpRequest, httpResponse, chain);
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    private void checkSecurity(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) throws IOException, ServletException {
        User user = getUser(httpRequest);
        String url = httpRequest.getRequestURI();
        if (isNull(user)) {
            httpResponse.sendRedirect(Pages.SERVLET_ACCOUNT);
            return;
        }
        if (!securityData.checkSecurityRole(url, user)) {
            httpResponse.sendError(403);
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    private boolean isPagesStatic(String url) {
        for (String regexPage : EXCEPTION_PAGES) {
            if (url.contains(regexPage)) {
                return true;
            }
        }
        return false;
    }

    private User getUser(HttpServletRequest httpRequest) {
        User user = (User) httpRequest.getSession().getAttribute(USER_SESSION);
        return user;
    }

    @Override
    public void destroy() {

    }
}
