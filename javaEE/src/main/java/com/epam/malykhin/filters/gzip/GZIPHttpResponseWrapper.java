package com.epam.malykhin.filters.gzip;

import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZIPHttpResponseWrapper extends HttpServletResponseWrapper {
    private static final Logger LOG = Logger.getLogger(GZIPHttpResponseWrapper.class);
    private HttpServletResponse origResponse = null;
    private ServletOutputStream stream = null;
    private PrintWriter writer = null;

    public GZIPHttpResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }

    public void finishResponse() {
        try {
            if (writer != null) {
                writer.close();
            } else {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
            LOG.debug("GZIPHttpResponseWrapper error with close writer or stream: " + e);
        }
    }

    public void flushBuffer() throws IOException {
        stream.flush();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called!");
        }

        if (stream == null)
            stream = createOutputStream();
        return (stream);
    }

    public PrintWriter getWriter() throws IOException {
        if (writer != null) {
            return (writer);
        }

        if (stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }

        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream, "UTF-8"));
        return (writer);
    }

    private ServletOutputStream createOutputStream() throws IOException {
        return new EpamGZIPOutputStream(origResponse);
    }

}