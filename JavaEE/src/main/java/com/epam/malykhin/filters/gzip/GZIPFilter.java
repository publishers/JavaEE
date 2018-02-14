package com.epam.malykhin.filters.gzip;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class GZIPFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(GZIPFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String acceptGZIP = httpRequest.getHeader("Accept-encoding");

        if (!isNull(acceptGZIP) && acceptGZIP.contains("gzip")) {
            GZIPHttpResponseWrapper gzipResponse = getWrapperResponse(httpResponse);
            chain.doFilter(httpRequest, gzipResponse);
            gzipResponse.finishResponse();
            return;
        }
        chain.doFilter(httpRequest, httpResponse);

    }

    private GZIPHttpResponseWrapper getWrapperResponse(HttpServletResponse httpResponse) {
        return new GZIPHttpResponseWrapper(httpResponse);
    }

    public void init(FilterConfig filterConfig) {
        LOG.trace("GZIPFilter init(finlterConfig)");
    }

    public void destroy() {
        LOG.trace("GZIPFilter destroy()");
    }
}