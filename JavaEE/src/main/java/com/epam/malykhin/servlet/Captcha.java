/*
 * Copyright 2011 Király Attila
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.epam.malykhin.servlet;

import com.epam.malykhin.captcha.MapCaptchas;
import com.github.cage.Cage;
import com.github.cage.GCage;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.malykhin.util.StaticTransformVariable.MAP_CAPTCHA;
import static com.epam.malykhin.util.StaticTransformVariable.SAVER_CAPTCHA;

/**
 * An example servlet that generates captcha images directly to the response
 * stream.
 * <p>
 * Created by Serhii_Malykhin on 12/7/2016.
 */
@WebServlet("/captcha")
public class Captcha extends HttpServlet {
    private static final Cage cage = new GCage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer token = getTokenCaptcha(request, response);
        if (token == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Captcha not found.");
            return;
        }

        setResponseHeaders(response);
        cage.draw(token.toString(), response.getOutputStream());
    }

    private Integer getTokenCaptcha(HttpServletRequest request, HttpServletResponse response) {
        ServletContext servletContext = request.getServletContext();
        com.epam.malykhin.captcha.save.Captcha captcha =
                (com.epam.malykhin.captcha.save.Captcha) servletContext.getAttribute(SAVER_CAPTCHA);
        captcha.setRequest(request);
        MapCaptchas mapCaptchas = (MapCaptchas) servletContext.getAttribute(MAP_CAPTCHA);
        if (captcha == null || mapCaptchas == null) return null;
        Integer val = captcha.getIdCaptcha();
        return (mapCaptchas == null || val == null) ? null :
                mapCaptchas.getCaptchaById(val) != null ?
                        mapCaptchas.getCaptchaById(val).getCaptcha() : null;
    }

    protected void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/" + cage.getFormat());
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
}
