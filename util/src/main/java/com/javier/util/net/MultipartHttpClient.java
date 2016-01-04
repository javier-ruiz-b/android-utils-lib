package com.javier.util.net;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.MultipartEntity;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


/**
 * Multipart HTTP client. Uses Apache library.
 *
 * Created by javier on 04.12.15.
 */
public class MultipartHttpClient {

    private final static String HEADER_OCTET_STREAM = "application/octet-stream";
    private final static String HEADER_TEXT_PLAIN = "text/plain";
    private final static String HEADER_APPLICATION_JSON = "application/json";
    private final static String HEADER_VIDEO_MP4 = "video/mp4";

    //Encodings
    private final static String TRANSFER_ENCODING_BINARY = "binary";
    private final static String TRANSFER_ENCODING_TEXT = "UTF-8";

    /**
     * In bytes. Progress granularity is also conditioned by this value.
     */
    private final static int BUFFER_SIZE = 2048;

    private final static int CHUNKED_STREAMING_MODE_SIZE = 1024*1024; //1MB

    /**
     * Timeout in milliseconds.
     */
    private final static int TIMEOUT_MS = 4000;

    private String mUrl;
    private String mBaseAuth;

    private List<Part> mParts = new LinkedList<>();

    /**
     * Response structure
     */
    public class Response {
        public boolean success;
        public String content;
    }

    public MultipartHttpClient(String url, String mBaseAuth) {
        this.mUrl = url;
        this.mBaseAuth = mBaseAuth;
    }

    public void addFilePart(String paramName, File data) throws FileNotFoundException {
        mParts.add(new FilePart(paramName, data));
    }

    public void addFilePart(String paramName, String fileName, File data) throws FileNotFoundException {
        FilePart part = new FilePart(paramName, fileName, data);
        part.setContentType(HEADER_OCTET_STREAM);
        mParts.add(part);
    }

    public void addVideoPart(String paramName, String fileName, File data) throws FileNotFoundException {
        FilePart part = new FilePart(paramName, fileName, data);
        part.setContentType(HEADER_VIDEO_MP4);
        part.setTransferEncoding(null);
        part.setCharSet(null);
        mParts.add(part);
    }

    public void addJsonStringPart(String paramName, String data) throws FileNotFoundException {
        StringPart part = new StringPart(paramName, data);
        part.setContentType(HEADER_APPLICATION_JSON);
        part.setTransferEncoding(TRANSFER_ENCODING_TEXT);
        mParts.add(part);
    }


    private MultipartEntity getEntity() {
        Part parts[] = mParts.toArray(new Part[mParts.size()]);
        mParts.clear();
        return new MultipartEntity(parts);
    }

    private String getStringResponse(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        byte[] b1 = new byte[BUFFER_SIZE];

        StringBuffer buffer = new StringBuffer();

        int size = 0;
        while ((size = is.read(b1)) != -1) {
            buffer.append(new String(b1, 0, size));
        }
        is.close();

        return buffer.toString();
    }

    private void sendData(HttpURLConnection conn, MultipartEntity reqEntity) throws IOException {

//        conn.setFixedLengthStreamingMode((int)reqEntity.getContentLength());
        conn.setChunkedStreamingMode(CHUNKED_STREAMING_MODE_SIZE);
        OutputStream os = conn.getOutputStream();
        InputStream is = reqEntity.getContent();

        byte[] buffer = new byte[BUFFER_SIZE];
        int size;
        while ((size = is.read(buffer)) != -1) {
            os.write(buffer, 0, size);
        }
        os.close();
    }

    public Response execute() throws IOException {
        URL url = new URL(mUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(TIMEOUT_MS);
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);

        MultipartEntity reqEntity = getEntity();
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Authorization", mBaseAuth);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("accept", HEADER_APPLICATION_JSON);
        conn.setRequestProperty("Content-Type", reqEntity.getContentType().getValue());
        conn.setRequestProperty("Content-Length", Long.toString(reqEntity.getContentLength()));

//        LogHelper.logEvent(reqEntity.getContentType().toString());
        for (String key :conn.getRequestProperties().keySet()) {
//            LogHelper.logEvent(key + "-" + conn.getRequestProperties().get(key).toString());
        }

        sendData(conn, reqEntity);

        Response response = new Response();
        response.success = (conn.getResponseCode() == HttpURLConnection.HTTP_OK);
        if (response.success) {
            response.content = getStringResponse(conn);
//            LogHelper.logEvent("Response: " + response.content);
        } else {
//            LogHelper.logEvent("Error: " + conn.getResponseMessage()
//                    + "(" + Integer.toString(conn.getResponseCode()) + ")");
        }

        conn.disconnect();
        return response;
    }

}
