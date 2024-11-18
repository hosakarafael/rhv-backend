package com.rafaelhosaka.gateway.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacUtils {
    public static String generateHmacSignature(String originalValue, String secretKey) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);

        byte[] rawHmac = mac.doFinal(originalValue.getBytes());
        return Base64.getEncoder().encodeToString(rawHmac);
    }

    public static boolean verifyHmacSignature(String originalValue, String secretKey, String receivedSignature) throws Exception {
        String computedSignature = HmacUtils.generateHmacSignature(originalValue, secretKey);
        return computedSignature.equals(receivedSignature);
    }
}
