package com.javier.util.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by javier on 17.12.15.
 */
public class NetworkUtils {

    public interface Listener {
        /**
         *
         * @param transferredBytes
         * @param totalBytes might be -1 if the entity doesn't provide contentLength
         */
        void progressUpdate(int transferredBytes, int totalBytes);
    }

    private final static int BUFFER_SIZE = 2048;

    public static void download(URL url,
                                File file)
            throws IOException {
        download(url, file, null);
    }
    public static void download(URL url,
                                File file,
                                Listener listener)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        download(url, fos, listener);
        fos.close();
    }

    public static void download(URL url,
                                OutputStream os,
                                Listener listener)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        int length = conn.getContentLength();
        InputStream is = conn.getInputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int size;
        int transferred = 0;
        while ((size = is.read(buffer)) != -1) {
            os.write(buffer, 0, size);
            if (listener != null) {
                transferred += size;
                listener.progressUpdate(transferred, length);
            }
        }
        is.close();
    }

}
