package com.wakacommerce.common.file.service;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @ hui
 */
public class GloballySharedInputStream extends InputStream {

    private InputStream parentInputStream;

    public GloballySharedInputStream(InputStream parentInputStream) {
        this.parentInputStream = parentInputStream;
    }

    public int available() throws IOException {
        return parentInputStream.available();
    }

    public void close() throws IOException {
        parentInputStream.close();
    }

    public void mark(int arg0) {
        parentInputStream.mark(arg0);
    }

    public boolean markSupported() {
        return parentInputStream.markSupported();
    }

    public int read() throws IOException {
        return parentInputStream.read();
    }

    public int read(byte[] arg0, int arg1, int arg2) throws IOException {
        return parentInputStream.read(arg0, arg1, arg2);
    }

    public int read(byte[] arg0) throws IOException {
        return parentInputStream.read(arg0);
    }

    public void reset() throws IOException {
        parentInputStream.reset();
    }

    public long skip(long arg0) throws IOException {
        return parentInputStream.skip(arg0);
    }

}
