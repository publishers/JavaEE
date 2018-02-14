package com.epam.malykhin.util;

import java.io.File;

/**
 * Created by Serhii Malykhin on 13.12.16.
 */
public class Paths {

    private static final String PATH = "avatars";
    private static final String EXTENSION = "JPG";
    private static final String DEFAULT_IMAGE = "defaultImage";

    public static String get(String pathToWeb, String outputFileName, boolean isNeedRead) {
        StringBuilder sb = new StringBuilder();
        sb.append(pathToWeb)
                .append("..").append(File.separator)
                .append("..").append(File.separator)
                .append(PATH);

        if (!isNeedRead && !isExistsPath(sb.toString())) {
            createDirectory(sb.toString());
        }
        sb.append(File.separator).append(outputFileName).append(".").append(EXTENSION);

        if (isNeedRead && !isExistsPath(sb.toString())) {
            sb.delete(0, sb.length());
            sb.append(pathToWeb).append(File.separator).append("images").append(File.separator)
                    .append(DEFAULT_IMAGE).append(".").append(EXTENSION);
        }

        return sb.toString();
    }

    public static String getEXTENSION() {
        return EXTENSION;
    }

    private static boolean isExistsPath(String path) {
        return createFile(path).exists();
    }

    private static boolean createDirectory(String path) {
        return createFile(path).mkdir();
    }

    private static File createFile(String path) {
        return new File(path);
    }
}
