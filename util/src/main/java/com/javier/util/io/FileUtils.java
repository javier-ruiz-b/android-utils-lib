package com.javier.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by javier on 17.12.15.
 */
public class FileUtils {

    private final static int BUFFER_SIZE = 4096;
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Copies a file with a buffer
     */
    public static void copyFile(String inputPath,
                                String outputPath)
            throws IOException {
        copyFile(new File(inputPath), new File(outputPath));
    }

    public static void copyFile(File inputFile,
                                File outputFile)
            throws IOException {
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);

        copyStreams(fis, fos);

        fis.close();
        fos.close();
    }

    public static void copyStreams(InputStream in,
                                   OutputStream out)
            throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    public static void moveFile(String inputPath, String outputPath) {
        moveFile(new File(inputPath), new File(outputPath));
    }

    /**
     * @param inputFile
     * @param outputFile
     * @return true on success
     */
    public static boolean moveFile(File inputFile, File outputFile) {
        return inputFile.renameTo(outputFile);
    }

    public static String readText(File file)
            throws IOException {
        FileInputStream fis = new FileInputStream(file);
        String result = readText(fis);
        fis.close();

        return result;
    }

    public static String readText(InputStream inputStream)
            throws IOException {
        return readText(inputStream, DEFAULT_ENCODING);
    }

    public static String readText(InputStream inputStream,
                                  String encoding)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, encoding);
        char[] buffer = new char[BUFFER_SIZE];

        int size;
        while((size = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, size);
        }
        reader.close();

        return builder.toString();
    }

    public void writeText(File file, String text) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        writeText(text, fos);
        fos.close();
    }

    public void writeText(String text, OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(text);
        osw.close();
    }

}
