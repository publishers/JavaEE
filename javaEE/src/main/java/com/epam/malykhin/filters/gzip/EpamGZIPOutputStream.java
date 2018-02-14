package com.epam.malykhin.filters.gzip;

import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import static java.util.Objects.isNull;


/**
 * Created by Serhii Malykhin on 28.12.16.
 */
public class EpamGZIPOutputStream extends ServletOutputStream {
    private static final Logger LOG = Logger.getLogger(EpamGZIPOutputStream.class);
    protected ByteArrayOutputStream baos = null;
    protected GZIPOutputStream gzipstream = null;
    protected boolean closed = false;
    protected HttpServletResponse response = null;
    protected ServletOutputStream output = null;

    public EpamGZIPOutputStream(HttpServletResponse response) throws IOException {
        super();
        closed = false;
        this.response = response;
        this.output = response.getOutputStream();
        baos = new ByteArrayOutputStream();
        gzipstream = new GZIPOutputStream(baos);
    }

    public void close() throws IOException {
        if (closed) {
            throw new IOException("This output stream has already been closed");
        }
        doChoose(() -> gzipstream.finish(), () -> {
        });
        byte[] bytes = baos.toByteArray();

        response.addHeader("Content-Length", Integer.toString(bytes.length));

        doChoose(() -> response.addHeader("Content-Encoding", "gzip"), () -> {
        });
        output.write(bytes);
        output.flush();
        output.close();
        closed = true;
    }

    public void flush() throws IOException {
        if (closed) {
            throw new IOException("Cannot flush a closed output stream");
        }
        doChoose(() -> gzipstream.flush(), () -> output.flush());
    }

    public void write(int b) throws IOException {
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
        doChoose(() -> gzipstream.write((byte) b), () -> output.write(b));
    }

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
        doChoose(() -> gzipstream.write(b, off, len), () -> output.write(b, off, len));
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        LOG.info("EpamGZIPOutputStream setWriteListener(writeListener)");
    }

    private void doChoose(StreamZIPWork streamZIPWork, StreamWork streamWork) throws IOException {
        if (isCorrectContentType()) {
            streamZIPWork.doChoose();
        } else {
            streamWork.doChoose();
        }
    }

    private boolean isCorrectContentType() {
        String contentText = response.getContentType();
        contentText = isNull(contentText) ? "" : contentText;
        return contentText.contains("text");
    }
}