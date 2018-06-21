package com.hariofspades.dagger2advanced.presenter;

import android.util.Log;

import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.model.Address;
import com.hariofspades.dagger2advanced.model.ApiResponse;
import com.hariofspades.dagger2advanced.model.PersonalDetails;
import com.hariofspades.dagger2advanced.model.Phone;
import com.hariofspades.dagger2advanced.model.UserDetails;
import com.hariofspades.dagger2advanced.network.NetworkManager;
import com.hariofspades.dagger2advanced.security.AESAlgorithm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainPresenter {
    private NetworkManager mNetworkManager;
    private Retrofit retrofit;
    private RandomUsersApi mRandomUsersApi;

    @Inject
    public MainPresenter(NetworkManager networkManager, Retrofit retrofit, RandomUsersApi randomUsersApi) {
        this.mNetworkManager = networkManager;
        this.retrofit = retrofit;
        this.mRandomUsersApi = randomUsersApi;
    }

    public String sayHello() {
        return "Hello";
    }

    public void callJoinApi(String userName, String password) {
        Long timeStamp = System.currentTimeMillis() / 1000;
        String data = "{\"UserName\":\"" + userName + "\",\"Password\":\"" + password + "\",\"Timestamp\":" + timeStamp + "}";
        String encryptedData = AESAlgorithm.encrypt(data);

        Map<String, String> header = mNetworkManager.getBasicHeader();
        header.put("oauth_timestamp", String.valueOf(timeStamp));
        encryptedData = removeSubString(encryptedData, "\n");
        header.put("data", encryptedData);
        String oauthSignature = "";
        List<String> values = new ArrayList<>();
        try {
            values.add("POST");
            values.add(URLEncoder.encode("https://test.mcm.simplicitycrm.com/services/mobileapi/201605/account/join", "UTF-8"));
            for (Map.Entry entry : header.entrySet()) {
                values.add(URLEncoder.encode(entry.getKey() + "=" + entry.getValue(), "UTF-8"));
            }
            String signatureValue = mNetworkManager.joinStringListWithAmpersand(values);
            oauthSignature = mNetworkManager.generateHMACSHA256EncodedHash(signatureValue);
            oauthSignature = removeSubString(oauthSignature, "\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("OAuth ");
        for (Map.Entry entry : header.entrySet()) {
            headerBuilder.append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"").append(",");
        }
        headerBuilder.append("oauth_signature").append("=").append("\"").append(oauthSignature).append("\"");
        header.clear();
        header.put("Authorization", headerBuilder.toString());
        header.put("Accept", "application/json");

        Call<ApiResponse> response = mRandomUsersApi.join(header, getDummyUserDetails());
        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d(MainPresenter.class.getSimpleName(), String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(MainPresenter.class.getSimpleName(), "error");
            }
        });
    }

    private UserDetails getDummyUserDetails() {
        return new UserDetails("D93CE404-D287-4A01-BB26-411469B3AF36", new PersonalDetails("1968-01-01T00:00:00+13:00",
                new Address("Rosedale", "New Zealand", "Auckland", "0632", "14 Triton Drive", ""),
                "Randall", new Phone("9", "+64", "9265400"),
                "Mr", "Steven", "abcdefgh@simplicitycrm.com"));

    }

    public RandomUsersApi getRandomUserService() {
        return retrofit.create(RandomUsersApi.class);
    }

    private String removeSubString(String input, String subString) {
        return input.replace(subString, "");
    }
}
