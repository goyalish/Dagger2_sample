package com.hariofspades.dagger2advanced.network;

import android.util.Base64;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

public class NetworkManager {
    //    public final String baseUrl = "https://test.demoloyalty.simplicitycrm.com/services/mobileapi/201605/";
    public final String baseUrl = "https://test.mcm.simplicitycrm.com/services/mobileapi/201605/";
    private final String secret = "i2WzqlmoAkh4vTFZaEnFgsFsVO4Kq8AgLDU2PNCAtQg";
    private final String oauth_consumer_key = "fAycQ9xEH0uobDv2twcclA";

    @Inject
    public NetworkManager() {
    }

    public Map<String, String> getBasicHeader() {
        Map<String, String> header = new LinkedHashMap<>();
        header.put("data", "");
        header.put("device", "TEST_DEVICE_ID");
        header.put("oauth_consumer_key", oauth_consumer_key);
        //header.put("oauth_consumer_key", "pjdrOnv4F06D3IPOmnP4AA");
        header.put("oauth_signature_method", "HMAC-SHA256");
        header.put("oauth_timestamp", "");
        return header;
    }

    public String joinStringListWithAmpersand(List<String> inputList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputList.size(); i++) {
            if (i < inputList.size() - 1)
                stringBuilder.append(inputList.get(i)).append("&");
        }
        stringBuilder.append(inputList.get(inputList.size() - 1));
        return stringBuilder.toString();
    }

    public String generateHMACSHA256EncodedHash(String message) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            hash = Base64.encodeToString(sha256_HMAC.doFinal(message.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(NetworkManager.class.getSimpleName(), e.getMessage());
        }
        return hash;
    }
}
