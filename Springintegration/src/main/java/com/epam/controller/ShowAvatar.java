package com.epam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.epam.util.Paths.get;


@Controller
public class ShowAvatar {
    @Autowired
    private ServletContext context;

    @GetMapping("/avatar")
    protected void userAvatar(HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        if (user == null) {
            response.sendError(404, "Avatar Not found");
            return;
        }
        showAvatar(response, user);
    }

    private void showAvatar(HttpServletResponse response, User user) throws IOException {
        setResponseHeaders(response);
        String pathToWeb = context.getRealPath(File.separator);
        File f = new File(get(pathToWeb, user.getUsername(), true));
        BufferedImage read = ImageIO.read(f);
        OutputStream out = response.getOutputStream();
        ImageIO.write(read, "jpg", out);
        out.close();
    }

    protected void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
}
