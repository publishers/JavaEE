package com.epam.malykhin.io;

import com.epam.malykhin.database.JdbcConnectionHolder;
import com.epam.malykhin.database.entity.User;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.epam.malykhin.util.Paths.get;
import static com.epam.malykhin.util.Paths.getEXTENSION;

/**
 * Created by Serhii_Malykhin on 12/9/2016.
 */
public class LoadImage {
    private static final Logger LOGGER = Logger.getLogger(JdbcConnectionHolder.class);

    public void executor(HttpServletRequest request, User user) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String pathToWeb = request.getServletContext().getRealPath(File.separator);
        uploadAvatar(filePart, user.getEmail(), pathToWeb);
    }

    public void uploadAvatar(Part part, String outputFileName, String pathToWeb) throws IOException {
        BufferedImage image = ImageIO.read(part.getInputStream());
        if (image != null) {
            try (FileOutputStream fileOutput = new FileOutputStream(get(pathToWeb, outputFileName, false))) {
                ImageIO.write(image, getEXTENSION(), fileOutput);
            }
        }
    }
}
