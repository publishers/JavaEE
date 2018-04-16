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
package com.epam.controller;

import com.epam.captcha.MapCaptchas;
import com.epam.captcha.save.Captcha;
import com.github.cage.Cage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.captcha.save.Captcha.MAP_CAPTCHA;
import static com.epam.util.StaticTransformVariable.SAVER_CAPTCHA;

@Controller
public class CaptchaController {
    private static final Cage CAGE = new Cage();

    @GetMapping("/captcha")
    protected void doGet(ServletContext servletContext, HttpServletResponse response)
            throws ServletException, IOException {
        Integer token = getTokenCaptcha(servletContext);
        setResponseHeaders(response);
        CAGE.draw(token.toString(), response.getOutputStream());
    }

    private Integer getTokenCaptcha(ServletContext servletContext) {
        Captcha captcha = (Captcha) servletContext.getAttribute(SAVER_CAPTCHA);
        MapCaptchas mapCaptchas = (MapCaptchas) servletContext.getAttribute(MAP_CAPTCHA);
        if (mapCaptchas == null) {
            return null;
        }
        Integer val = captcha.getIdCaptcha();
        return val != null ? (mapCaptchas.getCaptchaById(val) != null ?
                mapCaptchas.getCaptchaById(val).getCaptcha() : -1) : -1;
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/" + CAGE.getFormat());
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
}
