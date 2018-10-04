package it.app2ed;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class Main {
    private static final String URL = "https://www.getpostman.com";
    private static final String GET = "GET";
    private static final String CHARSET = "UTF-8";


    public static void main(String[] args) {
        try {
            takeDataStudent(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> readFromStream(InputStream inputStream) throws IOException {
        List<String>output= new ArrayList<>();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(CHARSET));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.add(line);
                line = reader.readLine();
            }
        }
        return output;
    }
    public static List<String> takeDataStudent(String requestUrl) throws Exception {
        final StringBuilder stringBuilder = new StringBuilder(URL);
        HttpURLConnection httpURLConnection = null;
        List<String> result = null;
        try {
            final URL requestURL = new URL(stringBuilder.toString());
            httpURLConnection = (HttpURLConnection) requestURL.openConnection();
            httpURLConnection.setRequestMethod(GET);
            final int httpResponseCode = httpURLConnection.getResponseCode();
            InputStream inputStream;
            if (httpResponseCode >= HttpURLConnection.HTTP_OK && httpResponseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                inputStream = httpURLConnection.getInputStream();

            } else {
                inputStream = httpURLConnection.getErrorStream();

            }
            final String httpResponseMessage = httpURLConnection.getResponseMessage();
            System.out.println("Result from server : " + httpResponseMessage);
            result = readFromStream(inputStream);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return result;
    }
}
