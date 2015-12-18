package com.javier.util.net;//package tv.goalgetter.android.api.core;
//
//import android.content.Context;
//
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.LinkedList;
//import java.util.List;
//
//import tv.goalgetter.android.utilities.FileManager;
//import tv.goalgetter.android.utilities.LogHelper;
//import tv.goalgetter.android.utilities.constants.Network;
//
///**
// * Created by javier on 04.12.15.
// */
//public class HttpClientApache {
//
//    private final static String HEADER_OCTET_STREAM = "application/octet-stream";
//    private final static String HEADER_TEXT_PLAIN = "text/plain";
//    private final static String HEADER_APPLICATION_JSON = "application/json";
//    private final static String HEADER_VIDEO_MP4 = "video/mp4";
//
//
//    private final static String TRANSFER_ENCODING_BINARY = "binary";
//    private final static String TRANSFER_ENCODING_TEXT = "UTF-8";
//
//    public class Response {
//        public boolean success;
//        public String content;
//    }
//
//
//    private String mUrl;
//    private String mBaseAuth;
//
//    private List<Part> mParts = new LinkedList<>();
//
//    private Context mContext; //only for debugging
//
//    public HttpClientApache(Context context, String url, String mBaseAuth) {
//        this.mUrl = url;
//        this.mBaseAuth = mBaseAuth;
//        this.mContext = context;
//    }
//
//    public void addFilePart(String paramName, File data) throws FileNotFoundException {
//        mParts.add(new FilePart(paramName, data));
//    }
//
//    public void addFilePart(String paramName, String fileName, File data) throws FileNotFoundException {
//        FilePart part = new FilePart(paramName, fileName, data);
//        part.setContentType(HEADER_OCTET_STREAM);
////        part.setTransferEncoding(TRANSFER_ENCODING_BINARY);
//        mParts.add(part);
//    }
//
//    public void addVideoPart(String paramName, String fileName, File data) throws FileNotFoundException {
//        FilePart part = new FilePart(paramName, fileName, data);
//        part.setContentType(HEADER_VIDEO_MP4);
////        part.setTransferEncoding(TRANSFER_ENCODING_BINARY);
//        part.setTransferEncoding(null);
//        part.setCharSet(null);
//        mParts.add(part);
//    }
//
//    public void addJsonStringPart(String paramName, String data) throws FileNotFoundException {
//        StringPart part = new StringPart(paramName, data);
//        part.setContentType(HEADER_APPLICATION_JSON);
//        part.setTransferEncoding(TRANSFER_ENCODING_TEXT);
//        mParts.add(part);
//    }
//
//
////    private MultipartEntity getEntity() {
////        Part parts[] = mParts.toArray(new Part[mParts.size()]);
////        mParts.clear();
////        return new MultipartEntity(parts);
////    }
////
////    private String getStringResponse(HttpURLConnection conn) throws IOException {
////        InputStream is = conn.getInputStream();
////        byte[] b1 = new byte[Network.BUFFER_SIZE];
////
////        StringBuffer buffer = new StringBuffer();
////
////        int size = 0;
////        while ((size = is.read(b1)) != -1) {
////            buffer.append(new String(b1, 0, size));
////        }
////        is.close();
////
////        return buffer.toString();
////    }
////
////    private void sendData(HttpURLConnection conn, MultipartEntity reqEntity) throws IOException {
////        OutputStream os = conn.getOutputStream();
////        InputStream is = reqEntity.getContent();
////
////        File outTmp = new File(FileManager.getRootFilesPath(mContext, FileManager.StoreType.STORE_TYPE_EXTERNAL)
////                + "/httpclient.txt");
////        FileOutputStream fosTemp = new FileOutputStream(outTmp);
////
////        byte[] buffer = new byte[Network.BUFFER_SIZE];
////        int size;
////        while ((size = is.read(buffer)) != -1) {
////            os.write(buffer, 0, size);
////            fosTemp.write(buffer, 0, size);
////        }
////        fosTemp.close();
////        os.close();
//    }
//
//    public Response execute() throws IOException {
//        URL url = new URL(mUrl);
//        HttpClient httpclient = new DefaultHttpClient();
//        httpclient.getParams( ).setParameter( CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1 );
//
//        HttpPost httppost = new HttpPost( "https://graph.facebook.com/me/photos" );
//        File file = new File( sdpicturePath );
//
//        // DEBUG
//        Log.d( "TSET", "FILE::" + file.exists( ) ); // IT IS NOT NULL
//        Log.d( "TEST", "AT:" + fbAccessToken ); // I GOT SOME ACCESS TOKEN
//
//        MultipartEntity mpEntity  = new MultipartEntity( );
//        ContentBody cbFile        = new FileBody( file, "image/png" );
//        ContentBody cbMessage     = new StringBody( "TEST TSET" );
//        ContentBody cbAccessToken = new StringBody( fbAccessToken );
//
//        mpEntity.addPart( "access_token", cbAccessToken );
//        mpEntity.addPart( "source",       cbFile        );
//        mpEntity.addPart( "message",      cbMessage     );
//
//        httppost.setEntity( mpEntity );
//
//        // DEBUG
//        System.out.println( "executing request " + httppost.getRequestLine( ) );
//        HttpResponse response = httpclient.execute( httppost );
//        HttpEntity resEntity = response.getEntity( );
//
//        // DEBUG
//        System.out.println( response.getStatusLine( ) );
//        if (resEntity != null) {
//            System.out.println( EntityUtils.toString( resEntity ) );
//        } // end if
//
//        if (resEntity != null) {
//            resEntity.consumeContent( );
//        } // end if
//
//        httpclient.getConnectionManager( ).shutdown( );
//        return response;
//    }
//
//}
