package com.rkd.binance.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureUtil {

    static final String HMAC_SHA256 = "HmacSHA256";

    /**
     *
     * @param data
     * @param key
     * @return
     */
    public static String getSignature(String data, String key) {

        byte[] hmacSha256 = null;

        try {

            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA256);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(data.getBytes());

        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }

        return bytesToHex(hmacSha256);
    }

    /**
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {

        final char[] hexArray = "0123456789abcdef".toCharArray();

        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}
