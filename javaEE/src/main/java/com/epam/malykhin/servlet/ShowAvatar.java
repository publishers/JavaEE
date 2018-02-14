package com.epam.malykhin.servlet;

import com.epam.malykhin.database.entity.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.epam.malykhin.util.Paths.get;
import static com.epam.malykhin.util.StaticTransformVariable.USER_SESSION;

/**
 * Created by Serhii_Malykhin on 12/9/2016.
 */
@WebServlet("/avatar")
public class ShowAvatar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_SESSION);
        if (user == null) {
            response.sendError(404, "Avatar Not found");
            return;
        }
        showAvatar(response, user);
    }

    private void showAvatar(HttpServletResponse response, User user) throws IOException {
        setResponseHeaders(response);
        String pathToWeb = getServletContext().getRealPath(File.separator);
        File f = new File(get(pathToWeb, user.getEmail(), true));
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
