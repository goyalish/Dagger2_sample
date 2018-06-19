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
import java.util.Calendar;
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
        String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
        // UserCredentials userCredentials = new UserCredentials(userName, password, timeStamp);
        String data = "{\"UserName\":\"" + userName + "\",\"Password\":\"" + password + "\",\"Timestamp\":" + timeStamp + "}";
        //data = "{\"UserName\":\"test.user@simplicitycrm.com\",\"Password\":\"P@ssW0rd\",\"Timestamp\":1528781748}";
        String encryptedData = AESAlgorithm.encrypt(data);

        Map<String, String> header = mNetworkManager.getBasicHeader();
        header.put("oauth_timestamp", timeStamp);
        encryptedData = removeSubString(encryptedData, "\n");
        header.put("data", encryptedData);
        //header.put("data", "kymaM4GCEzwZTW9bub1Hn93L9p0L4bytgr3GLnlYx6yNMiWn1etwf6pFSb8l0Dec2ZjibngETSQ/ip1g32gLF3dkYBMs6lMx9qGw2JKDJYn9eb00TmUFLTJurE1thDa7/Lbq37/Tb5tBqq0mf/WhDg==");
        String oauthSignature = "";
        List<String> values = new ArrayList<>();
        try {
            values.add("POST");
            //values.add(URLEncoder.encode("https://test.demoloyalty.simplicitycrm.com/services/mobileapi/201605/oauth/token", "UTF-8"));
            values.add(URLEncoder.encode(mNetworkManager.baseUrl + "oauth/token", "UTF-8"));
            for (Map.Entry entry : header.entrySet()) {
                values.add(URLEncoder.encode(entry.getKey() + "=" + entry.getValue(), "UTF-8"));
            }
            String signatureValue = mNetworkManager.joinStringListWithAmpersand(values);
            oauthSignature = mNetworkManager.generateHMACSHA256EncodedHash(signatureValue);
            oauthSignature = removeSubString(oauthSignature, "\n");
            //header.put("oauth_signature", oauthSignature);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("OAuth ");
        for (Map.Entry entry : header.entrySet()) {
            headerBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        headerBuilder.append("oauth_signature").append("=").append(oauthSignature);
        header.clear();
        String testString = "OAuth oauth_signature_method=\"HMAC-SHA256\",oauth_timestamp=\"1528783414\",oauth_consumer_key=\"pjdrOnv4F06D3IPOmnP4AA\",device=\"TEST_DEVICE_ID\",data=\"kymaM4GCEzwZTW9bub1Hn93L9p0L4bytgr3GLnlYx6yNMiWn1etwf6pFSb8l0Dec2ZjibngETSQ/ip1g32gLF3dkYBMs6lMx9qGw2JKDJYlfxSs98/l0aBzg/haj/LlT/oFaDAakbxDZqT8D9aZaYQ==\",oauth_signature=\"OPAElpA+qrk7UWx5AFKSI2CTY40KZAgNnRWaAS/eLEc=\"";// "OAuth " + headerBuilder.toString();
        header.put("Authorization", headerBuilder.toString());
        /*header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Host", "test.demoloyalty.simplicitycrm.com");
        header.put("Content-Length", String.valueOf(0));
        header.put("Connection", "Keep-Alive");*/

        Call<Object> response = mRandomUsersApi.getToken(header);
        response.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(MainPresenter.class.getSimpleName(), String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e(MainPresenter.class.getSimpleName(), "error");
            }
        });
    }

    private UserDetails getDummyUserDetails() {
        return new UserDetails("", new PersonalDetails("11/11/1990",
                new Address("Suburb", "country", "city", "postal", "l1", "l2"),
                "FamilyName", new Phone("STD", "IDD", "local"),
                "Title", "GivenName", "test.user2@simplicitycrm.com"));

    }

    public RandomUsersApi getRandomUserService() {
        return retrofit.create(RandomUsersApi.class);
    }

    private String removeSubString(String input, String subString) {
        return input.replace(subString, "");
    }
}
