package com.rkd.binance.util;

import com.rkd.binance.factory.CredentialFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    public static String joinQueryParameters(HashMap<String, String> parameters) {

        String urlPath = "";
        boolean isFirst = true;

        for (Map.Entry mapElement : parameters.entrySet()) {
            if (isFirst) {
                isFirst = false;
                urlPath += (String) mapElement.getKey() + "=" + (String) mapElement.getValue();
            } else {
                urlPath += "&" + (String) mapElement.getKey() + "=" + (String) mapElement.getValue();
            }
        }
        return urlPath;
    }

    public static void sendSignedRequest(HashMap<String, String> parameters, String urlPath, String httpMethod) throws Exception {

        String queryPath = "";
        String signature = "";

        if (!parameters.isEmpty()) {
            queryPath += RequestUtil.joinQueryParameters(parameters) + "&" + getTimeStamp();
        } else {
            queryPath += getTimeStamp();
        }
        try {
            signature = SignatureUtil.getSignature(queryPath, CredentialFactory.getInstance().getSecret());
        } catch (Exception e) {
            System.out.println("Please Ensure Your Secret Key Is Set Up Correctly! " + e);
            System.exit(0);
        }
        queryPath += "&signature=" + signature;

        URL obj = new URL(urlPath + "?" + queryPath);
        System.out.println("url:" + obj.toString());

        send(obj, httpMethod);
    }

    private static String getTimeStamp() {
        long timestamp = System.currentTimeMillis();
        return "timestamp=" + String.valueOf(timestamp);
    }

    private static void send(URL obj, String httpMethod) throws Exception {

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        if (httpMethod != null) {
            con.setRequestMethod(httpMethod);
        }

        con.setRequestProperty("X-MBX-APIKEY", CredentialFactory.getInstance().getKey());

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            printResponse(con);
        } else {
            printError(con);
        }
    }

    private static void printResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }

    private static void printError(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getErrorStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }
}
